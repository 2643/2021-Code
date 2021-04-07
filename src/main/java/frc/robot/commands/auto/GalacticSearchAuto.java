package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;

public class GalacticSearchAuto extends CommandBase {
    CommandScheduler schedulerInstance = CommandScheduler.getInstance();
    
    private double leftExcValue = 0;
    private double rightExcValue = 0;

    // MAGIC NUMBERS YAYY!
    private double endForwardSpeed = 0.35; // faster b/c idc.
    private double forwardSpeed = 0.25; // default speed to approach
    private double ECRate = 0.004; //0.2/500 basically P in PID Error Correcting Rate during approach
    private double forwardVal = 12; // in rotations dist to move once "near"
    // private double offsetSpeed = forwardSpeed + 0.2; // magic number
    private double turnSpeed = 0.15; // turning speed when no ball is found 
    
    private long intakeDelay = 0; // hold counter to allow for intake after not "near" 

    private boolean triedIntake = false;

    public GalacticSearchAuto() {
        addRequirements(RobotContainer.drivetrain);
        addRequirements(RobotContainer.intake);
        addRequirements(RobotContainer.conveyorBelt);
    }

    @Override
    public void initialize() {
        System.out.println("init");
        RobotContainer.drivetrain.resetAllEncoder();
    }
    
    @Override
    public void execute() {
        if (Constants.gsBallsHeld < 3) {
            // errors
            leftExcValue = Math.abs(Constants.gsVisionTable.getEntry("left_exceeded").getDouble(0)); // abs just in case:tm:
            rightExcValue = Math.abs(Constants.gsVisionTable.getEntry("right_exceeded").getDouble(0));

            System.out.println(leftExcValue + "  " + rightExcValue + "  " + Constants.gsVisionTable.getEntry("has_target").getBoolean(false) + Constants.gsVisionTable.getEntry("near").getBoolean(false) + "  " + intakeDelay);

            if (Constants.gsVisionTable.getEntry("near").getBoolean(false)) {
                intakeDelay = 1 * 50; // seconds * 50 scheduler runs/sec    
            }
            // runs when near and for x seconds after as given above.
            if (Constants.gsVisionTable.getEntry("near").getBoolean(false) && Constants.gsVisionTable.getEntry("has_target").getBoolean(false) || (intakeDelay > 0)) {
                RobotContainer.drivetrain.setLeftMotorPosition(RobotContainer.drivetrain.getLeftMotorEncoder() + forwardVal + ((rightExcValue) * 0.015)); // 0.015 conv to rot, ~32 deg max
                RobotContainer.drivetrain.setRightMotorPosition(RobotContainer.drivetrain.getRightMotorEncoder() + forwardVal + ((leftExcValue) * 0.015));
                
                RobotContainer.intake.setSpeed(Constants.intakeSpeed);
                if (RobotContainer.intake.isBallThere()) {
                    RobotContainer.conveyorBelt.setSpeed(Constants.conveyorBeltForwardSpeed);
                    triedIntake = true;
                } else {
                    if (!RobotContainer.intake.isBallThere() && triedIntake) {
                        Constants.gsBallsHeld += 1;
                        triedIntake = false;
                        intakeDelay = 0;
                    }
                }
                //schedulerInstance.schedule(new IntakeIndex());

                intakeDelay--;
            }
            else if (intakeDelay == 0) {
                triedIntake = false;
            }
            else {
                // schedulerInstance.schedule(new IndexBeforeIntake());
                RobotContainer.intake.setSpeed(0);
            }
            
            if (Constants.gsVisionTable.getEntry("has_target").getBoolean(false)) {
                if (rightExcValue > 0) {
                    RobotContainer.drivetrain.setLeftMotorSpeed(forwardSpeed + (ECRate * rightExcValue));
                }
                else {
                    RobotContainer.drivetrain.setLeftMotorSpeed(forwardSpeed);
                }

                if (leftExcValue > 0) {
                    RobotContainer.drivetrain.setRightMotorSpeed(forwardSpeed + (ECRate * leftExcValue));
                }
                else {
                    RobotContainer.drivetrain.setRightMotorSpeed(forwardSpeed);
                }
            } else if (intakeDelay <= 0) {
                // turn clockwise(?) slowly for target aquisition
                RobotContainer.drivetrain.setRightMotorSpeed(-turnSpeed);
                RobotContainer.drivetrain.setLeftMotorSpeed(turnSpeed);
            }
        }
        else {
            if (Constants.gsMarkerVisionTable.getEntry("has_target").getBoolean(false)) {
                RobotContainer.drivetrain.setRightMotorSpeed(-turnSpeed);
                RobotContainer.drivetrain.setLeftMotorSpeed(turnSpeed);
            }

            else {
                leftExcValue = Constants.gsMarkerVisionTable.getEntry("left_exceeded").getDouble(0);
                rightExcValue = Constants.gsMarkerVisionTable.getEntry("right_exceeded").getDouble(0);

                if (rightExcValue > 0) {
                    RobotContainer.drivetrain.setLeftMotorSpeed(endForwardSpeed + (ECRate * rightExcValue));
                }
                else {
                    RobotContainer.drivetrain.setLeftMotorSpeed(endForwardSpeed);
                }

                if (leftExcValue > 0) {
                    RobotContainer.drivetrain.setRightMotorSpeed(endForwardSpeed + (ECRate * leftExcValue));
                }
                else {
                    RobotContainer.drivetrain.setRightMotorSpeed(endForwardSpeed);
                }
            }
        }
    }

    @Override
    public void end (boolean interrupted) {
        RobotContainer.drivetrain.setMotorSpeed(0);
        RobotContainer.intake.setSpeed(0);
    }

    @Override
    public boolean isFinished() {
       return false; 
    }
}
