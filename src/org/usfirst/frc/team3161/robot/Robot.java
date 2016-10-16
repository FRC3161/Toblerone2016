package org.usfirst.frc.team3161.robot;

import static org.usfirst.frc.team3161.robot.RobotMap.backLeftDrivetrainMotor;
import static org.usfirst.frc.team3161.robot.RobotMap.backRightDrivetrainMotor;
import static org.usfirst.frc.team3161.robot.RobotMap.frontLeftDrivetrainMotor;
import static org.usfirst.frc.team3161.robot.RobotMap.frontRightDrivetrainMotor;

import ca.team3161.lib.robot.TitanBot;
import ca.team3161.lib.robot.motion.drivetrains.TankDrivetrain;
import ca.team3161.lib.utils.controls.DeadbandJoystickMode;
import ca.team3161.lib.utils.controls.Gamepad.PressType;
import ca.team3161.lib.utils.controls.JoystickMode;
import ca.team3161.lib.utils.controls.LogitechDualAction;
import ca.team3161.lib.utils.controls.LogitechDualAction.LogitechAxis;
import ca.team3161.lib.utils.controls.LogitechDualAction.LogitechButton;
import ca.team3161.lib.utils.controls.LogitechDualAction.LogitechControl;
import ca.team3161.lib.utils.controls.SquaredJoystickMode;
import java.util.concurrent.TimeUnit;

public class Robot extends TitanBot {

    private static final JoystickMode JOYSTICK_MODE = new SquaredJoystickMode().compose(new DeadbandJoystickMode(0.05));

    @Override
    public TankDrivetrain getDrivetrainBase() {
        return new TankDrivetrain.Builder()
                .leftControllers(frontLeftDrivetrainMotor, backLeftDrivetrainMotor)
                .rightControllers(frontRightDrivetrainMotor, backRightDrivetrainMotor)
                .build();
    }

    @Override
    public void robotSetup() {
        LogitechDualAction driverPad = new LogitechDualAction(0, 50, TimeUnit.MILLISECONDS);
        registerLifecycleComponent(driverPad);

        LogitechDualAction operatorPad = new LogitechDualAction(1, 50, TimeUnit.MILLISECONDS);
        registerLifecycleComponent(operatorPad);

        Intake intake = new Intake();
        registerLifecycleComponent(intake);

        driverPad.setMode(JOYSTICK_MODE);
        driverPad.map(LogitechControl.LEFT_STICK, LogitechAxis.Y, getDrivetrainBase()::setLeftTarget);
        driverPad.map(LogitechControl.RIGHT_STICK, LogitechAxis.Y, getDrivetrainBase()::setRightTarget);

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
    public void autonomousSetup() {}

    @Override
    public void autonomousRoutine() {}

    @Override
    public void teleopSetup() {}

    @Override
    public void teleopRoutine() {}

    @Override
    public void disabledSetup() {}

    @Override
    public void disabledPeriodic() {}

}