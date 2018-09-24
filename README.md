cell society
====

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

### Folder Structure

cellsociety_team10                                                  #
├── cellsociety_team10.iml                                    #
├── doc                                                       #
│   ├── DESIGN_PLAN.md                                      #
│   ├── INHERITANCE_REVIEW_jl729_dvt5.md                    #
│   ├── INHERITANCE_REVIEW_ih33_yq50.md                     #
│   ├── INHERITANCE_REVIEW_jgp17_cf164.md                   #
│   ├── README.md                                           #
│   ├── Simulation.png                                      #
│   ├── UI.png                                              #
│   └── XML_Display.png                                     #
├── data                                                      #
│   ├── Game_of_Life_checkerboard.xml                       #
│   ├── README.md                                           #
│   ├── writer_test_life.xml                                #
│   ├── writer_test_wator.xml                               #
│   ├── writer_test_fire.xml                                #
│   ├── writer_test.xml                                     #
│   └── wirter_test_segregation.xml                         #
├── LICENSE                                                   #
├── lib                                                       #
│   └── README.md                                           #
├── out                                                       #
│   └── production                                          #
│       cellsociety_team10                                        #
│       ├── module-info.class                               #
│       ├── README.md                                       #
│       ├── simulation                                      #
│       │   ├── Cell.class                                #
│       │   ├── CellGraph.class                           #
│       │   ├── factory                                   #
│       │   │   ├── GameOfLife.class                    #
│       │   │   ├── SpreadingFire.class                 #
│       │   │   ├── SquareGridUtils.class               #
│       │   │   ├── Segregation.class                   #
│       │   │   └── WaTor.class                         #
│       │   ├── models                                    #
│       │   │   ├── GameOfLifeModel.class               #
│       │   │   ├── SimulationModel.class               #
│       │   │   ├── SpreadingFireModel.class            #
│       │   │   ├── SegregationModel.class              #
│       │   │   ├── wator                               #
│       │   │   │   ├── Fish.class                    #
│       │   │   │   └── Shark.class                   #
│       │   │   └── WaTorModel.class                    #
│       │   └── Simulator.class                           #
│       ├── style.css                                       #
│       ├── utility                                         #
│       │   ├── ColorUtils.class                          #
│       │   └── ShapeUtils.class                          #
│       ├── visualization                                   #
│       │   ├── GUI.class                                 #
│       │   ├── model_panels                              #
│       │   │   ├── GameOfLifePanel.class               #
│       │   │   ├── ModelPanel.class                    #
│       │   │   ├── SegregationPanel.class              #
│       │   │   ├── SpreadingFirePanel.class            #
│       │   │   └── WaTorPanel.class                    #
│       │   ├── Main.class                                #
│       │   └── SimulationControlPanel.class              #
│       └── xml                                             #
│           ├── parser                                      #
│           │   ├── GameOfLifeXMLParser.class             #
│           │   ├── ParentXMLParser.class                 #
│           │   ├── SegregationXMLParser.class            #
│           │   ├── SpreadingFireXMLParser.class          #
│           │   └── WaTorXMLParser.class                  #
│           ├── writer                                      #
│           │   ├── GameOfLifeWriter.class                #
│           │   ├── SpreadingFireWriter.class             #
│           │   ├── SegregationWriter.class               #
│           │   ├── WaTorWriter.class                     #
│           │   └── XMLWriter.class                       #
│           └── XMLException.class                          #
├── README.md                                                 #
├── resources                                                 #
│   └── style.css                                           #
└── src                                                       #
    ├── module-info.java                                      #
    ├── README.md                                             #
    ├── simulation                                            #
    │   ├── CellGraph.java                                  #
    │   ├── Cell.java                                       #
    │   ├── factory                                         #
    │   │   ├── GameOfLife.java                           #
    │   │   ├── Segregation.java                          #
    │   │   ├── SquareGridUtils.java                      #
    │   │   ├── SpreadingFire.java                        #
    │   │   └── WaTor.java                                #
    │   ├── models                                          #
    │   │   ├── GameOfLifeModel.java                      #
    │   │   ├── SpreadingFireModel.java                   #
    │   │   ├── SegregationModel.java                     #
    │   │   ├── SimulationModel.java                      #
    │   │   ├── wator                                     #
    │   │   │   ├── Fish.java                           #
    │   │   │   └── Shark.java                          #
    │   │   └── WaTorModel.java                           #
    │   └── Simulator.java                                  #
    ├── utility                                               #
    │   ├── ColorUtils.java                                 #
    │   └── ShapeUtils.java                                 #
    ├── visualization                                         #
    │   ├── GUI.java                                        #
    │   ├── model_panels                                    #
    │   │   ├── GameOfLifePanel.java                      #
    │   │   ├── ModelPanel.java                           #
    │   │   ├── SegregationPanel.java                     #
    │   │   ├── SpreadingFirePanel.java                   #
    │   │   └── WaTorPanel.java                           #
    │   ├── Main.java                                       #
    │   └── SimulationControlPanel.java                     #
    └── xml                                                   #
        ├── parser                                            #
        │   ├── GameOfLifeXMLParser.java                    #
        │   ├── ParentXMLParser.java                        #
        │   ├── SpreadingFireXMLParser.java                 #
        │   ├── SegregationXMLParser.java                   #
        │   └── WaTorXMLParser.java                         #
        ├── writer                                            #
        │   ├── GameOfLifeWriter.java                       #
        │   ├── SpreadingFireWriter.java                    #
        │   ├── SegregationWriter.java                      #
        │   ├── WaTorWriter.java                            #
        │   └── XMLWriter.java                              #
        └── XMLException.java                                 #

