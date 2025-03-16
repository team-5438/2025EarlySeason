// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.ElevatorSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class ManualElevatorCommand extends Command {
  private ElevatorSubsystem elevatorSubsystem;
  private double speed;
  /** Creates a new ElevatorL4. */
  public ManualElevatorCommand(ElevatorSubsystem elevatorSubsystem, double speed) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.elevatorSubsystem = elevatorSubsystem;
    this.speed = speed;
    addRequirements(elevatorSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    elevatorSubsystem.elevatorEncoder.getDistance();
    if(elevatorSubsystem.elevatorEncoderDistance >= 1 || speed >= 0){
      //elevatorSubsystem.LElevator.set(speed);
      elevatorSubsystem.RElevator.set(-speed);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    elevatorSubsystem.RElevator.set(0);
    //elevatorSubsystem.RElevator.set(-elevatorSubsystem.elevatorPID.calculate(elevatorSubsystem.elevatorEncoderDistance, elevatorSubsystem.elevatorEncoderDistance));
    //new SetElevatorCommand(elevatorSubsystem, elevatorSubsystem.ElevatorEncoderDistance);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if((elevatorSubsystem.elevatorEncoderDistance <= 0.7 && speed < 0) || (elevatorSubsystem.elevatorEncoderDistance >= 52 && speed > 0)){
      return true;
    }
    return false;
  }
}
