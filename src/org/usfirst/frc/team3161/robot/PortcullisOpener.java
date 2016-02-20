package org.usfirst.frc.team3161.robot;

import java.util.concurrent.TimeUnit;

import ca.team3161.lib.robot.pid.PIDAngleValueSrc;
import ca.team3161.lib.robot.pid.PIDulum;
import ca.team3161.lib.robot.pid.PotentiometerVoltagePIDSrc;
import ca.team3161.lib.robot.subsystem.RepeatingPooledSubsystem;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

public class PortcullisOpener extends RepeatingPooledSubsystem {

	private static final int RAISED_ANGLE = 90;
	private static final int LOWERED_ANGLE = 0;
	
	private SpeedController pivot;
	private Encoder enc;
	private DigitalInput limitSwitch;
	private PIDulum<PIDAngleValueSrc<PotentiometerVoltagePIDSrc>> pidulum;
	private double pidulumTargetAngle;
	
	public PortcullisOpener(SpeedController passivePivot, Encoder enc, DigitalInput limitSwitch) {
		super(20, TimeUnit.MILLISECONDS);
		this.pivot = passivePivot;
		this.enc = enc;
		this.limitSwitch = limitSwitch;
//		PIDSrc<Potentiometer, Float> source = new PotentiometerVoltagePIDSrc(passivePot, minVolt, maxVolt, minAngle, maxAngle);
//		pidulum = new PIDulum<>(source, deadband, deadbandPeriod, deadbandUnit, kP, kI, kD, offsetAngle, torqueConstant);
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
	public void defineResources() {
		require(pivot);
		require(enc);
	}

	@Override
	public void task() throws Exception {
//		pidulum.pid((float) pidulumTargetAngle);

	}
	

}
