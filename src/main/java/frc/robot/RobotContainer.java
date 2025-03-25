// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;



import frc.robot.Constants.ClimberConstants;
import frc.robot.Constants.DrivebaseConstants.TargetSide;
import frc.robot.commands.ManualClimberCommand;
import frc.robot.commands.ManualCoralCommand;
import frc.robot.commands.ManualElevatorStickCommand;
import frc.robot.commands.MusicCommand;
import frc.robot.commands.SetCoralCommand;
import frc.robot.commands.SetElevatorCommand;
import frc.robot.commands.SpinCoral;
import frc.robot.commands.SpinCoralUntilHeldCommand;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.CoralSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import frc.robot.subsystems.swervedrive.Vision;
import swervelib.SwerveDrive;

import java.io.File;
import java.util.function.DoubleSupplier;

import com.ctre.phoenix6.Orchestra;
import com.ctre.phoenix6.configs.AudioConfigs;
import com.ctre.phoenix6.hardware.ParentDevice;
import com.ctre.phoenix6.hardware.TalonFX;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.PS5Controller;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  public ElevatorSubsystem elevatorSubsystem;
  public CoralSubsystem coralSubsystem;
  public ClimberSubsystem climberSubsystem;
  public Vision vision;
  
  //public Orchestra orchestra;
  public SwerveDrive swerveDrive;
  public SwerveSubsystem swerveSubsystem = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(), "swerve"));
  public final CommandXboxController driver = new CommandXboxController(Constants.Driver.id);
  public final CommandPS5Controller operator = new CommandPS5Controller(Constants.Operator.id);
  
  public ManualCoralCommand manualCoralCommand;
  public ManualElevatorStickCommand manualElevatorStickCommand;


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    elevatorSubsystem = new ElevatorSubsystem();
    coralSubsystem = new CoralSubsystem();
    climberSubsystem = new ClimberSubsystem();

    manualCoralCommand = new ManualCoralCommand(coralSubsystem, operator);
    manualElevatorStickCommand = new ManualElevatorStickCommand(elevatorSubsystem, operator);

    /*orchestra = new Orchestra();
    orchestra.addInstrument(elevatorSubsystem.LElevator, 0);
    orchestra.addInstrument(elevatorSubsystem.RElevator, 0);
    orchestra.addInstrument((TalonFX) swerveSubsystem.getSwerveDrive().getModules()[0].getDriveMotor().getMotor(), 0);
    orchestra.addInstrument((TalonFX) swerveSubsystem.getSwerveDrive().getModules()[1].getDriveMotor().getMotor(), 0);
    orchestra.addInstrument((TalonFX) swerveSubsystem.getSwerveDrive().getModules()[2].getDriveMotor().getMotor(), 0);
    orchestra.addInstrument((TalonFX) swerveSubsystem.getSwerveDrive().getModules()[3].getDriveMotor().getMotor(), 0);
    var status = orchestra.loadMusic("happy.chrp");*/
    // Configure the trigger bindings
    configureBindings();
    
    namedCommands();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    /* translation controls for the robot */
    DoubleSupplier speedMod = () -> driver.getRawAxis(XboxController.Axis.kRightTrigger.value) == 1 ? 2.5 : 1;
    /* NOTE: the division is used to reduce the speed of the robot when the left trigger is held */
    DoubleSupplier translationX = () -> -MathUtil.applyDeadband(driver.getLeftY(), Constants.Driver.leftStick.Y) / speedMod.getAsDouble();
    DoubleSupplier translationY = () -> -MathUtil.applyDeadband(driver.getLeftX(), Constants.Driver.leftStick.X) / speedMod.getAsDouble();

    /* rotation controls for the robot */
    DoubleSupplier angularRotationX = () -> -MathUtil.applyDeadband(driver.getRawAxis(4), Constants.Driver.rightStick.X) / speedMod.getAsDouble();

    Command driverControls = swerveSubsystem.driveCommand(translationX, translationY, angularRotationX);
    swerveSubsystem.setDefaultCommand(driverControls);

    driver.y().onTrue(new InstantCommand(swerveSubsystem::zeroGyro)); //zero gyro command
    driver.a().onTrue(new InstantCommand(elevatorSubsystem.elevatorEncoder::reset));  //reset elevator
    driver.x().whileTrue(new InstantCommand((swerveSubsystem::getPose)));

    // driver.a().and(driver.leftBumper()).whileTrue(swerveSubsystem.alignToReefScore(20, TargetSide.LEFT));
    // driver.x().and(driver.rightBumper()).whileTrue(swerveSubsystem.alignToReefScore(20,TargetSide.RIGHT));

    // driver.y().and(driver.leftBumper()).whileTrue(swerveSubsystem.alignToReefScore(21,TargetSide.LEFT));
    // driver.y().and(driver.rightBumper()).whileTrue(swerveSubsystem.alignToReefScore(21,TargetSide.RIGHT));

    // driver.b().and(driver.leftBumper()).whileTrue(swerveSubsystem.alignToReefScore(22,TargetSide.LEFT));
    // driver.b().and(driver.rightBumper()).whileTrue(swerveSubsystem.alignToReefScore(22,TargetSide.RIGHT));

    // driver.a().and(driver.leftBumper()).whileTrue(swerveSubsystem.alignToReefScore(6,TargetSide.LEFT));
    // driver.a().and(driver.rightBumper()).whileTrue(swerveSubsystem.alignToReefScore(6,TargetSide.RIGHT));

    // driver.leftBumper().whileTrue(swerveSubsystem.alignToReefScore(TargetSide.LEFT));
    // driver.rightBumper().whileTrue(swerveSubsystem.alignToReefScore(TargetSide.RIGHT));

    driver.leftBumper().whileTrue(Commands.run(()->{swerveSubsystem.alignToReefScore(()-> swerveSubsystem.getReefTargetTagID(), TargetSide.LEFT).schedule();}));
    driver.rightBumper().whileTrue(Commands.run(()->{swerveSubsystem.alignToReefScore(()-> swerveSubsystem.getReefTargetTagID(), TargetSide.RIGHT).schedule();}));

    operator.triangle().onTrue(new SequentialCommandGroup(   //L4 preset
      new SetElevatorCommand(elevatorSubsystem, Constants.ElevatorConstants.ElevatorL4)
      // Commands.waitSeconds(0.5),
      // new SetCoralCommand(coralSubsystem, 0.7, false)
      ));
    operator.circle().onTrue(new SequentialCommandGroup(    //L3 preset
      new SetElevatorCommand(elevatorSubsystem, Constants.ElevatorConstants.ElevatorL3)
      // Commands.waitSeconds(0.5),
      // new SetCoralCommand(coralSubsystem, 0.72, false)
      ));
    operator.cross().onTrue(new SequentialCommandGroup(    //L2 preset
      new SetElevatorCommand(elevatorSubsystem, Constants.ElevatorConstants.ElevatorL2)
      // Commands.waitSeconds(0.5),
      // new SetCoralCommand(coralSubsystem, 0.72, false)
      ));
    operator.square().onTrue(new SequentialCommandGroup(    //L1 preset
      new SetElevatorCommand(elevatorSubsystem, Constants.ElevatorConstants.ElevatorL1)
      // Commands.waitSeconds(0.5),
      // new SetCoralCommand(coralSubsystem, 0.72, false)
      ));
    //operator.options().onTrue(new SetCoralCommand(coralSubsystem, 0.75, false));

    /*operator.PS().onTrue(new SequentialCommandGroup(
      new InstantCommand(orchestra::play),
      new WaitCommand(200),
      new InstantCommand(orchestra::stop)
      ));
    operator.options().onTrue(new InstantCommand(orchestra::stop));
    */


    operator.options().whileTrue(new StartEndCommand(() -> coralSubsystem.coralSpinny.set(1), () -> coralSubsystem.coralSpinny.set(0)));
    operator.create().whileTrue(new StartEndCommand(() -> coralSubsystem.coralSpinny.set(-0.2), () -> coralSubsystem.coralSpinny.set(0)));   //coral 
    operator.touchpad().onTrue(new SequentialCommandGroup(          //intake preset
        new SetElevatorCommand(elevatorSubsystem, Constants.ElevatorConstants.elevatorIntake),
        Commands.waitSeconds(.5),
        Commands.print("Finished elevator"))
        // new SetCoralCommand(coralSubsystem, 0.82, false),
        // Commands.print("finished coral"),
        //new SpinCoralUntilHeldCommand(coralSubsystem, -0.15).withTimeout(10),
        //Commands.print("spun coral"))
    );
    operator.povLeft().whileTrue(new ManualClimberCommand(climberSubsystem, -0.9));
    operator.povRight().whileTrue(new ManualClimberCommand(climberSubsystem, 0.9));

    operator.PS().onTrue(new SetCoralCommand(coralSubsystem, 0.329));
    
  }

  private void namedCommands(){
    NamedCommands.registerCommand("zero gyro", new InstantCommand(swerveSubsystem::zeroGyro));
    NamedCommands.registerCommand("L4", new SequentialCommandGroup(
      new SetElevatorCommand(elevatorSubsystem, Constants.ElevatorConstants.ElevatorL4),
      Commands.waitSeconds(0.5),
      new SetCoralCommand(coralSubsystem, 0.5),
      Commands.waitSeconds(0.5),
      new SpinCoral(coralSubsystem, 0.5).withTimeout(2)
      //new SetCoralCommand(coralSubsystem, 0.5, true)
    ));
    NamedCommands.registerCommand("L3", new SequentialCommandGroup(
      new SetElevatorCommand(elevatorSubsystem, Constants.ElevatorConstants.ElevatorL3),
      Commands.waitSeconds(0.5),
      new SetCoralCommand(coralSubsystem, 0.5),
      Commands.waitSeconds(0.5),
      new SpinCoral(coralSubsystem, 0.5).withTimeout(2)
      //new SetCoralCommand(coralSubsystem, 0.5, true)
    ));
    NamedCommands.registerCommand("L2", new SequentialCommandGroup(
      new SetElevatorCommand(elevatorSubsystem, Constants.ElevatorConstants.ElevatorL2),
      Commands.waitSeconds(0.5),
      new SetCoralCommand(coralSubsystem, 0.80),
      Commands.waitSeconds(0.5),
      new SpinCoral(coralSubsystem, 0.5).withTimeout(2)
      //new SetCoralCommand(coralSubsystem, 0.5, true)
    ));
    NamedCommands.registerCommand("L1", new SequentialCommandGroup(
      new SetElevatorCommand(elevatorSubsystem, Constants.ElevatorConstants.ElevatorL1),
      Commands.waitSeconds(0.5),
      new SetCoralCommand(coralSubsystem, 0.5),
      Commands.waitSeconds(0.5),
      new SpinCoral(coralSubsystem, 0.5).withTimeout(2)
      //new SetCoralCommand(coralSubsystem, 0.5, true)
    ));
    NamedCommands.registerCommand("coral station", new SequentialCommandGroup(
      new SetElevatorCommand(elevatorSubsystem, Constants.ElevatorConstants.elevatorIntake),
      Commands.waitSeconds(0.5),
      new SetCoralCommand(coralSubsystem, 0.82)
    ));
    NamedCommands.registerCommand("drop coral", new StartEndCommand(() -> coralSubsystem.coralSpinny.set(-0.8), () -> coralSubsystem.coralSpinny.set(0)).withTimeout(3));


  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *  
   * @return the command to run in autonomous
   */
 public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    //return Autos.exampleAuto(swerveSubsystem);
    return swerveSubsystem.getAutonomousCommand("mid 1 coral");
 }
}
