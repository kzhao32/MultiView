import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class MultiView {
	
	String[] fileExtensionsArray = {"3gp", "asf", "wmv", "au", "avi", "flv", "mov", "mp4", "ogm", "ogg", "mkv", "mka", "ts", "mpg", "mp3", "mp2", "nsc", "nsv", "nut", "ra", "rem", "rm", "rv", "rmbv", "a52", "dts", "aac", "flac", "dv", "vid", "tta", "tac", "ty", "wav", "dts", "xa"};
	ArrayList<File> videoFiles = new ArrayList<File>();
	ArrayList<File> watchedVideoFiles = new ArrayList<File>();
	File[] videoFilesInProgress;
	boolean showDebug = true;
	
	public static void main(String[] args) {
		MultiView MultiViewInstance = new MultiView();
		MultiViewInstance.start();
//		String[] config
//		File VLCexecutable = new File("C:\\Program Files (x86)\\VideoLAN\\VLC\\vlc.exe");
//		File inputDirectory; // = new File("G:\\Windows.old.000\\Quarentine.DVDrip.Norsk tekst.Ripp Robban\\2D");
//		int numberOfInstances = 6;
//		String[] fileTypes = {"gom", "avi", "svi", "divx", "asx", "asf"};
//		boolean recursive = true;
////		File file         = new File("C:\\Program Files (x86)\\VideoLAN\\VLC\\vlc.exe");
//
//		//--no-loop
//		//--play-and-exit
//		//--start-time
//		//showdesktop?
//		//G:\Windows.old.000\Quarentine.DVDrip.Norsk tekst.Ripp Robban\2D\Aleska Diamond - Lack of Communication.mp4
		
		// mediainfo path of file name
	}
	
	public void start() {
		String[] prompts = {"VLC Executable:", "Input Directory:", "Filter:", "Number of Instances:", "Time (seconds):", "Playback Speed:", "isRecursive:", "isShowDesktop:", "Default Browsing Directory:"};
		//ArrayList<String> fileExtensions = new ArrayList<String>(Arrays.asList(fileExtensionsArray));
		String[] config = new String[prompts.length];
		String configFileName = "config.txt";
		config[0] = "C:\\Program Files (x86)\\VideoLAN\\VLC\\vlc.exe";
		config[1] = "I:\\Movies and Shows\\Futurama";
		config[2] = "";
		config[3] = "6";
		config[4] = "300";
		config[5] = "1";
		config[6] = "1";
		config[7] = "1";
		config[8] = "I:\\Movies and Shows";
		File configFile = new File(configFileName);
		if (!configFile.exists()) {
			PrintWriter configWriter;
			try {
				configWriter = new PrintWriter(configFile);
				for (int configIndex = 0; configIndex < prompts.length; configIndex++) {
					configWriter.println(prompts[configIndex] + " " + config[configIndex]);
				}
				configWriter.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		try {
			Scanner inputFile = new Scanner(new File(configFileName));
			while (inputFile.hasNext()) {
				String nextLine = inputFile.nextLine().trim();
				if (nextLine.length() > 1 && ((nextLine.charAt(0) == '/' && nextLine.charAt(1) == '/') || nextLine.charAt(0) == ';' || nextLine.charAt(0) == '#')) {
					continue;
				}
				for (int i = 0; i < prompts.length; i++) {
					if (nextLine.contains(prompts[i])) {
						config[i] = nextLine.substring(nextLine.indexOf(prompts[i]) + prompts[i].length()).trim();
					}
				}
			}
			inputFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		final JTextField[] configInputs = new JTextField[prompts.length];
		for (int configIndex = 0; configIndex < configInputs.length; configIndex++) {
			configInputs[configIndex] = new JTextField(80);
			configInputs[configIndex].setText(config[configIndex]);
		}
		
		JTextArea[] configPrompts = new JTextArea[prompts.length];
		for (int configIndex = 0; configIndex < configPrompts.length; configIndex++) {
			configPrompts[configIndex] = new JTextArea();
			configPrompts[configIndex].setEditable(true);
			configPrompts[configIndex].setForeground(Color.GREEN);
			configPrompts[configIndex].setBackground(Color.BLACK);
		}
		configPrompts[0].setText(
				"     \"C:\\Program Files (x86)\\VideoLAN\\VLC\\vlc.exe\"");
		
		configPrompts[1].setText(
				"     \"I:\\Movies and Shows\\Futurama\"");
		
		configPrompts[2].setText(
				"     \"esp\" open files directory containing 'esp'");
		
		configPrompts[3].setText(
				"     \"6\" for 6 instances");
		
		configPrompts[4].setText(
				"     \"300\" for 5 minutes\n" +
				"     \"600\" for 10 minutes\n" +
				"     \"900\" for 15 minutes\n" +
				"     \"1200\" for 20 minutes\n" +
				"     \"1800\" for 30 minutes");
		
		configPrompts[5].setText(
				"     \"1\" for nominal speed\n" +
				"     \"2\" for double speed");
		
		final String defaultBrowsingDirectory = config[config.length-1];
		JButton selectInput = new JButton("Select Input File, Files, File Directory, or File Directories");
		selectInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser fileChooser = new JFileChooser(defaultBrowsingDirectory);
				fileChooser.setMultiSelectionEnabled(true);
				fileChooser.setFileSelectionMode(2);
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File[] selectedFiles = fileChooser.getSelectedFiles();
					String inputDirectoryString = "";
					for (int fileIndex = 0; fileIndex < selectedFiles.length; fileIndex++) {
						if (fileIndex != 0) {
							inputDirectoryString += ";";
						}
						inputDirectoryString += selectedFiles[fileIndex].getAbsolutePath();
					}
					configInputs[1].setText(inputDirectoryString);
				}
			}
		});
		
		JCheckBox isRecursiveCheckBox = new JCheckBox("Recursive (Check To Use Subdirectories)", config[6].equals("1") || config[6].equalsIgnoreCase("y") || config[6].equalsIgnoreCase("yes"));
		
		JCheckBox isShowDesktop = new JCheckBox("ShowDesktop (Check To Minimize Other Windows)", config[7].equals("1") || config[7].equalsIgnoreCase("y") || config[7].equalsIgnoreCase("yes"));
		
		ArrayList<Object> message = new ArrayList<Object>();
		message.add(prompts[0]);
		message.add(configPrompts[0]);
		message.add(configInputs[0]);
		message.add("\n" + prompts[1]);
		message.add(configPrompts[1]);
		message.add(selectInput);
		message.add(configInputs[1]);
		message.add("\n" + prompts[2]);
		message.add(configPrompts[2]);
		message.add(configInputs[2]);
		message.add("\n" + prompts[3]);
		message.add(configPrompts[3]);
		message.add(configInputs[3]);
		message.add("\n" + prompts[4]);
		message.add(configPrompts[4]);
		message.add(configInputs[4]);
		message.add("\n" + prompts[5]);
		message.add(configPrompts[5]);
		message.add(configInputs[5]);
		message.add("\n");
		message.add(isRecursiveCheckBox);
		message.add(isShowDesktop);
		
		int option = JOptionPane.showConfirmDialog(null, message.toArray(), "Options", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			PrintWriter outputStream = null;
			// handle the checkboxes
			try { // config[
				if (isRecursiveCheckBox.isSelected()) {
					configInputs[6].setText("1");
				} else {
					configInputs[6].setText("0");
				}
				if (isShowDesktop.isSelected()) {
					configInputs[7].setText("1");
				} else {
					configInputs[7].setText("0");
				}
				// save config in config file
				outputStream = new PrintWriter(new FileOutputStream(new File(configFileName), true));
				for (int configIndex = 0; configIndex < prompts.length; configIndex++) {
					if (!config[configIndex].equals(configInputs[configIndex].getText().trim())) {
						outputStream.println(prompts[configIndex] + " " + configInputs[configIndex].getText().trim());
					}
					config[configIndex] = configInputs[configIndex].getText().trim();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				outputStream.close();
			}
		} else {
			System.out.println("Program Canceled");
			System.exit(0);
		}
		
		int numberOfInstances = Integer.parseInt(config[3]);
		videoFilesInProgress = new File[numberOfInstances];
		int time = Integer.parseInt(config[4]);
		
		String[] fileInputDirectories = config[1].split(";");
		
		for (int fileInputDirectoryIndex = 0; fileInputDirectoryIndex < fileInputDirectories.length; fileInputDirectoryIndex++) {
			recursiveMultiView(config, new File(fileInputDirectories[fileInputDirectoryIndex]), 1); // call base case
		} // for
		
		System.out.println("There are " + videoFiles.size() + " videos");
		Process[] processes = new Process[numberOfInstances];
		attachShutDownHook(processes);
		if (config[7].equalsIgnoreCase("1")) {
			try {
				Runtime.getRuntime().exec("cscript ShowDesktop.vbs");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String subAllCommand = "\" --play-and-exit --no-loop --no-qt-system-tray --qt-minimal-view --rate=" + config[5] + " --start-time ";
		if (videoFiles.size() == 1) {
			int totalVideoLength = getFileLength(videoFiles.get(0));
			for (int i = 0; i < numberOfInstances; i++) {
				runProcess(processes, i, "\"" + config[0] + "\" \"" + videoFiles.get(0) + subAllCommand + (time <= 0 ? i * totalVideoLength / numberOfInstances : (i * time) % totalVideoLength));
			}
			runSideBySide();
			int counter = 0;
			while (true) {
				for (int i = 0; i < numberOfInstances; i++) {
					try {
						processes[i].exitValue();
						runProcess(processes, i, "\"" + config[0] + "\" \"" + videoFiles.get(0) + subAllCommand + (time <= 0 ? ((numberOfInstances + counter++) * totalVideoLength / numberOfInstances) % totalVideoLength : ((numberOfInstances + counter++) * time) % totalVideoLength));
						maybeRunSideBySide(processes, numberOfInstances);
					} catch (IllegalThreadStateException e) {
					}
				}
			}
		} else {
			for (int i = 0; i < numberOfInstances; i++) {
				File videoFile = getVideoFile(numberOfInstances);
				runProcess(processes, i, "\"" + config[0] + "\" \"" + videoFile + subAllCommand + getVideoTime(videoFile, time));
			}
			runSideBySide();
			while (true) {
				for (int i = 0; i < numberOfInstances; i++) {
					try {
						processes[i].exitValue();
						File videoFile = getVideoFile(numberOfInstances);
						runProcess(processes, i, "\"" + config[0] + "\" \"" + videoFile + subAllCommand + getVideoTime(videoFile, time));
						maybeRunSideBySide(processes, numberOfInstances);
					} catch (IllegalThreadStateException e) {
					}
				}
			}
		}
	}
	
	public File getVideoFile(int numberOfInstances) {
		int random = (int) (Math.random() * videoFiles.size());
		if (videoFiles.size() == 0) {
			System.out.println("there are no videos. Exiting...");
			System.exit(0);
		}
		while (numberOfInstances > videoFiles.size() && Arrays.asList(videoFilesInProgress).contains(videoFiles.get(random))) {
			random++;
			if (random == videoFiles.size()) {
				random = 0;
			}
		}
		File returnFile = videoFiles.get(random);
		System.out.println("videoFiles.size: "+ videoFiles.size());
		watchedVideoFiles.add(returnFile);
		videoFiles.remove(random);
		if (videoFiles.size() <= 0) {
			videoFiles = watchedVideoFiles;
			watchedVideoFiles = new ArrayList<File>();
		}
		return returnFile;
	}
	
	public int getVideoTime(File videoFile, int time) {
		if (time == 0) {
			return 0;
		}
		int totalVideoLength = getFileLength(videoFile);
		if (time < 0) {
			return (int) (Math.random() * totalVideoLength);
		}
		int returnInteger = totalVideoLength - time;
		if (returnInteger < 0) return 0;
		return returnInteger;
	}
	
	public void sleep (int arg0) {
		try {
			Thread.sleep(arg0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void runProcess(Process[] processes, int i, String command) {
		if (showDebug) {
			System.out.println("command: " + command);
		}
		try {
			processes[i] = Runtime.getRuntime().exec(command);
			Runtime.getRuntime().exec("cscript SideBySide.vbs");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void runSideBySide() {
		try {
			Thread.sleep(2000);
			Runtime.getRuntime().exec("cscript SideBySide.vbs");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void maybeRunSideBySide(Process[] processes, int numberOfInstances) {
		boolean isDelay = true;
		for (int j = 0; j < numberOfInstances; j++) {
			try {
				processes[j].exitValue();
				isDelay = false;
			} catch (IllegalThreadStateException e) {
			}
		}
		if (isDelay) {
			runSideBySide();
		}
	}
	
	 public void attachShutDownHook(final Process[] processes){
		 Runtime.getRuntime().addShutdownHook(new Thread() {
			 @Override
			 public void run() {
				 if (videoFiles.size() == 0) {
					 System.out.println("there are no videos. Exiting...");
					 System.exit(0);
				 }
				 try {
					 Thread.sleep(2000);
				 } catch (InterruptedException e) {
					 e.printStackTrace();
				 }
				 for (int i = 0; i < processes.length; i++) {
					 processes[i].destroy();
				 }
			 }
		 });
	 }

	public int getFileLength(File file) {
		try {
			String command = "mediainfo \"" + file.getAbsolutePath() + "\"";
			if (showDebug) {
				System.out.println("command: " + command);
			}
			Process process = Runtime.getRuntime().exec(command);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String data = bufferedReader.readLine();
			int returnValue = 0;
			while (data != null) {
				//System.out.println("bufferedReaderIndex = 1" + "; data = " + data);
				if (data.contains("Duration")) {
					if (data.contains("h")) {
						returnValue += 3600 * Integer.parseInt(data.substring(data.indexOf("h") - 2, data.indexOf("h")).trim());
					}
					if (data.contains("mn")) {
						returnValue += 60 * Integer.parseInt(data.substring(data.indexOf("mn") - 2, data.indexOf("mn")).trim());
					}
					if (data.contains("s")) {
						returnValue += Integer.parseInt(data.substring(data.indexOf("s") - 2, data.indexOf("s")).trim());
					}
					return returnValue;
				}
				data = bufferedReader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public void recursiveMultiView(String[] config, File inputFile, int level) {
		if (showDebug) {
			for (int levelIndex = 0; levelIndex < level; levelIndex++)
				System.out.print("\t");
			System.out.println(inputFile.getAbsolutePath() + "\\");
		}

		File[] listOfInputFiles = null;
		if (inputFile.isDirectory()) {
			listOfInputFiles = inputFile.listFiles();
		} else {
			listOfInputFiles = new File[1];
			listOfInputFiles[0] = inputFile;
		}

		for (int i = 0; i < listOfInputFiles.length; i++) {
			if (config[6].equalsIgnoreCase("1") && listOfInputFiles[i].isDirectory()) {
				recursiveMultiView(config, listOfInputFiles[i], level + 1);
			} else if (listOfInputFiles[i].getName().toLowerCase().contains(config[2].toLowerCase())) {
				String fileExtension = listOfInputFiles[i].getName();
//				if (showDebug) { 
//					System.out.print("listOfInputFiles[i].getName() = " + listOfInputFiles[i].getName());
//					System.out.println("; config[3] = " + config[3]);
//					System.out.println("; add = " + listOfInputFiles[i].getName().toLowerCase().contains(config[3].toLowerCase()));
//				}
				if (fileExtension.contains(".")) {
					fileExtension = fileExtension.substring(fileExtension.lastIndexOf(".") + 1, fileExtension.length());
				}
				if (Arrays.asList(fileExtensionsArray).contains(fileExtension.toLowerCase())) {
					videoFiles.add(listOfInputFiles[i]);
					if (showDebug) {
						for (int levelIndex = 0; levelIndex < level + 1; levelIndex++)
							System.out.print("\t");
						System.out.println(listOfInputFiles[i].getName());
					}
				}
			}
		}
	}
}
