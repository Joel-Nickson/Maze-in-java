# Maze-in-java


## About Maze

Maze is path-finding-visualizer which shows a path from the starting point to the end point avoiding the obstacles in between the points.

Maze uses Bfs to find the shortest path between the given two vertices whereas Dfs just provides a path but it may or may not be the shortest one.

The program consist of 3 pages in total.

A login page and a registration page which connects to a elephantSQL server using java's JDBC.

The third page contains the program of path finding algorithm using BFS and DFS in java's GUI. 



![maze](Maze%20in%20java/img/maze.png)

START-it sets the starting point.

END-it sets the destination.

BLOCK-it sets the wall.

ERASER-it removes the wall.

RETRY-to try again.

FIND-PATH-it connects the starting and the end point.

RANDOM-it randomly generates a wall.

REPAINT-if program is half painted, use this button.

COMBOBOX - to set bfs or dfs

TEXTFIELD & SHOW -to change the array size


******************************************************************************************************************************
## Behind The Scene


![sample arr pres](Maze%20in%20java/img/sample%20arr%20pres.png) -> [gridnum](Maze%20in%20java/img/gridnum.png) -> ![sample out pres](Maze%20in%20java/img/sample%20out%20pres.png)

0-White 1-BLock
5-Start 3-End

![bfspathfound](Maze%20in%20java/img/path%20found.png)

BFS finding the shortest path

![dfspathfound](Maze%20in%20java/img/dfspath.png)

DFS finds "a path"

*************************************************************************************************

![bfspathblocked](Maze%20in%20java/img/bfspathblocked.png) 

BFS through a  wall drawn 

 ![dfspathblocked](Maze%20in%20java/img/dfspathblocked.png)
 
DFS through a  wall drawn 
 

*************************************************************************************************

![bfspathrandom](Maze%20in%20java/img/bfspathrandom.png) 

BFS throught a randomly generated wall

![dfspathrandom](Maze%20in%20java/img/dfspathrandom.png)

DFS throught a randomly generated wall


*************************************************************************************************

###[:Edit]

A jar file has been added to the repo.
Anyone who is interested on checking the project can run the Maze.jar file.
