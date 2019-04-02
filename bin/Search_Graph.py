#!/usr/bin/python
import matplotlib.pyplot as plt
from numpy import *

#Linear probing Search Graph
Load_LFactors=[]
LProbes=[]
file=open("GraphInfo/Linear_Probe_Data/linear.txt", "r")
for line in file.readlines():
	new_line = line.split(";")
	double_loadFactor=float(new_line[0])
	Load_LFactors.append(double_loadFactor)
	LProbes.append(new_line[2])

#Quad probing Search Graph
Load_QFactors=[]
QProbes=[]
file=open("GraphInfo/quad_Probe_Data/quad.txt", "r")
for line in file.readlines():
	new_line = line.split(";")
	double_loadFactor=float(new_line[0])
	Load_QFactors.append(double_loadFactor)
	QProbes.append(new_line[2])

#chaining Search Graph
Load_CFactors=[]
CProbes=[]
file=open("GraphInfo/chaining_data/chaining.txt", "r")
for line in file.readlines():
	new_line = line.split(";")
	double_loadFactor=float(new_line[1])
	Load_CFactors.append(double_loadFactor)
	CProbes.append(new_line[0])

plt.title('Graph of the Load Factor Against the total number of Probe Counts for search')
plt.plot(Load_LFactors,LProbes, 'b--'  )
plt.plot(Load_QFactors,QProbes, 'r--'  )
plt.plot(Load_CFactors,CProbes, 'g--'  )

plt.xlabel('Load Factor')
plt.ylabel('Probe Counts')

plt.show()
