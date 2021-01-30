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

public class PositionControl extends CommandBase {
    String theColor;
    String destColor;

    /**
     * Creates a new PositionControl.
     */
    public PositionControl() {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(RobotContainer.frictionWheel);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        destColor = Constants.fieldColorString;
        Constants.colorShifted = true;

        if (!Constants.colorShifted) {
            if (destColor.charAt(0) == 'Y') {
                destColor = "Green";
            } else if (destColor.charAt(0) == 'B') {
                destColor = "Red";
            } else if (destColor.charAt(0) == 'G') {
                destColor = "Yellow";
            } else if (destColor.charAt(0) == 'R') {
                destColor = "Blue";
            }
        }
    }

    //Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        theColor = RobotContainer.frictionWheel.getColor();

        RobotContainer.frictionWheel.setMotorSpeed(Constants.frictionWheelSpeed);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        RobotContainer.frictionWheel.setMotorSpeed(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        if (theColor.equals(destColor)) {
            return true;
        }
        return false;
    }
}
