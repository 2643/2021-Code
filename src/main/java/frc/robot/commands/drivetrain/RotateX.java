/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;


public class RotateX extends CommandBase {

  private double angle;
  private double error = RobotContainer.drivetrain.allowedError;

  public RotateX(double a) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.drivetrain);
    angle = a; 
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // Reset the drivetrain encoders
    RobotContainer.drivetrain.RotateAngle(angle);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //RobotContainer.drivetrain.setLeftMotorPosition(RobotContainer.drivetrain.getLeftMotorEncoder());
    //RobotContainer.drivetrain.setRightMotorPosition(RobotContainer.drivetrain.getRightMotorEncoder());

    if(interrupted == true){
      RobotContainer.drivetrain.setLeftMotorSpeed(0);
      RobotContainer.drivetrain.setLeftMotorSpeed(0);
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    /*
    if((Math.abs(RobotContainer.drivetrain.getLeftMotorEncoder()) <= (angle + error)) 
    && (Math.abs(RobotContainer.drivetrain.getLeftMotorEncoder()) >= (angle - error)))
    {
      if((Math.abs(RobotContainer.drivetrain.getRightMotorEncoder()) <= (angle + error)) 
      && (Math.abs(RobotContainer.drivetrain.getRightMotorEncoder()) >= (angle - error)))
      {
        System.out.println(RobotContainer.drivetrain.getLeftMotorEncoder());
        System.out.println(RobotContainer.drivetrain.getRightMotorEncoder());
        System.out.println("Finshed");
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
    */
    if(RobotContainer.drivetrain.getLeftMotorEncoder() <= (angle + error) && RobotContainer.drivetrain.getLeftMotorEncoder() >= (angle - error)){
      if(RobotContainer.drivetrain.getRightMotorEncoder() <= (angle + error) && RobotContainer.drivetrain.getRightMotorEncoder() >= (angle - error)){
        return true;
      }
      else{
        return false;
      }
    }
    else{
      return false;
    }
  }
}
