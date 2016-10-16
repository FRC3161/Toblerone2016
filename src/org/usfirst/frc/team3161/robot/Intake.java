package org.usfirst.frc.team3161.robot;

import ca.team3161.lib.robot.subsystem.RepeatingPooledSubsystem;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.SpeedController;
import java.util.concurrent.TimeUnit;

public class Intake extends RepeatingPooledSubsystem {

    private CANTalon talon;
    private IntakePivot pivot;
    private SpeedController intakeRoller;

    private double rollerTarget;
    private IntakePivot.Position lastPivotTarget = IntakePivot.Position.RAISED;
    private IntakePivot.Position pivotTarget = IntakePivot.Position.RAISED;

    public Intake(CANTalon talon, SpeedController intakeRoller) {
        super(50, TimeUnit.MILLISECONDS);
        this.talon = talon;
        this.pivot = new IntakePivot(talon);
        this.intakeRoller = intakeRoller;
    }

    @Override
    public void defineResources() {
        require(talon);
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

}