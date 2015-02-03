package org.usfirst.frc.team3255.robot2015.commands;

/**
 *
 */
public class ClawMoveUp extends CommandBase {

    double speed;
	
	public ClawMoveUp() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(claw);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	speed = claw.getLiftSpeed();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	claw.setSpeed(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return claw.isClawToppedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	claw.setSpeed(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}