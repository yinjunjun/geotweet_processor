python buildTree.py Wards_GB_2014_Boundaries/WD_DEC_2014_GB_BFE.shp quadTree.txt

python localPinP.py Wards_GB_2014_Boundaries/WD_DEC_2014_GB_BFE.shp uk_july_proj.txt > GB_Ward_July_output.txt

python localPinP.py Wards_GB_2014_Boundaries/WD_DEC_2014_GB_BFE.shp /home/jun/data_repo/uk/uk_june_proj.txt >GB_Ward_June_output.txt



hadoop jar /usr/lib/hadoop-mapreduce/hadoop-streaming-2.4.0.2.1.2.0-402.jar -files app -mapper "app/hdPinP.py" -input uk_june_iso_proj.txt -output uk_june_polygon_proj_iso.txt -jobconf "mapreduce.task.timeout=60000000"
