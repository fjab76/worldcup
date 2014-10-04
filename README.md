worldcup
========

The idea for this project emerged during the recent Brazil worldcup as an attempt to work out all the possible 
combinations of results for any given group. As it is known, the Groups stage of the worldcup consists in having groups formed by 4 teams which play each other once. The variety of results that happened got me thinking about how many combinations were possible.
The project has been developed using Java 8 and Groovy.

The initial efforts were fruitless until I came up with an appropriate representation of the potential results of a group, namely: fjab.worldcup.api.GroupResult. The use of this representation made it possible to generalise the problem to consider groups with any number of teams.
At the present moment, the implementation of this class is functional but not complete, in the sense that constraint number 2 "two elements of a column cannot have their partners in the same column" has escaped all my efforts to be validated and, therefore, it would be possible to create objects inconsistent with the definition of GroupResult. The problem I ran into is that, although it is easy to identify those cases that are not valid, it is not that easy to recognise as valid those cases that are so (as all the algorithms I have thought of depend on the specific order of the elements that are part of a combination of results to successfully identify a valid combination)
Most of the methods existing in GenericsArray, IntegerArray and IntegerMatrix are the result of my attempts to implement constraint number 2.

The methods to calculate the number of groups are declared in the interface GroupResultCalculator and there are 2 different implementations: SolutionByConstruction and SolutionBySimulation.
SolutionByConstruction constructs all the possible combinations of results according to the definition of GroupResult
SolutionBySimulation simulates the results of the different matches of a group to obtain all the possible combinations of results according to the definition of GroupResult. Repeating the simulation process 2,000 times is enough to get all the possible combinations (for 4-team groups).

A third implementation (in fact, the first one I tried because at that moment seemed the most straightforward) could be SolutionByCombination consisting in getting all the possible combinations of results (valid and invalid) and then discarding the invalid ones by making use of the validations applied when creating an object of type GroupResult (yet because of the failure to implement the validation of constraint number 2, that implementation is not possible).

Finally, if you are curious about the number of result combinations that are possible for any given group, download the project and run it!!
Just a word of caution: the project builds correctly in Eclipse. However, when running Maven to build it, it fails due to a bug (apparently) in the groovy-eclipse-compiler. The quick solution to get around this problem is to delete the class fjab.worldcup.util.GenericsArrayImplTest.





