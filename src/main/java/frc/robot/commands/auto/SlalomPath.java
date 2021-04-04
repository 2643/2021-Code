/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.drivetrain.MoveForward;
import frc.robot.commands.drivetrain.RotateX;


// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class SlalomPath extends SequentialCommandGroup {
  /**
   * Creates a new RotateRound.
   */
  public SlalomPath() {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    //19 ticks = 30in
    //360 = 51.5
    //180 = 25.8
    //90 = 13
    //super(new MoveForward(19).andThen(new RotateX(7.5)).andThen(new MoveForward(38)));
    super(new MoveForward(19));
  }
}
