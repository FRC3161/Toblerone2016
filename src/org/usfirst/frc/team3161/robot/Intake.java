package org.usfirst.frc.team3161.robot;

import static org.usfirst.frc.team3161.robot.RobotMap.intakePivot;
import static org.usfirst.frc.team3161.robot.RobotMap.intakeRoller;

import java.util.concurrent.TimeUnit;

import ca.team3161.lib.robot.LifecycleEvent;
import ca.team3161.lib.robot.LifecycleListener;
import ca.team3161.lib.robot.subsystem.RepeatingPooledSubsystem;

public class Intake extends RepeatingPooledSubsystem implements LifecycleListener {

    private IntakePivot pivot;

    private RollerState rollerState = RollerState.STOPPED;
    private IntakePivot.Position lastPivotTarget = IntakePivot.Position.CARRYING;
    private IntakePivot.Position pivotTarget = IntakePivot.Position.CARRYING;

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
        this.rollerState = RollerState.IN;
    }

    public void rollOut() {
        this.rollerState = RollerState.OUT;
    }

    public void stopRoller() {
        this.rollerState = RollerState.STOPPED;
    }

    public void intake() {
       
        setPivotTarget(IntakePivot.Position.INTAKE);
    }

    public void carry() {
    	pivot.enable();
        setPivotTarget(IntakePivot.Position.CARRYING);
    }
    
    public void breach() {
    	pivot.enable();
    	setPivotTarget(IntakePivot.Position.BREACHING);
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
        if (pivotTarget.equals(IntakePivot.Position.INTAKE)
                    && pivot.atTarget(IntakePivot.Position.INTAKE)) {
            pivot.disable();
        }
        intakeRoller.set(rollerState.getPwm());
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
                setPivotTarget(IntakePivot.Position.CARRYING);
            case NONE:
            case ON_INIT:
                stop();
                break;
        }
    }

    private enum RollerState {
        IN(0.5d),
        OUT(-1.0d),
        STOPPED(0.0d),
        ;

        private double pwm;

        RollerState(double pwm) {
            this.pwm = pwm;
        }

        public double getPwm() {
            return pwm;
        }
    }
}