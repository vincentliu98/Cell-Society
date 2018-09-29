cell society
====

TODO: comment for 
* models/wator/Fish
* models/SimulationModel
* models/Cell
* models/CellGraph
* models/Simulator
* utility
* everything in parser folder
* everything in writer folder
* everything in factory folder

This project implements a cellular automata simulator.

Names: Inchan Hwang, Vincent Liu, Jay Pande

### Timeline

Start Date: 9.17.2018

Finish Date: 9.30.2018

Hours Spent: 100+

### Primary Roles
Inchan Hwang: Simulation, XML writers, and a little bit of everything
Vincent Liu: Visualization, and a little bit of Simulation
Jay Pande: XML parsers and error checking

### Resources Used

[Yanbo Fang's cell society project](https://github.com/yanbofang/cell_society)
[Java Line Chart](https://docs.oracle.com/javafx/2/charts/line-chart.htm)

### Running the Program

#####Main class: src/visualization/Main.java

#####Data files needed: 
* resources/English.properties
* resources/ErrorsEnglish.properties
* resources/style.css
* data/error_files

#####Interesting data files:
* data/*

#####Features implemented:
* Simulation
    * Implement a 2D grid of cells with flexible model (Game Of Life, Spreading Of Fire, Segregation, Wa-Tor), size (1-10000 cells), and shape (triangle, rectangle)
    * Display a dynamic line chart consisting the number of the each kind of cells
* Configuration
    * Read in an XML formatted file that contains the initial settings for a simulation
    * Allow users to save the current parameters of the model and save it as an XML file.
* Visualization
    * Display the current states of the 2D grid and animate the simulation from its initial state until the user stops it.
    * Allow users to load a new configuration file, which stops the current simulation and starts the new one. 
    * Allow users to update the current model, the shape of the cell, and specific parameters of the model.
    * Allow users to pause and resume the simulation, as well as speeding up or slowing down the simulation

#####Assumptions or Simplifications:

#####Known Bugs:
* High Priority: None
* Medium Priority: None
* Low Priority
    * After switching the model, the num tick doesn't update immediately. It updates back to 0 only when the simulation starts.

#####Extra credit:
N/A

### Notes


### Impressions
Vincent Liu: This was a great project that I worked on. I learned a lot from working my teammates and looking at their code. It was very fulfilling to build this simulation model from scratch and see the simulation running. 

### src Folder Structure
<pre>
src                                                         #
├── module-info.java                                  #
├── README.md                                         #
├── simulation                                        #
│   ├── CellGraph.java                              #
│   ├── Cell.java                                   #
│   ├── factory                                     #
│   │   ├── GameOfLife.java                       #
│   │   ├── SpreadingFire.java                    #
│   │   ├── Segregation.java                      #
│   │   ├── SquareGridUtils.java                  #
│   │   ├── TriangleGridUtils.java                #
│   │   └── WaTor.java                            #
│   ├── models                                      #
│   │   ├── GameOfLifeModel.java                  #
│   │   ├── SpreadingFireModel.java               #
│   │   ├── SegregationModel.java                 #
│   │   ├── SimulationModel.java                  #
│   │   ├── wator                                 #
│   │   │   ├── Fish.java                       #
│   │   │   └── Shark.java                      #
│   │   └── WaTorModel.java                       #
│   └── Simulator.java                              #
├── utility                                           #
│   ├── ColorUtils.java                             #
│   └── ShapeUtils.java                             #
├── visualization                                     #
│   ├── GUI.java                                    #
│   ├── model_controls                              #
│   │   ├── GameOfLifeControl.java                #
│   │   ├── ModelControl.java                     #
│   │   ├── SpreadingFireControl.java             #
│   │   ├── SegregationControl.java               #
│   │   └── WaTorControl.java                     #
│   ├── Main.java                                   #
│   ├── statistics                                  #
│   │   ├── GameOfLifeStatistics.java             #
│   │   ├── ModelStatistics.java                  #
│   │   ├── SpreadingFireStatistics.java          #
│   │   ├── SegregationStatistics.java            #
│   │   └── WaTorStatistics.java                  #
│   ├── SimulationControl.java                      #
│   ├── SimulationPanel.java                        #
│   └── StatusCode.java                             #
└── xml                                               #
    ├── parser                                        #
    │   ├── GameOfLifeXMLParser.java                #
    │   ├── ParentXMLParser.java                    #
    │   ├── SpreadingFireXMLParser.java             #
    │   ├── SegregationXMLParser.java               #
    │   └── WaTorXMLParser.java                     #
    ├── writer                                        #
    │   ├── GameOfLifeWriter.java                   #
    │   ├── SpreadingFireWriter.java                #
    │   ├── SegregationWriter.java                  #
    │   ├── WaTorWriter.java                        #
    │   └── XMLWriter.java                          #
    └── XMLException.java                             #
</pre>
