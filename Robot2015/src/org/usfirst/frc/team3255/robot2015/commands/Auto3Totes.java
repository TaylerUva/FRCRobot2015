package org.usfirst.frc.team3255.robot2015.commands;

import org.usfirst.frc.team3255.robot2015.RobotPreferences;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auto3Totes extends CommandGroup {
    
    public  Auto3Totes() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	
    	addSequential(new DoDelay(RobotPreferences.auto3ToteDelay()));
    	addSequential(new DriveDistanceForward(RobotPreferences.auto3ToteStartSpeed(), RobotPreferences.auto3ToteStartDistance()));
    	addSequential(new RotateLeftHighSpeed());
    	addSequential(new DriveDistanceReverse(RobotPreferences.auto3ToteRevSpeed(), RobotPreferences.auto3ToteRevDistance()));
    	addSequential(new CassetteUnloadAndBackup());
    	addSequential(new Rotate180());
    }
}