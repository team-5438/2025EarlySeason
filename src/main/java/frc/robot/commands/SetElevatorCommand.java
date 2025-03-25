// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.ElevatorSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class SetElevatorCommand extends Command {
  private ElevatorSubsystem elevatorSubsystem;
  private double encoderSetPoint;
  private double  setPointFinal;
  /** Creates a new ElevatorL4. */
  public SetElevatorCommand(ElevatorSubsystem elevatorSubsystem, double encoderSetPoint) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.elevatorSubsystem = elevatorSubsystem;
    this.encoderSetPoint = encoderSetPoint;
    addRequirements(elevatorSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //elevatorSubsystem.LElevator.set(elevatorSubsystem.elevatorPID.calculate(elevatorSubsystem.ElevatorEncoderDistance, encoderSetPoint));
    elevatorSubsystem.elevator.set(-elevatorSubsystem.elevatorPID.calculate(elevatorSubsystem.elevatorEncoderDistance, encoderSetPoint));
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    elevatorSubsystem.elevator.set(-0.025);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    double aimError = Math.abs(elevatorSubsystem.elevatorEncoderDistance - encoderSetPoint);
    if (aimError <= Constants.ElevatorConstants.elevatorTolerance) {
        //setPointFinal = elevatorSubsystem.elevatorEncoderDistance;
        return true;
    }
    return false;
  }
}
