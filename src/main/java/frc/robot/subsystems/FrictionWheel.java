/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class FrictionWheel extends SubsystemBase {
  private static CANSparkMax frictionWheelMotor = new CANSparkMax(Constants.frictionWheelMotorPort, MotorType.kBrushless);

  private static DoubleSolenoid frictionWheelPiston = new DoubleSolenoid(Constants.doubleSolenoidPort1, Constants.doubleSolenoidPort2);

  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  public final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
  private final ColorMatch m_colorMatcher = new ColorMatch();
  private String globalColor;

  final double kP = 0.0001;
  final double kI = 0;
  final double kD = 0;
  final double kFF = 0;
  final double maxAccel = 1500;
  final double maxVel = 5000;
  final int slotID_frictionwheel = 0;

  
  /**
   * Creates a new FrictionWheel.
   */

  public FrictionWheel() {
    m_colorMatcher.addColorMatch(Constants.kBlueTarget);
    m_colorMatcher.addColorMatch(Constants.kGreenTarget);
    m_colorMatcher.addColorMatch(Constants.kRedTarget);
    m_colorMatcher.addColorMatch(Constants.kYellowTarget);

    frictionWheelMotor.getPIDController().setP(kP, slotID_frictionwheel);
    frictionWheelMotor.getPIDController().setI(kI, slotID_frictionwheel);
    frictionWheelMotor.getPIDController().setD(kD, slotID_frictionwheel);
    frictionWheelMotor.getPIDController().setFF(kFF, slotID_frictionwheel);
    frictionWheelMotor.getPIDController().setSmartMotionMaxAccel(maxAccel, slotID_frictionwheel);
    frictionWheelMotor.getPIDController().setSmartMotionMaxVelocity(maxVel, slotID_frictionwheel);
  }

  /**
   * Returns the color detected by the color sensor
   */
  private void detectColor() {
    final int proximity = m_colorSensor.getProximity();
    final Color detectedColor = m_colorSensor.getColor();
    final ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);
    String colorString = "initialized colorstring";
    if (proximity > Constants.colorSensorOptimalRange)
    {
      if (match.color == Constants.kBlueTarget) 
      {
        colorString = "Blue";
      } else if (match.color == Constants.kRedTarget) 
      {
        colorString = "Red";
      } else if (match.color == Constants.kGreenTarget) 
      {
        colorString = "Green";
      } else if (match.color == Constants.kYellowTarget) 
      {
        colorString = "Yellow";
      }
      else
      {
        colorString = "Unknown";
      }
      
      globalColor = colorString;


      final Color detectedColor_temp = m_colorSensor.getColor();
      final ColorMatchResult match_temp = m_colorMatcher.matchClosestColor(detectedColor_temp);
      String colorString_temp = "initialized coloring temp";
      if (match_temp.color == Constants.kGreenTarget) {
        colorString_temp = "Blue";
      } else if (match_temp.color == Constants.kRedTarget) {
        colorString_temp = "Red";
      } else if (match_temp.color == Constants.kGreenTarget) {
        colorString_temp = "Green";
      } else if (match_temp.color == Constants.kYellowTarget) {
        colorString_temp = "Yellow";
      }


      if (!colorString_temp.equals(colorString))
      {

          if (colorString.equals("Yellow"))
          {
            if (colorString_temp.equals("Blue") && !colorString_temp.equals("Green"))
            {
                globalColor = "Blue";
            }
          }
          else if (colorString.equals("Green"))
          {
            if (colorString_temp.equals("Red") && !colorString_temp.equals("Yellow"))
            {
              globalColor = "Red";
            }
          }
          else if (colorString.equals("Red"))
          {
            if (colorString_temp.equals("Yellow"))
            {
              globalColor = "Yellow";
            }
          }
          else if (colorString.equals("Blue"))
          {
            if (colorString_temp.equals("Green"))
            {
                globalColor = "Green";
            }
          }
      }
    }
    else
    {
      globalColor = "Too Far Away";
    }
  }

  /**
   * Give the color detected by the color sensor
   * @return String color
   */
  public String getColor(){
    return globalColor;
  }

  /**
   * Extends the FrictionWheel mechanism
   */
  public void extendMechanism(){
    frictionWheelPiston.set(Value.kForward);
  }

  /**
   * Retracts the FrictionWheel mechanism
   */
  public void retractMechanism(){
    frictionWheelPiston.set(Value.kReverse); 
  }

  /**
   * Sets the speed of the friction wheel
   * @param speed
   */
  public void setMotorSpeed(double speed){
    frictionWheelMotor.getPIDController().setReference(speed, ControlType.kDutyCycle, slotID_frictionwheel);
  }

  /**
   * Sets the position of the friction wheel
   * @param position
   */
  public void stop(){
    frictionWheelMotor.getPIDController().setReference(0, ControlType.kSmartVelocity, slotID_frictionwheel);
  }

  /**
   * Gets the color to turn to in position control
   */
  private void colorForPositionControl(){
    String message = DriverStation.getInstance().getGameSpecificMessage();

    if(message.length() > 0){
      if(message.charAt(0) == 'B'){
        Constants.fieldColorString = "Blue";
      }else if(message.charAt(0) == 'G'){
        Constants.fieldColorString =  "Green";
      }else if(message.charAt(0) == 'R'){
        Constants.fieldColorString = "Red";
      }else if(message.charAt(0) == 'Y'){
        Constants.fieldColorString = "Yellow";
      }
    }
  }

  @Override
  public void periodic() {
    detectColor();

    if(!Constants.fieldColorString.equals("Green") 
    || !Constants.fieldColorString.equals("Red")
    || !Constants.fieldColorString.equals("Blue")
    || !Constants.fieldColorString.equals("Yellow")){
      colorForPositionControl(); 
    }
  }

}
