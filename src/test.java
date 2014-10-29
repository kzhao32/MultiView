public class test {
 public void attachShutDownHook(){
  Runtime.getRuntime().addShutdownHook(new Thread() {
   @Override
   public void run() {
    System.out.println("Inside Add Shutdown Hook");
   }
  });
  System.out.println("Shut Down Hook Attached.");
 }
 
 public static void main(String[] args) {
	  test sample = new test();
	  sample.attachShutDownHook();
	  System.out.println("Last instruction of Program....");
	  while (true);
	  //System.exit(0);
	 }}

//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Map;
//
//import javax.sound.sampled.AudioFileFormat;
//import javax.sound.sampled.AudioFormat;
//import javax.sound.sampled.AudioInputStream;
//import javax.sound.sampled.AudioSystem;
//import javax.sound.sampled.DataLine;
//import javax.sound.sampled.LineUnavailableException;
//import javax.sound.sampled.SourceDataLine;
//import javax.sound.sampled.UnsupportedAudioFileException;
//
//
//// just deal with 1 screen for now
//public class test {
//
//	//	public static void main(String[] args) {
//	//		// TODO Auto-generated method stub
//	//		System.out.println(System.getenv("APPDATA"));
//	//	}
//	
//	public void testPlay(String filename)
//	{
//	  try {
//	    File file = new File(filename);
//	    AudioInputStream in= AudioSystem.getAudioInputStream(file);
//	    AudioInputStream din = null;
//	    AudioFormat baseFormat = in.getFormat();
//	    AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 
//	                                                                                  baseFormat.getSampleRate(),
//	                                                                                  16,
//	                                                                                  baseFormat.getChannels(),
//	                                                                                  baseFormat.getChannels() * 2,
//	                                                                                  baseFormat.getSampleRate(),
//	                                                                                  false);
//	    din = AudioSystem.getAudioInputStream(decodedFormat, in);
//	    // Play now. 
//	    rawplay(decodedFormat, din);
//	    in.close();
//	  } catch (Exception e)
//	    {
//		  System.out.println(e);
//	        //Handle exception.
//	    }
//	} 
//	
//	private void rawplay(AudioFormat targetFormat, AudioInputStream din) throws IOException,                                                                                                LineUnavailableException
//	{
//	  byte[] data = new byte[4096];
//	  SourceDataLine line = getLine(targetFormat); 
//	  if (line != null)
//	  {
//	    // Start
//	    line.start();
//	    int nBytesRead = 0, nBytesWritten = 0;
//	    while (nBytesRead != -1)
//	    {
//	        nBytesRead = din.read(data, 0, data.length);
//	        if (nBytesRead != -1) nBytesWritten = line.write(data, 0, nBytesRead);
//	    }
//	    // Stop
//	    line.drain();
//	    line.stop();
//	    line.close();
//	    din.close();
//	  } 
//	}
//	
//	private SourceDataLine getLine(AudioFormat audioFormat) throws LineUnavailableException
//	{
//	  SourceDataLine res = null;
//	  DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
//	  res = (SourceDataLine) AudioSystem.getLine(info);
//	  res.open(audioFormat);
//	  return res;
//	} 
//	
//	public void start() {
//		System.out.println("starting");
//		File file = new File("G:\\Music\\You Found Me.mp3");
//		System.out.println("length: " + file.length());
//		AudioFileFormat baseFileFormat = null;
//		AudioFormat baseFormat = null;
//		try {
//			baseFileFormat = AudioSystem.getAudioFileFormat(file);
//		} catch (UnsupportedAudioFileException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		baseFormat = baseFileFormat.getFormat();
//		// TAudioFileFormat properties
//		if (baseFileFormat instanceof TAudioFileFormat)
//		{
//		    Map properties = ((TAudioFileFormat)baseFileFormat).properties();
//		    String key = "author";
//		    String val = (String) properties.get(key);
//		    key = "mp3.id3tag.v2";
//		    InputStream tag= (InputStream) properties.get(key);
//		    System.out.println("author: " + val);
//		}
//		// TAudioFormat properties
//		if (baseFormat instanceof TAudioFormat)
//		{
//		     Map properties = ((TAudioFormat)baseFormat).properties();
//		     String key = "bitrate";
//		     Integer val = (Integer) properties.get(key);
//		     System.out.println("bitrate: " + val);
//		}
//
//		
//	}
//	
//	public static void main(String args[]) throws Exception {
//		test testInstance = new test();
//		//testInstance.testPlay("C:\\Users\\Tommy\\Desktop\\M1F1-int16-AFsp.wav");
//		testInstance.start();
////		testPlay("C:\\Users\\Tommy\\Dropbox\\new music\\Shower.mp3");
////		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
////		GraphicsDevice[] allScreens = env.getScreenDevices();
////		System.out.println(allScreens.length);
////		System.out.println(allScreens[0].getConfigurations());
////
////		AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(new File("C:\\Users\\Tommy\\Dropbox\\new music\\Shower.mp3"));
////	    if (fileFormat instanceof TAudioFileFormat) {
////	        Map<?, ?> properties = ((TAudioFileFormat) fileFormat).properties();
////	        String key = "duration";
////	        Long microseconds = (Long) properties.get(key);
////	        int mili = (int) (microseconds / 1000);
////	        int sec = (mili / 1000) % 60;
////	        int min = (mili / 1000) / 60;
////	        System.out.println("time = " + min + ":" + sec);
////	    } else {
////	        throw new UnsupportedAudioFileException();
////	    }
////	    
////	    
////		VideoInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("C:\\Users\\Tommy\\Dropbox\\new music\\Shower.mp3"));
////		AudioFormat format = audioInputStream.getFormat();
////		long frames = audioInputStream.getFrameLength();
////		double durationInSeconds = (frames+0.0) / format.getFrameRate(); 
////		System.out.println("durationInSeconds: " + durationInSeconds);
//		
////		Runtime.getRuntime().exec(
////				new String[] {
////						"C:\\Program Files (x86)\\VideoLAN\\VLC\\vlc.exe",
////						"G:\\Windows.old.000\\Quarentine.DVDrip.Norsk tekst.Ripp Robban\\Movies\\Sex And Corruption Episode 1-cd2.avi",
////						""
////						});
//	}
//}
////	    Runtime.getRuntime().exec(
////	        new String[] {
////	            "cmd.exe",
////	            "/c",
////	            "\"" + System.getenv("APPDATA")
////	                + "\\Microsoft\\Internet Explorer\\Quick Launch\\Show Desktop.scf" + "\"" });
////	  }
