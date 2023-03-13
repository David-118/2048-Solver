# Term 2
## Week 8
Created pruning for unlikely possibilities, helped by the previous work. A parameter keep track of how many
values with 4 have been calculated in a branch. When this parameter reaches 0 a nodes children is not calculated.
This prunes the children of a 4 node. Limited testing on performance was done this week.

Created DepthFunctions. These are functions that increase the depth of the search for moves with fewer possibilities.
This can lead to much longer games, in my limited tsting so far, and a slight increase in performance.

One game manged to get a 8192, 4096, 2048, 1024 and 512, which is quite close to the boundary of 16k.

## Week 7
Created a new txt format to represent trees and simple C program to view these trees in.
This file format is much more efficient, a depth 6 (seed 0) is no only ~10GiB.
A long time was spent generating a depth 7 tree for the same game (seed 0) ~82GiB.
Due to the file sizes I have not created many trees.

I Noticed, that as expected, trees at the beginning of a tree proving, that in at least this game 2048
the early trees are difficult to calculate compared to that of the later trees. 

Also, generated a depth 4 tree (seed 0) (134 MiB). This tree was much more useful to work with, I statred manualy
pruing less likely posablites out of the first tree for a test.
## Week 6
This week was used to figure out the best way to do pruning. I came to the conclusion that the best two approach's
were pruning unlikely possibles or pruning bad moves.

Started coming up with methods to analise trees. I started by trying to make a HTML Tree with collapsable nodes.
Collapsable nodes are not currently done

I determined that HTML was not a scale enough form. The folder with trees generated from a game was 10s of GiB for
a game of depth 6. The largest file was 260MiB and was not practical to open in any browser. 

## Week 5
Did further benchmark, tried to find the optimal fail setter/ratio, but for the monotonic heuristic. (Set -1000) turned
out to be the most optimal value. Again using 201 games. This took a while and limited the work I could do on the code.

I used this time to add to the heuristic section of the report with more information about applying the newer
heuristics Dynamic Snake and Monotonic to rectangular grids, and other square grids.

## Week 4
Calculated the best ratio/setter for the dynamic snake heuristic (ratio 0.8), using the newly developed optimiser game view.
This was initially done by running 100 games for each of {-1, 0.8, 0.6, ..., 0.6, 0.8, 1}. The data was inclusive.
repeated for running 201 games.

201 was chosen as it was more likely to be useful and the median function used is overly simplistic and works best
with an odd number.

Tested various modifications to existing heuristics, only mildly benfical one was the Dynamic Snake 'antitrap'.
This effectively penalises game state with trapped values. This lead to a reduction in median values, but an increase
in the good values.

## Week 3
This week I found the source of the older heuristic under preforming.

The new implementation of expetimax returned the first maximum child node found at a max node. This was
which ever maximum move was quicker to process, before there was much more consitiaty in which move was chosen.

Since this change both snake heuristics are able to reach 8192 on depth of 6.

Dynamic snake is also now supporting rectangular games, however I have not finished testing about 
what changes can be made to improve the performance, the only change so far is that a rectangular weight
matrix can be generated.

## Week 2
Two variable sized square heuristics were created this week, this stated a week late based on the plan.

I also created the DynamicSnake heuristic. This is a heuristic generates a weight matrix similar to the one
used in the old snake heuristic.

I also created a heurstic based on [10]. This annualised features like the monotony of the rows/cols as well as
the values on the edge. There is also a significant penalty for a losing game state.

The DynamicSnake and old snake heuristic appeared to be underprefoming in the benchmark however the Monotonic heuristic
was able to get 8192 on a depth of 6 or 7. With a depth of 7 it even acheived 8192 24% of time (100 samples).

While developing the heuristics I also added a Graphical option to set the heuristic and depth of the search.
Constantly changing the code was getting irritating.

## Week 1
I used java parellel streams to create a mutli core implmentation.
In this process I discovred a bug that in the origonal implemention.
When transversing the tree the each node subtracts two from the depth if it is 2.
This bug made it difficult to compare the new impementation.

Before reimpementing exptimax for mutlicore a simple protype was made using the old class.
The origonal imlementation took 18h23m56s to run 400 games (1 with each currently impemented heurstic)
The prototye took only took 9h19m53s.

# Christmas
Over the christmas break I mostly foucused on research.
First I attempted interpret the proof that 2048 was np complete
citited in the report.

Secondly I investigated the best approch to introducing multithreading to the project.
While I made an attempt with the thread object, the code was messy and ineffective.
I eventualy relised the java streams have a parellel() method. This returns a stream
were every item can be processed in parellel at the same time.

This was the approach I used in the end

# Term 1
## Week 11
This was after the deadlines, I simply just reheased the presentation ready for the Friday.

## Week 10
### Week 10.2
Finished off report, ensuring professial issues, and comlexity sections were complete.
The section on the np hardness of 2048 has been moved to next term,
as I needed more time to understand the proof, I was planning on citing.

### Week 10.1
Created and edited demo video of the alorithm running.

## Week 9
### Week 9.2
Stared work on sections of report such as big-O complexity for the 4x4
game. Also refactered the code to take advantage of software enginering
princabes such as design patterns. Almost method now has javadoc.

### Week 9.1
Countied work on presentation, ensuring that there is footagte of the game
2048 being played, and example of expectimax applied to a simple 2048 game.

## Week 8
### Week 8.2
Worked on the report and presentation.
As I have complete more than I intened to this for the programing.
I mostly desighed to work on the report and presentation this week.
Were 

### Week 8.1
Got the results from the benchmark on four orignaol benchmarks and
a snake herutstic with the same penlty as the diagonal heurstic.
The penatly is mutipled by a heurtsic.
## Week 7
### Week 7.2
I've created a benchmarking Command Line Interface. When the package
in run with command line parameters -b \[number of time to run each heuristic\]
and -o \[location of output file\]. This dumps the results out to a csv file.
I had problems caused when I initially tried to use opencsv, as I was unable
to create the package with JLink, and was unable to find a quick solution.
I eventually switched to jackson csv. I left the process running with 100
heuristics running Sunday night, but do not expect the result to monday 
afternoon.


### Week 7.1
Attempted to create more advanced heuristics from previous projects.
Currently unable to get the same results as the original projects.
First heuristic is a 'snake' [7] shape and the top left corner,
that also checks neighbors from [8]. I eventually found a bug in chance node.
This node was taking an average of the weighted values. As the weights of
the chance node add up to 1, it should have just been the sum.


## Week 6
Added sections on proof of concept expectimax with and without a heuristic
Counted working on the introduction to the report as well.

## Week 5
### Week 5.2
Developed expectimax algorithm a simple heuristic function applied to the
main project. The heusrstic can be passed into the constructor as a functional
interface.

### Week 5.1
Worked significantly on the introduction to the report, as I was a head of
schedule on the programing. I decided being up-to-date on the report was more
important.


## Week 4
## Week 4.2
This week, so far I have created a 2048 game in the main branch of the project.
This includes a graphical user interface (writen in javaFX) and theoreticaly
supports resing the interface, however there is not interface and graphics don't
look particauly good in this situation. This is futher ahead in the plan than
I was expecting to be by this point.


## Week 4.1
This week, so far I have created a simple 2048 prototype. This is both tested
with the junit test sweet as well as having a quickly writen command line
user interface to help test a wider array of situations. This does not include
dececting the end of a game, as when creating a tree. If I tray to make each move,
and fail then I now the game has ends. I condierd this function was not relvent.

## Week 3

This week I completed the expectimax early deliverable by adding both chance nodes
maximum nodes to the tree strucutre. I've also used what I learn't in the proof of
concept programs to develop the expectiman algorithm in the main branch. While the
algorithm currently works I would like to check validity of the tree. I also created 
a skeleton for the report and added some information about what I did for the first 
two early deliverables.

## Week 2

### Week 2.2

I complete the proof of concept program for a simple decision tree,
using references and class nodes. I have started adapting the tree to
the expectimax algorithm. I have created a maximum node which can get the score
of its largest child. I used JUnit and the IntelliJ debug tools to find why the
score of a maximizing node was always zero.

### Week 2.1

This week I have finished of my project plan and my planned reading for the
project so far. I have also made some progress on the prototype for a
decision tree. It is currently able to make a decision tree with basic layout.
Nodes are currently not able to store score and edges are not weighted.

## Week 1

This week I have only done two major things

* Firstly I have worked on my project plan ensuring that I know what to do over the
  project as well as what could go wrong.
* Secondly I have worked on reading sources relating to my project. These include 
  previous attempts to solve 2048 with the expectimax algorithm as well as some 
  material on the expetimax algorithm.
