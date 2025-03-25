// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.PS5Controller;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import frc.robot.Constants;
import frc.robot.subsystems.CoralSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class ManualElevatorStickCommand extends Command {
  /** Creates a new ManualCoralCommand. */
  private ElevatorSubsystem elevatorSubsystem;
  private CommandPS5Controller operator;
  private double pivotSpeed;
  public ManualElevatorStickCommand(ElevatorSubsystem elevatorSubsystem, CommandPS5Controller operator) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.elevatorSubsystem = elevatorSubsystem;
    this.operator = operator;

    addRequirements(elevatorSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if((elevatorSubsystem.elevatorEncoderDistance <= 0.7 && operator.getRightY() > 0) || (elevatorSubsystem.elevatorEncoderDistance >= 53 && operator.getRightY() < 0)){
      elevatorSubsystem.elevator.set(0);
    } else{
      pivotSpeed = MathUtil.applyDeadband(-operator.getRightY(), Constants.Operator.rightStick.Y);
      pivotSpeed = MathUtil.clamp(pivotSpeed, -0.4, 0.53);
      //pivotSpeed += elevatorSubsystem.elevatorFeedForward.getKs();
      //pivotSpeed += elevatorSubsystem.elevatorFeedForward.getKg();
      pivotSpeed += 0.025;
      elevatorSubsystem.elevator.set(-pivotSpeed);
    }
    // if(coralSubsystem.holdingCoralLeft){
    //   pivotSpeedRight = MathUtil.applyDeadband(-operator.getRightY(), Constants.Operator.rightStick.Y);
    //   pivotSpeedRight = MathUtil.clamp(pivotSpeedRight, -0.05, 0.05);
    //   //coralSubsystem.coralPivotRight.set(pivotSpeedRight);

    //   pivotSpeedLeft = MathUtil.applyDeadband(-operator.getLeftY(), Constants.Operator.leftStick.Y);
    //   pivotSpeedLeft = MathUtil.clamp(pivotSpeedLeft, -0.03, 0.15);
    //   if(coralSubsystem.coralPivotEncoderDistanceLeft < 0.795) pivotSpeedLeft += coralSubsystem.leftCoralFeedForwardHolding.calculate(coralSubsystem.coralPivotEncoderDistanceLeft, pivotSpeedLeft);
    //   coralSubsystem.coralPivotLeft.set(pivotSpeedLeft);
    // }
    //else{
      //coralSubsystem.coralPivotRight.set(pivotSpeedRight);
      //pivotSpeed = MathUtil.applyDeadband(-operator.getRightY(), Constants.Operator.rightStick.Y);
      //pivotSpeed = MathUtil.clamp(pivotSpeed, -0.45, 0.6);
      //pivotSpeed += elevatorSubsystem.elevatorFeedForward.calculate(elevatorSubsystem.elevatorEncoderDistance, pivotSpeed);
       //elevatorSubsystem.RElevator.set(-pivotSpeed);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //coralSubsystem.coralPivotRight.set(0);
    elevatorSubsystem.elevator.set(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
