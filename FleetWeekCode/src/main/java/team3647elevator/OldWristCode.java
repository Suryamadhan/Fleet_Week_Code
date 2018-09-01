// public static void runWrist()
// 	{
// 		switch(wristState)
// 		{
// 			case 0://starting code? big gay
// 				if(WristLevel.reachedFlat())
// 				{
// 					stopWrist();
// 					wristState = 1;
// 					aimedWristState = 1;
// 				} 
// 				else 
// 				{
// 					moveWrist(-0.1);
// 				}
// 			break;
// 				//should be below code for final
// 				//setWristIdle();
// 			case 1://flat
// 				if(manualOverride)
// 				{
// 					aimedWristState = -1;
// 				} 
// 				else if(flat)
// 				{
// 					aimedWristState = 1;
// 				} 
// 				else if(aim)
// 				{
// 					aimedWristState = 2;
// 				} 
// 				else if(idle)
// 				{
// 					aimedWristState = 3;
// 				}
// 				switch(aimedWristState)
// 				{
// 					case 1:
// 						if(WristLevel.reachedFlat())
// 						{
// 							stopWrist();
// 							wristState = 1;
// 						} 
// 						else
// 						{
// 							moveWrist(Constants.moveToFlat);
// 						}
// 					break;
// 					case 2:
// 						if(WristLevel.reachedAim())
// 						{
// 							WristLevel.maintainAimPosition();
// 							wristState = 2;
// 						} 
// 						else
// 						{
// 							moveWrist(Constants.moveToAim);
// 						}
// 					break;
// 					case 3:
// 						if(WristLevel.reachedIdle())
// 						{
// 							WristLevel.maintainIdlePosition();
// 							wristState = 3;
// 						}
// 						else
// 						{
// 						moveWrist(Constants.moveToIdle);
// 						}
// 					break;
// 					case -1:
// 						wristState = -1;
// 					break;
// 				}					
// 			break;
		
// 			case 2://aim
// 				if(manualOverride)
// 				{
// 					aimedWristState = -1;
// 				} 
// 				else if(flat)
// 				{
// 					aimedWristState = 1;
// 				}
// 				else if(aim)
// 				{
// 					aimedWristState = 2;
// 				} 
// 				else if(idle)
// 				{
// 					aimedWristState = 3;
// 				}
// 				switch(aimedWristState)
// 				{
// 					case 1:
// 						if(WristLevel.reachedFlat())
// 						{
// 							stopWrist();
// 							wristState = 1;
// 						}
// 						else
// 						{
// 							moveWrist(Constants.aimMoveToFlat);
// 						}
// 					break;
// 					case 2:
// 					WristLevel.maintainAimPosition();
// 						wristState = 2;
// 					break;
// 					case 3:
// 						if(WristLevel.reachedIdle())
// 						{
// 							WristLevel.maintainIdlePosition();
// 							wristState = 3;
// 						} 
// 						else
// 						{
// 							moveWrist(Constants.moveToIdle);
// 						}
// 					break;
// 					case -1:
// 						wristState = -1;
// 					break;
// 				}
// 			break;		
				
// 			case 3://idle
// 				if(manualOverride)
// 				{
// 					aimedWristState = -1;
// 				}
// 				else if(flat)
// 				{
// 					aimedWristState = 1;
// 				} else if(aim)
// 				{
// 					aimedWristState = 2;
// 				} 
// 				else if(idle)
// 				{
// 					aimedWristState = 3;
// 				}
// 				switch(aimedWristState)
// 				{
// 					case 1:
// 						if(WristLevel.reachedFlat())
// 						{
// 							stopWrist();
// 							wristState = 1;
// 						} 
// 						else
// 						{
// 							moveWrist(Constants.idleMoveToFlat);
// 						}
// 					break;
// 					case 2:
// 						if(WristLevel.reachedAim())
// 						{
// 							WristLevel.maintainAimPosition();
// 							wristState = 2;
// 						} 
// 						else
// 						{
// 							moveWrist(Constants.idleMoveToAim);
// 						}
// 					break;
// 					case 3:
// 					WristLevel.maintainIdlePosition();
// 						wristState = 3;
// 					break;
// 					case -1:
// 						wristState = -1;
// 					break;
// 				}
// 			break;

// 			case -1://manual
// 				if(flat || aim || idle)
// 				{
// 					if(flat)
// 					{
// 						if(WristLevel.reachedFlat())
// 						{
// 							stopWrist();
// 							wristState = 1;
// 							aimedWristState = 1;
// 						} 
// 						else if(WristLevel.wristEncoderValue>Constants.flat)
// 						{
// 							wristState = 2;
// 							aimedWristState = 1;
// 						}
// 					}
// 					else if(aim)
// 					{
// 						if(WristLevel.reachedAim())
// 						{
// 							WristLevel.maintainAimPosition();
// 							wristState = 2;
// 							aimedWristState = 2;
// 						}
// 						else if(WristLevel.wristEncoderValue>Constants.aim)
// 						{
// 							wristState = 3;
// 							aimedWristState = 2;
// 						}
// 						else if(WristLevel.wristEncoderValue<Constants.aim)
// 						{
// 							wristState = 1;
// 							aimedWristState = 2;
// 						}
// 					}
// 					else if (idle)
// 					{
// 						if(WristLevel.reachedIdle())
// 						{
// 							WristLevel.maintainIdlePosition();
// 							wristState = 3;
// 							aimedWristState = 3;
// 						} 
// 						else if(WristLevel.wristEncoderValue>Constants.idle)
// 						{
// 							wristState = 3;
// 							aimedWristState = 3;
// 						}
// 						else if(WristLevel.wristEncoderValue<Constants.idle)
// 						{
// 							wristState = 2;
// 							aimedWristState = 3;
// 						}
// 					}
// 					else
// 					{
// 						wristState = -1;
// 						aimedWristState = -1;
// 					}
					
// 				}
// 				else
// 				{
// 					if(!manualOverride)
// 					{
// 						overrideValue = 0;
// 					}
// 					moveWrist(overrideValue);
// 			}
// 			break;
// 		}
// 	}