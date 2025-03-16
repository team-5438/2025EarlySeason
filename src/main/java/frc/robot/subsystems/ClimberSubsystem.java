// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimberSubsystem extends SubsystemBase {
  public TalonFX climber;
  /** Creates a new ClimberSubsystem. */
  public ClimberSubsystem() {
    climber = new TalonFX(Constants.ClimberConstants.climberID);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
