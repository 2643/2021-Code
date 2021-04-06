/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.auto.RotateRound;
import frc.robot.commands.drivetrain.RotateX;

public class Drivetrain extends SubsystemBase {
  private static CANSparkMax leftFrontMotor = new CANSparkMax(Constants.leftFrontMotorPort, MotorType.kBrushless);
  private static CANSparkMax leftBackMotor = new CANSparkMax(Constants.leftBackMotorPort, MotorType.kBrushless);
  private static CANSparkMax rightFrontMotor = new CANSparkMax(Constants.rightFrontMotorPort, MotorType.kBrushless);
  private static CANSparkMax rightBackMotor = new CANSparkMax(Constants.rightBackMotorPort, MotorType.kBrushless);


  // Sets the SmartMotion PID variables
  private final double SmartMotionP = 0.0006;//0.0016;
  private final double SmartMotionI = 0;//0.000002;
  private final double SmartMotionD = 0.0004;//0.2;
  private final double SmartMotionFF = 0;//0.000156;

  // Sets the SmartVelocity PID variables
  private final double SmartVelocityP = 0.0001;
  private final double SmartVelocityI = 0;
  private final double SmartVelocityD = 0.0005;
  private final double SmartVelocityFF = 0;

  // Sets the max and min output for the motor speed
  private final double MaxOutput = 0.3;
  private final double MinOutput = -0.3;

  // Sets the max acceleration for the motors
  private final double maxAccel = 2500;
  private final int SmartMotionID = 0;
  private final int SmartVelocityID = 1;
  private final int maxVel = 5000;
  private final int minVel = 0;

  public final double allowedError = 0.1;

  /**
   * Creates a new Drivetrain.
   */
  public Drivetrain() {
    /*
    leftFrontMotor.restoreFactoryDefaults();
    leftBackMotor.restoreFactoryDefaults();
    rightFrontMotor.restoreFactoryDefaults();
    rightBackMotor.restoreFactoryDefaults();
    */

    // Set the SmartMotion PID Controller for Left Front Motor
    leftFrontMotor.getPIDController().setP(SmartMotionP, SmartMotionID);
    leftFrontMotor.getPIDController().setI(SmartMotionI, SmartMotionID);
    leftFrontMotor.getPIDController().setD(SmartMotionD, SmartMotionID);
    leftFrontMotor.getPIDController().setFF(SmartMotionFF, SmartMotionID);
    leftFrontMotor.getPIDController().setOutputRange(MinOutput, MaxOutput, SmartMotionID);
    leftFrontMotor.getPIDController().setSmartMotionAllowedClosedLoopError(allowedError, SmartMotionID);
    leftFrontMotor.getPIDController().setSmartMotionMaxAccel(maxAccel, SmartMotionID);
    leftFrontMotor.getPIDController().setSmartMotionMaxVelocity(maxVel, SmartMotionID);
    leftFrontMotor.getPIDController().setSmartMotionMinOutputVelocity(minVel, SmartMotionID);

    // Set the SmartVelocity PID Controller for Left Front Motor
    /*
    leftFrontMotor.getPIDController().setP(SmartVelocityP, SmartVelocityID);
    leftFrontMotor.getPIDController().setI(SmartVelocityI, SmartVelocityID);
    leftFrontMotor.getPIDController().setD(SmartVelocityD, SmartVelocityID);
    leftFrontMotor.getPIDController().setFF(SmartVelocityFF, SmartVelocityID);
    leftFrontMotor.getPIDController().setOutputRange(MinOutput, MaxOutput, SmartVelocityID);

    leftFrontMotor.getPIDController().setSmartMotionMaxAccel(maxAccel, SmartVelocityID);
    leftFrontMotor.getPIDController().setSmartMotionMaxVelocity(maxVel, SmartVelocityID);
    leftFrontMotor.getPIDController().setSmartMotionMinOutputVelocity(minVel, SmartVelocityID);
    */

    // Set the SmartMotion PID Controller for Left Back Motor
    leftBackMotor.getPIDController().setP(SmartMotionP, SmartMotionID);
    leftBackMotor.getPIDController().setI(SmartMotionI, SmartMotionID);
    leftBackMotor.getPIDController().setD(SmartMotionD, SmartMotionID);
    leftBackMotor.getPIDController().setFF(SmartMotionFF, SmartMotionID);
    leftBackMotor.getPIDController().setOutputRange(MinOutput, MaxOutput, SmartMotionID);
    leftBackMotor.getPIDController().setSmartMotionAllowedClosedLoopError(allowedError, SmartMotionID);
    leftBackMotor.getPIDController().setSmartMotionMaxAccel(maxAccel, SmartMotionID);
    leftBackMotor.getPIDController().setSmartMotionMaxVelocity(maxVel, SmartMotionID);
    leftBackMotor.getPIDController().setSmartMotionMinOutputVelocity(minVel, SmartMotionID);

    // Set the SmartVelocity PID Controller for Left Back Motor
    /*
    leftBackMotor.getPIDController().setP(SmartVelocityP, SmartVelocityID);
    leftBackMotor.getPIDController().setI(SmartVelocityI, SmartVelocityID);
    leftBackMotor.getPIDController().setD(SmartVelocityD, SmartVelocityID);
    leftBackMotor.getPIDController().setFF(SmartVelocityFF, SmartVelocityID);
    leftBackMotor.getPIDController().setOutputRange(MinOutput, MaxOutput, SmartVelocityID);

    leftBackMotor.getPIDController().setSmartMotionMaxAccel(maxAccel, SmartVelocityID);
    leftBackMotor.getPIDController().setSmartMotionMaxVelocity(maxVel, SmartVelocityID);
    leftBackMotor.getPIDController().setSmartMotionMinOutputVelocity(minVel, SmartVelocityID);
    */

    // Set the SmartMotion PID Controller for Right Front Motor
    rightFrontMotor.getPIDController().setP(SmartMotionP, SmartMotionID);
    rightFrontMotor.getPIDController().setI(SmartMotionI, SmartMotionID);
    rightFrontMotor.getPIDController().setD(SmartMotionD, SmartMotionID);
    rightFrontMotor.getPIDController().setFF(SmartMotionFF, SmartMotionID);
    rightFrontMotor.getPIDController().setOutputRange(MinOutput, MaxOutput, SmartMotionID);
    rightFrontMotor.getPIDController().setSmartMotionAllowedClosedLoopError(allowedError, SmartMotionID);
    rightFrontMotor.getPIDController().setSmartMotionMaxAccel(maxAccel, SmartMotionID);
    rightFrontMotor.getPIDController().setSmartMotionMaxVelocity(maxVel, SmartMotionID);
    rightFrontMotor.getPIDController().setSmartMotionMinOutputVelocity(minVel, SmartMotionID);

    // Set the SmartVelocity PID Controller for Right Front Motor
    /*
    rightFrontMotor.getPIDController().setP(SmartVelocityP, SmartVelocityID);
    rightFrontMotor.getPIDController().setI(SmartVelocityI, SmartVelocityID);
    rightFrontMotor.getPIDController().setD(SmartVelocityD, SmartVelocityID);
    rightFrontMotor.getPIDController().setFF(SmartVelocityFF, SmartVelocityID);
    rightFrontMotor.getPIDController().setOutputRange(MinOutput, MaxOutput, SmartVelocityID);

    rightFrontMotor.getPIDController().setSmartMotionMaxAccel(maxAccel, SmartVelocityID);
    rightFrontMotor.getPIDController().setSmartMotionMaxVelocity(maxVel, SmartVelocityID);
    rightFrontMotor.getPIDController().setSmartMotionMinOutputVelocity(minVel, SmartVelocityID);
    */

    // Set the SmartMotion PID Controller for Right Back Motor
    rightBackMotor.getPIDController().setP(SmartMotionP, SmartMotionID);
    rightBackMotor.getPIDController().setI(SmartMotionI, SmartMotionID);
    rightBackMotor.getPIDController().setD(SmartMotionD, SmartMotionID);
    rightBackMotor.getPIDController().setFF(SmartMotionFF, SmartMotionID);
    rightBackMotor.getPIDController().setOutputRange(MinOutput, MaxOutput, SmartMotionID);
    rightBackMotor.getPIDController().setSmartMotionAllowedClosedLoopError(allowedError, SmartMotionID);
    rightBackMotor.getPIDController().setSmartMotionMaxAccel(maxAccel, SmartMotionID);
    rightBackMotor.getPIDController().setSmartMotionMaxVelocity(maxVel, SmartMotionID);
    rightBackMotor.getPIDController().setSmartMotionMinOutputVelocity(minVel, SmartMotionID);

    // Set the SmartVelocity PID Controller for Right Back Motor
    /*
    rightBackMotor.getPIDController().setP(SmartVelocityP, SmartVelocityID);
    rightBackMotor.getPIDController().setI(SmartVelocityI, SmartVelocityID);
    rightBackMotor.getPIDController().setD(SmartVelocityD, SmartVelocityID);
    rightBackMotor.getPIDController().setFF(SmartVelocityFF, SmartVelocityID);
    rightBackMotor.getPIDController().setOutputRange(MinOutput, MaxOutput, SmartVelocityID);

    rightBackMotor.getPIDController().setSmartMotionMaxAccel(maxAccel, SmartVelocityID);
    rightBackMotor.getPIDController().setSmartMotionMaxVelocity(maxVel, SmartVelocityID);
    rightBackMotor.getPIDController().setSmartMotionMinOutputVelocity(minVel, SmartVelocityID);
    */
  }

  /**
   * Gets the encoder position of the left side of the drivetrain
   * 
   * @return double rotations
   */
  public double getLeftMotorEncoder() {
    return leftFrontMotor.getEncoder().getPosition();
  }

  /**
   * Gets the encoder position of the right side of the drivetrain
   * 
   * @return double rotations
   */
  public double getRightMotorEncoder() {
    return rightFrontMotor.getEncoder().getPosition();
  }

  /**
   * Sets the speed of the left side motors using SmartVelocity
   * 
   * @param speed from -1 to 1, speed to set motor
   */
  public void setLeftMotorSpeed(double speed) {
    leftFrontMotor.getPIDController().setReference(-speed, ControlType.kDutyCycle);
    leftBackMotor.getPIDController().setReference(-speed, ControlType.kDutyCycle);
  }

  /**
   * Sets the speed of the right side motors using SmartVelocity
   * 
   * @param speed -1 to 1, speed to set motor
   */
  public void setRightMotorSpeed(double speed) {
    rightFrontMotor.getPIDController().setReference(speed, ControlType.kDutyCycle);
    rightBackMotor.getPIDController().setReference(speed, ControlType.kDutyCycle);
    //System.out.print("Hello too");
  }
 
  /**
   * Sets the speed of the drivetrain using SmartVelocity
   * 
   * @param speed -1 to 1, speed to set motor
   */
  public void setMotorSpeed(double speed) {
    setLeftMotorSpeed(speed);
    setRightMotorSpeed(speed);
  }

  /**
   * Sets the position of the left side of the drivetrain using SmartMotion
   * 
   * @param rotations
   */
  public void setLeftMotorPosition(double rotations) {
    leftFrontMotor.getPIDController().setReference(rotations, ControlType.kSmartMotion, SmartMotionID);
    leftBackMotor.getPIDController().setReference(rotations, ControlType.kSmartMotion, SmartMotionID);
    //System.out.println("Left Motor Position: " + rotations);
  }


  /**
   * Sets the position of the right side of the drivetrain using SmartMotion
   * 
   * @param rotations
   */
  public void setRightMotorPosition(double rotations) {
    rightFrontMotor.getPIDController().setReference(-rotations, ControlType.kSmartMotion, SmartMotionID);
    rightBackMotor.getPIDController().setReference(-rotations, ControlType.kSmartMotion, SmartMotionID);
    //System.out.println("Hello World!");
  }
  /**
   * Sets the position of both sides of the drivetrain using SmartMotion
   * @param rotations
   */
  public void setAllMotorPosition(double rotations) {
    setRightMotorPosition(rotations);
    setLeftMotorPosition(rotations);
  }
  
  public void RotateAngle(double a)
  {
    //double b = (((double)13 / 90) * a);
    setRightMotorPosition(-a);
    setLeftMotorPosition(a);
  }

  /**
   * Resets the left encoder
   */
  public void resetLeftEncoder() {
    leftFrontMotor.getEncoder().setPosition(0);
    leftBackMotor.getEncoder().setPosition(0);
  }

  /**
   * Resets the right encoder
   */
  public void resetRightEncoder() {
    rightFrontMotor.getEncoder().setPosition(0);
    rightBackMotor.getEncoder().setPosition(0);
  }

  public void resetAllEncoder() {
    
    resetLeftEncoder();
    resetRightEncoder();
  }

  public void imSorry(){
    int loopback = 0;
    int turnback = 0;
    switch((int)Constants.visionMoveMode.getEntry("mode").getNumber(4)){
      case 1:
        //move foward
        setMotorSpeed(0.3);
        System.out.print("1");
        break;
      case 2: 
        //180
        if(turnback%10 ==0)
          RobotContainer.drivetrain.RotateAngle(25.8);
        turnback+=1;
        System.out.print("2");
        break;
      case 3 :
        //turn
        if (loopback%5 ==0)
          RobotContainer.drivetrain.RotateAngle(3.7);
        loopback +=1;
        System.out.print("3");
        break;
      case 4:
        //end program?!?!?!
        System.out.print("4");
        setMotorSpeed(0);
        break;
    }
  }
  @Override
  public void periodic() {
    imSorry();
  }
}
