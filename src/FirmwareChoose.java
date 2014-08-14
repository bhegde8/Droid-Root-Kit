//Unlicense

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.*;

public class FirmwareChoose extends JFrame implements ActionListener
{
    public static JLabel osIndicator;
    public static JLabel firmwareIndicator;
    public static JButton downloadDriversButton;
    public static JButton downgradeFirmwareButton;
    public static JButton rootButton;
    public static JTextArea terminalConnection;
    
    private static Process mProcess;
     
   public FirmwareChoose()
   {
     getContentPane().setLayout(null);
     setupGUI();
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }
   void setupGUI()
   {
     	osIndicator = new JLabel();
	osIndicator.setLocation(280,11);
	osIndicator.setSize(100,20);
	osIndicator.setText("Detecting OS...");
	getContentPane().add(osIndicator);

	firmwareIndicator = new JLabel();
	firmwareIndicator.setLocation(9,11);
	firmwareIndicator.setSize(150,20);
	firmwareIndicator.setText("");
	getContentPane().add(firmwareIndicator);

	downloadDriversButton = new JButton();
	downloadDriversButton.setLocation(7,90);
	downloadDriversButton.setSize(120,50);
	downloadDriversButton.setText("1: Get Drivers");
	getContentPane().add(downloadDriversButton);
	
	downloadDriversButton.addActionListener(this);

	downgradeFirmwareButton = new JButton();
	downgradeFirmwareButton.setLocation(136,90);
	downgradeFirmwareButton.setSize(120,50);
	downgradeFirmwareButton.setText("2: Downgrade");
	getContentPane().add(downgradeFirmwareButton);
	
	downgradeFirmwareButton.addActionListener(this);

	rootButton = new JButton();
	rootButton.setLocation(265,90);
	rootButton.setSize(120,50);
	rootButton.setText("3: Root");
	getContentPane().add(rootButton);
	
	rootButton.addActionListener(this);

	terminalConnection = new JTextArea();
	terminalConnection.setLocation(8,156);
	terminalConnection.setSize(377,200);
	terminalConnection.setForeground( new Color(-13369549) );
	terminalConnection.setBackground( new Color(-16777216) );
	
	terminalConnection.setRows(5);
	terminalConnection.setColumns(5);
	terminalConnection.setEditable(false);
	getContentPane().add(terminalConnection);

	setTitle("1963Root");
	setSize(410,400);
	setVisible(true);
	setResizable(true);
	
	
   }
    public static void main( String args[] )
   {
     new FirmwareChoose();
     
     
     
     
     
     downloadDriversButton.setEnabled(false);
     downgradeFirmwareButton.setEnabled(false);
     rootButton.setEnabled(false);
     
     
     terminalConnection.setText("Loaded 1963Root Components\n");
     
     String os = System.getProperty("os.name");
     
     if(os.contains("OS X"))
     {
    	 osIndicator.setText("(Mac) OS X");
    	
    	 
    	
     }
     
     else if (os.contains("Windows"))
     {
    	 osIndicator.setText("Windows");
    	
     }
     
     else if(os.contains("Linux"))
     {
    	 osIndicator.setText("Linux");
   
    	 
    	 
     }
     
     terminalConnection.setText("Detected OS as " + System.getProperty("os.name") + "\n");
     
     terminalConnection.setText("Attempting to detect device firmware... \n");
    
     boolean needsDrivers = false;
     
     if(osIndicator.getText().contains("Windows"))
     {
    	 
    	String testDrivers = executeCommand("adb.exe shell getprop ro.build.version.sdk"); //test if drivers are needed
    	String tdTrim = testDrivers.trim();
    	
    	System.out.println(tdTrim);
    	
    	 if(!tdTrim.contains("19"))
    	 {
    		 firmwareIndicator.setText("Detection error / not 4.4");
    		 needsDrivers = true;
    		 downloadDriversButton.setEnabled(true);
    		 terminalConnection.setText("IMPORTANT:\n--------------\nIf you are using a 4.2.2 build, upgrade to KitKat (use FXZ if rooted) \nand restart the program. \n\nIf you didn't enable USB debugging and didn't connect your phone \nto your PC, do it now and restart the program. \n\nIf you have checked these things, click Get Drivers.");
    	 }
     }
     
     if(needsDrivers == false) //doesn't need drivers, can continue trying
     {
    	 
    	 //-----------------------------------------------------------------------------------------
    	 String deviceModelUntouched = ""; //this program won't work for non windows/linux/mac users for sure, intentionally
    	 if(osIndicator.getText().contains("Windows"))
    	 {
    		 deviceModelUntouched = executeCommand("adb.exe shell getprop ro.product.model");
    	 }	
    	 if(osIndicator.getText().contains("OS X"))
    	 {
    		 deviceModelUntouched = executeCommand("./mac/adb shell getprop ro.product.model");
    	 }
    	 if(osIndicator.getText().contains("Linux"))
    	 {
    		 deviceModelUntouched = executeCommand("./linux/adb shell getprop ro.product.model");
    	 }
	     terminalConnection.setText("Got device model");
	     //-------------------------------------------------------------------------------------------
	     String deviceFirmwareUntouched = "";
	     if(osIndicator.getText().contains("Windows"))
    	 {
    		 deviceFirmwareUntouched = executeCommand("adb.exe shell getprop ro.build.display.id");
    	 }	
    	 if(osIndicator.getText().contains("OS X"))
    	 {
    		 deviceFirmwareUntouched = executeCommand("./mac/adb shell getprop ro.build.display.id");
    	 }
    	 if(osIndicator.getText().contains("Linux"))
    	 {
    		 deviceFirmwareUntouched = executeCommand("./linux/adb shell getprop ro.build.display.id");
    	 }
	     terminalConnection.setText("Got device firmware version");
	     //-----------------------------------------------------------------------------------------------
	     
	     String deviceModel = deviceModelUntouched.trim();
	     System.out.println(deviceModel);
	     
	     String deviceFirmware = deviceFirmwareUntouched.trim();
	     
	     deviceFirmware.trim();
	     
	     if(deviceModel.contains("XT1030")) //droid mini
	     {
	     		firmwareIndicator.setText("DROID MINI |");
	     }
	     if(deviceModel.contains("XT1080")) //droid ultra
	     {
	     		firmwareIndicator.setText("DROID ULTRA |");
	     }
	     if(deviceModel.contains("XT1080M")) //droid maxx
	     {
	     		firmwareIndicator.setText("DROID MAXX |");
	     }
	     
	     terminalConnection.setText("Stored device model");
	     
	     switch(deviceFirmware)
	     {
	     	case "SU2-3":
	     		String fi = firmwareIndicator.getText();
	     		
	     		firmwareIndicator.setText(fi + " 19.5.3");
	     		break;
	     	case "SU2-3.3":
	     		String fiy = firmwareIndicator.getText();
	     		
	     		System.out.println(fiy);
	     		firmwareIndicator.setText(fiy + " 19.6.3");
	     		break;
	     }
	     
	     terminalConnection.setText("Stored device firmware");
	     
	    
	     
	     if(firmwareIndicator.getText().contains("19.6.3")) //put the downgrade button there for 19.6.3 users
	     {
	    	 downgradeFirmwareButton.setEnabled(true);
	     }
	     
	     if(firmwareIndicator.getText().contains("19.5.3")) //root button available for 19.5.3 users
	     {
	    	 rootButton.setEnabled(true);
	     }
	     
	     terminalConnection.setText("EXTREMELY IMPORTANT\n-----------\nProceed to downgrade or root only if the text\nat the left top corner has your device name and build number!\n\nI am not responsible for any possible damage caused by this progra\nm.\n-------------\nDO NOT DISCONNECT YOUR DEVICE DURING EITHER PROCESS\n\nI WILL NOT GUARANTEE THAT THIS WORKS");
     }
   }
    
    private static String executeCommand(String command) { //execute shell command (windows/mac/linux)
    	 
		StringBuffer output = new StringBuffer();
 
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = 
                            new BufferedReader(new InputStreamReader(p.getInputStream()));
 
                        String line = "";			
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}
 
		} catch (Exception e) {
			e.printStackTrace();
		}
 
		return output.toString();
 
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == downloadDriversButton) // http://d1tsx1og7dz7v4.cloudfront.net/MotorolaDeviceManager_2.4.5.exe
		{
			terminalConnection.setText("Restart your computer after you finish the installation\n\nRestart your computer after you finish the installation\n\nRestart your computer after you finish the installation\n\nRestart your computer after you finish the installation\n\nRestart your computer after you finish the installation\n\nRestart your computer after you finish the installation");
			executeCommand("Elevate.exe mdm.exe");
			
			
		}
		if(e.getSource() == downgradeFirmwareButton)
		{
			downgradeFirmwareButton.setEnabled(false);
			terminalConnection.setText("Sending command: adb reboot-bootloader");
			
			if(osIndicator.getText().contains("Windows"))
	    	 {
	    		 executeCommand("adb.exe reboot-bootloader");
	    	 }	
	    	 if(osIndicator.getText().contains("OS X"))
	    	 {
	    		 executeCommand("./mac/adb reboot-bootloader");
	    	 }
	    	 if(osIndicator.getText().contains("Linux"))
	    	 {
	    		 executeCommand("./linux/adb reboot-bootloader");
	    	 }
		     
		     terminalConnection.setText("\n\nSENT REBOOT COMMAND, WAITING.......");
		     try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		     terminalConnection.setText("Finished waiting, testing fastboot connection...");
		     
		     String outputr = "";
				if(osIndicator.getText().contains("Windows"))
		    	 {
		    		outputr = executeCommand("fastboot.exe getvar reason");
		    	 }	
		    	 if(osIndicator.getText().contains("OS X"))
		    	 {
		    		 outputr = executeCommand("./mac/fastboot getvar reason");
		    	 }
		    	 if(osIndicator.getText().contains("Linux"))
		    	 {
		    		 outputr = executeCommand("./linux/fastboot getvar reason");
		    	 }
		    	 
		    	 System.out.println(outputr);
		    	 
		    	 if(!outputr.contains("waiting for device")) //if the program hangs here, plug in your USB cable!
		    	 {
		    		 terminalConnection.setText("Detected working connection.");
		    		 terminalConnection.setText("Starting to downgrade...");
		    		 if(osIndicator.getText().contains("Windows"))
			    	 {
		    			 terminalConnection.setText("oem fb_mode_set");
		    			 outputr = executeCommand("fastboot.exe oem fb_mode_set");
		    			 
		    			 terminalConnection.setText("flash partition gpt.bin");
		    			 outputr = executeCommand("fastboot.exe flash partition 19.5.3\\gpt.bin");
		    			 
		    			 terminalConnection.setText("flash motoboot motoboot.img");
		    			 outputr = executeCommand("fastboot.exe flash motoboot 19.5.3\\motoboot.img");
		    			 
		    			 terminalConnection.setText("flash logo logo.bin");
		    			 outputr = executeCommand("fastboot.exe flash logo 19.5.3\\logo.bin");
		    			 
		    			 terminalConnection.setText("flash boot boot.img");
		    			 outputr = executeCommand("fastboot.exe flash boot 19.5.3\\boot.img");
		    			 
		    			 terminalConnection.setText("flash recovery recovery.img");
		    			 outputr = executeCommand("fastboot.exe flash recovery 19.5.3\\recovery.img");
		    			 
		    			 terminalConnection.setText("mFlash system system.img");
		    			 outputr = executeCommand("mfastboot.exe flash system 19.5.3\\system.img");
			    	 
		    			 terminalConnection.setText("flash modem NON-HLOS.bin");
		    			 outputr = executeCommand("fastboot.exe flash modem 19.5.3\\NON-HLOS.bin");
		    			 
		    			 terminalConnection.setText("erase modemst1");
		    			 outputr = executeCommand("fastboot.exe erase modemst1");
		    			 
		    			 terminalConnection.setText("erase modemst2");
		    			 outputr = executeCommand("fastboot.exe erase modemst2");
		    			 
		    			 terminalConnection.setText("flash fsg fsg.mbn");
		    			 outputr = executeCommand("fastboot.exe flash fsg fsg.mbn");
		    			 
		    			 terminalConnection.setText("erase cache");
		    			 outputr = executeCommand("fastboot.exe erase cache");
		    			 
		    			 terminalConnection.setText("erase userdata");
		    			 outputr = executeCommand("fastboot.exe erase userdata");
		    			 
		    			 terminalConnection.setText("erase customize");
		    			 outputr = executeCommand("fastboot.exe erase customize");
		    			 
		    			 terminalConnection.setText("erase clogo");
		    			 outputr = executeCommand("fastboot.exe erase clogo");
		    			 
		    			 terminalConnection.setText("oem config carrier vzw");
		    			 outputr = executeCommand("fastboot.exe oem config carrier vzw");
		    			 
		    			 terminalConnection.setText("oem fb_mode_clear");
		    			 outputr = executeCommand("fastboot.exe oem fb_mode_clear");
		    			 
		    			 terminalConnection.setText("reboot");
		    			 outputr = executeCommand("fastboot.exe reboot");
		    			 
		    			 terminalConnection.setText("Done! PLEASE RESTART THIS PROGRAM!");
			    	 
			    	 
			    	 }	
			    	 if(osIndicator.getText().contains("OS X"))
			    	 {
			    		 terminalConnection.setText("oem fb_mode_set");
		    			 outputr = executeCommand("./mac/fastboot oem fb_mode_set");
		    			 
		    			 terminalConnection.setText("flash partition gpt.bin");
		    			 outputr = executeCommand("./mac/fastboot flash partition 19.5.3\\gpt.bin");
		    			 
		    			 terminalConnection.setText("flash motoboot motoboot.img");
		    			 outputr = executeCommand("./mac/fastboot flash motoboot 19.5.3\\motoboot.img");
		    			 
		    			 terminalConnection.setText("flash logo logo.bin");
		    			 outputr = executeCommand("./mac/fastboot flash logo 19.5.3\\logo.bin");
		    			 
		    			 terminalConnection.setText("flash boot boot.img");
		    			 outputr = executeCommand("./mac/fastboot flash boot 19.5.3\\boot.img");
		    			 
		    			 terminalConnection.setText("flash recovery recovery.img");
		    			 outputr = executeCommand("./mac/fastboot flash recovery 19.5.3\\recovery.img");
		    			 
		    			 terminalConnection.setText("mFlash system system.img");
		    			 outputr = executeCommand("/mac/mfastboot flash system 19.5.3\\system.img");
			    	 
		    			 terminalConnection.setText("flash modem NON-HLOS.bin");
		    			 outputr = executeCommand("./mac/fastboot flash modem 19.5.3\\NON-HLOS.bin");
		    			 
		    			 terminalConnection.setText("erase modemst1");
		    			 outputr = executeCommand("./mac/fastboot erase modemst1");
		    			 
		    			 terminalConnection.setText("erase modemst2");
		    			 outputr = executeCommand("./mac/fastboot erase modemst2");
		    			 
		    			 terminalConnection.setText("flash fsg fsg.mbn");
		    			 outputr = executeCommand("./mac/fastboot flash fsg fsg.mbn");
		    			 
		    			 terminalConnection.setText("erase cache");
		    			 outputr = executeCommand("./mac/fastboot erase cache");
		    			 
		    			 terminalConnection.setText("erase userdata");
		    			 outputr = executeCommand("./mac/fastboot erase userdata");
		    			 
		    			 terminalConnection.setText("erase customize");
		    			 outputr = executeCommand("./mac/fastboot erase customize");
		    			 
		    			 terminalConnection.setText("erase clogo");
		    			 outputr = executeCommand("./mac/fastboot erase clogo");
		    			 
		    			 terminalConnection.setText("oem config carrier vzw");
		    			 outputr = executeCommand("./mac/fastboot oem config carrier vzw");
		    			 
		    			 terminalConnection.setText("oem fb_mode_clear");
		    			 outputr = executeCommand("./mac/fastboot oem fb_mode_clear");
		    			 
		    			 terminalConnection.setText("reboot");
		    			 outputr = executeCommand("./mac/fastboot reboot");
		    			 
		    			 terminalConnection.setText("Done! PLEASE RESTART THIS PROGRAM!");
			    	 }
			    	 if(osIndicator.getText().contains("Linux"))
			    	 {
			    		 terminalConnection.setText("oem fb_mode_set");
		    			 outputr = executeCommand("./linux/fastboot oem fb_mode_set");
		    			 
		    			 terminalConnection.setText("flash partition gpt.bin");
		    			 outputr = executeCommand("./linux/fastboot flash partition 19.5.3\\gpt.bin");
		    			 
		    			 terminalConnection.setText("flash motoboot motoboot.img");
		    			 outputr = executeCommand("./linux/fastboot flash motoboot 19.5.3\\motoboot.img");
		    			 
		    			 terminalConnection.setText("flash logo logo.bin");
		    			 outputr = executeCommand("./linux/fastboot flash logo 19.5.3\\logo.bin");
		    			 
		    			 terminalConnection.setText("flash boot boot.img");
		    			 outputr = executeCommand("./linux/fastboot flash boot 19.5.3\\boot.img");
		    			 
		    			 terminalConnection.setText("flash recovery recovery.img");
		    			 outputr = executeCommand("./linux/fastboot flash recovery 19.5.3\\recovery.img");
		    			 
		    			 terminalConnection.setText("mFlash system system.img");
		    			 outputr = executeCommand("/mac/mfastboot flash system 19.5.3\\system.img");
			    	 
		    			 terminalConnection.setText("flash modem NON-HLOS.bin");
		    			 outputr = executeCommand("./linux/fastboot flash modem 19.5.3\\NON-HLOS.bin");
		    			 
		    			 terminalConnection.setText("erase modemst1");
		    			 outputr = executeCommand("./linux/fastboot erase modemst1");
		    			 
		    			 terminalConnection.setText("erase modemst2");
		    			 outputr = executeCommand("./linux/fastboot erase modemst2");
		    			 
		    			 terminalConnection.setText("flash fsg fsg.mbn");
		    			 outputr = executeCommand("./linux/fastboot flash fsg fsg.mbn");
		    			 
		    			 terminalConnection.setText("erase cache");
		    			 outputr = executeCommand("./linux/fastboot erase cache");
		    			 
		    			 terminalConnection.setText("erase userdata");
		    			 outputr = executeCommand("./linux/fastboot erase userdata");
		    			 
		    			 terminalConnection.setText("erase customize");
		    			 outputr = executeCommand("./linux/fastboot erase customize");
		    			 
		    			 terminalConnection.setText("erase clogo");
		    			 outputr = executeCommand("./linux/fastboot erase clogo");
		    			 
		    			 terminalConnection.setText("oem config carrier vzw");
		    			 outputr = executeCommand("./linux/fastboot oem config carrier vzw");
		    			 
		    			 terminalConnection.setText("oem fb_mode_clear");
		    			 outputr = executeCommand("./linux/fastboot oem fb_mode_clear");
		    			 
		    			 terminalConnection.setText("reboot");
		    			 outputr = executeCommand("./linux/fastboot reboot");
		    			 
		    			 terminalConnection.setText("Done! PLEASE RESTART THIS PROGRAM!");
			    	 }
		    	 }
		     
		}
		if(e.getSource() == rootButton)
		{
			rootButton.setEnabled(false);
			terminalConnection.setText("Unfourtanetly, this is not a permanent root.\nThat's because the maker of a write protection exploit is not\nallowing anyone to integrate the exploiter in one-click tools.\nI can only root your device whenever you click this button.");
			try {
				 
			      File file = new File("alreadyThere.txt");
		 
			      if (file.createNewFile()){
			    	  
			    	  if(osIndicator.getText().contains("Windows"))
			    	  {
			    		  terminalConnection.setText("push pie.jar");
			    		  executeCommand("adb.exe push pie.jar /data/local/atvc");
			    		  
			    		  terminalConnection.setText("push root.sh");
			    		  executeCommand("adb.exe push root.sh /data/local/atvc");
			    		  
			    		  executeCommand("adb.exe shell chmod 755 /data/local/atvc/root.sh");
			    		  
			    		  executeCommand("adb.exe shell /data/local/atvc/root.sh");
			    		  terminalConnection.setText("Applied root!");
			    	  }
			    	  if(osIndicator.getText().contains("OS X"))
			    	  {
			    		  terminalConnection.setText("push pie.jar");
			    		  executeCommand("./mac/adb push pie.jar /data/local/atvc");
			    		  
			    		  terminalConnection.setText("push root.sh");
			    		  executeCommand("./mac/adb push root.sh /data/local/atvc");
			    		  
			    		  executeCommand("./mac/adb shell chmod 755 /data/local/atvc/root.sh");
			    		  
			    		  executeCommand("./mac/adb shell /data/local/atvc/root.sh");
			    		  terminalConnection.setText("Applied root!");
			    	  }
			    	  if(osIndicator.getText().contains("Linux"))
			    	  {
			    		  terminalConnection.setText("push pie.jar");
			    		  executeCommand("./linux/adb push pie.jar /data/local/atvc");
			    		  
			    		  terminalConnection.setText("push root.sh");
			    		  executeCommand("./linux/adb push root.sh /data/local/atvc");
			    		  
			    		  executeCommand("./linux/adb shell chmod 755 /data/local/atvc/root.sh");
			    		  
			    		  executeCommand("./linux/adb shell /data/local/atvc/root.sh");
			    		  terminalConnection.setText("Applied root!");
			    	  }
			    	  
			      }
			      else
			      {
			    	  if(osIndicator.getText().contains("Windows"))
			    	  {
			    		  executeCommand("adb.exe shell /data/local/atvc/root.sh");
			    		  terminalConnection.setText("Applied root!");
			    	  }
			    	  if(osIndicator.getText().contains("Linux"))
			    	  {
			    		  executeCommand("./linux/adb shell /data/local/atvc/root.sh");
			    		  terminalConnection.setText("applied root!");
			    	  }
			    	  if(osIndicator.getText().contains("OS X"))
			    	  {
			    		  executeCommand("./mac/adb shell /data/local/atvc/root.sh");
			    		  terminalConnection.setText("applied root!");
			    	  }
			    	  
			      }
		 
		    	} catch (IOException err) {
			      err.printStackTrace();
			}
		 
		    	
		}
	}

}  
