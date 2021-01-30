/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ConveyorBelt extends SubsystemBase {

  public static WPI_TalonSRX conveyorBeltMotor = new WPI_TalonSRX(Constants.conveyorBeltMotorPort);
  public static DigitalInput conveyoriRSensor1 = new DigitalInput(Constants.conveyoriRSensor1Channel);
  public static DigitalInput conveyoriRSensor2 = new DigitalInput(Constants.conveyoriRSensor2Channel);
  public static DigitalInput conveyoriRSensor3 = new DigitalInput(Constants.conveyoriRSensor3Channel);
  public static DigitalInput conveyoriRSensor4 = new DigitalInput(Constants.conveyoriRSensor4Channel);
  public static DigitalInput conveyoriRSensor5 = new DigitalInput(Constants.conveyoriRSensor5Channel);
  public static DigitalInput conveyoriRSensor6 = new DigitalInput(Constants.conveyoriRSensor6Channel);
  public static DigitalInput conveyoriRSensor7 = new DigitalInput(Constants.conveyoriRSensor7Channel);
  public static DigitalInput conveyoriRSensor8 = new DigitalInput(Constants.conveyoriRSensor8Channel);
  public static DigitalInput conveyoriRSensor9 = new DigitalInput(Constants.conveyoriRSensor9Channel);
  public static DigitalInput[] conveyoriRSensors = {conveyoriRSensor1,conveyoriRSensor2, conveyoriRSensor3, conveyoriRSensor4, conveyoriRSensor5, conveyoriRSensor6, conveyoriRSensor7, conveyoriRSensor8, conveyoriRSensor9};
  public boolean noBallsHeld = true; 
  private static int ballsHeld_temp = 0;

  private static boolean[] ballsHeldArray = {false, false, false, false, false};

  /**
   * Creates a new ConveyorBelt.
   */
  public ConveyorBelt() {
    
  }

  /**
   * Checks the number of balls held in the conveyor belt 
   */
  public void updateBallsHeld(){
    for(int c = ballsHeldArray.length-1; c >= 0; c--){
      if(ballsHeldArray[c] == true){
        ballsHeld_temp++;
      }
    }

    if(ballsHeld_temp != Constants.ballsHeld){
      Constants.ballsHeld = ballsHeld_temp; 
    }

    ballsHeld_temp = 0;
  }

  public boolean[] getBallsHeldArray(){
    return ballsHeldArray;
  }
  /**
   * Moves the conveyor belt at a set speed
   */
  public void setSpeed(double speed){
    conveyorBeltMotor.set(speed);
  }

  /**
   * Returns the index of the last IR activated
   * @return int index 0 - 4; default return -1 when none activated
   */
  public int lastIndex(){
      for(int c = ballsHeldArray.length-1; c >= 0; c--){
        if(ballsHeldArray[c] == true){
          return c;
        }
      }
      return -1;
  }

  public boolean atLimit(){
    return !conveyoriRSensor9.get();
  }

  /**
   * Returns the index of the first IR activated
   * @return int index 0 - 4; default return -1 when none activated 
   */
  public int firstIndex(){
    for(int c = 0; c < ballsHeldArray.length; c++){
      if(ballsHeldArray[c] == true){
        return c;
      }
    }
    return -1;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    noBallsHeld = true;
    for (int i = 0; i < conveyoriRSensors.length; i++) {
      if (!conveyoriRSensors[i].get()){
        noBallsHeld = false;
      }
    }

    if(conveyoriRSensor1.get() == false){
      ballsHeldArray[0] = true;
    }else{
      ballsHeldArray[0] = false;
    }

    if(conveyoriRSensor2.get() == false && conveyoriRSensor3.get() == false){
      ballsHeldArray[1] = true;
    }else{
      ballsHeldArray[1] = false;
    }

    if(conveyoriRSensor4.get() == false && conveyoriRSensor5.get() == false){
      ballsHeldArray[2] = true;
    }else{
      ballsHeldArray[2] = false;
    }

    if(conveyoriRSensor6.get() == false && conveyoriRSensor7.get() == false){
      ballsHeldArray[3] = true;
    }else{
      ballsHeldArray[3] = false;
    }
    
    if(conveyoriRSensor8.get() == false && conveyoriRSensor9.get() == false){
      ballsHeldArray[4] = true;  
    }else{
      ballsHeldArray[4] = false;
    }
    if(Constants.debugConveyorBelt) {
      int sum = 0;
      for (int i = 0; i < ballsHeldArray.length; i++) 
        if(ballsHeldArray[i]) {
          sum += 1;
        }
      System.out.println(sum);
    }
  }
}
