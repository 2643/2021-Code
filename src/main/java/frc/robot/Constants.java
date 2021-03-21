/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.function.BooleanSupplier;

import com.revrobotics.ColorMatch;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {

    //Auto Constants
    public static int autoMode = 2; 
    public static double moveOffInitiationLineRotations = 20; 

    public static double conveyorBeltForwardSpeedAutoShoot = -0.4;

    //Vision ConstantsS
    public static boolean valid = false; 
    public static double lastTurretPosition = 0; 
    public static double offset = 40; 
    public static NetworkTable visionTable = NetworkTableInstance.getDefault().getTable("vision-movement");

    //the BAD constants for vision yea
    public static NetworkTable visionMoveMode = NetworkTableInstance.getDefault().getTable("mode");

    //Debug Options
    public static final boolean debugConveyorBelt = false;
    
    //OI Constants
    public static final int leftAxis = 1;
    public static final int rightAxis = 5; 


    //Drivetrain Constants
    public static int leftFrontMotorPort = 1;
    public static int leftBackMotorPort = 2;
    public static int rightFrontMotorPort = 3; 
    public static int rightBackMotorPort = 4; 

    public static boolean slowMode = false; 
    public static double slowModeMultipler = 0.5;
    /**
     * Determines the number of encoder ticks necessary for drivetrain to turn at certain angle
     * @param angle the angle to turn
     * @return number of encoder ticks for the each side of the drivetrain to turn 
     */
    public static double rotateX(double angle){
      double encoderTicks = ((13/90)* angle);
      return encoderTicks;
    }

    public static final double allowedError = 0.05;

    //Shooter Constants
    public static int leftShooterMotorPort = 5;
    public static int rightShooterMotorPort = 6;

    public static double shooterDutyCycleSpeed = 0.7;

    //Turret Constants
    public static int turretMotorPort = 7;
    public static int turretLimitSwitchPort = 0; //TODO Necessary value: find port of the turret limit switch

    public static double leftTurretSpeed = 0.15;
    public static double rightTurretSpeed = -0.15;

    public static double leftTurretPOVSpeed = 0.15; 
    public static double rightTurretPOVSpeed = -0.15; 

    public static double leftTurretHighSpeed = 0.08;
    public static double rightTurretHighSpeed = -0.08;

    public static double leftTurretLowSpeed = 0.04;
    public static double rightTurretLowSpeed = -0.04;

    public static final int turretEncoderLeftSoftLimit = 25; //TODO Reminder: soft limit should become 85 when safe
    public static final int turretEncoderRightSoftLimit = -25; //TODO Reminder: soft limit should become -85 when safe

    //Hood Constants
    public static int hoodMotorPort = 8;
    public static int upperHoodLimitPort = 12; 
    public static int lowerHoodLimitPort = 11;

    public static int upperEncoderSoftLimit = 26; //TODO get upper limit for hood
    public static int lowerEncoderSoftLimit = 0; //TODO get lower limit for hood

    public static double hoodUpSpeed = 0.2; 
    public static double hoodDownSpeed = -0.2;

    //Friction Wheel Constants
    public static final int frictionWheelMotorPort = 9;

    public static final int doubleSolenoidPort1 = 6; 
    public static final int doubleSolenoidPort2 = 7;

    public static final double maxRPM = 5500;
    public static final double frictionWheelSpeed = -0.2;
    
    public static final int pistonTimer = 1;

    public static final int colorSensorOptimalRange = 180;
    public static final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    public static final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    public static final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    public static final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

    public static String fieldColorString = "initialized"; 
    public static boolean colorShifted = false; 

    public static boolean frictionWheelToggleVariable = false; 
    public static BooleanSupplier frictionWheelToggle = () -> frictionWheelToggleVariable; 

    //Climber Constants
    public static final int climberDeliveryMotorPort1 = 10; 
    public static final int leftWinchPort = 13;
    public static final int rightWinchPort = 12;
    
    public static final double deliveryHookSpeed = 0.3;
    
    public static final double leftWinchUpSpeed = 0.3; 
    public static final double rightWinchUpSpeed =  0.3; 

    public static final double bothWinchUnwindSpeed = 0.3;
    
    public static final double deliveryTopLimit = 0; //TODO Necessary value: determine top encoder value for climber delivery
    public static final double deliveryBottomLimit = 0; 

    //Intake Constants
    public static final int intakeMotorPort = 14;
    public static final int intakeIRChannel = 0;

    public static final double intakeSpeed = -0.6;
    public static final double reverseIntakeSpeed = 0.6;

    public static final int intakeSolenoidPort1 = 5; 
    public static final int intakeSolenoidPort2 = 4; 

    public static final int intakeRaiseTime = 1;

    public static boolean intakingBall = false; 

    public static boolean verticalIntakeToggleVariable = false; 
    public static BooleanSupplier verticalIntakeToggle = () -> verticalIntakeToggleVariable;
        
    //Conveyor Constants
    public static final int conveyorBeltMotorPort = 15;

    public static final double conveyorBeltForwardSpeed = -0.6;
    public static final double conveyorBeltBackwardSpeed = 0.6; 

    public static final int conveyoriRSensor1Channel = 1; 
    public static final int conveyoriRSensor2Channel = 2; 
    public static final int conveyoriRSensor3Channel = 3; 
    public static final int conveyoriRSensor4Channel = 4; 
    public static final int conveyoriRSensor5Channel = 5; 
    public static final int conveyoriRSensor6Channel = 6;
    public static final int conveyoriRSensor7Channel = 7;
    public static final int conveyoriRSensor8Channel = 8; 
    public static final int conveyoriRSensor9Channel = 9;

    public static int ballsHeld = 0; 

}
