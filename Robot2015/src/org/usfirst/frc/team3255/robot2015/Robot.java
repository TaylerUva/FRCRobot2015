
package org.usfirst.frc.team3255.robot2015;

import org.usfirst.frc.team3255.robot2015.commands.*;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    Command autonomousCommand = null;
    SendableChooser autoChooser;
    int session;
    Image frame;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    
    public void robotInit() {
    	CommandBase.init();
    	
    	// Autonomous Chooser
    	autoChooser = new SendableChooser();
    	autoChooser.addDefault("Auto Pickup 3 Totes", new AutoPickup3Totes());
    	// TODO determine DriveDistance value
    	autoChooser.addObject("Drive Forward", new DriveDistanceForward(RobotPreferences.autoDriveSpeed(), 
    			RobotPreferences.autoDriveDistance()));
    	// 15 seconds is the duration of autonomous
    	autoChooser.addObject("Do Nothing", new DoDelay(2.0));
    	SmartDashboard.putData("Autonomous Mode Chooser", autoChooser);
    	
    	// USB Camera
    	cameraInit();
        cameraStart();
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();

    	// USB Camera
        cameraUpdate();
	}

    public void autonomousInit() {
        // schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
    	cameraStop();
        cameraStart();
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    
    // TODO Double check if this is the right place to put unlock cassette 
    public void disabledInit() {
    	cameraStop();
        cameraStart();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();

    	// USB Camera
        cameraUpdate();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    
    public void cameraInit() {
    	frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
        // the camera name (ex "cam0") can be found through the roborio web interface
        session = NIVision.IMAQdxOpenCamera(RobotPreferences.cameraName(),
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        NIVision.IMAQdxConfigureGrab(session);
    }
    
    public void cameraStart() {
    	NIVision.IMAQdxStartAcquisition(session);
    }
    
    public void cameraUpdate() {
    	 NIVision.IMAQdxGrab(session, frame, 1);
         CameraServer.getInstance().setImage(frame);
    }
    
    public void cameraStop() {
    	NIVision.IMAQdxStopAcquisition(session);
    }
    
}
