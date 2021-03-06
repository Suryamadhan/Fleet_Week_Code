package org.usfirst.frc.team3647.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.MotorSafetyHelper;
import edu.wpi.first.wpilibj.Timer;
import team3647elevator.Elevator;
import team3647elevator.IntakeWheels;
import team3647elevator.Wrist;
import team3647pistons.Intake;
import team3647pistons.Lock;
import team3647pistons.Compressor007;
import team3647pistons.Forks;
import team3647pistons.Shifter;
import team3647subsystems.ClimbButton;
import team3647subsystems.Drivetrain;
import team3647subsystems.Encoders;
import team3647subsystems.Joysticks;
import team3647subsystems.Lights;
import team3647subsystems.TiltServo;


public class Robot extends IterativeRobot 
{
	//Objects
	Encoders enc;
	Joysticks joy;
	Elevator eleVader;
	Wrist wrist;
	MotorSafety safety;
	MotorSafetyHelper safetyChecker;
	CameraServer server;
	
	Timer stopWatch = new Timer();
	Timer newStopWatch = new Timer();
	int run = 0;
	double prevLeftEncoder = 0, prevRightEncoder = 0;

	//Test Variables
	boolean driveEncoders, driveCurrent, elevatorCurrent, elevatorEncoder, bannerSensor, currentState, wristEncoder, wristLimitSwitch, wristCurrent, intakeBanner;

	@Override
	public void robotInit() 
	{
		try
		{
			CrashChecker.logRobotInit();
			enc = new Encoders();
			safetyChecker = new MotorSafetyHelper(safety);
			joy = new Joysticks();
			eleVader = new Elevator();
			Encoders.resetEncoders();
			Elevator.resetElevatorEncoders();
			Elevator.elevatorInitialization();
			Drivetrain.drivetrainInitialization();
			setTests();
			Wrist.configWristPID();
			
		}
		catch(Throwable t)
		{
			CrashChecker.logThrowableCrash(t);
			throw t;
		}
	}
	
	public void setTests()
	{
		driveEncoders = false;
		driveCurrent = false;
		elevatorCurrent = false;
		elevatorEncoder = false;
		bannerSensor = false;
		currentState = false;
		wristEncoder = false;
		wristLimitSwitch = false;
		wristCurrent = false;
		intakeBanner = false;
	}
	
	@Override
	public void autonomousInit() 
	{
		try 
		{
			CrashChecker.logAutoInit();
			Autonomous.initialize();
		}
		catch(Throwable t)
		{
			CrashChecker.logThrowableCrash(t);
			throw t;
		}	
	}

	@Override
	public void autonomousPeriodic() 
	{
		while(DriverStation.getInstance().isAutonomous() && !DriverStation.getInstance().isDisabled())
		{
			//runMotorSafety();
			Elevator.setElevatorEncoder();
			Wrist.setWristEncoder();
			Autonomous.runAuto();
			Lights.runLights();
			//Autonomous.runAuto(Encoders.leftEncoderValue, Encoders.rightEncoderValue);
			//runTests();
		}
	}
	
	@Override
	public void disabledPeriodic()
	{
		Drivetrain.setToCoast();
	}
	
	@Override
	public void teleopInit()
	{
		Drivetrain.setToCoast();
		Forks.lockTheForks();
		Shifter.lowGear();
		Elevator.aimedElevatorState = 0;
		Wrist.aimedWristState = 0;
		stopWatch.stop();
		stopWatch.reset();
		run = 0;
		ClimbButton.buttonState = 1;
	}
	
	@Override
	public void teleopPeriodic() 
	{
		try 
		{
			CrashChecker.logTeleopPeriodic();
			updateJoysticks();
			runMotorSafety();
			runPistonsandForks();
			runDrivetrain();
			runElevator();
			IntakeWheels.runIntake(joy.leftTrigger1, joy.rightTrigger1, false, 0, 0);
			runWrist();
			Lights.runLights();
			runTests();
		}
		catch(Throwable t)
		{
			CrashChecker.logThrowableCrash(t);
			throw t;
		}
	}

	@Override
	public void testInit()
	{
		try 
		{
			CrashChecker.logAutoInit();
		}
		catch(Throwable t)
		{
			CrashChecker.logThrowableCrash(t);
			throw t;
		}	
	}
	
	@Override
	public void testPeriodic() 
	{
//		updateJoysticks();
//		enc.setEncoderValues();
//		eleVader.setElevatorEncoder();
//		runPistonsandForks();
//		IntakeWheels.runIntake(joy.leftTrigger1, joy.rightTrigger1, false, 0, 0);
//		Elevator.moveEleVader(joy.rightJoySticky * .4);
//		Drivetrain.tankDrive(joy.leftJoySticky, joy.leftJoySticky);
//		Lights.runLights();
//		runTests();
		runMotorSafety();
		updateJoysticks();
		enc.setEncoderValues();
//		Autonomous.test(Encoders.leftEncoderValue, Encoders.rightEncoderValue, joy.buttonA);
//		Elevator.moveEleVader(joy.rightJoySticky * 1);
//		Shifter.runPiston(joy.buttonY);
	}
	
	
	public void updateJoysticks()
	{
		joy.setMainContollerValues();
		joy.setCoDriverContollerValues();
	}
	
	public void runElevator()
	{
		eleVader.setElevatorEncoder();
		if(Shifter.piston.get() == DoubleSolenoid.Value.kReverse)
		{
			Elevator.moveEleVader(joy.rightJoySticky1 * -1);
		}
		else
		{
			Elevator.setElevatorButtons(joy.buttonA1, joy.buttonB1,  joy.buttonY1, joy.buttonX1);
			Elevator.setManualOverride(joy.rightJoySticky1 * .6);
			Elevator.runElevator();
		}
		//Elevator.climbPrep(joy.buttonB);
	}

	public void runWrist(){
		Wrist.setWristEncoder();
		Wrist.setWristButtons(joy.dPadDown,joy.dPadSide,joy.dPadUp);
		Wrist.setManualWristOverride(joy.leftJoySticky1 * 0.6);
		Wrist.runWrist();
	}

	public void runPistonsandForks()
	{
		Intake.runIntake(joy.rightBumper1);
		Forks.runPiston(joy.buttonX);
		Shifter.runPiston(joy.buttonY);
		TiltServo.PullForks(joy.leftTrigger, joy.rightTrigger);
		Lock.runPiston(joy.buttonA);
		Compressor007.runCompressor();
	}
	
	public void runDrivetrain()
	{
		enc.setEncoderValues();
		if(joy.leftBumper)
		{
			Drivetrain.arcadeDrive(Encoders.leftEncoderValue, Encoders.rightEncoderValue, joy.leftJoySticky * .45, joy.rightJoyStickx * .5);
		}
		else
		{
			//Drivetrain.arcadeDrive(Encoders.leftEncoderValue, Encoders.rightEncoderValue, joy.leftJoySticky, joy.rightJoyStickx);
			//Drivetrain.FRCarcadedrive(joy.leftJoySticky, joy.rightJoyStickx);
			//Drivetrain.runMEATDrivetrain(joy.leftJoySticky, joy.rightJoyStickx);
			Drivetrain.newArcadeDrive(joy.leftJoySticky, joy.rightJoyStickx);
		}
	}
	
	public void runMotorSafety()
	{
		safetyChecker.setSafetyEnabled(false);
	}
	
	public void runTests()
	{
		if(driveEncoders)
		{
			Encoders.testEncoders();
		}
		if(driveCurrent)
		{
			Drivetrain.testDrivetrainCurrent();
		}
		if(elevatorCurrent)
		{
			Elevator.testElevatorCurrent();
		}
		if(bannerSensor)
		{
			Elevator.testBannerSensor();
		}
		if(currentState)
		{
			//System.out.println(Autonomous.currentState);
		}
		if(elevatorEncoder)
		{
			Elevator.testElevatorEncoders();
		}
		if(wristEncoder){
			wrist.testWristEncoder();
		}
		if(wristCurrent){
			Wrist.testWristCurrent();
		}
		if(wristLimitSwitch){
			Wrist.testLimitSwitch();
		}
		if(intakeBanner){
			IntakeWheels.testBannerSensor();
		}
	}
}
