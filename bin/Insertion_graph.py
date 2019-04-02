#!/usr/bin/python
import matplotlib.pyplot as plt
from numpy import *

#Linear probing Graph
Load_LFactors=[]
LProbes=[]
file=open("GraphInfo/linear.txt", "r")
for line in file.readlines():
	new_line = line.split(";")
	double_loadFactor=float(new_line[0])
	Load_LFactors.append(double_loadFactor)
	LProbes.append(new_line[1])

#Quad probing Graph
Load_QFactors=[]
QProbes=[]
file=open("GraphInfo/quad.txt", "r")
for line in file.readlines():
	new_line = line.split(";")
	double_loadFactor=float(new_line[0])
	Load_QFactors.append(double_loadFactor)
	QProbes.append(new_line[1])

#chaining Graph
Load_CFactors=[]
CProbes=[]
file=open("GraphInfo/chaining.txt", "r")
for line in file.readlines():
	new_line = line.split(";")
	double_loadFactor=float(new_line[0])
	Load_CFactors.append(double_loadFactor)
	CProbes.append(new_line[1])

plt.title('Graph of the Load Factor Against the total number of Probe Counts')
plt.plot(Load_LFactors,LProbes, 'b--'  )
plt.plot(Load_QFactors,QProbes, 'r--'  )
plt.plot(Load_CFactors,CProbes, 'g--'  )

plt.xlabel('Load Factor')
plt.ylabel('Probe Counts')

plt.show()
