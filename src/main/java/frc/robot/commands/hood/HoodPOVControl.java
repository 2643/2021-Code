/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.hood;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class HoodPOVControl extends CommandBase {

  /**
   * Creates a new HoodPOVControl.
   */
  public HoodPOVControl() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.hood);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(RobotContainer.driveStick.getPOV()==0 /*&& !RobotContainer.hood.atTopLimit()*/){
      RobotContainer.hood.moveHoodUp();
    }else if(RobotContainer.driveStick.getPOV() == 180 /*&& !RobotContainer.hood.atBottomLimit()*/){
      RobotContainer.hood.moveHoodDown();
    }else{
      RobotContainer.hood.stopHood();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.hood.stopHood();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
