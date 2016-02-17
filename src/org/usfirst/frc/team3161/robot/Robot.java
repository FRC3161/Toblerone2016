
package org.usfirst.frc.team3161.robot;

import static java.util.Objects.requireNonNull;
import ca.team3161.lib.robot.TitanBot;
import ca.team3161.lib.robot.motion.drivetrains.Drivetrains;
import ca.team3161.lib.robot.motion.drivetrains.SpeedControllerGroup;
import ca.team3161.lib.robot.motion.drivetrains.TankDrivetrain;
import ca.team3161.lib.robot.pid.VelocityController;
import ca.team3161.lib.utils.controls.Gamepad;
import ca.team3161.lib.utils.controls.Gamepad.PressType;
import ca.team3161.lib.utils.controls.LogitechDualAction;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

public class Robot extends TitanBot {

	private SpeedController leftDrive, rightDrive,
			intakePivot, intakeRoller, passivePivot;
	private Encoder leftEncoder, rightEncoder;
	private AnalogPotentiometer intakePot, passivePot;
//	private TobleroneDrive drivetrain;
	private TankDrivetrain drivetrain;
	private Gamepad gamepad;
	private Intake intake;
	private PortcullisOpener portOpener;
	
	@Override
	public void robotSetup() {
		gamepad = new LogitechDualAction(0);
		
		float maxRotationalRate = 1000f;
		float kP = 0.005f;
		float kI = 0.0001f;
		float kD = 0.1f;
		float deadband = 0.1f;
		float maxIntegralError = 100;
		
		leftEncoder = new Encoder(0, 1, true);
		rightEncoder = new Encoder(2, 3, false);
		leftDrive = new VelocityController(new SpeedControllerGroup(new Talon(2), new Talon(4)), leftEncoder, maxRotationalRate, kP, kI, kD, maxIntegralError, deadband);
		rightDrive = new VelocityController(new SpeedControllerGroup(new Talon(1), new Talon(3)), rightEncoder, maxRotationalRate, kP, kI, kD, maxIntegralError, deadband);
//		drivetrain = new TobleroneDrive(leftDrive, rightDrive);
		drivetrain = Drivetrains.tankdrive()
				.leftControllers(new SpeedControllerGroup(leftDrive))
				.rightControllers(new SpeedControllerGroup(rightDrive))
				.build();
		
		intakePivot = new Talon(4);
		intakeRoller = new Talon(5);
		intakePot = new AnalogPotentiometer(0);
		intake = new Intake(intakePivot, intakeRoller, intakePot);
		
		passivePivot = new Talon(6);
		passivePot = new AnalogPotentiometer(1);
		portOpener = new PortcullisOpener(passivePivot, passivePot);
		
		gamepad.bind(LogitechDualAction.LogitechButton.LEFT_BUMPER, intake::rollIn);
		gamepad.bind(LogitechDualAction.LogitechButton.LEFT_BUMPER, PressType.RELEASE, intake::stopRoller);
		
		gamepad.bind(LogitechDualAction.LogitechButton.RIGHT_BUMPER, intake::rollOut);
		gamepad.bind(LogitechDualAction.LogitechButton.RIGHT_BUMPER, PressType.RELEASE, intake::stopRoller);
		
		gamepad.bind(LogitechDualAction.LogitechButton.A, intake::raise);
		gamepad.bind(LogitechDualAction.LogitechButton.B, intake::lower);
		
		gamepad.bind(LogitechDualAction.LogitechButton.X, portOpener::raise);
		gamepad.bind(LogitechDualAction.LogitechButton.Y, portOpener::lower);
		
		gamepad.map(LogitechDualAction.LogitechControl.LEFT_STICK,
				LogitechDualAction.LogitechAxis.Y, drivetrain::setLeftTarget);
		gamepad.map(LogitechDualAction.LogitechControl.RIGHT_STICK,
				LogitechDualAction.LogitechAxis.Y, drivetrain::setRightTarget);
		
		drivetrain.start();
		intake.start();
		portOpener.start();
	}
	
	@Override
	public void autonomousSetup() {
		gamepad.disableBindings();
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
		
	}

	@Override
	public void disabledSetup() {
		gamepad.disableBindings();
	}

	@Override
	public int getAutonomousPeriodLengthSeconds() {
		return 15;
	}
    
}
