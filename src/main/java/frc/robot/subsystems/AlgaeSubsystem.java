// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class AlgaeSubsystem extends SubsystemBase {
  public SparkMax algaeLeft;
  public SparkMax algaeRight;

  public SparkMax algaePivot;
  public PIDController algaePivotPID;
  public DutyCycleEncoder algaeEncoder;
  public double algaeEncoderDistance;

  public DigitalInput algaeDetector;
  public boolean holdingAlgae;

  public ShuffleboardTab tab;
  public GenericEntry hasAlgaeShuffle;
  public GenericEntry algaeEncoderShuffle;

  /** Creates a new AlgaeSubsystem. */
  public AlgaeSubsystem() {
    algaeRight = new SparkMax(Constants.AlgaeConstants.algaeRightID, MotorType.kBrushless);
    algaeLeft = new SparkMax(Constants.AlgaeConstants.algaeLeftID, MotorType.kBrushless);

    algaePivot = new SparkMax(Constants.AlgaeConstants.algaePivotID, MotorType.kBrushless);
    algaeEncoder = new DutyCycleEncoder(Constants.AlgaeConstants.algaePivotEncoderDIOPort);
    algaePivotPID = Constants.AlgaeConstants.algaePivotPID;

    algaeDetector = new DigitalInput(Constants.AlgaeConstants.algaeDetectorID);

    tab = Shuffleboard.getTab("Algae Subsystem");
    hasAlgaeShuffle = tab.add("Holding Algae", false).getEntry();
    algaeEncoderShuffle = tab.add("algaeEncoder", 0.0).getEntry();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    algaeEncoderDistance = Math.abs(algaeEncoder.get());
    algaeEncoderShuffle.setDouble(algaeEncoderDistance);

    holdingAlgae = !algaeDetector.get(); //returns true when not seeing anything
    hasAlgaeShuffle.setBoolean(holdingAlgae); //make it true when seeing something
     
  }
}
