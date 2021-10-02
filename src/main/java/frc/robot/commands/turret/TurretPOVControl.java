/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.turret;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class TurretPOVControl extends CommandBase {
  /**
   * Creates a new TurretPOVControl.
   */
  public TurretPOVControl() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.turret);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Constants.leftTurretSpeed = Constants.leftTurretPOVSpeed; 
    Constants.rightTurretSpeed = Constants.rightTurretPOVSpeed; 
    
    if(RobotContainer.driveStick.getPOV() == 90){
      RobotContainer.turret.moveTurretRight();
    }else if(RobotContainer.driveStick.getPOV() == 270){
      RobotContainer.turret.moveTurretLeft();
    }else{
      RobotContainer.turret.stopTurret();
    }
    Constants.lastTurretPosition = RobotContainer.turret.getPosition();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.turret.stopTurret();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
