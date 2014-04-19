

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class WordCount {

  public static class Map 
            extends Mapper<LongWritable, Text, Text,IntWritable>{

	  private final static IntWritable one = new IntWritable(1); // type of output value
    private Text word = new Text();   // type of output key
      private Text word2 =new Text();
      
  
	
    
    
    public void map(LongWritable key, Text value, Context context
                    ) throws IOException, InterruptedException {
    	
    	//System.out.println(value);
    	
    	//System.out.println("I am in the map method");
    	
       
    	JSONParser parser = new JSONParser();
    	
    	
 
 		
      
      String tweet=null,timeZone=null, NullString=null, Emotion = "No Emotion";
  	Long id=null ;
  	String output = null ;
  	
  	Object obj=null;
  	
	try {
		
		//System.out.println(value.toString());
		
		obj = parser.parse(value.toString());
		
		//System.out.println(obj);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  	
  	JSONObject jsonObject = (JSONObject)obj;
  	JSONObject userObject = (JSONObject)jsonObject.get("user");

  	if (jsonObject.get("text")!= null){
  	tweet = (String) jsonObject.get("text");
  	timeZone = (String) userObject.get("time_zone");
  	id = (Long) jsonObject.get("id");
  	Emotion = "No Emotion";
  //	output= "No Emotion"+"		"+"		"+tweet;
  	
  	  			
  	while(tweet.contains("\n")){
			
  		tweet =tweet.replace("\n", " ");
			
		}
  	
  	
  	
  	if( timeZone == null  ){
  		
  		timeZone = "Default";
  		
  	}
  	
  	
  	
  	
  	List<String> list = new ArrayList<String>();
  	
  	// systems,windows,linux,back track,ubuntu,Mac OS,ios,Android,Chromebook,ITRON,Tron operating system
	list.add("linux");
	//list.add("back track");
	list.add("Android");
	list.add("Chrome");
	list.add("Mac");
	//list.add("back track");
	list.add("ios");
	list.add("Windows");
	
	Iterator<String> iterator = list.iterator();
	while (iterator.hasNext()) {
		String company = iterator.next();
		
		//System.out.println(iterator.next());
		
		if(tweet.contains(company))
		{
			
			
			
		  	if(!timeZone.equals("Default")){
		  	  	
		  	  	
		  	  	
		  	  //	output = "[{\"id\":\""+timeZone+" Sad\",\"Emotion\":\""+Emotion+"\",\"Location\":\""+timeZone+"\"}]" ;
		  	  	
		  	 
		  	//	output = company+"	"+id+"	"+tweet ;
		  	   
		  		word.set("[{\"id\":\""+timeZone+"\",\"PopularOs_s\":\""+company+"\"");
		  	  	
		  			System.out.println(output);
		  			//word.set(output);
		  			//word2.set("");
		  		//	one.set("Hi");
		  			
		  			
		  			
		  			context.write(word,one);
		  			}
			
		}
	}
  	
  
  	
  	

  	
    	}
      
      
    
  	
    }
 
   
    
  }  
    


    
    
    
  
  
  
  
  
  


  
  
  
  
 
  public static class Reduce
       extends Reducer<Text,IntWritable,Text,Text> {

    private IntWritable result = new IntWritable();
    private Text value =new Text();

    public void reduce(Text key, Iterable<IntWritable> values, 
                       Context context
                       ) throws IOException, InterruptedException {
      int sum = 0; // initialize the sum for each keyword
      for (IntWritable val : values) {
        sum += val.get();  
      }
      result.set(sum);
      value.set(",\"Count_s\":\""+sum+"\"}]");
     
      context.write(key,value);
    }
  } 

  
  
  // Driver program
  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration(); 
   // String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs(); // get all args
    if (args.length != 2) {
      System.err.println("Usage: WordCount <in> <out>");
      System.exit(2);
    } 
    
    System.out.println("I am in main thread");

    // create a job with name "wordcount"
    System.out.println("Before new Job");
   
    Job job = new Job(conf, "wordcount");
    System.out.println("after new Job");
    System.out.println("Before set jar by class");
   
    job.setJarByClass(WordCount.class);
    System.out.println("after set ajr by class");
    System.out.println("Before set mapper class");
  
    job.setMapperClass(Map.class);
    System.out.println("after set mapper class ");
   job.setReducerClass(Reduce.class);
   
    // uncomment the following line to add the Combiner
    //job.setCombinerClass(Reduce.class);
     

    // set output key type   
    job.setOutputKeyClass(Text.class);
    // set output value type
    job.setOutputValueClass(IntWritable.class);
    //set the HDFS path of the input data
    FileInputFormat.addInputPath(job, new Path(args[0]));
    // set the HDFS path for the output
    FileOutputFormat.setOutputPath(job, new Path(args[1]));

    //Wait till job completion
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
