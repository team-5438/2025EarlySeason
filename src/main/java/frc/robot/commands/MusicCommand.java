// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.ctre.phoenix6.Orchestra;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.swervedrive.SwerveSubsystemOG;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class MusicCommand extends Command {
  Orchestra orchestra;
  ElevatorSubsystem elevatorSubsystem;
  /** Creates a new MusicCommand. */
  public MusicCommand(ElevatorSubsystem elevatorSubsystem) {
    orchestra = new Orchestra();
    this.elevatorSubsystem = elevatorSubsystem;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    /*orchestra.addInstrument(elevatorSubsystem.RElevator);
    orchestra.addInstrument(elevatorSubsystem.LElevator);
    var status = orchestra.loadMusic("imperialmarch.chrp");
    orchestra.play();*/
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
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
