package frc.robot.commands.turret;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class TurretAlign extends CommandBase {
  private double vision_error;  //in pixels

  public TurretAlign() {
    addRequirements(RobotContainer.turret);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    vision_error = (double)Constants.visionTable.getEntry("2020-High-Target_x_offset").getNumber(Constants.offset);

    // System.out.println("Vision Error " + vision_error);
    if(Math.abs(vision_error - Constants.offset) < 50){
      Constants.leftTurretSpeed = Constants.leftTurretLowSpeed;
      Constants.rightTurretSpeed = Constants.rightTurretLowSpeed;
    }else{
      Constants.leftTurretSpeed = Constants.leftTurretHighSpeed;
      Constants.rightTurretSpeed = Constants.rightTurretHighSpeed;
    }

    if((vision_error - Constants.offset) > 0){
      RobotContainer.turret.moveTurretRight();
    }else if((vision_error - Constants.offset) < 0){
      RobotContainer.turret.moveTurretLeft();
    }else{
      RobotContainer.turret.stopTurret();
    }

  }

  @Override
  public void end(boolean interrupted) {
    // RobotContainer.drivetrain.setLeftMotorSpeed(0);
    // RobotContainer.drivetrain.setRightMotorSpeed(0);
    RobotContainer.turret.aimTurret(Constants.lastTurretPosition);
  }

  // Returns true when the command should end
  @Override
  public boolean isFinished() {
    Constants.valid  = Constants.visionTable.getEntry("valid").getBoolean(false);
    if(!Constants.valid){
      System.out.println("Not valid");
      return true; 
    }else if(((vision_error-Constants.offset) >= -1) && ((vision_error-Constants.offset) <= 1)){
      Constants.lastTurretPosition = RobotContainer.turret.getPosition(); 
      System.out.println("Centered");
      return true; 
    }else{
      System.out.println("Valid");
      return false; 
    } 
  }
}


