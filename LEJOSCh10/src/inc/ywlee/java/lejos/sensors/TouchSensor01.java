package inc.ywlee.java.lejos.sensors;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class TouchSensor01 {

	EV3 ev3Brick;
	Keys btns;

	EV3LargeRegulatedMotor leftMotor, rightMotor;
	int speed = 30;

	EV3TouchSensor touchSensor;
	SampleProvider touchSample;
	float[] touchValue;

	public TouchSensor01() {
		ev3Brick = (EV3) BrickFinder.getLocal();
		btns = ev3Brick.getKeys();

		leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
		rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);

		touchSensor = new EV3TouchSensor(SensorPort.S1);
		touchSample = touchSensor.getTouchMode();
		touchValue = new float[touchSensor.sampleSize()];

		LCD.drawString("Press any key", 0, 0);
		btns.waitForAnyPress();

		while (true) {

			if (btns.getButtons() == Keys.ID_ESCAPE) {
				break;
			}
			leftMotor.setSpeed(speed);
			rightMotor.setSpeed(speed);
			touchSample.fetchSample(touchValue, 0);

			if (touchValue[0] == 0) {
				leftMotor.forward();
				rightMotor.forward();
				LCD.drawString("No touch", 0, 1);
			}
			if (touchValue[0] == 1) {
				leftMotor.backward();
				rightMotor.backward();
				LCD.drawString(" Touched", 0, 1);
				Delay.msDelay(5000);
			}
		}

	}

	public static void main(String[] args) {
		new TouchSensor01();
	}

}
