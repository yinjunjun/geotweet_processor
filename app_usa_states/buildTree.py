#!/usr/bin/env python
import shapefile
import sys
e=shapefile.Editor(sys.argv[1])
e.buildQuadTree()
output=open(sys.argv[2],'w')
e.printNode(e.root,output)
output.close()
