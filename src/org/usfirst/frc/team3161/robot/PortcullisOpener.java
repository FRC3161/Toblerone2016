package org.usfirst.frc.team3161.robot;

import java.util.concurrent.TimeUnit;

import ca.team3161.lib.robot.pid.PIDAngleValueSrc;
import ca.team3161.lib.robot.pid.PIDSrc;
import ca.team3161.lib.robot.pid.PIDulum;
import ca.team3161.lib.robot.pid.PotentiometerVoltagePIDSrc;
import ca.team3161.lib.robot.subsystem.RepeatingPooledSubsystem;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

public class PortcullisOpener extends RepeatingPooledSubsystem {

	private static final int RAISED_ANGLE = 90;
	private static final int LOWERED_ANGLE = 0;
	
	private SpeedController pivot;
	private AnalogPotentiometer pot;
	private PIDulum<PIDAngleValueSrc<PotentiometerVoltagePIDSrc>> pidulum;
	private double pidulumTargetAngle;
	private double pivotTarget;

	public PortcullisOpener(SpeedController passivePivot) {
		super(20, TimeUnit.MILLISECONDS);
		this.pivot = passivePivot;
		//this.pot = passivePot;
//		PIDSrc<Potentiometer, Float> source = new PotentiometerVoltagePIDSrc(passivePot, minVolt, maxVolt, minAngle, maxAngle);
//		pidulum = new PIDulum<>(source, deadband, deadbandPeriod, deadbandUnit, kP, kI, kD, offsetAngle, torqueConstant);
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
	
	private void setPivotAngle(double angle) {
		this.pidulumTargetAngle = angle;
	}

	@Override
	public void defineResources() {
		require(pivot);
		//require(pot);
	}

	@Override
	public void task() throws Exception {
//		pidulum.pid((float) pidulumTargetAngle);
		pivot.set(pivotTarget);
	}

}
