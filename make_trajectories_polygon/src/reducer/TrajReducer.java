package reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.lang.Comparable;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

import util.GeoDistance;

public class TrajReducer extends MapReduceBase implements Reducer<LongWritable, Text, LongWritable, Text>{

    @Override
    public void reduce(LongWritable key, Iterator<Text> values,
	    OutputCollector<LongWritable, Text> output, Reporter reporter) throws IOException {

	List<TimeSpaceTweet> traj = new ArrayList<TimeSpaceTweet>();
	double lon;
	double lat;
	long time;
        int polyID;
	String[] value;
	TimeSpaceTweet nt;
	int i = 0;
        //values: latitude,longitude,time,polygonID;
	while(values.hasNext()){
	    value = values.next().toString().split(",");
	    lat = Double.parseDouble(value[0]);
	    lon = Double.parseDouble(value[1]);
	    time = Long.parseLong(value[2]);
            polyID = Integer.parseInt(value[3].replaceAll("\\s+",""));

	    nt = new TimeSpaceTweet(time, lat, lon, i, polyID);
	    traj.add(nt);
	    i++;
	}

	//System.out.println("size = " + traj.size());

	//Sort the trajectory list based on time
	Collections.sort(traj);
	
	long sourceId;
	long targetId;
	double sourceLon, sourceLat,targetLon,targetLat;
	TimeSpaceTweet current;
	TimeSpaceTweet previous = traj.get(0);
	StringBuffer sb = new StringBuffer();

	sb.append(previous.getLon() + "&" + previous.getLat() + "&" + previous.getTime() + "&" + previous.getPolyID());

	for(i = 1; i < traj.size() ; i++){
	    current = traj.get(i);

	    //Check if this is a duplicate tweet
	    if (current.getTime() == previous.getTime())
		continue;

	    //Check if the user has moved more than a threshold (0.2km) from previous position
	    
            //for this program, takes all points
            //if (GeoDistance.Distance(current.getLat(), current.getLon(), previous.getLat(), previous.getLon()) > 0.1){
		sb.append( "," + current.getLon() + "&" + current.getLat() + "&" + current.getTime() + "&" + current.getPolyID());
		previous = current;	 
	   //}

	}

	output.collect(key, new Text(sb.toString()));	
    }

}
class TimeSpaceTweet implements Comparable<TimeSpaceTweet>{
	private long time;
	private double lat;
	private double lon;
	private int id;
        private int polyID;

	public TimeSpaceTweet(long time, double lat, double lon, int id, int polyID){
		this.lat = lat;
		this.lon = lon;
		this.time = time;
                this.polyID = polyID;
		this.id = id;
	}

	public double getLon(){
		return this.lon;
	}

	public double getLat(){
		return this.lat;
	}
	
	public long getTime(){
		return this.time;
	}

	public int getID(){
		return this.id;
	}
        
        public int getPolyID(){
        
                return this.polyID;

        }

	public int compareTo(TimeSpaceTweet t){
		return (new Long(time)).compareTo(t.getTime());
	}
}
