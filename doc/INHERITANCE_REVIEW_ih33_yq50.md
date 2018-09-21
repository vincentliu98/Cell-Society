# Inheritance review

## PART 1
* What is an implementation decision that your design is encapsulating (i.e., hiding) for other areas of the program?
    * The Simulator class hides all the information about the simulation, so that other parts of the code must go through the Simulator to retreive/manipulate the simulation informations. 
* What inheritance hierarchies are you intending to build within your area and what behavior are they based around?
    * We have SimulationModel interface, that defines all the model-specific behaviors and rules.
* What parts within your area are you trying to make closed and what parts open to take advantage of this polymorphism you are creating?
    * We are closing nearly everything for the SimulationModel interface. 
* What exceptions (error cases) might occur in your area and how will you handle them (or not, by throwing)?
    * The simulation works with the clean side of the program (no human errors), and hence there will not be any exceptions unless it propagated from the frontend/XML side.
* Why do you think your design is good (also define what your measure of good is)?
    * For each model, we only need to add two Classes; A subclass of the SimulationModel and a factory class to help generate Simulators with ease. The Cell/CellGraph/Simulator doesn't even need to be bothered with.

## PART 2
* How is your area linked to/dependent on other areas of the project?
    * Only the Simulator instance interacts with the UI, and only the SimulationModel instance are created by other parts of the code.
* Are these dependencies based on the other class's behavior or implementation?
    * No; only way the other classes can modify anything inside Simulator instance is either tick() or setSimulationModel(). These are not dependent on behavior/implementation of any other part of the code.
* How can you minimize these dependencies?
    * I think it's good enough to the point where it's convenient to use.
* Go over one pair of super/sub classes in detail to see if there is room for improvement. 
    * SimulationModel interface / GameOfLifeModel class. Hmm. It's really clean, but we had to make multiple refactoring to get to this "right form of" abstraction covering all the use cases.
* Focus on what things they have in common (these go in the superclass) and what about them varies (these go in the subclass).
    * localUpdate(): The CA models are supposed to decide its action via its local neighbors, hence the method.
    * globalUpdate(): However, in some models like Segregation, the cells can move to any empty location available.

## PART 3
* Come up with at least five use cases for your part (most likely these will be useful for both teams).
    * Hm. 
* What feature/design problem are you most excited to work on?
    * I'm really excited to make more models, to see if this abstraction can cover those as well.
* What feature/design problem are you most worried about working on?
    * Also the models, since if our abstraction is "awkward" for some models, we might need to change the overall abstraction one more time.

