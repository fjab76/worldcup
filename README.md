worldcup
========

The idea for this project emerged during the recent <b>Brazil Worldcup</b> as an attempt to work out all the possible 
combinations of results for any given group. As it is known, the Groups stage of the worldcup consists in having groups formed by 4 teams which play each other once. The variety of results that happened got me thinking about how many combinations were possible.<br>
The project has been developed using <b>Java 8</b> and <b>Groovy</b>.

The initial efforts were fruitless until I came up with an appropriate representation of the potential results of a group, namely: fjab.worldcup.api.GroupResult. The use of this representation made it possible to generalise the problem to consider groups with any number of teams.

The method to calculate the number of groups is declared in the interface GroupResultCalculator and there are 2 different implementations: SolutionByConstruction and SolutionBySimulation.<br>
<b>SolutionByConstruction</b> constructs all the possible combinations of results according to the definition of GroupResult.<br>
<b>SolutionBySimulation</b> simulates the results of the different matches of a group to obtain all the possible combinations of results according to the definition of GroupResult. Repeating the simulation process 2,000 times is enough to get all the possible combinations for 4-team groups.

Finally, if you are curious about the number of result combinations that are possible for any given group, download the project and run it!!<br>
There are 2 client classes with a main method, one for each algorithm: SolutionBySimulationClient and SolutionByConstructionClient.





