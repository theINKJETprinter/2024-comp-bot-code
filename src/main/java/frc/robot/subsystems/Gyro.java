package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Gyro extends SubsystemBase{
  
  // private final ADXRS450_Gyro gyro = new ADXRS450_Gyro();

  private final AHRS gyro = new AHRS();
  private boolean isFake=false;
  private double fakeRoll;
  private double fakeYaw; 

  XboxController gyroController = new XboxController(1);

  

  public Gyro(){
    
    addChild("Gyro", gyro);
    gyro.calibrate();
  }

  public void reset() {
    gyro.reset();
  }

  public void log() {
    SmartDashboard.putNumber("Gyro", gyro.getAngle());
    SmartDashboard.putNumber("yaw", getYaw());
    SmartDashboard.putNumber("Roll", getRoll());
  }

  public double getHeading() {
    return gyro.getAngle();
  }

  public double getRoll() {
    if (isFake){
      return fakeRoll;
    }
    else{
      return gyro.getRoll();
    }
  }
  public double getYaw(){
    if (isFake){
      return fakeYaw;
    }
    else{
      return gyro.getYaw();
    }
  }

  public void setFakeStatus(){
    isFake=true;
  }

  public void setFake(double yaw, double roll){
    if (isFake){
      fakeRoll=roll;
      fakeYaw=yaw;
        
    }
    log();

  }
}
