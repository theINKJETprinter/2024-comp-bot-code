// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.*;
import frc.robot.subsystems.*;
import frc.robot.commands.auto.AutoSelector;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...


  final Limelight limeLight = new Limelight();
  final Pneumatics pneumatics = new Pneumatics();
  final DriveBase m_driveSubsystem = new DriveBase();
  final Intake intake = new Intake(pneumatics);
  final Bucket bucket = new Bucket(pneumatics);
  final Gyro gyro = new Gyro();

  final AutoSelector selector = new AutoSelector(m_driveSubsystem, intake, bucket, gyro);

  final CommandXboxController movementJoystick = new CommandXboxController(Constants.MOVEMENT_JOYSTICK);
  final CommandXboxController manipulatorJoystick = new CommandXboxController(Constants.MANIPULATOR_JOYSTICK);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    
    //start cameraServer
    CameraServer.startAutomaticCapture();
    
    m_driveSubsystem.setDefaultCommand(
      new ArcadeDrive(
            m_driveSubsystem,
            () -> ((-movementJoystick.getLeftTriggerAxis() + movementJoystick.getRightTriggerAxis())),
            () -> (-movementJoystick.getLeftX() )
      ));

    gyro.log();

  }

  private void configureButtonBindings() {
    manipulatorJoystick.leftBumper() //intake
    .onTrue(intake.setSpeed(Constants.intake.fwdSpeed))
    .onFalse(intake.stopMotors());
    
    manipulatorJoystick.rightBumper()//eject
    .onTrue(intake.setSpeed(Constants.intake.revSpeed))
    .onFalse(intake.stopMotors());

    manipulatorJoystick.x()
    .onTrue(bucket.toggle());

    manipulatorJoystick.a()
    .onTrue(intake.togglePiston());

    manipulatorJoystick.y()
    .onTrue(pneumatics.toggleCompressor());
  }

  // Auto Stuff
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    //return the selector's selected command
    return selector.getSelected();
  }
}
