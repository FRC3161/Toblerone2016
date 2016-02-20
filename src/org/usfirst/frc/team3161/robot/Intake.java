package org.usfirst.frc.team3161.robot;

import java.util.concurrent.TimeUnit;

import javax.naming.LimitExceededException;

import ca.team3161.lib.robot.pid.PIDAngleValueSrc;
import ca.team3161.lib.robot.pid.PIDulum;
import ca.team3161.lib.robot.pid.PotentiometerVoltagePIDSrc;
import ca.team3161.lib.robot.subsystem.RepeatingPooledSubsystem;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

public class Intake extends RepeatingPooledSubsystem {
	
	private static final int RAISED_ANGLE = 90;
	private static final int LOWERED_ANGLE = 0;

	private SpeedController intakePivot;
	private SpeedController intakeRoller;
	private Encoder enc;
	private DigitalInput limitSwitch;
	
	private PIDulum<PIDAngleValueSrc<PotentiometerVoltagePIDSrc>> pidulum;
	private double pidulumTargetAngle;
	private double rollerTarget;

	public Intake(SpeedController intakePivot, SpeedController intakeRoller,
			Encoder enc, DigitalInput limitSwitch) {
		super(20, TimeUnit.MILLISECONDS);
		this.intakePivot = intakePivot;
		this.intakeRoller = intakeRoller;
		this.enc = enc;
		this.limitSwitch = limitSwitch;
//		PIDSrc<Potentiometer, Float> source = new PotentiometerVoltagePIDSrc(intakePot, minVolt, maxVolt, minAngle, maxAngle);
//		pidulum = new PIDulum<>(source, deadband, deadbandPeriod, deadbandUnit, kP, kI, kD, offsetAngle, torqueConstant);
	}

	@Override
	public void defineResources() {
		require(intakePivot);
		require(intakeRoller);
		require(enc);
	}
	
	public void rollIn() {
		setRoller(1);
	}
				
	public int ret() {
		return 5;
	}
		
	public void rollOut() {
		setRoller(-1);
	}
	
	public void stopRoller() {
		setRoller(0);
	}
	
	private void setRoller(double val) {
		this.rollerTarget = val;
	}
	
	public void raise() {
		setPivotAngle(RAISED_ANGLE);
	}
	
	public void lower() {
		setPivotAngle(LOWERED_ANGLE);
	}

	private void setPivotAngle(double angle) {
		this.pidulumTargetAngle = angle;
	}
	
	@Override
	public void task() throws Exception {
//		pidulum.pid((float) pidulumTargetAngle);
//		intakeRoller.set(rollerTarget);
	}

}
