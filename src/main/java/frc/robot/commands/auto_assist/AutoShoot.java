package frc.robot.commands.auto_assist;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import java.lang.Math;

public class AutoShoot extends CommandBase {

    private double XfromTarget;

    public AutoShoot() {
        addRequirements(RobotContainer.shooter);
        addRequirements(RobotContainer.hood);
        addRequirements(RobotContainer.tfmini);
        addRequirements(RobotContainer.turret);
    }

    @Override
    public void initialize() {
        XfromTarget = RobotContainer.tfmini.getDistance();
    }

    @Override
    public void execute() {
        // Find distance from the target
        // Compute shooter RPM
        // Linear function:
        //double MaxshooterRPM = (1.14 * XfromTarget) + 1513;
        //double MinshooterRPM = (1.27 * XfromTarget) + 1074;
        
        RobotContainer.turret.aimTurret(Constants.lastTurretPosition);

        // Exponential function:
        // double MaxshooterRPM = 1577*(Math.exp(5.25*(Math.pow(10,-4))*XfromTarget));
        // double MinshooterRPM = 1160*(Math.exp(7.11*(Math.pow(10,-4))*XfromTarget));
        // double shooterRPM = (MaxshooterRPM + MinshooterRPM) / 2;
        double shooterRPM = (1.42*XfromTarget) + 1088;

        System.out.println(shooterRPM);

        // Compute hood angle value
        // logorithmic function
        double hoodRotation = -15.7 + (5.71*Math.log(XfromTarget));

        // Shoot Bruh
        RobotContainer.hood.moveHood(hoodRotation);
        RobotContainer.shooter.spinMotors(shooterRPM);
    }

    @Override
    public void end (boolean interrupted) {
        RobotContainer.shooter.stopMotors();
        RobotContainer.hood.moveHood(1);
    }

    @Override
    public boolean isFinished() {
       return false; 
    }
}
