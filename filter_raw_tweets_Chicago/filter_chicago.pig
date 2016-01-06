REGISTER piggybank.jar;
DEFINE Get_Time org.apache.pig.piggybank.evaluation.datetime.convert.CustomFormatToISO();
DEFINE To_ISO org.apache.pig.piggybank.evaluation.datetime.convert.ISOToUnix();
%declare IN_DIR '';
%declare OUT_DIR ''

-- Load the Data
raw = LOAD '${IN_DIR}${input}' USING PigStorage('\u0001') AS (tweetId,text : chararray ,geo,source,isretweet,search_id,create_at,meta_data,to_user_id,language_code, user_id,profile_image_url,user_name,place_id,place_name,place_fullname,country,place_type,street_address,boundary,boundary_type,place_url);

-- Filter out missing data
clean = DISTINCT raw;

step0 = FILTER clean BY geo is not null AND geo != 'null' AND  geo != '' AND isretweet == 'false' AND create_at is not null AND create_at != 'null' AND create_at != '';

-- Project and separate location and format time
--currently, do not deal with timestamp yet

--step1 = FOREACH step0 GENERATE user_id, flatten(STRSPLIT(geo, ',')), To_ISO(Get_Time(REPLACE(REPLACE(create_at, 'CDT', '-05:00'), 'CST', '-06:00'), 'EEE MMM dd HH:mm:ss Z yyyy')) , text;

step1 = FOREACH step0 GENERATE user_id, flatten(STRSPLIT(geo, ',')), create_at, text;

--New York
--step2 = FILTER step1 BY $2 > -74.256090 AND $2 < -73.700273 AND $1 > 40.496111 AND $1 < 40.917585;

step2 = FILTER step1 BY $2 > -88.707599 AND $2 < -87.524535 AND $1 > 41.201577 AND $1 < 42.495775;


-- Project the output for trajectory generation
step3 = FOREACH step2 GENERATE $0, $1, $2, $3, $4;
STORE step3 INTO '${OUT_DIR}${input}_chi_iso.txt' USING PigStorage('\t');

