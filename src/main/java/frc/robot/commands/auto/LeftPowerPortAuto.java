/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.auto_assist.AutoShoot;
import frc.robot.commands.conveyor.ForwardConveyor;
import frc.robot.commands.drivetrain.MoveForward;
import frc.robot.commands.intake.LowerIntake;
import frc.robot.commands.turret.TurretAlign;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class LeftPowerPortAuto extends SequentialCommandGroup {
  /**
   * Creates a new LeftPowerPortAuto.
   */
  public LeftPowerPortAuto() {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(new LowerIntake(), new MoveForward(Constants.moveOffInitiationLineRotations), new TurretAlign().andThen(new AutoShoot().raceWith(new WaitCommand(1).andThen(new ForwardConveyor()))));
  }
}
