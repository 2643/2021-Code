/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {
  public static TalonFX leftWinchMotor = new TalonFX(Constants.leftWinchPort);
  public static TalonFX rightWinchMotor = new TalonFX(Constants.rightWinchPort);

  public static CANSparkMax climberDeliveryMotor1 = new CANSparkMax(Constants.climberDeliveryMotorPort1, MotorType.kBrushless);
  
  //TODO Necessary feature: Implement soft limits for delivery

  private double kP = 0.00016;//0.006;
  private double kI = 0;//0.000002;
  private double kD = 0;//0.004;//0.2;
  private double kFF = 0.000156;

  /**
   * Creates a new Climber.
   */
  public Climber() {
    climberDeliveryMotor1.getPIDController().setP(kP, 0);
    climberDeliveryMotor1.getPIDController().setI(kI, 0);
    climberDeliveryMotor1.getPIDController().setD(kD, 0);
    climberDeliveryMotor1.getPIDController().setFF(kFF, 0);
    
  }

  /**
   * Raises the climber delivery hook
   */
  public void setDeliveryMotorSpeed(double speed){
    climberDeliveryMotor1.getPIDController().setReference(speed, ControlType.kDutyCycle); 
  }

  /**
   * Makes the climber motor stay at the height it was released at
   */
  public void stay(){
    if(climberDeliveryMotor1.getEncoder().getPosition() == Constants.deliveryBottomLimit){
      climberDeliveryMotor1.getPIDController().setReference(0, ControlType.kDutyCycle);
    }else{
      climberDeliveryMotor1.getPIDController().setReference(climberDeliveryMotor1.getEncoder().getPosition(), ControlType.kPosition);
    }
  }

  /**
   * Sets the speed of both the winches to pull up the robot
   * @param speed double from -1 to 1
   */
  public void setBothWinch(double speed){
    leftWinchMotor.set(ControlMode.PercentOutput, -speed);
    rightWinchMotor.set(ControlMode.PercentOutput, speed);
  }

  /**
   * Sets the speed of the right winch to pull up the robot
   * @param speed double from -1 to 1
   */
  public void setRightWinch(double speed){
    rightWinchMotor.set(ControlMode.PercentOutput, speed);
  }

  /**
   * Sets the speed of the left winch to pull up the robot
   */
  public void setLeftWinch(double speed){
    leftWinchMotor.set(ControlMode.PercentOutput, -speed);
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
