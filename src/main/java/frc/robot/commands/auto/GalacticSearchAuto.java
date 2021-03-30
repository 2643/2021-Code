package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.conveyor.ForwardConveyor;
import frc.robot.commands.conveyor.IndexBeforeIntake;
import frc.robot.commands.conveyor.IntakeIndex;
import frc.robot.commands.intake.ForwardIntake;
import frc.robot.commands.intake.LowerIntake;


public class GalacticSearchAuto extends CommandBase {
    CommandScheduler schedulerInstance = CommandScheduler.getInstance();
    
    private double leftExcValue = 0;
    private double rightExcValue = 0;

    // MAGIC NUMBERS YAYY!
    private double forwardSpeed = 0.5; // default speed to approach
    private double ECRate = 0.0004; //curr 0.2/500 basically P in PID Error Correcting Rate during approach
    private double forwardVal = 20; // in rotations dist to move once "near"
    // private double offsetSpeed = forwardSpeed + 0.2; // magic number
    private double turnSpeed = 0.15; // turning speed when no ball is found 
    
    private long intakeDelay = 0; // hold counter to allow for intake after not "near" 

    public GalacticSearchAuto() {
        addRequirements(RobotContainer.drivetrain);
        // addRequirements(RobotContainer.intake);
        // addRequirements(RobotContainer.conveyorBelt);
    }

    @Override
    public void initialize() {
        RobotContainer.drivetrain.resetAllEncoder();
        schedulerInstance.schedule(new LowerIntake());
    }
    
    @Override
    public void execute() {
        // errors
        leftExcValue = Constants.gsVisionTable.getEntry("left_exceeded").getDouble(0);
        rightExcValue = Constants.gsVisionTable.getEntry("right_exceeded").getDouble(0);

        if (Constants.gsVisionTable.getEntry("near").getBoolean(false)) {
            intakeDelay = 3 * 50; // seconds * 50 scheduler runs/sec    
        }
        // runs when near and for x seconds after as given above.
        if (Constants.gsVisionTable.getEntry("near").getBoolean(false) || (intakeDelay > 0)) {
            RobotContainer.drivetrain.setLeftMotorPosition(RobotContainer.drivetrain.getLeftMotorEncoder() + forwardVal + ((rightExcValue - 200) * 0.015)); // 0.015 conv to rot, ~32 deg max
            RobotContainer.drivetrain.setRightMotorPosition(RobotContainer.drivetrain.getRightMotorEncoder() + forwardVal + ((leftExcValue - 200) * 0.015));
            schedulerInstance.schedule(new ForwardIntake());
            schedulerInstance.schedule(new IntakeIndex());

            intakeDelay--;
        }
        else {
            schedulerInstance.schedule(new IndexBeforeIntake());
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

        if (Constants.ballsHeld >= 3) {
            // TODO: implement seek for marker
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
