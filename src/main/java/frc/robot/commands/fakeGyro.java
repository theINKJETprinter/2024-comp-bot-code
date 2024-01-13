package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.Gyro;

public class fakeGyro extends RunCommand {
    public fakeGyro(Gyro gyro, DoubleSupplier yaw, DoubleSupplier roll) {
        super(
          ()->{    
            gyro.setFake(
                yaw.getAsDouble(),
            roll.getAsDouble()
            );
          },
          gyro
        );
        addRequirements(gyro);
        }
    public boolean runsWhenDisabled(){
        return true;
    }
}
