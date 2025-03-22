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
    new StickDeadband(0.15          , 0.15), /* left stick deadband */
    new StickDeadband(0.15, 0.15)); /* right stick deadband */

  //public static final Rotation3d gyroOffset = new Rotation3d(0,0,90);
  public static final double MAX_SPEED  = 4.5; //in meters/sec

  public static class DrivebaseConstants{
      public static double DriveFastScale = 1
      ;
      public static double DrivePrecisionScale = 0.35;
      // Hold time on motor brakes when disabled
      public static final double WHEEL_LOCK_TIME = 10.0; // seconds
      public enum TargetSide {LEFT, RIGHT};
      // robot camera offsets need to be correct with bumper so the 
      //align to reef works correctly, the reef poles are 6.5 inches from the 
      //center of the april tag
      public static double ReefLeftYOffset = Units.inchesToMeters(-7.5);
      public static double ReefRightYOffset = Units.inchesToMeters(7.5);
      public static double ReefXDistance = Units.inchesToMeters(14.0);                                                                                             
  }

  public static class ElevatorConstants{

    /*-----------CAN IDs------------ */
    public static final int LElevatorID = 6;
    public static final int RElevatorID = 7;

    /*-----------DIO PORTS------------- */
    public static final int ElevatorEncoderPortA = 0;
    public static final int ElevatorEncoderPortB = 1;
    public static final int elevatorDetectorID = 9;

    /*-----------PID STUFF------------ */
    public static final PIDController ElevatorPID = new PIDController(0.24, 0, 0.01);
    public static final double elevatorTolerance = 0.5;

    /* -----------PRESETS------------- */
    public static final double ElevatorL4 = 53.2;   //52 <- old value
    public static final double ElevatorL3 = 30;   //27 <- old value
    public static final double ElevatorL2 = 13;   //11 <- old value
    public static final double ElevatorL1 = 4;    //2 <- old value
    public static final double elevatorIntake = 10;
  }

  public static class CoralConstants{
    /* ----------CAN IDs------------ */
    public static final int coralPivotID = 8;   //NEW CORAL PIVOT ID
    //public static final int coralSpinnyID = 14;   //NEW CORAL SPINNY ID

    /* ----------DIO PORTS----------- */
    public static final int coralDetectorID = 4;
    public static final int coralEncoderID = 6;

    /* ----------PID STUFF----------- */
    public static final double coralTolerance = 0.05;
    public static final PIDController coralPIDWithout = new PIDController(0, 0, 0); //pid without a coral
    public static final PIDController coralPIDWith = new PIDController(0, 0, 0);  //pid with a coral
  }

  public static class ClimberConstants{
    /*------------CAN IDs------------- */
    public static final int climberID = 17;
  }

  public static class AlgaeConstants{
    // public static final int algaeLeftID = 12;
    // public static final int algaeRightID = 16;

    //public static final int algaePivotID = 8;
    //public static final int algaePivotEncoderDIOPort = 6;
    public static final PIDController algaePivotPID = new PIDController(0.3, 0, 0);
    public static final ArmFeedforward algaePivotFeedForward = new ArmFeedforward(0.01, 0.025, 0.01);

    //public static final int algaeDetectorID = 7;

    public static final double algaeTolerance = 0.2;

  }
}
