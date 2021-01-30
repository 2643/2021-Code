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

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Hood extends SubsystemBase {
  private static CANSparkMax hoodMotor = new CANSparkMax(Constants.hoodMotorPort, MotorType.kBrushless);
  private static DigitalInput upperHoodLimit = new DigitalInput(Constants.upperHoodLimitPort);  
  private static DigitalInput lowerHoodLimit = new DigitalInput(Constants.lowerHoodLimitPort);

  //Hood PID Constants
  double kP_hood = 0.095;//0.006;
  double kI_hood = 0;//0.000002;
  double kD_hood = 0;//0.004;//0.2;
  double kFF_hood = 0;
  double MaxOutput_hood = 0.3;
  double MinOutput_hood = -0.3;
  double maxAccel_hood = 300;
  int slotID_hood = 0;
  int maxVel_hood = 2000;
  int minVel_hood = 0;
  double allowedErr_hood = 0.05;


  /**
   * Creates a new Hood.
   */
  public Hood() {
    hoodMotor.getPIDController().setP(kP_hood, slotID_hood);
    hoodMotor.getPIDController().setI(kI_hood, slotID_hood);
    hoodMotor.getPIDController().setD(kD_hood, slotID_hood);
    hoodMotor.getPIDController().setFF(kFF_hood, slotID_hood);
    hoodMotor.getPIDController().setOutputRange(MinOutput_hood, MaxOutput_hood, slotID_hood);
  }

  /**
   * Moves hood to shoot at certain angle
   * @param position angle for the hood
   */
  public void moveHood(double position){
    if(position >= Constants.lowerEncoderSoftLimit && position <= Constants.upperEncoderSoftLimit)
      hoodMotor.getPIDController().setReference(position, ControlType.kPosition, slotID_hood);
  }

  /**
   * Moves the hood up 
   */
  public void moveHoodUp(){
    if(!(atTopLimit() || atBottomLimit()))
      hoodMotor.getPIDController().setReference(Constants.hoodUpSpeed, ControlType.kDutyCycle);
  }

  /**
   * Moves the hood down
   */
  public void moveHoodDown(){
    if(!(atTopLimit() || atBottomLimit()))
      hoodMotor.getPIDController().setReference(Constants.hoodDownSpeed, ControlType.kDutyCycle);
  }

  /**
   * Stops the hood at the given location
   */
  public void stopHood(){
    hoodMotor.getPIDController().setReference(0, ControlType.kDutyCycle);
  }

  /**
   * Returns whether the hood is at the top limit according to the top limit switch
   * @return boolean 
   */
  public boolean atTopLimit(){
    return upperHoodLimit.get();
  }

  /**
   * Returns whether the hood is at the bottom limit according the bottom limit switch
   * @return boolean
   */
  public boolean atBottomLimit(){
    return !lowerHoodLimit.get();
  }

  /**
   * Gets the current position of the encoder
   * @return double position
   */
  public double getPosition(){
    return hoodMotor.getEncoder().getPosition();
  }

  /**
   * Resets the encoder
   */
  public void resetEncoder(){
    hoodMotor.getEncoder().setPosition(0);
  }

  @Override
  public void periodic() {
    //This method will be called once per scheduler run

  }
}
