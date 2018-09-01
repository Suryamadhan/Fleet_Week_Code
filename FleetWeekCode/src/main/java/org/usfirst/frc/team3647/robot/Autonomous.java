package org.usfirst.frc.team3647.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation;
import team3647ConstantsAndFunctions.Constants;
import team3647ConstantsAndFunctions.Functions;
import team3647elevator.Elevator;
import team3647elevator.IntakeWheels;
import team3647elevator.Wrist;
import team3647pistons.Forks;
import team3647pistons.Shifter;
import team3647pistons.Intake;
import team3647subsystems.Drivetrain;
import team3647subsystems.Encoders;

public class Autonomous 
{
	//Timer-Stuff
	public static Timer stopWatch = new Timer();
	static double time;
	
	//Other variables for auto
	static double prevLeftEncoder, prevRightEncoder;
	static int currentState;
	static double lSSpeed, rSSpeed, speed, sum;
	static int b;
	
	static int [] differences = new int[10];

	public static void runAuto()
	{
		String gameData;
		gameData = "LRL";
		int priorityForSwitch = 0; //0 if we dont care, any other number means we only go for switch not scale
		boolean cross = false;
		if(cross)
		{
			cross();
		}
		else if(priorityForSwitch != 0)
		{
			if(gameData.charAt(0) == 'R')
			{
				rightSide2Cube();
			}
			else if(gameData.charAt(0) == 'L')
			{
				leftSide2Cube();
			}
			else
			{
				cross();
			}
		}
		else if(priorityForSwitch == 0)
		{
			if(gameData.charAt(1) == 'R' && gameData.charAt(0) == 'L')
			{
				rightScaleLeftSwitch();
			}
			else if(gameData.charAt(1) == 'R' && gameData.charAt(0) == 'R')
			{
				rightScaleRightSwitch();
			}
			else if(gameData.charAt(1) == 'L' && gameData.charAt(0) == 'R')
			{
				rightSide2Cube();
			}
			else if(gameData.charAt(1) == 'L' && gameData.charAt(0) == 'R')
			{
				leftSide2Cube();
			}
			else
			{
				cross();
			}
		}
		else
		{
			cross();
		}
	}
	
	// Cheese autos
	
	//212 inches in y, 78.66 inches in x
	public static void rightSide2Cube()
	{
		switch(currentState)
		{
			case 0:
				IntakeWheels.runIntake(0, 0, true, .12, .12);
				stopWatch.stop();
				time = stopWatch.get();
				if(time == 0)
				{
					stopWatch.start();
					currentState = 1;
				}
				else
				{
					stopWatch.reset();
				}
				break;
			case 1:
				time = stopWatch.get();
				if(Elevator.elevatorEncoderValue == 0 && Wrist.wristEncoderValue == 0)
				{
					stopWatch.stop();
					stopWatch.reset();
					stopWatch.start();
					currentState = 99;
					Elevator.stopElevator();
					Wrist.stopWrist();
				}
				else if(time > 1.4)
				{
					Elevator.resetElevatorEncoders();
					Wrist.resetWristEncoder();
					Elevator.stopElevator();
					Wrist.stopWrist();
				}
				else if(time > 1.5)
				{
					stopWatch.stop();
					stopWatch.reset();
					stopWatch.start();
					currentState = 99;
				}
				else
				{
					Elevator.moveElevator(-.3);
					Wrist.moveToFlat();
				}
				break;
			case 99:
				stopWatch.stop();
				time = stopWatch.get();
				if(time == 0)
				{
					stopWatch.start();
					currentState = 2;
				}
				else
				{
					stopWatch.reset();
				}
				break;
			case 2:
				if(Elevator.reachedSwitch())
				{
					currentState = 3;
				}
				else if(stopWatch.get() > 2)
				{
					currentState = 3;
				}
				else 
				{
					Elevator.moveElevatorPosition(Constants.sWitch);
				}
				break;
			case 3:
				Elevator.moveElevatorPosition(Constants.sWitch);
				time = stopWatch.get();
				if(time == 0)
				{
					stopWatch.start();
					currentState = 4;
				}
				else
				{
					stopWatch.reset();
				}
				break;
			case 4:
				time = stopWatch.get();
				lSSpeed = Functions.rightSide2Cube(time, false);
				rSSpeed = Functions.rightSide2Cube(time, true);
				Drivetrain.tankDrive(lSSpeed, rSSpeed);
				break;
		}
	}
	
	public static void leftSide2Cube()
	{
		switch(currentState)
		{
			case 0:
				IntakeWheels.runIntake(0, 0, true, .12, .12);
				stopWatch.stop();
				time = stopWatch.get();
				if(time == 0)
				{
					stopWatch.start();
					currentState = 1;
				}
				else
				{
					stopWatch.reset();
				}
				break;
			case 1:
				time = stopWatch.get();
				if(Elevator.elevatorEncoderValue == 0 && Wrist.wristEncoderValue == 0)
				{
					stopWatch.stop();
					stopWatch.reset();
					stopWatch.start();
					currentState = 99;
					Elevator.stopElevator();
					Wrist.stopWrist();
				}
				else if(time > 1.4)
				{
					Elevator.resetElevatorEncoders();
					Wrist.resetWristEncoder();
					Elevator.stopElevator();
					Wrist.stopWrist();
				}
				else if(time > 1.5)
				{
					stopWatch.stop();
					stopWatch.reset();
					stopWatch.start();
					currentState = 99;
				}
				else
				{
					Elevator.moveElevator(-.3);
					Wrist.moveToFlat();
				}
				break;
			case 99:
				stopWatch.stop();
				time = stopWatch.get();
				if(time == 0)
				{
					stopWatch.start();
					currentState = 2;
				}
				else
				{
					stopWatch.reset();
				}
				break;
			case 2:
				if(Elevator.reachedSwitch())
				{
					currentState = 3;
				}
				else if(stopWatch.get() > 2)
				{
					currentState = 3;
				}
				else 
				{
					Elevator.moveElevatorPosition(Constants.sWitch);
				}
				break;
			case 3:
				Elevator.moveElevatorPosition(Constants.sWitch);
				time = stopWatch.get();
				if(time == 0)
				{
					stopWatch.start();
					currentState = 4;
				}
				else
				{
					stopWatch.reset();
				}
				break;
			case 4:
				time = stopWatch.get();
				lSSpeed = Functions.leftSide2Cube(time, false);
				rSSpeed = Functions.leftSide2Cube(time, true);
				Drivetrain.tankDrive(lSSpeed, rSSpeed);
				break;
		}
	}

	public static void rightScaleRightSwitch()
	{
		switch(currentState)
		{
			case 0:
				IntakeWheels.runIntake(0, 0, true, .12, .12);
				stopWatch.stop();
				time = stopWatch.get();
				if(time == 0)
				{
					stopWatch.start();
					currentState = 1;
				}
				else
				{
					stopWatch.reset();
				}
				break;
			case 1:
				time = stopWatch.get();
				if(Elevator.elevatorEncoderValue == 0 && Wrist.wristEncoderValue == 0)
				{
					stopWatch.stop();
					stopWatch.reset();
					stopWatch.start();
					currentState = 99;
					Elevator.stopElevator();
					Wrist.stopWrist();
				}
				else if(time > 1.4)
				{
					Elevator.resetElevatorEncoders();
					Wrist.resetWristEncoder();
					Elevator.stopElevator();
					Wrist.stopWrist();
				}
				else if(time > 1.5)
				{
					stopWatch.stop();
					stopWatch.reset();
					stopWatch.start();
					currentState = 99;
				}
				else
				{
					Elevator.moveElevator(-.3);
					Wrist.moveToFlat();
				}
				break;
			case 99:
				stopWatch.stop();
				time = stopWatch.get();
				if(time == 0)
				{
					stopWatch.start();
					currentState = 2;
				}
				else
				{
					stopWatch.reset();
				}
				break;
			case 2:
				if(Elevator.reachedSwitch())
				{
					currentState = 3;
				}
				else if(stopWatch.get() > 2)
				{
					currentState = 3;
				}
				else 
				{
					Elevator.moveElevatorPosition(Constants.sWitch);
				}
				break;
			case 3:
				Elevator.moveElevatorPosition(Constants.sWitch);
				time = stopWatch.get();
				if(time == 0)
				{
					stopWatch.start();
					currentState = 4;
				}
				else
				{
					stopWatch.reset();
				}
				break;
			case 4:
				time = stopWatch.get();
				lSSpeed = Functions.rightScaleRightSwitch(time, false);
				rSSpeed = Functions.rightScaleRightSwitch(time, true);
				Drivetrain.tankDrive(lSSpeed, rSSpeed);
				break;
		}
	}

	public static void rightScaleLeftSwitch()
	{
		switch(currentState)
		{
			case 0:
				IntakeWheels.runIntake(0, 0, true, .12, .12);
				stopWatch.stop();
				time = stopWatch.get();
				if(time == 0)
				{
					stopWatch.start();
					currentState = 1;
				}
				else
				{
					stopWatch.reset();
				}
				break;
			case 1:
				time = stopWatch.get();
				if(Elevator.elevatorEncoderValue == 0 && Wrist.wristEncoderValue == 0)
				{
					stopWatch.stop();
					stopWatch.reset();
					stopWatch.start();
					currentState = 99;
					Elevator.stopElevator();
					Wrist.stopWrist();
				}
				else if(time > 1.4)
				{
					Elevator.resetElevatorEncoders();
					Wrist.resetWristEncoder();
					Elevator.stopElevator();
					Wrist.stopWrist();
				}
				else if(time > 1.5)
				{
					stopWatch.stop();
					stopWatch.reset();
					stopWatch.start();
					currentState = 99;
				}
				else
				{
					Elevator.moveElevator(-.3);
					Wrist.moveToFlat();
				}
				break;
			case 99:
				stopWatch.stop();
				time = stopWatch.get();
				if(time == 0)
				{
					stopWatch.start();
					currentState = 2;
				}
				else
				{
					stopWatch.reset();
				}
				break;
			case 2:
				if(Elevator.reachedSwitch())
				{
					currentState = 3;
				}
				else if(stopWatch.get() > 2)
				{
					currentState = 3;
				}
				else 
				{
					Elevator.moveElevatorPosition(Constants.sWitch);
				}
				break;
			case 3:
				Elevator.moveElevatorPosition(Constants.sWitch);
				time = stopWatch.get();
				if(time == 0)
				{
					stopWatch.start();
					currentState = 4;
				}
				else
				{
					stopWatch.reset();
				}
				break;
			case 4:
				time = stopWatch.get();
				lSSpeed = Functions.rightScaleLeftSwitch(time, false);
				rSSpeed = Functions.rightScaleLeftSwitch(time, true);
				Drivetrain.tankDrive(lSSpeed, rSSpeed);
				break;
		}
	}

	public static void cross()
	{
		switch(currentState)
		{
			case 0:
				stopWatch.stop();
				time = stopWatch.get();
				if(time == 0)
				{
					stopWatch.start();
					currentState = 1;
				}
				else
				{
					stopWatch.reset();
				}
				break;
			case 1:
				if(stopWatch.get() < 2.2)
				{
					Drivetrain.tankDrive(.7, .7);
				}
				else
				{
					Drivetrain.tankDrive(0, 0);
				}
				break;
		}
		
	}
	
//	public static void rightTwoSwitch(double lValue, double rValue)
//	{
//		switch(currentState)
//		{
//			case 0:
//				stopWatch.stop();
//				stopWatch.reset();
//				stopWatch.start();
//				//IntakeWheels.pickUp(.2);
//				currentState = 1;
//				break;
//			case 1:
//				IntakeWheels.runIntake(0, 0, true, .2, .2);
//				time = stopWatch.get();
//				if(lValue == 0 && rValue == 0)
//				{
//					stopWatch.stop();
//					Elevator.stopEleVader();
//					ElevatorLevel.resetElevatorEncoders();
//					stopWatch.reset();
//					currentState = 2;
//					
//				}
//				else
//				{
//					Encoders.resetEncoders();
//				}
//				break;
//			case 2:
//				if(!Drivetrain.reachedDistance(lValue, rValue, 5100))
//				{
//					Drivetrain.newArcadeDrive(.74, 0);
//				}
//				else if(!Drivetrain.reachedDistance(lValue, rValue, 6000))
//				{
//					Drivetrain.newArcadeDrive(.3, 0);
//				}
//				else
//				{
//					Encoders.resetEncoders();
//					currentState = 3;
//				}
//				break;
//			case 3:
//				if(lValue == 0 && rValue == 0)
//				{
//					currentState = 4;
//					
//				}
//				else
//				{
//					Encoders.resetEncoders();
//				}
//				break;
//			case 4:
//				if(Encoders.rightEncoderValue < 8100)
//				{
//					Drivetrain.goStraightLeft(lValue, rValue, 4, .2, .8, .07);
//				}
//				else if(Encoders.rightEncoderValue < 9010)
//				{
//					Drivetrain.goStraightLeft(lValue, rValue, 3, .13, .4, .05);
//				}
//				else
//				{
//					currentState = 100;
//				}
//				break;
//			case 100:
//				Drivetrain.stop();
//				break;
//		}
//	}
//	
//	public static void testReset(double lValue, double rValue)
//	{
//		switch(currentState)
//		{
//			case 0:
//				stopWatch.stop();
//				stopWatch.reset();
//				stopWatch.start();
//				IntakeWheels.pickUp(.2);
//				currentState = 1;
//				break;
//			case 1:
//				time = stopWatch.get();
//				if(lValue == 0 && rValue == 0)
//				{
//					stopWatch.stop();
//					Elevator.stopEleVader();
//					ElevatorLevel.resetElevatorEncoders();
//					stopWatch.reset();
//					currentState = 2;
//					
//				}
//				else
//				{
//					Encoders.resetEncoders();
//				}
//				break;
//			case 2:
//				if(!Drivetrain.reachedDistance(lValue, rValue, 8500))
//				{
//					Drivetrain.driveForw(lValue, rValue, .74);
//				}
//				else if(!Drivetrain.reachedDistance(lValue, rValue, 9500))
//				{
//					Drivetrain.driveForw(lValue, rValue, .3);
//				}
//				else
//				{
//					Drivetrain.stop();
//				}
//				break;
//		}
//	}
//	
//	public static void test(double lValue, double rValue, boolean button)
//	{
//		speed = 1;
//		switch(currentState)
//		{
//			case 0:
//				stopWatch.stop();
//				stopWatch.reset();
//				if(lValue == 0 && rValue == 0)
//				{
//					Elevator.stopEleVader();
//					ElevatorLevel.resetElevatorEncoders();
//					//stopWatch.start();
//					currentState = 1;
//				}
//				else
//				{
//					Encoders.resetEncoders();
//				}
//				break;
//			case 1:
//				if(!button)
//				{
//					Drivetrain.tankDrive(speed, speed);
//					//Encoders.testEncoders();
//					//System.out.println(stopWatch.get());
//				}
//				else
//				{
//					Encoders.testEncoders();
//					Drivetrain.stop();
//					currentState = 2;
//				}
//				break;
//			case 2:
//				Drivetrain.stop();
//				
//				break;
//		}
//	}
//	
//	
//	public static void cross(double lValue, double rValue)
//	{
//		switch(currentState)
//		{
//			case 0:
//				stopWatch.stop();
//				stopWatch.reset();
//				stopWatch.start();
//				IntakeWheels.pickUp(.2);
//				currentState = 1;
//				break;
//			case 1:
//				time = stopWatch.get();
//				if(lValue == 0 && rValue == 0 && time >= 2)
//				{
//					stopWatch.stop();
//					Elevator.stopEleVader();
//					ElevatorLevel.resetElevatorEncoders();
//					stopWatch.reset();
//					currentState = 2;
//					
//				}
//				else if(lValue == 0 && rValue == 0 && ElevatorLevel.reachedStop())
//				{
//					Elevator.stopEleVader();
//					ElevatorLevel.resetElevatorEncoders();
//					currentState = 2;
//					stopWatch.stop();
//				}
//				else
//				{
//					Elevator.moveEleVader(-.23);
//					Encoders.resetEncoders();
//				}
//				break;
//			case 2:
//				if(!Drivetrain.reachedDistance(lValue, rValue, 8500))
//				{
//					Drivetrain.driveForw(lValue, rValue, .74);
//				}
//				else if(!Drivetrain.reachedDistance(lValue, rValue, 9500))
//				{
//					Drivetrain.driveForw(lValue, rValue, .3);
//				}
//				else
//				{
//					Drivetrain.stop();
//				}
//				break;
//		}
//	}
//	
//	public static void testDatScaleCurve(double lValue, double rValue)
//	{
//		switch(currentState)
//		{
//			case 0:
//				stopWatch.stop();
//				stopWatch.reset();
//				stopWatch.start();
//				IntakeWheels.pickUp(.2);
//				currentState = 1;
//				break;
//			case 1:
//				time = stopWatch.get();
//				if(lValue == 0 && rValue == 0 && time >= 2)
//				{
//					stopWatch.stop();
//					Elevator.stopEleVader();
//					ElevatorLevel.resetElevatorEncoders();
//					stopWatch.reset();
//					currentState = 8;
//					
//				}
//				else if(lValue == 0 && rValue == 0 && ElevatorLevel.reachedStop())
//				{
//					Elevator.stopEleVader();
//					ElevatorLevel.resetElevatorEncoders();
//					currentState = 8;
//					stopWatch.stop();
//				}
//				else
//				{
//					Elevator.moveEleVader(-.23);
//					Encoders.resetEncoders();
//				}
//				break;
//			case 2:
//				if(!Drivetrain.reachedDistance(lValue, rValue, 7000))
//				{
//					Drivetrain.driveForw(lValue, rValue, .74);
//				}
//				else if(!Drivetrain.reachedDistance(lValue, rValue, 8200))
//				{
//					Drivetrain.driveForw(lValue, rValue, .3);
//				}
//				else
//				{
//					stopWatch.stop();
//					stopWatch.reset();
//					prevLeftEncoder = lValue;
//					prevRightEncoder = rValue;
//					currentState = 4;
//				}
//				break;
//			case 4:
//				rValue -= prevRightEncoder;
//				lSSpeed = Drivetrain.keepMotorInPlace(prevLeftEncoder, lValue);
//				rSSpeed = .5;
//				double dist = 1700;
//				if(rValue < dist)
//				{
//					Drivetrain.tankDrive(lSSpeed, rSSpeed);
//				}
//				else
//				{
//					stopWatch.start();
//					currentState = 6;
//				}
//				break;
//			case 6:
//				if(stopWatch.get() < .6)
//				{
//					
//				}
//				else
//				{
//					stopWatch.stop();
//					stopWatch.reset();
//					prevRightEncoder = rValue;
//					currentState = 7;
//				}
//				break;
//			case 7:
//				rValue -= prevRightEncoder;
//				lSSpeed = Drivetrain.keepMotorInPlace(prevLeftEncoder, lValue);
//				rSSpeed = -.7;
//				dist = 2700;
//				rValue = Math.abs(rValue);
//				if(rValue < dist)
//				{
//					Drivetrain.tankDrive(lSSpeed, rSSpeed);
//				}
//				else
//				{
//					prevLeftEncoder = lValue;
//					prevRightEncoder = rValue;
//					currentState = 99;
//				}
//				break;
//			case 8:
////				rValue -= prevRightEncoder;
////				lValue -= prevLeftEncoder;
//				if(!Drivetrain.reachedDistance(lValue, rValue, 2400))
//				{
//					Drivetrain.driveBack(lValue, rValue, -.4);
//				}
//				else
//				{
//					currentState = 9;
//					prevLeftEncoder = lValue;
//					prevRightEncoder = rValue;
//				}
//				break;
//			case 9:
//				lValue -= prevLeftEncoder;
//				rSSpeed = Drivetrain.keepMotorInPlace(prevRightEncoder, rValue);
//				lSSpeed = -.5;
//				dist = 4480;
//				lValue = Math.abs(lValue);
//				if(lValue < dist)
//				{
//					Drivetrain.tankDrive(lSSpeed, rSSpeed);
//				}
//				else
//				{
//					prevLeftEncoder = lValue;
//					prevRightEncoder = rValue;
//					stopWatch.start();
//					currentState = 99;
//				}
//				break;
//			case 99:
//				rValue -= prevRightEncoder;
//				lValue -= prevLeftEncoder;
//				System.out.println("lValue: " + lValue);
//				System.out.println("rValue: " + rValue);
//				if(!Drivetrain.reachedDistance(lValue, rValue, 3000))
//				{
//					Drivetrain.driveBack(lValue, rValue, -.3);
//				}
//				else
//				{
//					currentState = 100;
//				}
//				break;
//			case 100:
//				Drivetrain.stop();
//				break;
//		}
//	}
//	
//	public static void testCurve(double lValue, double rValue)
//	{
//		switch(currentState)
//		{
//			case 0:
//				stopWatch.stop();
//				stopWatch.reset();
//				stopWatch.start();
//				IntakeWheels.pickUp(.2);
//				currentState = 1;
//				break;
//			case 1:
//				time = stopWatch.get();
//				if(lValue == 0 && rValue == 0 && time >= 2)
//				{
//					stopWatch.stop();
//					Elevator.stopEleVader();
//					ElevatorLevel.resetElevatorEncoders();
//					stopWatch.reset();
//					currentState = 2;
//					
//				}
//				else if(lValue == 0 && rValue == 0 && ElevatorLevel.reachedStop())
//				{
//					Elevator.stopEleVader();
//					ElevatorLevel.resetElevatorEncoders();
//					currentState = 2;
//					stopWatch.stop();
//				}
//				else
//				{
//					Elevator.moveEleVader(-.23);
//					Encoders.resetEncoders();
//				}
//				break;
//			case 2:
//				if(!Drivetrain.reachedDistance(lValue, rValue, 5000))
//				{
//					Drivetrain.driveForw(lValue, rValue, .7);
//				}
//				else if(!Drivetrain.reachedDistance(lValue, rValue, 8200))
//				{
//					Drivetrain.driveForw(lValue, rValue, .4);
//				}
//				else
//				{
//					prevLeftEncoder = lValue;
//					prevRightEncoder = rValue;
//					currentState = 3;
//				}
//				break;
//			case 3:
//				rValue -= prevRightEncoder;
//				lValue -= prevLeftEncoder;
//				if(Functions.twoCubeSwitchLeftSideFirstCurve(rValue, 5300) != 0)
//				{
//					rSSpeed = Functions.twoCubeSwitchLeftSideFirstCurve(rValue, 5300);
//					lSSpeed = rSSpeed/2.5;
//					Drivetrain.goStraightLeft(lValue, rValue, 2.5, lSSpeed, rSSpeed, .06);
//				}
//				else
//				{
//					prevLeftEncoder = lValue;
//					prevRightEncoder = rValue;
//					currentState = 4;
//				}
//				break;
//			case 4:
//				rValue -= prevRightEncoder;
//				lValue -= prevLeftEncoder;
//				if(!Drivetrain.reachedDistance(lValue, rValue, 3000))
//				{
//					Drivetrain.driveForw(lValue, rValue, .5);
//				}
//				else if(!Drivetrain.reachedDistance(lValue, rValue, 5000))
//				{
//					Drivetrain.driveForw(lValue, rValue, .6);
//				}
//				else
//				{
//					currentState = 5;
//				}
//				break;
//			case 5:
//				Drivetrain.stop();
//				break;
//		}
//	}
//	
//	public static void testPID(double lValue, double rValue)
//	{
//		switch(currentState)
//		{
//			case 0:
//				stopWatch.stop();
//				stopWatch.reset();
//				stopWatch.start();
//				IntakeWheels.pickUp(.2);
//				currentState = 1;
//				break;
//			case 1:
//				time = stopWatch.get();
//				if(lValue == 0 && rValue == 0 && time >= 2)
//				{
//					stopWatch.stop();
//					Elevator.stopEleVader();
//					ElevatorLevel.resetElevatorEncoders();
//					stopWatch.reset();
//					currentState = 2;
//					
//				}
//				else if(lValue == 0 && rValue == 0 && ElevatorLevel.reachedStop())
//				{
//					Elevator.stopEleVader();
//					ElevatorLevel.resetElevatorEncoders();
//					currentState = 2;
//					stopWatch.stop();
//				}
//				else
//				{
//					Elevator.moveEleVader(-.23);
//					Encoders.resetEncoders();
//				}
//				break;
//			case 2:
//				if(!Drivetrain.reachedDistance(lValue, rValue, 8500))
//				{
//					Drivetrain.tankDrive(.7, .7);
//				}
//				else if(!Drivetrain.reachedDistance(lValue, rValue, 10000))
//				{
//					Drivetrain.tankDrive(.4, .4);
//				}
//				else
//				{
//					//120 inches
//					Encoders.testEncoders();
//					currentState = 3;
//				}
//				break;
//			case 3:
//				Drivetrain.stop();
//				break;
//		}
//	}
//	
//	public static void shootRight(double lValue, double rValue)
//	{
//		switch(currentState)
//		{
//			case 0:
//				stopWatch.stop();
//				stopWatch.reset();
//				stopWatch.start();
//				IntakeWheels.pickUp(.2);
//				currentState = 1;
//				break;
//			case 1:
//				time = stopWatch.get();
//				if(lValue == 0 && rValue == 0 && time >= 2)
//				{
//					stopWatch.stop();
//					Elevator.stopEleVader();
//					ElevatorLevel.resetElevatorEncoders();
//					stopWatch.reset();
//					currentState = -1;
//					
//				}
//				else if(lValue == 0 && rValue == 0 && ElevatorLevel.reachedStop())
//				{
//					Elevator.stopEleVader();
//					ElevatorLevel.resetElevatorEncoders();
//					currentState = 2;
//					stopWatch.stop();
//				}
//				else
//				{
//					Elevator.moveEleVader(-.23);
//					Encoders.resetEncoders();
//				}
//				break;
//			case 2:
//				stopWatch.reset();
//				if(ElevatorLevel.reachedPickUp())
//				{
//					currentState = 3;
//				}
//				else
//				{
//					Elevator.moveEleVader(.4);
//				}
//				break;
//		}
//	}
//	
	public static void initialize()
	{
		stopWatch.stop();
		stopWatch.reset();
		Drivetrain.stop();
		Forks.lockTheForks();
		Shifter.lowGear();
		Intake.closeIntake();
		Encoders.resetEncoders();
		IntakeWheels.runIntake(0, 0, true, 0, 0);
		Elevator.stopElevator();
		Elevator.aimedElevatorState = 0;
		Drivetrain.setToBrake();
		prevLeftEncoder = 0;
		prevRightEncoder = 0;
		currentState = 0;
		time = 0;
		stopWatch.start();
	}
}
