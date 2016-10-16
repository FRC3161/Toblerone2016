package org.usfirst.frc.team3161.robot;

import static org.usfirst.frc.team3161.robot.RobotMap.intakePivot;
import static org.usfirst.frc.team3161.robot.RobotMap.intakeRoller;

import ca.team3161.lib.robot.LifecycleEvent;
import ca.team3161.lib.robot.LifecycleListener;
import ca.team3161.lib.robot.subsystem.RepeatingPooledSubsystem;
import java.util.concurrent.TimeUnit;

public class Intake extends RepeatingPooledSubsystem implements LifecycleListener {

    private IntakePivot pivot;

    private double rollerTarget;
    private IntakePivot.Position lastPivotTarget = IntakePivot.Position.RAISED;
    private IntakePivot.Position pivotTarget = IntakePivot.Position.RAISED;

    public Intake() {
        super(50, TimeUnit.MILLISECONDS);
        this.pivot = new IntakePivot();
    }

    @Override
    public void defineResources() {
        require(intakePivot);
        require(intakeRoller);
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
        if (val > 0) {
            this.rollerTarget = val / 2;
        } else {
            this.rollerTarget = val;
        }
    }

    public void raise() {
        pivot.enable();
        setPivotTarget(IntakePivot.Position.RAISED);
    }

    public void lower() {
        setPivotTarget(IntakePivot.Position.LOWERED);
    }

    public void setPivotTarget(IntakePivot.Position pivotTarget) {
        this.pivotTarget = pivotTarget;
    }

    @Override
    public void start() {
        super.start();
        pivot.enable();
    }

    public void stop() {
        pivot.disable();
    }

    @Override
    public void task() throws Exception {
        if (lastPivotTarget != pivotTarget) {
            lastPivotTarget = pivotTarget;
            pivot.setPosition(pivotTarget);
        }
        if (pivotTarget.equals(IntakePivot.Position.LOWERED)
                    && pivot.atTarget(IntakePivot.Position.LOWERED)) {
            pivot.disable();
        }
        intakeRoller.set(rollerTarget);
    }

    @Override
    public void lifecycleStatusChanged(final LifecycleEvent previous, final LifecycleEvent current) {
        switch (current) {
            case ON_AUTO:
            case ON_TELEOP:
            case ON_TEST:
                start();
                break;
            case ON_DISABLED:
                setPivotTarget(IntakePivot.Position.RAISED);
            case NONE:
            case ON_INIT:
                stop();
                break;
        }
    }
}