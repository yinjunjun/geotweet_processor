python buildTree.py us/us_states.shp quadTree.txt

python localPinP.py us/us_states.shp twitter_data.txt > us_geo_tweets.txt


hadoop jar /usr/lib/hadoop-mapreduce/hadoop-streaming-2.4.0.2.1.2.0-402.jar -files app -mapper "app/hdPinP.py" -input geotweets.txt -output us_geotweets.txt -jobconf "mapreduce.task.timeout=60000000"
