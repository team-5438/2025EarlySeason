// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.utils.StickDeadband;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.util.Units;
import frc.robot.utils.Controller;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static final Controller Operator = new Controller(
    /* NOTE: this is a PS5 Controller */
    1, /* id */
    new StickDeadband(0.1, 0.1), /* left stick deadband */
    new StickDeadband(0.1, 0.1)); /* right stick deadband+ */

  public static final Controller Driver = new Controller(
    /* NOTE: this is a Xbox Controller */
    0, /* id */
    new StickDeadband(0.2, 0.2), /* left stick deadband */
    new StickDeadband(0.2, 0.2)); /* right stick deadband */

  //public static final Rotation3d gyroOffset = new Rotation3d(0,0,90);
  public static final double MAX_SPEED  = 4.5; //in meters/sec

  public static class ElevatorConstants{
    //ALL THE FOLLOWING ID's ARE TEMPORARY
    public static final int LElevatorID = 6;
    public static final int RElevatorID = 7;
    //public static final int wristMotorID = 2;

    public static final int ElevatorEncoderPortA = 0;
    public static final int ElevatorEncoderPortB = 1;
    public static final PIDController ElevatorPID = new PIDController(0.24, 0, 0.01);
    public static final ElevatorFeedforward elevatorFeedForward = new ElevatorFeedforward(0.04,0.35,0.05);

    public static final double elevatorTolerance = 3;

    public static final double ElevatorL4 = 53.2;   //52
    public static final double ElevatorL3 = 30;   //27
    public static final double ElevatorL2 = 13;   //11
    public static final double ElevatorL1 = 4;    //2

    public static final int elevatorDetectorID = 9;

    public static final double elevatorIntake = 40;
  }

  public static class CoralConstants{
    public static final int coralSpinnyRightID = 17;
    public static final int coralPivotRightID = 15;
    public static final int coralSpinnyLeftID = 14;
    public static final int coralPivotLeftID = 13;

    public static final int coralDetectorID = 4;

    //public static final int coralPivotEncoderRightDIOPort = 3;
    public static final int coralPivotEncoderLeftDIOPort = 2;

    public static final ArmFeedforward leftCoralFeedForward = new ArmFeedforward(0.04, 0.03, 0.04);
    public static final ArmFeedforward leftCoralFeedForwardHolding = new ArmFeedforward(0.04, 0.045, 0.04);
    //public static final PIDController coralPIDLow = new PIDController(0.12, 0, 0.01);
    public static final PIDController coralPIDHigh = new PIDController(0.35, 0, 0.01);

    public static final double coralTolerance = 0.05;

    public static final double coralL4 = 69; //angle for coral during L4 preset
    public static final double coralL3 = 50; //angle for coral during L4 preset
    public static final double coralL2 = 50; //angle for coral during L4 preset
    public static final double coralL1 = 20; //angle for coral during L4 preset
    
  }

  public static class ClimberConstants{
    public static final int climberID = 17;
  }

  public static class AlgaeConstants{
    public static final int algaeLeftID = 12;
    public static final int algaeRightID = 16;

    public static final int algaePivotID = 8;
    public static final int algaePivotEncoderDIOPort = 6;
    public static final PIDController algaePivotPID = new PIDController(0.3, 0, 0);
    public static final ArmFeedforward algaePivotFeedForward = new ArmFeedforward(0.01, 0.025, 0.01);

    public static final int algaeDetectorID = 7;

    public static final double algaeTolerance = 0.2;

  }
}
