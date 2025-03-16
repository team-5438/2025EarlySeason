// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class ElevatorSubsystem extends SubsystemBase {
  public ShuffleboardTab tab;
  public TalonFX LElevator;
  public TalonFX RElevator;
  //public final Follower RElevator;
  public Encoder elevatorEncoder;
  public PIDController elevatorPID;

  public double elevatorEncoderDistance;
  public GenericEntry elevatorEncoderShuffleBoard;
  
  public boolean isElevator;
  public GenericEntry elevatorSensorShuffleBoard;

  public DigitalInput elevatorSensor;

  public ElevatorFeedforward elevatorFeedForward;

  //public TalonFX wristMotor;

  /** Creates a new ElavatorSubsystem. */
  public ElevatorSubsystem() {
    //LElevator = new TalonFX(Constants.ElevatorConstants.LElevatorID);
    RElevator = new TalonFX(Constants.ElevatorConstants.RElevatorID);

    elevatorEncoder = new Encoder(Constants.ElevatorConstants.ElevatorEncoderPortA, Constants.ElevatorConstants.ElevatorEncoderPortB);
    elevatorEncoder.setDistancePerPulse(46.0/8192.0);
    elevatorPID = Constants.ElevatorConstants.ElevatorPID;

    tab = Shuffleboard.getTab("ElevatorSubsystem");
    elevatorEncoderShuffleBoard = tab.add("Elevator Encoder", 0.0).getEntry();

    elevatorSensor = new DigitalInput(Constants.ElevatorConstants.elevatorDetectorID);
    elevatorSensorShuffleBoard = tab.add("Elevator Sensor", false).getEntry();

    elevatorFeedForward = Constants.ElevatorConstants.elevatorFeedForward;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    elevatorEncoderDistance = Math.abs(elevatorEncoder.getDistance());
    elevatorEncoderShuffleBoard.setDouble(elevatorEncoderDistance);

    isElevator = elevatorSensor.get();
    elevatorSensorShuffleBoard.setBoolean(isElevator);

    if(!isElevator && elevatorEncoderDistance >= 0.2) elevatorEncoder.reset();
  }

  public double getElevatorEncoderDistance() {
    return Math.abs(elevatorEncoder.getDistance());
  }
}
