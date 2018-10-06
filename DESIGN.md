

## High-Level Design Goals
* Simulation
    * Cell.java initializes cells with multiple values, such as coordinate, height/width, shape, and the value for the next stage
    * CellGraph.java is a customized HashMap containing all the cells as the key, and each cell's neighbors as the value. It is designed this way to flexibly display different shapes and structure of the cells. 
    * Simulator.java manages the CellGraph that contains all the individual cells. It performs a tick method to update the all cell's value, update the color of the cells that will be rendered to the GUI. It handles the change of the cell shape or the simulation model. It also communicates between the model and the GUI. For instance, it retrieves the statistics from a model and return that information to the GUI for it to draw a line chart.
    * models
        * Contains four models: Game Of Life, Spreading Of Fire, Segregation, Wa-Tor
        * The models all implement the SimulationModel interface, which specifies the methods for each model.
        * We use an interface because there are always some minor differences between models. For example, Segregation Model requires global update while others do not. Therefore, we specify a method named globalUpdate for the cells to move around in the entire CellGraph. Besides, each model contains different model parameters, thus we are supposed to use an interface to ensure the uniformity while allowing the existence of differences.
        * Each model is able to update cell value, update model parameters, update color, retrieve statistics of the current model (such as the number of each type of the cell), and call an XML writer to generate an XML file.
    * factory
        * Contains simulator generator for each model
        * Generates a simulator for a corresponding model based on the shape of the cell, with/without boundary, and the specific parameters to a model
        * The shapes that are available now are rectangle and triangle. However, it will be fairly easy to add other shapes, as the design now is pretty adaptable.
* Visualization
    * Main.java starts the animation in the GUI
    * GUI.java displays four components: SimulationControl, ModelChart, ModelControl, SimulationPanel
        * SimulationControl manages the modelControl (i.e. update the model) and the modelChart (i.e. update the line chart), and the modelControl manages simulationPanel (i.e. update the parameters for the existing model).
    * SimulationControl.java is the bottom part of the GUI. The user will be able to load/save settings from XML file, start/pause the simulation, change the rate of simulation, change the model, or change the cell shape.
    * model_controls
        * Contains sliders to allow users to change the parameters specific to each model.
        * Each model contains different number of parameters and names of the parameters, except the cell number is the common parameter. Thus I chose to use an abstract class that contains a slider for the cell number. In this way, I avoid duplicated code for cell number slider in each modelControl
    * statistics
        * ModelStatistics is an abstract class of a line chart. It contains the information about the axis, but does not have any information about the series in the chart.
        * Each model has their corresponding statistics class, which provides the abstract with series for each model.
        * Each model has a method updateStatistics, which will be called each time the new statistics is retrieved in the SimulationControl
* XML
    * parsers
        * ParentXMLParser.java is an abstract class which outlines what the children class need to perform
        * By using an abstract class, we are able to reduce the amount of the duplicated code from each parser
        * parsers that are specific to each model parse different parameters and allow the GUI to retrieve the values and render
    * writers
        * XMLWriter.java is an abstract class which outlines what the children class need to perform
        * By using an abstract class, we are able to reduce the amount of the duplicated code from each writer
        * writers that are specific to each model create tags for each parameters in the model and allow the user to save the new XML file to the local directory
    * XMLException
        * Throws exceptions when an XML error occurs

## How to Add New Features
* Simulation
    * New model needs to be added into models folder. It should include the specific parameters to the model and the rule of the model
    * New model's generator needs to be added into the factory. It should allow multiple ways to randomly generate a simulation
* Visualization
    * New model's specific parameters need to be added into model_controls package, which creates sliders for each parameter
    * New model's roles need to be added to statistics package to display the number of each role on the line chart
* Configuration
    * Need to create a child XML parser for the new model that extends ParentXMLParser
    * Need to create a child XML wirter for the new model that extends XMLWriter. Specific tags need to be added for each parameter.
* resources
    * Add the model name in English into English.properties

## Major Design Choices/Trade-Offs
* There is a subclass of the parent XML parser for each of the different types of simulations. The current design means that every time a new simulation is added a new subclass of the parser needs to be made. 
    * Pros
        * Each model-specific parser can instantiate a Simulation with the appropriate type of SimulationModel; by calling the right parser, the resulting Simulation will automatically have the correct SimulationModel, no extra code needs to be written to ensure this.
    * Cons
        * For each new simulation type, a parser subclass is one of many classes that need to be created. We were not able to achieve a design in which parsing for a new simulation could be handled simply by creating a new implementation of the SimulationModel interface.
## Assumptions and Simplifications
* One assumption we made when loading XML files for WaTor simulations is that all cells would start with a breedCount and starveCount of 0. The user probably cares more about loading a paticular layout of Fish, Sharks, and empty cells, and not very much about how close a fish or shark is to breeding or dying when a simulation is initialized.
