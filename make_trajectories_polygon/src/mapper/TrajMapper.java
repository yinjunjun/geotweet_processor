package mapper;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class TrajMapper extends MapReduceBase implements Mapper<LongWritable, Text, LongWritable, Text> {

    @Override
    public void map(LongWritable key, Text value, OutputCollector<LongWritable, Text> output,
	    Reporter reporter) throws IOException {
	
	String[] line = value.toString().split(",");
	long sendKey = Long.parseLong(line[0]);

	String out = line[1] + "," + line[2] + "," + line[3] + "," + line[4] + "," + line[5];
	output.collect(new LongWritable(sendKey), new Text(out));
    }
}
