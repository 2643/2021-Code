/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.conveyor;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

/**
 * Moves the power cells to the beginning of the conveyor belt before intaking
 */
public class IndexBeforeIntake extends CommandBase {
  
  private int firstIRActivated = 0; 
  private boolean finished = true; 
  /**
   * Creates a new IndexBeforeIntake.
   */
  public IndexBeforeIntake() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.conveyorBelt);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    firstIRActivated = RobotContainer.conveyorBelt.firstIndex();
    if(firstIRActivated == 0 || RobotContainer.conveyorBelt.noBallsHeld){
      finished = true; 
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotContainer.conveyorBelt.setSpeed(Constants.conveyorBeltBackwardSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.conveyorBelt.setSpeed(0);
    finished = false; 
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    firstIRActivated = RobotContainer.conveyorBelt.firstIndex(); 
    if(firstIRActivated == 0 || RobotContainer.conveyorBelt.noBallsHeld){
      finished = true; 
    }
    return finished; 
  }
}
