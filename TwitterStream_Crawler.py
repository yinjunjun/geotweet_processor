# Twitter API Crawler
# -*- coding: utf-8 -*-

'''
Author: Junjun Yin
Email: yinjunjun@gmail.com
Urbana-Champaign, 2014/08/28
'''

#!/usr/bin/env python
from tweepy.streaming import StreamListener
from tweepy import OAuthHandler
from tweepy import Stream

from tweepy.utils import import_simplejson
json = import_simplejson()



# Go to http://dev.twitter.com and create an app.
# The consumer key and secret will be generated for you after
consumer_key="LVo7mCTLc1cs78nXOaS4xEXZj"
consumer_secret="ARo6H6VCoMC6bPavm4uNzHb0YorNXlZhuy3qxy3RP9C19e3M0N"

# After the step above, you will be redirected to your app's page.
# Create an access token under the the "Your access token" section
access_token="2712462792-qxD5B7KS3C6wYYYOgBD6PP6uw81dj4DZQJS4dE4"
access_token_secret="fwiFwlQCFdWmqQCXWxaQF3exYnPKqrluTgMFHvoKcQuDK"

class TwitterCrawler(StreamListener):
    """ A listener handles tweets are the received from the stream.
    This is a basic listener that just prints received tweets to stdout.

    """
    def __init__(self):
    	self.file = open("twitterData.txt",'wb') # save to csv file
    def on_data(self, data):
        dataX = json.loads(data)

        if 'limit' in data:
            pass
            # if self.on_limit(data['limit']['track']) is False:
            #     return False
    	else:
            self.file.write(data)
            print data
        return True

    def on_error(self, status):
        print status

    def on_limit(self, track):
        """Called when a limitation notice arrives"""
        
        return

if __name__ == '__main__':
    app = TwitterCrawler()
    auth = OAuthHandler(consumer_key, consumer_secret)
    auth.set_access_token(access_token, access_token_secret)

    stream = Stream(auth, app)
    # stream.filter(track=['KEYWORD'], loc=[])
    # The following coordiantes are the bounding box of the United States: minLongitude, minLatitude, maxLongitude, maxLatitude
    stream.filter(locations = [-167.276413,5.499550,-52.233040,83.162102])
