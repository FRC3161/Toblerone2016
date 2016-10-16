package org.usfirst.frc.team3161.robot;

import ca.team3161.lib.robot.TitanBot;
import ca.team3161.lib.robot.motion.drivetrains.SpeedControllerGroup;
import ca.team3161.lib.robot.motion.drivetrains.TankDrivetrain;
import ca.team3161.lib.utils.controls.Gamepad;
import ca.team3161.lib.utils.controls.Gamepad.PressType;
import ca.team3161.lib.utils.controls.JoystickMode;
import ca.team3161.lib.utils.controls.LogitechDualAction;
import ca.team3161.lib.utils.controls.LogitechDualAction.LogitechAxis;
import ca.team3161.lib.utils.controls.LogitechDualAction.LogitechButton;
import ca.team3161.lib.utils.controls.LogitechDualAction.LogitechControl;
import ca.team3161.lib.utils.controls.SquaredJoystickMode;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import java.util.concurrent.TimeUnit;

public class Robot extends TitanBot {

    private SpeedController intakeRoller,
            frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor;
    private CANTalon intakePivot;
    private Gamepad driverPad, operatorPad;
    private TankDrivetrain tankDrivetrain;
    private Intake intake;

    @Override
    public void robotSetup() {
        driverPad = new LogitechDualAction(0, 20, TimeUnit.MILLISECONDS);
        driverPad.setMode(LogitechControl.LEFT_STICK, LogitechAxis.Y, new EpimetheusJoystickMode(0.05));
        driverPad.setMode(LogitechControl.RIGHT_STICK, LogitechAxis.Y, new EpimetheusJoystickMode(0.05));

        operatorPad = new LogitechDualAction(1, 20, TimeUnit.MILLISECONDS);

        frontLeftMotor = new Talon(2);
        backLeftMotor = new Talon(3);
        frontRightMotor = new Talon(0);
        backRightMotor = new Talon(1);

        SpeedControllerGroup leftControllers = new SpeedControllerGroup(frontLeftMotor, backLeftMotor);
        leftControllers.setInverted(true);
        SpeedControllerGroup rightControllers = new SpeedControllerGroup(frontRightMotor, backRightMotor);
        rightControllers.setInverted(true);
        tankDrivetrain = new TankDrivetrain.Builder()
                                 .leftControllers(leftControllers)
                                 .rightControllers(rightControllers)
                                 .build();

        intakePivot = new CANTalon(0);
        intakeRoller = new Talon(5);
        intake = new Intake(intakePivot, intakeRoller);

        driverPad.map(LogitechControl.LEFT_STICK, LogitechAxis.Y, tankDrivetrain::setLeftTarget);
        driverPad.map(LogitechControl.RIGHT_STICK, LogitechAxis.Y, tankDrivetrain::setRightTarget);

        operatorPad.bind(LogitechButton.A, intake::lower);
        operatorPad.bind(LogitechButton.B, intake::raise);

        operatorPad.bind(LogitechButton.LEFT_BUMPER, PressType.PRESS, intake::rollIn);
        operatorPad.bind(LogitechButton.LEFT_BUMPER, PressType.RELEASE, intake::stopRoller);
        operatorPad.bind(LogitechButton.RIGHT_BUMPER, PressType.PRESS, intake::rollOut);
        operatorPad.bind(LogitechButton.RIGHT_BUMPER, PressType.RELEASE, intake::stopRoller);

        driverPad.disableBindings();
        operatorPad.disableBindings();
    }

    @Override
    public int getAutonomousPeriodLengthSeconds() {
        return 15;
    }

    @Override
    public void autonomousSetup() {
        tankDrivetrain.stop();
        intake.stop();

        operatorPad.disableBindings();
        driverPad.disableBindings();
    }

    @Override
    public void autonomousRoutine() {
    }

    @Override
    public void teleopSetup() {
        tankDrivetrain.start();
        intake.start();

        operatorPad.enableBindings();
        driverPad.enableBindings();
    }

    @Override
    public void teleopRoutine() {
    }

    @Override
    public void disabledSetup() {
        tankDrivetrain.stop();
        intake.stop();
        intake.setPivotTarget(IntakePivot.PivotPosition.RAISED);

        operatorPad.disableBindings();
        driverPad.disableBindings();
    }

    @Override
    public void disabledPeriodic() {
    }

    class EpimetheusJoystickMode implements JoystickMode {

        private final JoystickMode squared = new SquaredJoystickMode();
        private final double deadband;

        public EpimetheusJoystickMode(double deadband) {
            this.deadband = deadband;
        }

        @Override
        public double adjust(double in) {
            if (in < deadband && in > -deadband) {
                return 0;
            }
            return squared.adjust(in);
        }
    }

}