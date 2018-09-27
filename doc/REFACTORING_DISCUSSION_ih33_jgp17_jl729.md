# Refactoring Lab
For each section in your discussion file, describe why you chose the fixes you did and what, if any, alternatives you considered.
## Duplication Refactoring
- No duplication found by SonarQube. (There are indeed some similar code while constructing classes for each model, but this kind of "duplication" should be allowed). 
- We were able to refactor similar code for creating CellGraphs, so there is now only one method in the ParentXMLParser class that creates all types of CellGraphs. Thus complex simulations like Wator no longer need a separate method to create a CellGraph, and changes made to the way CellGraphs are created only need to be made in one place in the code.
## Checklist Refactoring
- Removed public global variables (they were declared final but were an array). 
- Removed the magic values that we thought were "magic". We did not change **0.5 * width** to **HALF * width**
## General Refactoring
- Added comments to empty methods in subclasses. These methods are required because subclasses have to implement their parent class's abstract method. By adding comments, it makes the code more readable.
## Longest Method Refactoring
- In SimulationControl.class, the initializeStructure method was refactored. Inside of it, we call initializeElements method to initialize buttons and boxes. It makes it more obvious what the code is doing.