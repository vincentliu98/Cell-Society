Inheritance Review jgp17
========================

Partner from discussion: cf164 (Claire)

Part 1
------

1.  What is an implementation decision that your design is encapsulating (i.e.,
    hiding) for other areas of the program?

    -   Classes outside the xml package cannot access the methods or data
        contained within the XMLParser class or any of its subclasses.

2.  What inheritance hierarchies are you intending to build within your area and
    what behavior are they based around?

    -   I was not originally planning on having any inheritance hierarchies, but
        our team has come to the conclusion that it is best to have separate
        parser classes for each simulation model (e.g. GameOfLifeXMLParser would
        be the parser for any Game Of Life files) which inherit methods from an
        abstract parent class (XMLParser).

3.  What parts within your area are you trying to make closed and what parts
    open to take advantage of this polymorphism you are creating?
    -   All of the methods and data in the parent class are public so that they can 
        be accessed by the subclasses.

1.  What exceptions (error cases) might occur in your area and how will you
    handle them (or not, by throwing)?

    -   I will throw an XMLException if a file passed into one of the parser
        subclasses does not have the right type attribute or is not an XML file.
        I will look at creating a new type of exception to throw when a file
        does not have the correct number of data fields to instantiate a
        Simulation for the desired model.

2.  Why do you think your design is good (also define what your measure of good
    is)?

    -  I think my design is good in that it is very straightforward to add
        capability to process a file encoding the settings for a new type of
        model. One simply needs to create a new subclass of XMLParser, and
        within that subclass define the abstract method to return a Simulation,
        as well as any tags to look for which are specific to that model. When I
        was trying to have one XML Parser class handle all types of files, I was
        having trouble figuring out how to tell the getSimulation method which
        type of simulation to produce, and the tag data was placed in classes
        that were in other packages. The design is much better now because it
        has subclasses of the XMLParser.

Part 2
------

1.  How is your area linked to/dependent on other areas of the project?

    -   Simulation: each XMLParser subclass has a method which returns a
        Simulation object

    -   CellGraph: in order to return a Simulation that method needs to create a
        CellGraph object

    -   Cell: a CellGraph consists of a map of Cell objects to lists of their
        neighbor Cells

2.  Are these dependencies based on the other class's behavior or
    implementation?

    -  These dependencies are primarily based on the behavior of the three
        classes mentioned above

3.  How can you minimize these dependencies?

    -  There is not much that I can do to minimize these dependencies without
        the rest of the team completely restructuring the way that Simulations
        are created, and a restructuring may not necessarily result in a better
        design of the project overall.

4.  Go over one pair of super/sub classes in detail to see if there is room for
    improvement.

    -   See the answer to the question below. All the different parts seem to be
        in the correct class already, so there’s really no room for improvement.

5.  Focus on what things they have in common (these go in the superclass) and
    what about them varies (these go in the subclass).

    -   All subclasses parse the standard attributes of a cell (unique ID,
        neighbors, x-position, y-position) in the same way, thus the code and
        data tags for parsing these attributes are contained in the parent
        class. For each simulation, each cell needs to have values that are
        unique to the simulation (for game of life, each cell needs an isAlive
        value, whereas for segregation, each cell needs a stay/leave value, and
        a red/blue value). Because the number of unique values varies, the code
        for parsing unique values, and the specific data tags to look for to
        find these unique values, are contained within each subclass.
        Additionally, each model has certain parameters which apply to the whole
        simulation (for example, the segregation model has a tolerance ratio.
        The code and data tags for parsing these non-cell specific unique values
        are contained within each subclass.

Part 3

1.  Come up with at least five use cases for your part (most likely these will
    be useful for both teams).

    -   Load a file at just after launching the program: Load button will be
        pressed in UI, file will be selected from dialog box, XMLParser parent
        class will be called, a method in this class will call the method in the
        appropriate subclass to return a Simulation object, this simulation
        object will be loaded into the UI

    -   Load a file in the middle of running a different simulation: Load button
        will be pressed in UI, file will be selected from dialog box, XMLParser
        parent class will be called, a method in this class will call the method
        in the appropriate subclass to return a Simulation object, this
        simulation object will be loaded into the UI

    -   Simulation name is not valid: XML exception will be thrown

    -   Required values are missing: An Exception (maybe
        IllegalArgumentException?) will be thrown

    -   Load probability generated configuration: I will create a
        getRandomSimulation method in each parser subclass which will randomly
        generate a CellGraph object based on the Probabilities provided, this
        CellGraph will then be used to generate a Simulation object to load into
        the UI.

2.  What feature/design problem are you most excited to work on?

    -  I’m excited to set up all of the subclasses

3.  What feature/design problem are you most worried about working on?

    -   getRandomSimulation, because so far we are only working on parsing files
        with cells represented individually
