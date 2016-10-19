package org.usfirst.frc.team3161.robot;

import static org.usfirst.frc.team3161.robot.RobotMap.intakePivot;

import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class IntakePivot extends PIDSubsystem {

    public static final double KP = 0.0004d;
    public static final double KI = 0.0d;
    public static final double KD = 0.0009d;
    public static final double KF = 0.0001d;

    public static final int TOLERANCE = 150;
    public static final double MINIMUM_MOTOR_OUTPUT = -1d;
    public static final double MAXIMUM_MOTOR_OUTPUT = 1d;

    private volatile Position position = Position.CARRYING;
    private double initialTicks;

    public IntakePivot() {
        super("IntakePivot", KP, KI, KD, KF);
        this.initialTicks = returnPIDInput();
        setAbsoluteTolerance(TOLERANCE);
        getPIDController().setContinuous(false);
        setInputRange(Position.INTAKE.getValue(), Position.BREACHING.getValue());
        setOutputRange(MINIMUM_MOTOR_OUTPUT, MAXIMUM_MOTOR_OUTPUT);
        setSetpoint(getTicks(position));
    }

    @Override
    public void initDefaultCommand() {
    }

    @Override
    protected double returnPIDInput() {
        return intakePivot.getEncPosition();
    }

    @Override
    protected void usePIDOutput(double output) {
        intakePivot.pidWrite(output);
    }

    public boolean atTarget(Position pos) {
        double position = returnPIDInput();
        double upper = getTicks(pos) + pos.getTolerance();
        double lower = getTicks(pos) - pos.getTolerance();

        return lower < position && position < upper;
    }

    public boolean atTarget() {
        return atTarget(position);
    }

    public void setPosition(Position position) {
        this.position = position;
        setSetpoint(getTicks(position));
    }
    
    public double getTicks(Position position) {
    	return initialTicks + position.getValue();
    }

    public enum Position {
    	BREACHING(-800, TOLERANCE),
        CARRYING(-2500, TOLERANCE),
        INTAKE(-7600, 4 * TOLERANCE),
        ;

        private double value;
        private double tolerance;

        Position(double value, double tolerance) {
            this.value = value;
            this.tolerance = tolerance;
        }

        public double getValue() {
            return value;
        }
        
        public double getTolerance() {
        	return tolerance;
        }
    }

}
