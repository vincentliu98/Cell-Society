cell society
====

TODO: comment for 
* models/wator/Fish
* models/SimulationModel
* models/Cell
* models/CellGraph
* models/Simulator
* utility
* model_panels/ModelPanel
* GUI
* SimulationControlPanel
* everything in parser folder
* everything in writer folder
* everything in factory folder

This project implements a cellular automata simulator.

Names: Inchan Hwang, Vincent Liu, Jay Pande

### Timeline

Start Date: 9.17.2018

Finish Date: 

Hours Spent: About 70 hours Till 9.24

### Primary Roles


### Resources Used


### Running the Program

Main class:

Data files needed: 

Interesting data files:

Features implemented:

Assumptions or Simplifications:

Known Bugs:

Extra credit:


### Notes


### Impressions

### src Folder Structure
<pre>
src                                                      #
├── module-info.java                               #
├── README.md                                      #
├── simulation                                     #
│   ├── CellGraph.java                           #
│   ├── Cell.java                                #
│   ├── factory                                  #
│   │   ├── GameOfLife.java                    #
│   │   ├── Segregation.java                   #
│   │   ├── SquareGridUtils.java               #
│   │   ├── SpreadingFire.java                 #
│   │   └── WaTor.java                         #
│   ├── models                                   #
│   │   ├── GameOfLifeModel.java               #
│   │   ├── SpreadingFireModel.java            #
│   │   ├── SegregationModel.java              #
│   │   ├── SimulationModel.java               #
│   │   ├── wator                              #
│   │   │   ├── Fish.java                    #
│   │   │   └── Shark.java                   #
│   │   └── WaTorModel.java                    #
│   └── Simulator.java                           #
├── utility                                        #
│   ├── ColorUtils.java                          #
│   └── ShapeUtils.java                          #
├── visualization                                  #
│   ├── GUI.java                                 #
│   ├── model_panels                             #
│   │   ├── GameOfLifePanel.java               #
│   │   ├── ModelPanel.java                    #
│   │   ├── SegregationPanel.java              #
│   │   ├── SpreadingFirePanel.java            #
│   │   └── WaTorPanel.java                    #
│   ├── Main.java                                #
│   └── SimulationControlPanel.java              #
└── xml                                            #
    ├── parser                                     #
    │   ├── GameOfLifeXMLParser.java             #
    │   ├── ParentXMLParser.java                 #
    │   ├── SpreadingFireXMLParser.java          #
    │   ├── SegregationXMLParser.java            #
    │   └── WaTorXMLParser.java                  #
    ├── writer                                     #
    │   ├── GameOfLifeWriter.java                #
    │   ├── SpreadingFireWriter.java             #
    │   ├── SegregationWriter.java               #
    │   ├── WaTorWriter.java                     #
    │   └── XMLWriter.java                       #
    └── XMLException.java                          #
</pre>
