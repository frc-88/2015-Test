package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.Wiring;
import org.usfirst.frc.team88.robot.commands.DriveWithControllerSimple;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.hal.CanTalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CANTalon;
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
    //private final Ultrasonic ultrasonic;
    //private DigitalOutput ping;
    //private DigitalInput echo;

    public Drive() {
    	lTalonMaster = new CANTalon(Wiring.leftMotorController);
    	lTalonSlave = new CANTalon(Wiring.leftMotorController2);
    	rTalonMaster = new CANTalon(Wiring.rightMotorController);
    	rTalonSlave = new CANTalon(Wiring.rightMotorController2);
    	mTalon = new CANTalon(Wiring.middleMotorController);
    	lTalonMaster.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
    	rTalonMaster.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
    	//Sets up masters and slaves
    	lTalonSlave.changeControlMode(CANTalon.ControlMode.Follower);
    	lTalonSlave.set(lTalonMaster.getDeviceID());
    	rTalonSlave.changeControlMode(CANTalon.ControlMode.Follower);
    	rTalonSlave.set(rTalonMaster.getDeviceID());
    	
    	
    	gyro = new Gyro(Wiring.gyro);
    	gyro.initGyro();

    	//ping =new DigitalOutput(0);
    	//echo = new DigitalInput(1);
    	//ultrasonic=new Ultrasonic(ping, echo);
    	//ultrasonic.setEnabled(true);
    	//ultrasonic.setAutomaticMode(false);
    }
    

    public void driveSimple(double left, double right, double middle) {        
        lTalonMaster.set(left);
        rTalonMaster.set(right);
        mTalon.set(middle);
        SmartDashboard.putNumber("Left Encoder: ", lTalonMaster.getPosition());
        SmartDashboard.putNumber("Right Encoder: ", rTalonMaster.getPosition());
        SmartDashboard.putNumber("Gyro Angle: ", gyro.getAngle());
        //ultrasonic.ping();
        //SmartDashboard.putNumber("inches from detected object:", ultrasonic.getRangeInches());
    }
    
    public double getFacing() {
    	return gyro.getAngle();
    }
    public void initDefaultCommand() {
        setDefaultCommand(new DriveWithControllerSimple());
    }
}

