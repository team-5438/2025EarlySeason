// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.AlgaeSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class SpinAlgaeCommand extends Command {
  private AlgaeSubsystem algaeSubsystem;
  private double speed;

  /** Creates a new IntakeAlgae. */
  public SpinAlgaeCommand(AlgaeSubsystem algaeSubsystem, double speed) {
    this.algaeSubsystem = algaeSubsystem;
    this.speed = speed;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    algaeSubsystem.algaeLeft.set(speed);
    algaeSubsystem.algaeRight.set(-speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    if(algaeSubsystem.holdingAlgae){
      algaeSubsystem.algaeLeft.set(0.1);
      algaeSubsystem.algaeRight.set(-0.1);
    } else{
        algaeSubsystem.algaeLeft.set(0);
        algaeSubsystem.algaeRight.set(0);
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(algaeSubsystem.holdingAlgae){
      return true;
    }
    return false;
  }
}
