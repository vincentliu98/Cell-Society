## Introduction
* Problem
    * Build a flexible model to simulate any 2D Cellular Automata
* Primary Design goal
    * Create a superclass for general simulation so that it’s easy to add more simulations
    * XML file should be designed to change any parameters related to the simulation, and it shouldn’t be intended for direct modification by the users.
    * UI is easy to understand and use, provides functionality to change all aspects of the simulation (align with XML).
* Primary Architecture
    * UI
        * State
        * Start/Pause/Stop
        * Grid layout
        * Scale/Size
        * Loading and saving the parameters from/on to XML
        * Splash/start screen to set initial parameters, then start simulation
        * Change rate
    * Simulations
        * One superclass for general simulations
        * Subclasses for each specific simulations: game of life, segregation, predator-prey, and fire
    * XML: 
        * Set initial settings
        * Retrieve every necessary parameter from the configuration file.


## Overview
![Simulation](https://preview.ibb.co/kJ4upz/Simulation.png)
![XML and UI](https://preview.ibb.co/c1hEpz/XML_Display.png)
* Simulation Model
    * Cell (class) 
        * Function
            * Keeps its current/next value, so that all updates can happen                 simultaneously.
            * Data Structures
                * something to keep tack of current value/state
            * Methods
                * Public void update(ArrayList neighbors) that looks at                       their neighbors from its parent CellGrid instance to                         update its next value.
                * Public void commit() that makes next value its current.
    * CellGraph (interface)
        * Methods: updateAll, commitAll, getNeighbors, getView
        * We currently only have one class that implements this, but we               probably want more flexibility for implementing layouts.
    * CellGrid (class) implements CellGraph
        * Keeps track of 2D array of Cells and calls update()/commit() for             each cell.
        * Subclasses
            * SegregationCellGrid
        * Data Structures
            * CellArray, a 2D array of all the Cells in the simulation
        * Methods
            * Public void updateAll()
                * For each Cell c in CellArray, calls c.update(updateRule)
            * Public void commitAll()
                * For each Cell c in CellArray, calls c.commit()
            * Public ArrayList\<Cell> getNeighbors(Cell c)
                * Returns an ArrayList of c’s neighbors
            * Public Node getView()
                * Returns JavaFX Node, to be displayed.
            * Public void updateView()
                * Updates each drawable within the view.
            * Public void setUpdateRule(rule)
                * Sets up the updateRule (will be called by                                   SimulationModelPanel)
    * UpdateRule (interface): specify the rules for the cells to be updated,       output the “next value” for the cell
        * Implemented by
            * GameOfLifeUpdateRule
            * SegregationUpdateRule
            * PredatorPreyUpdateRule
            * SpreadingFireUpdateRule
        * Methods
            * public T nextValue(Cell, ArrayList\<Cell>);
* XML: 
    * One parent XMLRouter
        * Looks at the model name and calls the relevant ReaderWriter class to load or write XML
        * Methods
            * Public void setParams(String filename, simulationModelPanel panel)
                * Read the desired simulation model from the file
                * Call the read method from the XMLReaderWriter for the                       simulation model, save the map that is returned in a local                   variable
                * For each parameter name key in the Map, set the parameter in panel
            * Public void callWriter(String modelName)
                * Call the write() method from the XMLReaderWriter for the simulation model specified by modelName
    * XMLReaderWriter for each model (data model <-> .XML files)
        * Separate but with uniform method names
        * Methods
            * Public Map<String, String> read(String filename)
                * Set the parameters of the simulation (set the tick speed, and, for a Segregation Model simulation for example, set the minimum acceptable percentage of like-minded neighbors), by adding them to a map with the keys being parameter names and the values being the parameter values
                * Return the Map 
            * Public void write()
                * Create and save an XML file with the parameters of the simulation that is currently running


* Display
    * Receive data from the CellGraph and paint different colors depending on the cell’s value.
    * Update the display for a certain time interval, using a simulation tick
    * Data Structures
        * CellGrid myCellGrid, the CellGrid for this simulation 
    * Methods
        * simulationTick() calls
            * myCellGrid.updateAll()
            * myCellGrid.commitAll()
            * myCellGrid.getView() for initial JavaFX display
            * myCellGrid.updateView() for later display updates (don’t have to clear/refill the components, just change their inner values)

* ControlPanel class, that takes care of simulation controls on the bottom of the UI.
* SimulationModelPanel for setting up model-specific parameters.
    * Param getParameter(String parameterName) 
    * Void setParameter(String, Params)   (every time this method is called, we need to decide whether to setup a new updateRule or not)

## User Interface
* On the control panel, if the user presses
    - Save: it will pop the usual save dialog to have the user choose the directory in which to save the configuration XML file.
    - Load: it will pop the usual open dialog to choose a XML file to be loaded. If a unsaved simulation w as ongoing, it will display a warning popup before opening the dialog. Also, if the XML file is ill-formatted, it will display a error popup stating the error. 
    - Pause: It’ll stop the next tick from happening.
    - Play: It’ll continue the ticks, depending on the tick rate
    - Speed Up: it will increase the tick rate
    - Speed Down: it will decrease the tick rate
* On the simulation model panel, the user should be able to change the parameters ONLY WHEN the simulation is paused.
* On the simulation panel, when we press a grid, the value will change, but only when the simulation is paused.
* ![UI](https://preview.ibb.co/hTQENK/UI.png)


## Design Details 

##### Apply the rules to a middle cell: set the next state of a cell to dead by counting its number of neighbors using the Game of Life rules for a cell in the middle (i.e., with all its neighbors)
- For a middle cell m, call cell.update(). The GameOfLifeUpdateRule instance should decide what the next value would be.
##### Apply the rules to an edge cell: set the next state of a cell to live by counting its number of neighbors using the Game of Life rules for a cell on the edge (i.e., with some of its neighbors missing)
- For an edge cell e, also call cell.update(). The edge case will be dealt in the GameOfLifeUpdateRule instance.
##### Move to the next generation: update all cells in a simulation from their current state to their next state and display the result graphically
- Call SimulationTick()
- Call updateAll in CellGrid
- Call updateView(), which displays the view of the cells based on the state of each cell.
##### Set a simulation parameter: set the value of a parameter, probCatch, for a simulation, Fire, based on the value given in an XML fire
- The XMLRouter will use the callReader() method to read the model name (which should be the first tag of the XML), and call the static read() method from the appropriate XMLReader to parse the rest of the XML and output a Map<String, String> which contains all the data we need to setup a simulation.
- The XMLRouter will then call setParams method on SimulationModelPanel instance to setup the parameters. 
##### Switch simulations: use the GUI to change the current simulation from Game of Life to Wator
- Click “Wator” in the dropdown menu of simulations, found in the control panel at the bottom of the screen.
- A new XML file will be created using the WatorXMLWriter class, this XML file will encode the default configuration for a Wator Simulation
- The WatorXMLLoader will then load the configuration specified by the previously created XML file 
- The initial state of the CellArray

## Design Considerations 
* Whether to allow user to change specific parameters of a simulation model via the UI.
    * Pros: easier to user to get the desired simulation
    * Cons: may not be possible to implement given time constraints 

## Team Responsibilities
- Simulation
    - Primary: Inchan Hwang
    - Secondary: Jay Pande
- Configuration
    - Primary: Jay Pande
    - Secondary: Vincent Liu
- Visualization
    - Primary: Vincent Liu
    - Secondary: Inchan Hwang
