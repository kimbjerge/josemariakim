package communication;
import lejos.nxt.*;
import java.io.*;
/**
 * A simple data logger to sample a sequence
 * of integer values in a flash file. The
 * file is in text format with
 * comma's between sample values and 
 * new lines inserted to obtain a fixed number
 * of sample values per line.
 * 
 * After use the flash file can be transfered to a
 * PC by means of the tool nxjbrowse.
 * 
 * @author  Ole Caprani
 * @version 2.09.08
 */
public class DataLogger 
{
    private File f;
    private FileOutputStream fos;
    
    public DataLogger (String fileName)
    {
        try
        {	        
            f = new File(fileName);
            if( ! f.exists() )
            {
                f.createNewFile();
            }
            else
            {
                f.delete();
                f.createNewFile();
            }
             
            fos = new  FileOutputStream(f);
        }
        catch(IOException e)
        {
            LCD.drawString("Error in creation",0,0);
            System.exit(0);
        }
    }
	
    public void logReceived(String line)
	{
		writeLine("<< " + line);
	}
	public void logSent(String line)
	{
		writeLine(">> " + line);
	}
    
    public void writeLine (String line)
    {
    	
        try 
        {
        	if (line.length() > 0)
        	{
	        	byte[] bytes = line.getBytes(line);
	        	fos.write(bytes);
	        	// New line
	            fos.write((byte)('\r'));
	            fos.write((byte)('\n'));
        	}
		} 
        catch (IOException e) 
        {
			LCD.drawString("Error with line",0,0);
            System.exit(0);
		}
        
    }
    	
    public void close()
    {
        try
        {
            fos.close();
        }
        catch(IOException e)
        {
            LCD.drawString("Error in closing",0,0);
            System.exit(0);
        }		 
    }
}

