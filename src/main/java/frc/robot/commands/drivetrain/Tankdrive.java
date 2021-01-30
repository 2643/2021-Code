/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class Tankdrive extends CommandBase {
  private double leftSpeed = 0;
  private double rightSpeed = 0;
  private boolean finished = false;

  /**
   * Creates a new Tankdrive.
   */
  public Tankdrive() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (Math.abs(RobotContainer.driveStick.getRawAxis(Constants.leftAxis)) < 0.05) {
      if (Math.abs(RobotContainer.driveStick.getRawAxis(Constants.rightAxis)) < 0.05) {
        finished = true;
      }
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(RobotContainer.driveStick.getRawButtonPressed(1)){
      Constants.slowMode = !Constants.slowMode;
    }
    
    if (Math.abs(RobotContainer.driveStick.getRawAxis(Constants.leftAxis)) > 0.03) {
      if(Constants.slowMode == true){
        leftSpeed = Constants.slowModeMultipler*(RobotContainer.driveStick.getRawAxis(Constants.leftAxis));
      }else{
        leftSpeed = (RobotContainer.driveStick.getRawAxis(Constants.leftAxis));
      }
    }
    else{
      leftSpeed = 0;
    }

    if (Math.abs(RobotContainer.driveStick.getRawAxis(Constants.rightAxis)) > 0.03) {
      if(Constants.slowMode == true){
        rightSpeed = Constants.slowModeMultipler*(RobotContainer.driveStick.getRawAxis(Constants.rightAxis));
      }else{
        rightSpeed = (RobotContainer.driveStick.getRawAxis(Constants.rightAxis));
      }
    }
    else{
      rightSpeed = 0;
    }
    
    RobotContainer.drivetrain.setLeftMotorSpeed(leftSpeed);
    RobotContainer.drivetrain.setRightMotorSpeed(rightSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.drivetrain.setLeftMotorSpeed(0);
    RobotContainer.drivetrain.setRightMotorSpeed(0);
    finished = false;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finished;
  }
}
