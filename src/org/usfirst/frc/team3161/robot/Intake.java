package org.usfirst.frc.team3161.robot;

import java.util.concurrent.TimeUnit;

import ca.team3161.lib.robot.pid.PIDAngleValueSrc;
import ca.team3161.lib.robot.pid.PIDSrc;
import ca.team3161.lib.robot.pid.PIDulum;
import ca.team3161.lib.robot.pid.PotentiometerVoltagePIDSrc;
import ca.team3161.lib.robot.subsystem.RepeatingPooledSubsystem;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

public class Intake extends RepeatingPooledSubsystem {
	
	private static final int RAISED_ANGLE = 90;
	private static final int LOWERED_ANGLE = 0;

	private SpeedController intakePivot;
	private SpeedController intakeRoller;
	private DigitalInput intakeSwitch;
	
	private PIDulum<PIDAngleValueSrc<PotentiometerVoltagePIDSrc>> pidulum;
	private double pidulumTargetAngle;
	private double rollerTarget;
	private double pivotTarget;
	
	public Intake(SpeedController intakePivot, SpeedController intakeRoller,
			DigitalInput intakeSwitch) {
		super(20, TimeUnit.MILLISECONDS);
		this.intakePivot = intakePivot;
		this.intakeRoller = intakeRoller;
		this.intakeSwitch = intakeSwitch;
//		PIDSrc<Potentiometer, Float> source = new PotentiometerVoltagePIDSrc(intakePot, minVolt, maxVolt, minAngle, maxAngle);
//		pidulum = new PIDulum<>(source, deadband, deadbandPeriod, deadbandUnit, kP, kI, kD, offsetAngle, torqueConstant);
	}

	@Override
	public void defineResources() {
		require(intakePivot);
		require(intakeRoller);
		require(intakeSwitch);
	}
	
	public void rollIn() {
		setRoller(1);
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
		//setPivotAngle(RAISED_ANGLE);
		this.pivotTarget = 1;
	}
	
	public void lower() {
		//setPivotAngle(LOWERED_ANGLE);
		this.pivotTarget = -1;
	}
	
	public void stop() {
		this.pivotTarget = 0;
	}

	/*private void setPivotAngle(double angle) {
		this.pidulumTargetAngle = angle;
	}*/
	
	@Override
	public void task() throws Exception {
//		pidulum.pid((float) pidulumTargetAngle);
//		intakeRoller.set(rollerTarget);
		
		if(intakeSwitch.get() && pivotTarget > 0){
			intakePivot.set(0);
		}
		else{
			intakePivot.set(pivotTarget);
		}
	}

}
