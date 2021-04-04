/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class MoveForward extends CommandBase {
  private double rotationsForward; 

  /**
   * Creates a new MoveForward.
   */
  public MoveForward(double rotations) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.drivetrain);

    rotationsForward = rotations;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.drivetrain.resetAllEncoder();
    RobotContainer.drivetrain.setAllMotorPosition(rotationsForward);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {    
      RobotContainer.drivetrain.setLeftMotorSpeed(0);
      RobotContainer.drivetrain.setRightMotorSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if((Math.abs(RobotContainer.drivetrain.getLeftMotorEncoder()) <= (rotationsForward + RobotContainer.drivetrain.allowedError)) 
    && (Math.abs(RobotContainer.drivetrain.getLeftMotorEncoder()) >= (rotationsForward - RobotContainer.drivetrain.allowedError)))
    {
      if((Math.abs(RobotContainer.drivetrain.getRightMotorEncoder()) <= (rotationsForward + RobotContainer.drivetrain.allowedError)) 
      && (Math.abs(RobotContainer.drivetrain.getRightMotorEncoder()) >= (rotationsForward - RobotContainer.drivetrain.allowedError)))
      {
        System.out.println(RobotContainer.drivetrain.getLeftMotorEncoder());
        System.out.println(RobotContainer.drivetrain.getRightMotorEncoder());
        return true; 
      }
      else
      {
        return false;
      }
    }
    else
    {
      return false; 
    }
  }
}
