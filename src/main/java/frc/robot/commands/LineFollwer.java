/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class LineFollwer extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})


  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public LineFollwer() {
    //m_subsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    //addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() 
  {
    int defaultValue = 0;
    int cx = (int) Constants.table.getEntry("Idk").getNumber(defaultValue);
            
    if (cx >= 120)
    {
                // System.out.println("Turn Left!");
      Constants.leftFrontMotor.set(0);
      Constants.leftBackMotor.set(0);
      Constants.rightFrontMotor.set(0.3);
      Constants.rightBackMotor.set(0.3);
    }
    else if (cx < 120 && cx > 50)
    {
    // System.out.println("On Track")
        Constants.leftFrontMotor.set(0.3);
        Constants.leftBackMotor.set(0.3);
        Constants.rightFrontMotor.set(0.3);
        Constants.rightBackMotor.set(0.3);
    }
    else if (cx <= 50)
    {
    // System.out.println("Turn Right");
        Constants.leftFrontMotor.set(0.3);
        Constants.leftBackMotor.set(0.3);
        Constants.rightFrontMotor.set(0);
        Constants.rightBackMotor.set(0);
    }
        //System.out.println("I don't see the line");
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Constants.leftFrontMotor.set(0);
    Constants.leftBackMotor.set(0);
    Constants.rightFrontMotor.set(0);
    Constants.rightBackMotor.set(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
