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


public class RotateX extends CommandBase {

  private double angle;

  public RotateX(int a) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.drivetrain);
    angle = a;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // Reset the drivetrain encoders
    RobotContainer.drivetrain.resetLeftEncoder();
    RobotContainer.drivetrain.resetRightEncoder();
    
    RobotContainer.drivetrain.setRightMotorPosition(-Constants.rotateX(angle));
    RobotContainer.drivetrain.setLeftMotorPosition(Constants.rotateX(angle));

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.drivetrain.setLeftMotorPosition(RobotContainer.drivetrain.getLeftMotorEncoder());
    RobotContainer.drivetrain.setRightMotorPosition(RobotContainer.drivetrain.getRightMotorEncoder());

    if(interrupted == true){
      RobotContainer.drivetrain.setLeftMotorSpeed(0);
      RobotContainer.drivetrain.setLeftMotorSpeed(0);
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if((RobotContainer.drivetrain.getLeftMotorEncoder() == Constants.rotateX(angle)) 
    && (RobotContainer.drivetrain.getRightMotorEncoder() == Constants.rotateX(angle)))
      return true;
    return false;
  }
}
