package team3647subsystems;

import team3647elevator.Elevator;
import team3647elevator.Wrist;
import team3647pistons.Lock;
import team3647pistons.Shifter;
import com.ctre.phoenix.motorcontrol.ControlMode;
import team3647ConstantsAndFunctions.Constants;

public class ClimbButton 
{
	public static int buttonState;
	
	public static void climb()
	{
		switch(buttonState)
		{
            case 1:
                if(Wrist.reachedUp() && Elevator.reachedBottom())
                {
                    buttonState = 2;
                }
                else if(Wrist.reachedUp() && !Elevator.reachedBottom())
                {
                    Elevator.moveEleVader(-.3);
                }
                else if(!Wrist.reachedUp() && Elevator.reachedBottom())
                {
                    Wrist.wristMotor.set(ControlMode.Position, Constants.up);
                }
                else
                {
                    Elevator.moveEleVader(-.3);
                    Wrist.wristMotor.set(ControlMode.Position, Constants.up);
                }
                //need elevator zero
                break;
            case 2:
                Lock.lock();
                buttonState = 3;
                break;
            case 3:
                if(Elevator.reachedSwitch())
                {
                    buttonState = 4;
                    Elevator.stopEleVader();
                }
                else
                {
                    Elevator.moveElevatorPosition(Constants.sWitch);
                }
                break;
            case 4:
                Shifter.lowGear();
                break;
		}
	}

}