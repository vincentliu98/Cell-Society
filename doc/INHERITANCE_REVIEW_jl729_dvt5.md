Vincent Liu (jl729) and Duy (dvt5)
---
### Part 1
* What is an implementation decision that your design is encapsulating (i.e., hiding) for other areas of the program?
    * I make the root private 
* What inheritance hierarchies are you intending to build within your area and what behavior are they based around?
    * ModelPanel is an abstract class that extends HBox class. In this way, each model will extend ModelPanel, and the
    specific parameters will be display on the left side of the screen.
* What parts within your area are you trying to make closed and what parts open to take advantage of this polymorphism you are creating?
    * The ModelPanel is open for being extended. ModelPanel for specific models will be closed because the parameters cannot
    be changed randomly.
* What exceptions (error cases) might occur in your area and how will you handle them (or not, by throwing)?
    * NullPointer exception might occur when there the model name does not match. However, this is not supposed to happen
    because we only give the choice of the models provided.
* Why do you think your design is good (also define what your measure of good is)?
    * The current design is good because it breaks down the Main class into multiple components.

### Part 2
* How is your area linked to/dependent on other areas of the project?
    * In the Simulation Panel of the GUI, the simulation will be initialized, and the user interface will be updated in the
    tick method.
* Are these dependencies based on the other class's behavior or implementation?
    * Yes, when the simulation needs to be updated the dependency will be used.
* How can you minimize these dependencies?
    * The aforementioned dependency is necessary, and it is better not to minimized.
* Go over one pair of super/sub classes in detail to see if there is room for improvement. 
    * Right now, I am still working on ModelPanel and GameOfLifePanel, they will share some features.
* Focus on what things they have in common (these go in the superclass) and what about them varies (these go in the subclass).
    * Common: Number of cells, Rate
    * Variation: specific parameters such as probCatch

### Part 3
* Come up with at least five use cases for your part (most likely these will be useful for both teams).
    * When the user clicks begin, the simulation will start
    * When the user clicks save, an XML file will be generated
    * When the user clicks load, an XML files' parameters will be read
    * When the user clicks up arrow, the speed of rate will increase
    * When the user drags the button to the left or the right, the cell numbers will change
* What feature/design problem are you most excited to work on?
    * I am most excited to work on dynamically displaying the parameters to the ModelPanel.
* What feature/design problem are you most worried about working on?
    * I am worried about the XML and how it will interact with the User Interface.