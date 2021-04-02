/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.auto.RotateRound;
import frc.robot.commands.drivetrain.RotateX;
import frc.robot.commands.drivetrain.Tankdrive;
import frc.robot.commands.hood.HoodPOVControl;
import frc.robot.commands.turret.TurretPOVControl;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private static RobotContainer m_robotContainer;
  SendableChooser<Integer> autoChooser = new SendableChooser<>();

  String trajectoryJSON = "paths/PathOne.wpilib.json";
Trajectory trajectory = new Trajectory();


  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();

    RobotContainer.hood.resetEncoder();
    RobotContainer.drivetrain.resetAllEncoder();
    RobotContainer.conveyorBelt.updateBallsHeld();

    autoChooser.setDefaultOption("Cross Initiation Line", 0);
    autoChooser.addOption("Left Power Port Auto", 1);
    autoChooser.addOption("Center Power Port Auto", 2);
    autoChooser.addOption("Right Power Port Auto", 3);
    autoChooser.addOption("Move Forward", 4);
    autoChooser.addOption("Rotate Round", 5);

    SmartDashboard.putData("Autonomous routine", autoChooser);
    CameraServer.getInstance().startAutomaticCapture();

    try {
      Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
      trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
   } catch (IOException ex) {
      DriverStation.reportError("Unable to open trajectory: " + trajectoryJSON, ex.getStackTrace());
   }
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler. This is responsible for polling buttons, adding
    // newly-scheduled
    // commands, running already-scheduled commands, removing finished or
    // interrupted commands,
    // and running subsystem periodic() methods. This must be called from the
    // robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
    CommandScheduler.getInstance().setDefaultCommand(RobotContainer.drivetrain, new Tankdrive());
    CommandScheduler.getInstance().setDefaultCommand(RobotContainer.turret, new TurretPOVControl());
    CommandScheduler.getInstance().setDefaultCommand(RobotContainer.hood, new HoodPOVControl());
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
  }

  /**
   * This function is called periodically each time the robot enters Disabled
   * mode.
   */
  @Override
  public void disabledPeriodic() {
    // System.out.println("Hood Rotations: " + RobotContainer.hood.getPosition());
    // System.out.println("Turret Position: " +
    // RobotContainer.turret.getPosition());
    // System.out.println("Distance: " + RobotContainer.tfmini.getDistance());
    // //RobotContainer.hood.resetEncoder();

  }

  /**
   * This autonomous runs the autonomous command selected by your
   * {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    RobotContainer.drivetrain.resetAllEncoder();
    RobotContainer.conveyorBelt.updateBallsHeld();
    RobotContainer.hood.resetEncoder();

    Constants.autoMode = autoChooser.getSelected();

    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    // System.out.println(RobotContainer.drivetrain.getLeftMotorEncoder());
    // System.out.println(RobotContainer.drivetrain.getRightMotorEncoder());
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    RobotContainer.conveyorBelt.updateBallsHeld();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    CommandScheduler.getInstance().run();
    // System.out.println("Distance: " + RobotContainer.tfmini.getDistance());
    // System.out.println("Shooter speed:" +
    // RobotContainer.shooter.getShooterSpeed()[1]);
  }

  /**
   * This function is called once at the beginning of test mode.
   */
  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
    RobotContainer.drivetrain.resetAllEncoder();
    RobotContainer.drivetrain.RotateAngle(103);
    //360 = 51.5
    //180 = 25.8
    //90 = 13
    //RobotContainer.drivetrain.setAllMotorPosition(60);
  }
  
  
  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    /**
     * Drivetrain Testing
     */
    //Check if encoders have reset
    //RobotContainer.drivetrain.setRightMotorSpeed(0.2);
    
    System.out.println("Left: " + RobotContainer.drivetrain.getLeftMotorEncoder() + " Right: " + RobotContainer.drivetrain.getRightMotorEncoder());
    //Test MoveForward with the new allowed error -- schedule this in AutonomousInit
    //Test RotateX to make sure it turns in the right direction - schedule this in AutonomousInit
    //Make/test autonomous routine - with/without shooting

  }
}
