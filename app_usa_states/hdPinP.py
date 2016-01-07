#!/usr/bin/env python
import shapefile
import sys
import os

e=shapefile.Editor('app_usa_states/us/us_states.shp')
e.buildQuadTree('app_usa_states/quadTree.txt')

for line in sys.stdin:
    tmp = line.strip().split(',')
    ind=e.index_of_first_feature_contains_point(float(tmp[2]),float(tmp[1]))
    if ind != -1:
        print line.strip()+','+str(ind)
