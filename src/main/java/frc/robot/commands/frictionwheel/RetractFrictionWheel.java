/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.frictionwheel;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.Timer;

/**
 * Retracts the friction wheel mechanism
 */
public class RetractFrictionWheel extends CommandBase {
  /**
   * Creates a new RetractFrictionWheel.
   */

  private Timer timer = new Timer();

  public RetractFrictionWheel() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.frictionWheel);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.reset();
    timer.start();
    RobotContainer.frictionWheel.retractMechanism(); 
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Constants.frictionWheelToggleVariable = true; 
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (timer.get() >= Constants.pistonTimer)
    {
      return true;
    }
    return false;
  }
}
