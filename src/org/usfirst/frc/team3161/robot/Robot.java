
package org.usfirst.frc.team3161.robot;

import ca.team3161.lib.robot.TitanBot;
import ca.team3161.lib.robot.motion.drivetrains.ArcadeDrivetrain;
import ca.team3161.lib.robot.motion.drivetrains.Drivetrains;
import ca.team3161.lib.robot.motion.drivetrains.SpeedControllerGroup;
import ca.team3161.lib.robot.pid.VelocityController;
import ca.team3161.lib.utils.controls.DeadbandJoystickMode;
import ca.team3161.lib.utils.controls.Gamepad;
import ca.team3161.lib.utils.controls.Gamepad.PressType;
import ca.team3161.lib.utils.controls.JoystickMode;
import ca.team3161.lib.utils.controls.LogitechDualAction;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;

public class Robot extends TitanBot {

	private SpeedController leftDrive, rightDrive,
			intakePivot, intakeRoller, passivePivot;
	private Encoder leftEncoder, rightEncoder;
	private AnalogPotentiometer  passivePot;
	private DigitalInput intakeSwitch;
	private ArcadeDrivetrain drivetrain;
	private Gamepad driverPad, operatorPad;
	private Intake intake;
	private PortcullisOpener portOpener;
	
	@Override
	public void robotSetup() {
		driverPad = new LogitechDualAction(0);
		operatorPad = new LogitechDualAction(1);
		
		for (LogitechDualAction.LogitechControl control : LogitechDualAction.LogitechControl.values()) {
			for (LogitechDualAction.LogitechAxis axis : LogitechDualAction.LogitechAxis.values()) {
				JoystickMode deadband = new DeadbandJoystickMode(0.05);
				driverPad.setMode(control,  axis, deadband);
				operatorPad.setMode(control,  axis, deadband);
			}
		}
		
		float maxRotationalRate = 1000;
		float kP = 0.005f;
		float kI = 0.0001f;
		float kD = 0.1f;
		float deadband = 0.1f;
		float maxIntegralError = 100;
		
		leftEncoder = new Encoder(0, 1, true);
		rightEncoder = new Encoder(2, 3, false);
		leftDrive = new VelocityController(new SpeedControllerGroup(new Talon(2), new Talon(3)).setInverted(true), leftEncoder, maxRotationalRate, kP, kI, kD, maxIntegralError, deadband);
		rightDrive = new VelocityController(new SpeedControllerGroup(new Talon(0), new Talon(1)).setInverted(true), rightEncoder, maxRotationalRate, kP, kI, kD, maxIntegralError, deadband);
		drivetrain = Drivetrains.arcade()
				.leftControllers(new SpeedControllerGroup(leftDrive))
				.rightControllers(new SpeedControllerGroup(rightDrive))
				.build();
		
		intakePivot = new Victor(5);
		intakeRoller = new Talon(6);
		intakeSwitch = new DigitalInput(0);
		intake = new Intake(intakePivot, intakeRoller, intakeSwitch);
		
		passivePivot = new Talon(7);
//		passivePot = new AnalogPotentiometer(1);
		portOpener = new PortcullisOpener(passivePivot);
		
		operatorPad.bind(LogitechDualAction.LogitechButton.LEFT_BUMPER, intake::rollIn);
		operatorPad.bind(LogitechDualAction.LogitechButton.LEFT_BUMPER, PressType.RELEASE, intake::stopRoller);
		
		operatorPad.bind(LogitechDualAction.LogitechButton.RIGHT_BUMPER, intake::rollOut);
		operatorPad.bind(LogitechDualAction.LogitechButton.RIGHT_BUMPER, PressType.RELEASE, intake::stopRoller);
		
		operatorPad.bind(LogitechDualAction.LogitechButton.A, intake::raise);
		operatorPad.bind(LogitechDualAction.LogitechButton.A, PressType.RELEASE, intake::stop);
		operatorPad.bind(LogitechDualAction.LogitechButton.B, intake::lower);
		operatorPad.bind(LogitechDualAction.LogitechButton.B, PressType.RELEASE, intake::stop);
		
		operatorPad.bind(LogitechDualAction.LogitechButton.X, portOpener::raise);
		operatorPad.bind(LogitechDualAction.LogitechButton.Y, portOpener::lower);
		
		driverPad.map(LogitechDualAction.LogitechControl.LEFT_STICK,
				LogitechDualAction.LogitechAxis.Y, drivetrain::setForwardTarget);
		driverPad.map(LogitechDualAction.LogitechControl.LEFT_STICK,
				LogitechDualAction.LogitechAxis.X, drivetrain::setTurnTarget);
		
//		operatorPad.map(LogitechDualAction.LogitechControl.LEFT_STICK,
//				LogitechDualAction.LogitechAxis.Y, intakePivot::set);
//		operatorPad.map(LogitechDualAction.LogitechControl.RIGHT_STICK,
//				LogitechDualAction.LogitechAxis.Y, passivePivot::set);
		
		drivetrain.start();
		intake.start();
		portOpener.start();
	}
	
	@Override
	public void autonomousSetup() {
		driverPad.disableBindings();
	}

	@Override
	public void autonomousRoutine() throws Exception {
	}

	@Override
	public void teleopSetup() {
		driverPad.enableBindings();
	}

	@Override
	public void teleopRoutine() {
		
	}

	@Override
	public void disabledSetup() {
		driverPad.disableBindings();
	}

	@Override
	public int getAutonomousPeriodLengthSeconds() {
		return 15;
	}
    
}
