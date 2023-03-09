#!/bin/sh

jar_path="../program/target/Solve2048-0.0.1-SNAPSHOT-jar-with-dependencies.jar"
states="samples.txt"

while IFS= read -r file
do
  read -r state
  ./info "$file"
  echo -n ","
  java -jar $jar_path -d 4 4 $state 3000
done < "$states"
