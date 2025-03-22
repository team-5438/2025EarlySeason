// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CoralSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class SpinCoralUntilHeldCommand extends Command {
  CoralSubsystem coralSubsystem;
  double speed;
  /** Creates a new SpinCoralUntilHeldCommand. */
  public SpinCoralUntilHeldCommand(CoralSubsystem coralSubsystem, double speed) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.coralSubsystem = coralSubsystem;
    this.speed = speed;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //coralSubsystem.coralSpinnyRight.set(speed);
    //coralSubsystem.coralSpinny.set(speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //coralSubsystem.coralSpinnyRight.set(0);
    //coralSubsystem.coralSpinny.set(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(coralSubsystem.coralDetectorValue){
      return true;
    } else return false;
  }
}
