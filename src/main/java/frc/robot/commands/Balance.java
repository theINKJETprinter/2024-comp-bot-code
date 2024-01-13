package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;

import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Gyro;

public class Balance extends CommandBase {

    private DriveBase drive;
    private final Gyro gyro;

    private final PIDController pid = new PIDController(0.12, 0, 0.01);

    /** Creates a new BalanceRobot. */
    public Balance(DriveBase driveTrain, Gyro gyro) {
        // Use addRequirements() here to declare subsystem dependencies.
        drive = driveTrain;
        this.gyro = gyro;
        addRequirements(gyro);
        addRequirements(drive);
        pid.setTolerance(1);

        // lowPass = LinearFilter.movingAverage(3);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        pid.setSetpoint(0);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        var controllerOutput = pid.calculate(gyro.getRoll());

        drive.drive(controllerOutput, 0);
        

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        drive.drive(0, 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return pid.atSetpoint();
    }

}

