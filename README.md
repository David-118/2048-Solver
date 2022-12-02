# Final Year Project: 2048 Solver

## Requirements to run (from source)
- Java 17 +
- Maven


## Packaging
There is currently no way to build a jar file, bundled with all the dependecies, due to difficulties with javaFX.
It can be packed with jlink using the command
`mvn javafx:jlink` from the program folder.

The project can then be run from `program/target/solve2048/bin/solve2048`

## Running Graphical Mode
`mvn javafx:run`

You can also run the exetable produced in the previous section.

## Running Benchark Mode
After packaging the program with jlink move to the folder `program/target/solve2048/bin/solve2048`, and run the command:

`./solve2048 -b [number of time to benchamk each heurstic] - o [output file]`