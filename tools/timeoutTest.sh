#!/bin/sh

jar_path="../program/target/Solve2048-0.0.1-SNAPSHOT-jar-with-dependencies.jar"
states="states.txt"

while IFS= read -r state
do
  java -jar $jar_path -d 4 4 $state 3000
done < "$states"