package org.usfirst.frc.team3161.robot;

import ca.team3161.lib.robot.motion.drivetrains.SpeedControllerGroup;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Talon;

public final class RobotMap {

    private RobotMap() {}

    static SpeedControllerGroup intakeRoller = new SpeedControllerGroup(new Talon(5));
    static CANTalon intakePivot = new CANTalon(0);

    static SpeedControllerGroup frontLeftDrivetrainMotor = new SpeedControllerGroup(new Talon(2)).setInversion(true);
    static SpeedControllerGroup frontRightDrivetrainMotor = new SpeedControllerGroup(new Talon(0)).setInversion(true);
    static SpeedControllerGroup backLeftDrivetrainMotor = new SpeedControllerGroup(new Talon(3)).setInversion(true);
    static SpeedControllerGroup backRightDrivetrainMotor = new SpeedControllerGroup(new Talon(1)).setInversion(true);

    {
        intakePivot.enableBrakeMode(false);
    }

}
