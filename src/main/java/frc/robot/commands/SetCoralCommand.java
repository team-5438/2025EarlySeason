// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.CoralSubsystem;



/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class SetCoralCommand extends Command {
  private CoralSubsystem coralSubsystem;
  private double encoderSetPoint;
  /** Creates a new CoralPreset. */
  public SetCoralCommand(CoralSubsystem coralSubsystem, double encoderSetPoint, boolean right) {
    this.coralSubsystem = coralSubsystem;
    this.encoderSetPoint = encoderSetPoint;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(coralSubsystem.coralDetectorValue) coralSubsystem.coralPivot.set(coralSubsystem.coralPIDWith.calculate(coralSubsystem.coralEncoderDistance, encoderSetPoint));
    else coralSubsystem.coralPivot.set(coralSubsystem.coralPIDWithout.calculate(coralSubsystem.coralEncoderDistance, encoderSetPoint));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    coralSubsystem.coralPivot.set(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    double aimError = Math.abs(coralSubsystem.coralEncoderDistance - encoderSetPoint);
    if(aimError <= Constants.CoralConstants.coralTolerance) return true;
    return false;

  }
}