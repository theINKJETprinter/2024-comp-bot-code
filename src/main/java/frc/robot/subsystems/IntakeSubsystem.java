 package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class IntakeSubsystem extends SubsystemBase {
  
  DoubleSolenoid intakeSolenoid;

  CANSparkMax[] intakeMotors = {
    new CANSparkMax(Constants.intake.motor1,MotorType.kBrushless),
    new CANSparkMax(Constants.intake.motor2,MotorType.kBrushless)
  };

  MotorControllerGroup intakeMotorsGroup;

  
  public IntakeSubsystem(PneumaticsSubsytem pneumatics) {

    intakeSolenoid = pneumatics.makeDoubleSolenoid(
      Constants.intake.solenoid.fwdPort, 
      Constants.intake.solenoid.revPort
    );

    for(CANSparkMax s : intakeMotors){
      s.setSmartCurrentLimit(40, 25);
      s.setInverted(true);
    }

    intakeMotorsGroup = new MotorControllerGroup(intakeMotors);

    intakeSolenoid.set(DoubleSolenoid.Value.kForward);
  }

  public InstantCommand togglePiston(){
    return new InstantCommand(
      () -> intakeSolenoid.toggle()
    );
  }

  public InstantCommand setIntakeSpeed(double speed) {
    return new InstantCommand(
      () -> intakeMotorsGroup.set(speed)
    );
  }

  public InstantCommand stopMotors(){
    return new InstantCommand(
      () -> intakeMotorsGroup.stopMotor()
    );
  }

  public InstantCommand set(DoubleSolenoid.Value val){
    return new InstantCommand(
      () -> intakeSolenoid.set(val)
    );
  }

}
