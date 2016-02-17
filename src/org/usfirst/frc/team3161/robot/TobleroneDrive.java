package org.usfirst.frc.team3161.robot;

import java.util.concurrent.TimeUnit;

import ca.team3161.lib.robot.subsystem.RepeatingPooledSubsystem;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;

public class TobleroneDrive extends RepeatingPooledSubsystem {
	
	private RobotDrive robotDrive;
	private SpeedController leftDrive, rightDrive;

	private double leftTarget;
	private double rightTarget;
	
	public TobleroneDrive(SpeedController leftDrive, SpeedController rightDrive) {
		super(20, TimeUnit.MILLISECONDS);
		this.leftDrive = leftDrive;
		this.rightDrive = rightDrive;
		this.robotDrive = new RobotDrive(leftDrive, rightDrive);
	}

	public void setLeftDriveTarget(double target) {
		this.leftTarget = target;
	}
	
	public void setRightDriveTarget(double target) {
		this.rightTarget = target;
	}

	@Override
	public void defineResources() {
		require(leftDrive);
		require(rightDrive);
	}

	@Override
	public void task() throws Exception {
		robotDrive.tankDrive(leftTarget, rightTarget);
	}
	
}
