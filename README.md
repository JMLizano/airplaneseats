# Airplane seats arrangement

Small project to arrange seats in an airplane aiming to maximize customer satisfaction. Satisfaction is measured in 
two ways:
* Group of customers are seated together
* Customers with window preference are provided with window seats

# Strategy

In this solution, sitting groups of customer together has preference over giving them a window seat.
Following this, the strategy used is very simple, a sort-of greedy algorithm were we start by allocating the biggest group.

For example given the following list of customers:

1W 2 3

4 5 6 7

8

9 10 11W

12W

13 14

15 16

the first group to be allocated would be (4,5,6,7) then one of [(1,2,3), (9,10,11)] and so on.

# Requirements

To execute the project you will need one of: 
* [sbt (1.1 recommended)](https://www.scala-sbt.org/)
* docker

To execute the tests you will need sbt.

# Usage

To run the tool, you can use the prebuilt docker image or you can build the project by yourself, 
but to doing this yo will need sbt.

To run the tool using the prebuilt docker image use:

````bash

$ docker run -v $(pwd)/<file>:/opt/docker/<file> jmlizano/airplaneseats:1.0 <file>
````

To build the project yourself and run the tool use (from project root):
````bash

$ sbt "run <file>"
````

There are already some preloaded test files you can use: 

* test.txt : Default use case
* test_oversubscribed.txt: When there are more passengers than seats
* test_large_plane.txt: Plane with lot of passengers and seats
* test_split_group.txt: When a group can not be seated together

To use this preloaded files you can simply run:

````bash

$ docker run jmlizano/airplaneseats:1.0 <file>
````

# Test

To execute the tests run (from project root):
````bash

$ sbt test
````
