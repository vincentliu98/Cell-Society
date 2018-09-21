# Inheritance Review jgp17
Partner from discussion: cf164 (Claire)
## Part 1
1. What is an implementation decision that your design is encapsulating 
(i.e., hiding) for other areas of the program?
    * The XML parser 
2. What inheritance hierarchies are you intending to build within your area
 and what behavior are they based around?
    * I am not planning on having any inheritance hierarchies.
3. What parts within your area are you trying to make closed and what parts
 open to take advantage of this polymorphism you are creating?
 
4. What exceptions (error cases) might occur in your area and how will you handle them (or not, by throwing)?
    * I  will throw exceptions
5. Why do you think your design is good (also define what your measure of good is)?
## Part 2
1. How is your area linked to/dependent on other areas of the project?
    * CellGraph
    * Cell
    * All models
2. Are these dependencies based on the other class's behavior or implementation?
3. How can you minimize these dependencies?
4. Go over one pair of super/sub classes in detail to see if there is room for improvement. 
5. Focus on what things they have in common (these go in the superclass) and what about them varies (these go in the subclass).
## Part 3
1. Come up with at least five use cases for your part (most likely these will be useful for both teams).
    * Load at beginning 
    * Load in middle
    * Simulation name is not valid
    * Required values are missing
    * Load probability generated configuration
2. What feature/design problem are you most excited to work on?
    *  
3. What feature/design problem are you most worried about working on?
    * How to make sure the cells in the CellGraph are of the correct