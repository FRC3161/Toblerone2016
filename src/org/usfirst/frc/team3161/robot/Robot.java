
package org.usfirst.frc.team3161.robot;

import static java.util.Objects.requireNonNull;
import ca.team3161.lib.robot.TitanBot;
import ca.team3161.lib.utils.controls.Gamepad;
import ca.team3161.lib.utils.controls.LogitechDualAction;
import ca.team3161.lib.utils.controls.Gamepad.PressType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

public class Robot extends TitanBot {

	private SpeedController motorFL, motorFR, motorBL, motorBR, intake;
	private RobotDrive drivetrain;
	private Gamepad gamepad;
	private double arcadeX, arcadeY;
	
	@Override
	public void robotSetup() {
		gamepad = new LogitechDualAction(0);
		motorFL = new Talon(2);
		motorFR = new Talon(1);
		motorBL = new Talon(3);
		motorBR = new Talon(0);
		intake = new Talon(4);
		
		drivetrain = new RobotDrive(motorFL, motorBL, motorFR, motorBR);
		drivetrain.setMaxOutput(0.5);
		
		gamepad.map(LogitechDualAction.LogitechControl.LEFT_STICK,
				LogitechDualAction.LogitechAxis.X, this::setArcadeX);
		gamepad.map(LogitechDualAction.LogitechControl.LEFT_STICK,
				LogitechDualAction.LogitechAxis.Y, this::setArcadeY);
		
		gamepad.bind(LogitechDualAction.LogitechButton.A, () -> setIntakeMotor(1.));
		gamepad.bind(LogitechDualAction.LogitechButton.A, PressType.RELEASE, () -> setIntakeMotor(0.));
		
		gamepad.bind(LogitechDualAction.LogitechButton.B, () -> setIntakeMotor(-1.));
		gamepad.bind(LogitechDualAction.LogitechButton.B, PressType.RELEASE, () -> setIntakeMotor(0.));
	}
	
	private void setArcadeX(Double d) {
		double d2 = requireNonNull(d);
		double sign = d2 < 0 ? -1 : 1;
		arcadeX = sign * (d2 * d2);
	}
	
	private void setArcadeY(Double d) {
		double d2 = requireNonNull(d);
		double sign = d2 < 0 ? -1 : 1;
		arcadeY = sign * (d2 * d2);
	}
	
	private void setIntakeMotor(Double d) {
		intake.set(requireNonNull(d));
	}

	@Override
	public void autonomousSetup() {
		
	}

	@Override
	public void autonomousRoutine() throws Exception {
		
	}

	@Override
	public void teleopSetup() {
		gamepad.enableBindings();
	}

	@Override
	public void teleopRoutine() {
		drivetrain.arcadeDrive(arcadeY, arcadeX);
	}

	@Override
	public void disabledSetup() {
		
	}

	@Override
	public int getAutonomousPeriodLengthSeconds() {
		return 15;
	}
    
}
