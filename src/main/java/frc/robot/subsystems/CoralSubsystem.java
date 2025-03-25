// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class CoralSubsystem extends SubsystemBase {
  /** Creates a new TempCoralSubsystem. */
  public TalonFX coralPivot;
  public SparkMax coralSpinny;

  public DutyCycleEncoder coralEncoder;
  public DigitalInput coralDetector;

  public PIDController coralPIDWith;
  public PIDController coralPIDWithout;

  public ShuffleboardTab tab;
  public GenericEntry coralEncoderEntry;
  public GenericEntry coralDetectorEntry;

  public double coralEncoderDistance;
  public boolean coralDetectorValue;

  public CoralSubsystem() {
    coralPivot = new TalonFX(Constants.CoralConstants.coralPivotID);
    coralSpinny = new SparkMax(Constants.CoralConstants.coralSpinnyID, MotorType.kBrushless);

    coralEncoder = new DutyCycleEncoder(Constants.CoralConstants.coralEncoderID);
    coralDetector = new DigitalInput(Constants.CoralConstants.coralDetectorID);

    coralPIDWith = Constants.CoralConstants.coralPIDWith;
    coralPIDWithout = Constants.CoralConstants.coralPIDWithout;

    tab = Shuffleboard.getTab("New Coral Subsystem");
    coralEncoderEntry = tab.add("coralEncoderEntry", 0.0).getEntry();
    coralDetectorEntry = tab.add("coralDetectorentry", false).getEntry();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    coralEncoderDistance = coralEncoder.get();
    coralEncoderEntry.setDouble(coralEncoderDistance);

    coralDetectorValue = !coralDetector.get();    //will return true when holding
    coralDetectorEntry.setBoolean(coralDetectorValue);
  }
}
