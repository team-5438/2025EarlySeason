// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.utils.StickDeadband;

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
  public static class OperatorConstants {}

  public static final Controller Driver = new Controller(
    /* NOTE: this is a Xbox Controller */
    0, /* id */
    new StickDeadband(0.1, 0.1), /* left stick deadband */
    new StickDeadband(0.1, 0.1)); /* right stick deadband */


  public static final double MAX_SPEED  = 4.5; //in meters/sec
}
