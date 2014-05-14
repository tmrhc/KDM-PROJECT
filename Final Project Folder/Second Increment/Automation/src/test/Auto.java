package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * BufferedReader and Scanner can be used to read line by line from any File or
 * console in Java.
 * This Java program demonstrate line by line reading using BufferedReader in Java
 *
 * 
 */
public class Auto{   
	
	
	
	
	

	JSONParser parser = new JSONParser();
    
    public String JSONAnalysis(String jsonString) throws FileNotFoundException, IOException, ParseException
      {
    	
    	String tweet=null,timeZone=null;
    	Long id=null ;
    	String output = null ;
    	
    	Object obj = parser.parse(jsonString);
    	
    	JSONObject jsonObject = (JSONObject)obj;
    	JSONObject userObject = (JSONObject)jsonObject.get("user");
 
    	if (jsonObject.get("text")!= null){
    	tweet = (String) jsonObject.get("text");
    	timeZone = (String) userObject.get("time_zone");
    	id = (Long) jsonObject.get("id");
    	
    	output = id+"		"+timeZone+"		"+tweet;
    	
     
    	
		System.out.println(output);
    
    	
      	}
    	
    	return output;
    }
 
	
	
	
	

    public static void main(String args[]) throws ParseException {
      
        //reading file line by line in Java using BufferedReader       
        FileInputStream fis = null;
        BufferedReader reader = null;
        BufferedWriter bw = null;
        Auto a = new Auto();
      
        try {
            fis = new FileInputStream("C:/EnglishTweets2.txt");
            reader = new BufferedReader(new InputStreamReader(fis));
          
            File file = new File("D:/ProcessedTweets.txt");
            
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			bw= new BufferedWriter(fw);
            
            
            System.out.println("Reading File line by line using BufferedReader");
          
            String line;
            
            while((line = reader.readLine()) != null){
               System.out.println(line);
               // line = reader.readLine();
                
                
           String newline = a.JSONAnalysis(line);
           System.out.println(newline);     
           
    		if(newline!=null){	
           bw.write(newline);
    			bw.newLine();
    		}
                   
            }
            
            System.out.println("Done");
            
          
        } catch (FileNotFoundException ex) {
        ex.printStackTrace();
        } catch (IOException ex) {
           ex.printStackTrace();
          
        } finally {
            try {
                reader.close();
                fis.close();
                bw.close();
            } catch (IOException ex) {
               ex.printStackTrace();
            }
        }
  } 
    
    
    
    
    
    
    
    
    
       
    
    
    
    
    
    
    
    
}


