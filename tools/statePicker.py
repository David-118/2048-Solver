#!/bin/python

from os import listdir
from os.path import isfile, join

import random
import numpy as np

# Note this 92 GB and contains all geneted trees for the game (seed=0) of depth 7.
# For obvious reasons it is not part of the git repo.
DIR = "/home/david/2048trees/seed-0-d7/"

frequencies = np.empty(15, dtype=tuple)

for i in range(15):
    frequencies[i] = []

files = [f for f in listdir(DIR) if isfile(join(DIR, f))]

states = []

for file in files:
    print(join(DIR, file))
    with open(join(DIR, file)) as f:
        line = f.readline()
        arr = line.split('#')
        indx = len(list(filter(lambda n: n=='0', arr[1:-2])))
        frequencies[indx].append((join(DIR,file),arr[1:-2]))

a = open("samples.txt", 'w')

for i in range(15):
    xss = random.sample(frequencies[i], min(10, len(frequencies[i])))
    for file, samples in xss:
        a.write(file + "\n")
        for sample in samples:
            a.write(sample + " ")
        a.write("\n")
    
