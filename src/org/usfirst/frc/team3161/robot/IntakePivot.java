package org.usfirst.frc.team3161.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class IntakePivot extends PIDSubsystem {

    public enum PivotPosition {
        RAISED(7600),
        LOWERED(950),
        ;

        private double value;

        PivotPosition(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }
    }

    private static final int TOLERANCE = 150;

    private CANTalon talon;
    private IntakePivot.PivotPosition pivotPosition = IntakePivot.PivotPosition.RAISED;

    public IntakePivot(CANTalon talon) {
        super("IntakePivot", 0.0005d, 0.0d, 0.00075d);
        this.talon = talon;
        setAbsoluteTolerance(TOLERANCE);
        getPIDController().setContinuous(false);
        setInputRange(PivotPosition.LOWERED.getValue(), PivotPosition.RAISED.getValue());
        setOutputRange(-0.85, 0.85);
        talon.enableBrakeMode(false);
        setSetpoint(pivotPosition.getValue());
    }

    @Override
    public void initDefaultCommand() {
    }

    @Override
    protected double returnPIDInput() {
        return talon.getEncPosition();
    }

    @Override
    protected void usePIDOutput(double output) {
        talon.pidWrite(output);
    }

    public boolean atTarget(PivotPosition pos) {
        double position = returnPIDInput();
        double upper = pos.getValue() + TOLERANCE;
        double lower = pos.getValue() - TOLERANCE;

        return lower < position && position < upper;
    }

    public boolean atTarget() {
        return atTarget(pivotPosition);
    }

    public void setPivotPosition(IntakePivot.PivotPosition pivotPosition) {
        setSetpoint(pivotPosition.getValue());
    }

}