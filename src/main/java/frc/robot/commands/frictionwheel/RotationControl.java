  /*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
//turns the wheel 7 times over a singular color

package frc.robot.commands.frictionwheel;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class RotationControl extends CommandBase {
  int x=0;
  boolean keepgoing;

  boolean firstColor = false; 
  /**
   * Creates a new RotationControl
   */
  public RotationControl() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.frictionWheel);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //Starting the counter
    x=0;
    keepgoing=false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if(RobotContainer.frictionWheel.getColor().equals("Blue")){
        keepgoing = true;
    }
    
    if(!RobotContainer.frictionWheel.getColor().equals("Blue") && keepgoing==true){
        x++;
        keepgoing = false; 
    }

    RobotContainer.frictionWheel.setMotorSpeed(Constants.frictionWheelSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.frictionWheel.setMotorSpeed(0);
    x=0;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    if(x>=7){
        return true;
    }
    return false; 
  }
}
