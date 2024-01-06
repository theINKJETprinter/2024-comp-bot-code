package frc.robot.commands.auto.AutoFunctions;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Constants;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Gyro;


public class TurnTo extends CommandBase {
    // Encoder leftEncoder = new Encoder(4,5); 
    // Encoder rightEncoder = new Encoder(5);


    private final DriveBase driveBase;
    private Gyro gyro;
    private static final PIDController pid = new PIDController(0.50, 0, 0.10);
    public double setpoint;

    double goal;
    double startDegrees;
    public TurnTo(DriveBase driveSubsystem, double degrees, Gyro gyro) {
        // super( ()-> driveSubsystem.drive(
        //     MathUtil.clamp(
		// 			pid.calculate(degrees-gyro.getYaw()), 
		// 			Constants.auto.turnVarMin,
		// 			Constants.auto.turnVarMax
		// 		)/3.5,
        //         0
        // )
        // );
      setpoint = degrees;
      driveBase=driveSubsystem;
      this.gyro=gyro;
      SmartDashboard.getNumber("setpoint", degrees-gyro.getYaw());


    
    //   encoderValue = ((((3*12)//converts feet to inches
    //   /((Constants.auto.wheelRadius*3.14*2))//converts inches to wheel rotations
    //   *Constants.drive.gearRatio)// converts wheel rotations to motor rotations
    //   *Constants.auto.TicksPerRotation)//converts motor rotations to encoder ticks
    //   );

    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        pid.setSetpoint(0);
        SmartDashboard.getNumber("setpoint", setpoint);
        
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        SmartDashboard.putNumber("setpoint", setpoint);
        SmartDashboard.putNumber("pidOutput", pid.calculate(setpoint-gyro.getYaw()));
        driveBase.drive(0, pid.calculate(setpoint-gyro.getYaw()));
        
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        driveBase.drive(0, 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        SmartDashboard.getNumber("setpoint-encoder", setpoint-driveBase.getEncoder());
        return Math.abs(gyro.getYaw()-goal)<=1;
    }

      // Called repeatedly when this Command is scheduled to run
}
