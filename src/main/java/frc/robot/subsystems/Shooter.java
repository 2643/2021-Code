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

public class Shooter extends SubsystemBase {
  public static CANSparkMax leftShooterMotor = new CANSparkMax(Constants.leftShooterMotorPort, MotorType.kBrushless);
  public static CANSparkMax rightShooterMotor = new CANSparkMax(Constants.rightShooterMotorPort, MotorType.kBrushless);

  //Shooter PID Constants
  private static final double kP = 0.00067;
  private static final double kI = 0.000001;
  private static final double kD = 0.0002;
  private static final double kIz = 100;
  private static final double kFF = 0.0002;
  private static final double kMaxOutput = 0.7;
  private static final double kMinOutput = -0.7;
  private static final double maxRPM = 5700;
  
  private static int slotID = 0; 
  
  /**
   * Creates a new Shooter.
   */
  public Shooter() {
    leftShooterMotor.getPIDController().setP(kP, slotID);
    leftShooterMotor.getPIDController().setI(kI, slotID);
    leftShooterMotor.getPIDController().setD(kD, slotID);
    leftShooterMotor.getPIDController().setIZone(kIz, slotID);
    leftShooterMotor.getPIDController().setFF(kFF, slotID);
    leftShooterMotor.getPIDController().setOutputRange(kMinOutput, kMaxOutput, slotID);

    rightShooterMotor.getPIDController().setP(kP, slotID);
    rightShooterMotor.getPIDController().setI(kI, slotID);
    rightShooterMotor.getPIDController().setD(kD, slotID);
    rightShooterMotor.getPIDController().setIZone(kIz, slotID);
    rightShooterMotor.getPIDController().setFF(kFF, slotID);
    rightShooterMotor.getPIDController().setOutputRange(kMinOutput, kMaxOutput, slotID);
  }

  /**
   * Spins the motor at a given speed in RPM
   * DO NOT USE FOR STOPPING MOTOR
   * @param speed RPM to spin motor at
   */
  public void spinMotors(double speed){
    if(speed != 0){
      leftShooterMotor.getPIDController().setReference(-speed, ControlType.kVelocity);
      rightShooterMotor.getPIDController().setReference(speed, ControlType.kVelocity);
    }
  }

  /**
   * Stops the shooter motors using duty cycle
   */
  public void stopMotors(){
      leftShooterMotor.getPIDController().setReference(0, ControlType.kDutyCycle);
      rightShooterMotor.getPIDController().setReference(0, ControlType.kDutyCycle);
  }

  /**
   * Spins the motors at the given speed in duty cycle
   * @param speed
   */
  public void spinMotorsDutyCycle(double speed){
    leftShooterMotor.getPIDController().setReference(-speed, ControlType.kDutyCycle);
    rightShooterMotor.getPIDController().setReference(speed, ControlType.kDutyCycle);
  }

  /**
   * Returns the shooter speed of both motor
   * @return double[] = {leftSpeed, rightSpeed};
   */
  public double[] getShooterSpeed(){
    double[] speed = {leftShooterMotor.getEncoder().getVelocity(), rightShooterMotor.getEncoder().getVelocity()};
    return speed; 
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
