# Week 7
## Week 7.2
I've created a benchmarking Command Line Interface. When the package
in run with command line parameters -b \[number of time to run each heuristic\]
and -o \[location of output file\]. This dumps the results out to a csv file.
I had problems caused when I initially tried to use opencsv, as I was unable
to create the package with JLink, and was unable to find q quick solution.
I eventually switched to jackson csv. I left the process running with 100
heuristics running Sunday night, but do not expect the result to monday 
afternoon.


## Week 7.1
Attempted to create more advanced heuristics from previous projects.
Currently unable to get the same results as the original projects.
First heuristic is a 'snake' [7] shape and the top left corner,
that also checks neighbors from [8]. I eventually found a bug in chance node.
This node was taking an average of the weighted values. As the weights of
the chance node add up to 1, it should have just been the sum.


# Week 6
Added sections on proof of concept expectimax with and without a heuristic
Counted working on the introduction to the report as well.

# Week 5
## Week 5.2
Developed expectimax algorithm a simple heuristic function applied to the
main project. The heusrstic can be passed into the constructor as a functional
interface.

## Week 5.1
Worked significantly on the introduction to the report, as I was a head of
schedule on the programing. I decided being up-to-date on the report was more
important.


# Week 4
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

# Week 3

This week I completed the expectimax early deliverable by adding both chance nodes
maximum nodes to the tree strucutre. I've also used what I learn't in the proof of
concept programs to develop the expectiman algorithm in the main branch. While the
algorithm currently works I would like to check validity of the tree. I also created 
a skeleton for the report and added some information about what I did for the first 
two early deliverables.

# Week 2

## Week 2.2

I complete the proof of concept program for a simple decision tree,
using references and class nodes. I have started adapting the tree to
the expectimax algorithm. I have created a maximum node which can get the score
of its largest child. I used JUnit and the IntelliJ debug tools to find why the
score of a maximizing node was always zero.

## Week 2.1

This week I have finished of my project plan and my planned reading for the
project so far. I have also made some progress on the prototype for a
decision tree. It is currently able to make a decision tree with basic layout.
Nodes are currently not able to store score and edges are not weighted.

# Week 1

This week I have only done two major things

* Firstly I have worked on my project plan ensuring that I know what to do over the
  project as well as what could go wrong.
* Secondly I have worked on reading sources relating to my project. These include 
  previous attempts to solve 2048 with the expectimax algorithm as well as some 
  material on the expetimax algorithm.
