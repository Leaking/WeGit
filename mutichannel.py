# -*- coding: utf-8 -*

import zipfile
import datetime
from shutil import copyfile
import sys
import os
import os.path

emptyfile = 'empty'
open(emptyfile, 'a').close()
if(len(sys.argv) < 2):
	raise ValueError('You must input one apk')
originApk = sys.argv[1]
if not os.path.isfile(originApk):
	raise ValueError('You must input one existing apk')

print 'now generate muti-channal apks for', originApk

beginTime = datetime.datetime.now()

# 渠道名，按需求定义
channles = [
1,2,3,4,5,6,7,8,9,10,
11,12,13,14,15,16,17,18,19,20,
21,22,23,24,25,26,27,28,29,30,
31,32,33,34,35,36,37,38,39,40,
41,42,43,44,45,46,47,48,49,50
]

for name in channles:
	index = originApk.find('.apk')
	channelX = originApk[:index] + '_' + str(name) + originApk[index:]
	copyfile(originApk, channelX)
	zipped = zipfile.ZipFile(channelX, 'a', zipfile.ZIP_DEFLATED)
	empty_channel_file = "META-INF/channel_{channel}".format(channel=name)
	zipped.write(emptyfile, empty_channel_file)
	print 'Generating ', channelX

print 'Generated channel count ', len(channles)
print 'Total used time ', datetime.datetime.now() - beginTime
