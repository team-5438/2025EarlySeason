// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.lang.management.OperatingSystemMXBean;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import frc.robot.Constants;
import frc.robot.subsystems.CoralSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class ManualCoralCommand extends Command {
  private CoralSubsystem coralSubsystem;
  private CommandPS5Controller operator;
  private double pivotSpeed;

  /** Creates a new TempCoralCommand. */
  public ManualCoralCommand(CoralSubsystem coralSubsystem, CommandPS5Controller operator) {
    this.coralSubsystem = coralSubsystem;
    this.operator = operator;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(coralSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if((coralSubsystem.coralEncoderDistance <= 0.160 && operator.getLeftY() < 0) || (coralSubsystem.coralEncoderDistance >= 0.485 && operator.getLeftY() > 0)) coralSubsystem.coralPivot.set(0);
    else{
      pivotSpeed = MathUtil.applyDeadband(operator.getLeftY(), Constants.Operator.leftStick.Y);
      pivotSpeed = MathUtil.clamp(pivotSpeed, -0.5, 0.2);

      coralSubsystem.coralPivot.set(pivotSpeed);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
