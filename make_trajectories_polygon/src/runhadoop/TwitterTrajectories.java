package runhadoop;


import mapper.TrajMapper;
import reducer.TrajReducer;

import java.util.Date;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.KeyValueTextInputFormat;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;

public class TwitterTrajectories {
    
    public static void main(String[] args) throws Exception{
	long start;
   	long end;
	long time = 0;

	Path in = new Path(args[1]);
	Path out = new Path(args[2]);

	JobConf conf1 = new JobConf(TwitterTrajectories.class);
	conf1.set("mapred.textoutputformat.separator", ",");
	//conf.set("mapred.textinputformat.separator", new String(Character.toString((char)1)));
	//conf.addResource(new Path(rootAddress + "mysepconfig.xml"));
	conf1.setJobName("MakeTrajectories");	
	conf1.setMapperClass(TrajMapper.class);
	//conf.setCombinerClass(FilterReducer.class);
	conf1.setReducerClass(TrajReducer.class);
	
	conf1.setOutputKeyClass(LongWritable.class);
	conf1.setOutputValueClass(Text.class);
	conf1.setInputFormat(TextInputFormat.class);	
	conf1.setOutputFormat(TextOutputFormat.class);
	FileInputFormat.setInputPaths(conf1, in);
	FileOutputFormat.setOutputPath(conf1, out);			
	
	start =  new Date().getTime();	
	System.out.println("Start Job");
	JobClient.runJob(conf1);
	System.out.println("End Job");
	end =  new Date().getTime();
	time += (end-start);
	System.out.println("Total time: " + time + " ms to run both jobs");

    }

}


