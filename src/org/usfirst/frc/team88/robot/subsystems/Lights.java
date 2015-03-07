package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.Wiring;
import org.usfirst.frc.team88.robot.commands.LightsDefault;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *	Subsystem responsible for controlling the lights assuming I can get it wired.
 * 
 */
public class Lights extends Subsystem {
    private int activeMode = 0;
    private double heightAnalogOutput = 0.0;
    
    private static final double ANALOG_LOWER_THRESHHOLD = -1.0;
    private static final double ANALOG_UPPER_THRESHHOLD = 1.0;
    
    private DigitalOutput digitalOut1;
    private DigitalOutput digitalOut2;
    private DigitalOutput digitalOut3;
    // We aren't actually using Jags, but this gives us an easy way
    // to control PWM outputs.
    private Jaguar pwmOut1;
    
    //rename and fix modes
    public static final int MODE_RAINBOW_MISC = 0;
    public static final int MODE_RED_ALLIANCE = 1;
    public static final int MODE_BLUE_ALLIANCE = 2;
    public static final int MODE_LIFT_FILL = 3;
    public static final int MODE_BLINKY = 4;
    public static final int MODE_RAINBOW = 5;
    public static final int MODE_FLASH_GREEN = 6;
    public static final int MODE_ENDGAME = 7;
    
    public static final int ANALOG_CHANNEL_LEFT = 0;
    public static final int ANALOG_CHANNEL_RIGHT = 1;
    
    public Lights() {
        digitalOut1 = new DigitalOutput(Wiring.lightDigitalOutPin1);
        digitalOut2 = new DigitalOutput(Wiring.lightDigitalOutPin2);
        digitalOut3 = new DigitalOutput(Wiring.lightDigitalOutPin3);
        pwmOut1 = new Jaguar(Wiring.lightPWMOutput);
    }
    
    public void initDefaultCommand() {
    //    setDefaultCommand(new LightsDefault());
    }
    
    public void increaseMode(){
    	activeMode+=1;
    	updateOutput();
    }
    public void decreaseMode(){
    	activeMode-=1;
    	updateOutput();
    }
    
    private void updateOutput() {
        // Set each of the digital outputs to a bit of the mode value
        // Currently mode is only supported as 4 bits, even though it's actually
        // a 32 bit int in this subsystem.  We shouldn't need more than 4 bits.
    	//only need 3 bits for what I'm doing
        digitalOut1.set((activeMode & 1) == 1);
        digitalOut2.set(((activeMode >> 1) & 1) == 1);
        digitalOut3.set(((activeMode >> 2) & 1) == 1);
        
        SmartDashboard.putNumber("activeMode", activeMode); 

        SmartDashboard.putBoolean("Light output1", (activeMode & 1) == 1); 
        SmartDashboard.putBoolean("Light output2", ((activeMode >> 1) & 1) == 1); 
        SmartDashboard.putBoolean("Light output3", ((activeMode >> 2) & 1) == 1); 
        // Also need to set the analog outputs
        // OH GOD I THINK WE CAN ONLY DO ANALOG INPUTS OH GOD NO
        // MikeE: we can use the PWM output and pass it through a simple RC filter to give us an analog signal
        // I checked the rules and there's nothing to say that PWM can only go to servo/motor controller
        // going to assume these rules still apply after 2 years
        pwmOut1.set(heightAnalogOutput);
    }
    
    public void setMode(int mode) {
        activeMode = mode;
        updateOutput();
    }
    
    public int getMode() {
        return activeMode;
    }
    
    public void setAnalog(double value) {
        // Value should never go out of the range of 0.0 and 1.0, so we limit it
        //value = Math.abs(value);
        if (value > ANALOG_UPPER_THRESHHOLD) {
            value = ANALOG_UPPER_THRESHHOLD;
        } else if (value < ANALOG_LOWER_THRESHHOLD) {
            value = ANALOG_LOWER_THRESHHOLD;
        }
        heightAnalogOutput = value;
        updateOutput();       
        // a -1.0 PWM has a pulse width of 0.68 ms
        // a 1.0 PWM has a pulse width of 2.29 ms
    }
}