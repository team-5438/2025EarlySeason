// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class CoralSubsystem extends SubsystemBase {
  //public SparkMax coralSpinnyRight;
  //public SparkMax coralPivotRight;
  public SparkMax coralSpinnyLeft;
  public SparkMax coralPivotLeft;

  public DutyCycleEncoder coralPivotEncoderRight;
  public DutyCycleEncoder coralPivotEncoderLeft;

  public DigitalInput coralDetectorLeft;
  public boolean holdingCoralLeft;

  public PIDController coralPID;
  public double coralPivotEncoderDistanceRight;
  public double coralPivotEncoderDistanceLeft;

  public ShuffleboardTab tab;
  public GenericEntry hasCoralLeft;
  public GenericEntry leftCoralEncoderShuffle;
  public GenericEntry hasCoralRight;

  public ArmFeedforward leftCoralFeedForward;
  public ArmFeedforward leftCoralFeedForwardHolding;
  public static SparkClosedLoopController closedLoopController;

  private SparkMaxConfig motorConfig;

  /** Creates a new CoralSubsystem. */
  public CoralSubsystem() {
    //coralSpinnyRight = new SparkMax(Constants.CoralConstants.coralSpinnyRightID, MotorType.kBrushless);
    //coralPivotRight = new SparkMax(Constants.CoralConstants.coralPivotRightID, MotorType.kBrushless);
    coralSpinnyLeft = new SparkMax(Constants.CoralConstants.coralSpinnyLeftID, MotorType.kBrushless);
    coralPivotLeft = new SparkMax(Constants.CoralConstants.coralPivotLeftID, MotorType.kBrushless);
    // closedLoopController = coralPivotLeft.getClosedLoopController();
    // motorConfig = new SparkMaxConfig();
    //coralPivotEncoderRight = new DutyCycleEncoder(Constants.CoralConstants.coralPivotEncoderRightDIOPort);
    coralPivotEncoderLeft = new DutyCycleEncoder(Constants.CoralConstants.coralPivotEncoderLeftDIOPort, 1, 0.07);

    coralPID = Constants.CoralConstants.coralPIDHigh;

    coralDetectorLeft = new DigitalInput(Constants.CoralConstants.coralDetectorID);

    tab = Shuffleboard.getTab("Coral Subsystem");
    hasCoralLeft = tab.add("Holdng Coral Left", false).getEntry();
    //hasCoralRight = tab.add("Holdng Coral Right", false).getEntry();
    leftCoralEncoderShuffle = tab.add("leftCoralEncoder", 0.0).getEntry();

    leftCoralFeedForward = Constants.CoralConstants.leftCoralFeedForward;
    leftCoralFeedForwardHolding = Constants.CoralConstants.leftCoralFeedForwardHolding;
    // motorConfig.closedLoop.feedbackSensor(FeedbackSensor.kAbsoluteEncoder).p(.3).i(0).d(.01).outputRange(-.1, .1).velocityFF(0.03);
    // coralPivotLeft.configure(motorConfig,ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    //
    //coralPivotEncoderDistanceRight = coralPivotEncoderRight.get();
    coralPivotEncoderDistanceLeft = coralPivotEncoderLeft.get();
    leftCoralEncoderShuffle.setDouble(coralPivotEncoderDistanceLeft);
    holdingCoralLeft = !coralDetectorLeft.get();
    if(holdingCoralLeft) hasCoralLeft.setBoolean(true);
    else hasCoralLeft.setBoolean(false);
  }

  // public Command setCoralPosition(double setpoint){
  //   double feedForward = leftCoralFeedForwardHolding.calculate(setpoint, .1);
  //   return run(() -> closedLoopController.setReference(setpoint, ControlType.kMAXMotionPositionControl)).until(null);
  // }
}
