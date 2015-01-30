package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.Wiring;
import org.usfirst.frc.team88.robot.commands.DriveWithControllerSimple;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.hal.CanTalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Gyro ;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Ultrasonic;

/**
 *
 */
public class Drive extends Subsystem {
    private final CANTalon lTalonMaster, lTalonSlave, rTalonMaster, rTalonSlave, mTalon;
    //private DoubleSolenoid suspension;
    private final Gyro gyro;
    public final Ultrasonic ultrasonic;
    private DigitalOutput ping;
    private DigitalInput echo;
    
    public final static double ENC_CYCLES_PER_REV = 360.0;
    public final static double GEAR_RATIO = 28.0 / 22.0;
    public final static double WHEEL_DIAMETER = 6.0;

    private final static double MAX_SPEED = 400.0;
    
    private final static double LEFT_P = 0.5;
    private final static double LEFT_I = 0.002;

    private final static double RIGHT_P = 0.55;
    private final static double RIGHT_I = 0.002;

    private final static double D = 1.0;
    private final static double F = 0.5;
    private final static int IZONE = 0;
    private final static double RAMPRATE = 6.0;
    private final static int PROFILE = 0;
       
    
    public Drive() {
    	// set up drive masters
    	lTalonMaster = new CANTalon(Wiring.leftMotorController);
    	lTalonMaster.changeControlMode(ControlMode.Speed);
    	lTalonMaster.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
    	lTalonMaster.reverseSensor(false);
    	lTalonMaster.setPID(LEFT_P, LEFT_I, D, F, IZONE, RAMPRATE, PROFILE);
    	
    	rTalonMaster = new CANTalon(Wiring.rightMotorController);
    	rTalonMaster.changeControlMode(ControlMode.Speed);
    	rTalonMaster.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
    	rTalonMaster.reverseSensor(false);
    	rTalonMaster.setPID(RIGHT_P, RIGHT_I, D, F, IZONE, RAMPRATE, PROFILE);
    	
    	// set up drive slaves
    	lTalonSlave = new CANTalon(Wiring.leftMotorController2);
    	lTalonSlave.changeControlMode(CANTalon.ControlMode.Follower);
    	lTalonSlave.set(lTalonMaster.getDeviceID());
    	
    	rTalonSlave = new CANTalon(Wiring.rightMotorController2);
    	rTalonSlave.changeControlMode(CANTalon.ControlMode.Follower);
    	rTalonSlave.set(rTalonMaster.getDeviceID());
    	
    	// set up middle wheel
    	mTalon = new CANTalon(Wiring.middleMotorController);
    	    	
    	gyro = new Gyro(Wiring.gyro);
    	gyro.initGyro();

    	ping =new DigitalOutput(0);
    	echo = new DigitalInput(1);
    	ultrasonic=new Ultrasonic(ping, echo);
    	ultrasonic.setEnabled(true);
    	ultrasonic.setAutomaticMode(false);
        ultrasonic.ping();
        SmartDashboard.putNumber("inches from detected object:", ultrasonic.getRangeInches());
    }
    

    public void driveSimple(double left, double right, double middle) {
    	double leftRPS, rightRPS, leftSpeed, rightSpeed;
    	
        lTalonMaster.set(left * MAX_SPEED);
        rTalonMaster.set(right * MAX_SPEED);
        mTalon.set(middle);
        SmartDashboard.putNumber("Left Encoder: ", lTalonMaster.getEncPosition());
        SmartDashboard.putNumber("Right Encoder: ", rTalonMaster.getEncPosition());
        SmartDashboard.putNumber("Gyro Angle: ", gyro.getAngle());
    
        
        leftRPS = lTalonMaster.getEncVelocity() / ENC_CYCLES_PER_REV * 10.0;
        leftSpeed = leftRPS * GEAR_RATIO * WHEEL_DIAMETER * Math.PI / 12.0;
        rightRPS = rTalonMaster.getEncVelocity() / ENC_CYCLES_PER_REV * 10.0;
        rightSpeed = rightRPS * GEAR_RATIO * WHEEL_DIAMETER * Math.PI  / 12.0;
        		
        SmartDashboard.putNumber("Left Enc Velocity: ", lTalonMaster.getEncVelocity());
        SmartDashboard.putNumber("Right Enc Velocity: ", rTalonMaster.getEncVelocity());
        SmartDashboard.putNumber("Left Enc RPS: ", leftRPS);
        SmartDashboard.putNumber("Right Enc RPS: ", rightRPS);
        SmartDashboard.putNumber("Left Speed: ", leftSpeed);
        SmartDashboard.putNumber("Right Speed: ", -rightSpeed);

        

    }
    
    public double getFacing() {
    	return gyro.getAngle();
    }
    
    public double getLeftEncoderPosition(){
    	return lTalonMaster.getEncPosition();
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new DriveWithControllerSimple());
    }
}

