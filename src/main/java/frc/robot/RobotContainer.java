/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.commands.*;
import frc.robot.subsystems.*;
import frc.robot.commands.auto.*;
import frc.robot.commands.auto_assist.*;
import frc.robot.commands.climber.*;
import frc.robot.commands.conveyor.*;
import frc.robot.commands.frictionwheel.*;
import frc.robot.commands.intake.*;
import frc.robot.commands.shooter.*;
import frc.robot.commands.turret.*;


/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands and OI devices are defined here...

  //Subsystems
  public static Drivetrain drivetrain = new Drivetrain();
  public static Shooter shooter = new Shooter();
  public static Turret turret = new Turret(); 
  public static Hood hood = new Hood();
  public static TFMini tfmini = new TFMini();

  public static Intake intake = new Intake();
  public static ConveyorBelt conveyorBelt = new ConveyorBelt();
  public static FrictionWheel frictionWheel = new FrictionWheel();
  public static Climber climber = new Climber();
  
  // //Operator Interface
  public static Joystick driveStick = new Joystick(0);
  public static JoystickButton slowToggle = new JoystickButton(driveStick, 1);
  public static JoystickButton controlPanel = new JoystickButton(driveStick, 5);
  public static JoystickButton verticalIntake = new JoystickButton(driveStick, 6);
  public static JoystickButton TestButton = new JoystickButton(driveStick, 2);

  public static Joystick opBoard = new Joystick(1);
  public static JoystickButton forwardConveyor = new JoystickButton(opBoard, 11); 
  public static JoystickButton reverseConveyor = new JoystickButton(opBoard, 10); 
  public static JoystickButton manualIntake = new JoystickButton(opBoard, 12); 
  public static JoystickButton reverseIntake = new JoystickButton(opBoard, 9); 
  public static JoystickButton manualControlPanel = new JoystickButton(opBoard, 5); 
  public static JoystickButton autoIntake = new JoystickButton(opBoard, 7); 
  public static JoystickButton rotationControl = new JoystickButton(opBoard, 6); 
  public static JoystickButton positionControl = new JoystickButton(opBoard, 4); 
  public static JoystickButton autoShoot = new JoystickButton(opBoard, 2); 
  public static JoystickButton manualShooting = new JoystickButton(opBoard, 3); 
  public static JoystickButton hookDelivery = new JoystickButton(opBoard, 8); 
  public static JoystickButton dropTelescope = new JoystickButton(opBoard, 15);
  public static JoystickButton leftClimb = new JoystickButton(opBoard, 13); 
  public static JoystickButton rightClimb = new JoystickButton(opBoard, 16); 
  public static JoystickButton bothWinchClimb = new JoystickButton(opBoard, 14);

  //Shooter Testing
  //public static JoystickButton button  = new JoystickButton(driveStick, 1);
  
  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    forwardConveyor.whileHeld(new ForwardConveyor());
    reverseConveyor.whileHeld(new ReverseConveyor());
    manualIntake.whileHeld(new ForwardIntake());
    reverseIntake.whileHeld(new ReverseIntake());
    manualControlPanel.whileHeld(new MoveWheel());
    
    autoIntake.whileHeld(new ConditionalCommand(new IndexBeforeIntake(), new ForwardIntake().raceWith(new IntakeIndex()), () -> opBoard.getRawButtonPressed(7)));


    rotationControl.whileHeld(new RotationControl().andThen(new WaitCommand(666))); 
    positionControl.whileHeld(new PositionControl().andThen(new WaitCommand(4)));  //check if at desired position before running position control

    autoShoot.whileHeld(new ConditionalCommand(new TurretAlign().andThen(new AutoShoot().raceWith(new WaitCommand(6).andThen(new ShootingIndex()))), new Nothing(), () -> Constants.visionTable.getEntry("valid").getBoolean(false)));
    manualShooting.whileHeld(new ForwardConveyor().alongWith(new Shoot()));

    hookDelivery.whileHeld(new SendHook());
    dropTelescope.whileHeld(new DropHook());

    leftClimb.whileHeld(new WinchLeft());
    bothWinchClimb.whileHeld(new WinchUp());
    rightClimb.whileHeld(new WinchRight());

    //TestButton.whenPressed(new RotateRound());

    controlPanel.whenPressed(new ConditionalCommand(new ExtendFrictionWheel(), new RetractFrictionWheel(), Constants.frictionWheelToggle));
    verticalIntake.whenPressed(new ConditionalCommand(new LowerIntake(), new RaiseIntake(), Constants.verticalIntakeToggle));
  }

  /*
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous

    if(Constants.autoMode == 1){
      System.out.println("Left");
      return (new LeftPowerPortAuto());
    }else if(Constants.autoMode == 2){
      System.out.println("Center");
      return (new CenterPowerPortAuto());
    }else if(Constants.autoMode == 3){
      System.out.println("Right");
      return (new RightPowerPortAuto());
    }else if(Constants.autoMode == 4){
      System.out.println("Moving Forward");
      return (new CrossInitiationLine());
    }else if(Constants.autoMode == 5){
      System.out.println("Rotate");
      return (new RotateRound());
    }else{
      return (new CrossInitiationLine());
    }

  }

}
