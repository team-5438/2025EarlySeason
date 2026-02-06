// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import static edu.wpi.first.units.Units.Meters;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class SpencerAutoAim extends Command {
  SwerveSubsystem swerveSubsystem;
  DoubleSupplier translationX;
  DoubleSupplier translationY;
  DoubleSupplier rightStickRotation;
  PIDController anglePID;
  Pose2d robotPose2d;
  double robotX;
  double robotY;
  double diffX;
  double diffY;
  double desiredAngle;
  double PIDOutput;
  /** Creates a new SpencerAutoAim. */
  public SpencerAutoAim(SwerveSubsystem swerveSubsystem, DoubleSupplier translationX, DoubleSupplier translationY, DoubleSupplier rightStickRotation) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.swerveSubsystem = swerveSubsystem;
    this.translationX = translationX;
    this.translationY = translationY;
    this.rightStickRotation = rightStickRotation;
    anglePID = Constants.anglePID;
    addRequirements(swerveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    DoubleSupplier pIDOutputDoubleSupplier = () -> {
      robotPose2d = swerveSubsystem.getPose();
      robotX = robotPose2d.getMeasureX().in(Meters);
      robotY = robotPose2d.getMeasureY().in(Meters);
      diffX = Constants.HUB_X - robotX;
      diffY = Constants.HUB_Y - robotY;
      desiredAngle = (diffX == 0) ? 0 : Math.atan2(diffY, diffX);
      PIDOutput = anglePID.calculate(robotPose2d.getRotation().getRadians(), desiredAngle);
      return PIDOutput*0.2;
    };                
    swerveSubsystem.driveCommand(translationX, translationY, pIDOutputDoubleSupplier);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {



    /*
     * get robot pose
     * get robot x and y
     * subtract robot x from hoop x
     * subtract robot y from hoop y
     * arctan(diff) = desired angle
     * (edge case: if diffX = 0, then we know we must point at 0 theta)
     * PID get to desired angle
     * 
     */

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(rightStickRotation.getAsDouble() != 0){
      return true;
    }
    return false;
  }
}
