package org.usfirst.frc.team3161.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class IntakePivot extends PIDSubsystem {

    public static final double KP = 0.0005d;
    public static final double KI = 0.0d;
    public static final double KD = 0.00075d;

    private static final int TOLERANCE = 150;
    public static final double MINIMUM_MOTOR_OUTPUT = -0.85;
    public static final double MAXIMUM_MOTOR_OUTPUT = 0.85;

    private CANTalon talon;

    private Position position = Position.RAISED;
    public IntakePivot(CANTalon talon) {
        super("IntakePivot", KP, KI, KD);
        this.talon = talon;
        setAbsoluteTolerance(TOLERANCE);
        getPIDController().setContinuous(false);
        setInputRange(Position.LOWERED.getValue(), Position.RAISED.getValue());
        setOutputRange(MINIMUM_MOTOR_OUTPUT, MAXIMUM_MOTOR_OUTPUT);
        talon.enableBrakeMode(false);
        setSetpoint(position.getValue());
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

    public boolean atTarget(Position pos) {
        double position = returnPIDInput();
        double upper = pos.getValue() + TOLERANCE;
        double lower = pos.getValue() - TOLERANCE;

        return lower < position && position < upper;
    }

    public boolean atTarget() {
        return atTarget(position);
    }

    public void setPosition(Position position) {
        setSetpoint(position.getValue());
    }

    public enum Position {
        RAISED(7600),
        LOWERED(950),
        ;

        private double value;

        Position(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }
    }

}
