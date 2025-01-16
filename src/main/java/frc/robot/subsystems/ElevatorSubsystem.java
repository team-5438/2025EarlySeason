// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

public class ElevatorSubsystem extends SubsystemBase {
  // public SparkMax left_back_drive = new CANSparkMax(Constants.ElevatorConstants.E1, MotorType.kBrushless);
  // public SparkMax left_front_drive = new CANSparkMax(Constants.ElevatorConstants.E2, MotorType.kBrushless);

  SparkMax elevator1;
  SparkMax elevator2;

  SparkMaxConfig elevator2Config;

  // Creates a new ElevatorSubsystem.
  public ElevatorSubsystem() {
    elevator1 = new SparkMax(Constants.ElevatorConstants.E1, null);
    elevator2 = new SparkMax(Constants.ElevatorConstants.E2, null);
    elevator2Config.follow(elevator1);
    elevator2.configure(elevator2Config, null, null);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
