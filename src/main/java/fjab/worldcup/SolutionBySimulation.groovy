package fjab.worldcup

import fjab.worldcup.api.GroupResult
import fjab.worldcup.api.GroupResultCalculator
import fjab.worldcup.util.SingleTeamResult;

import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

class SolutionBySimulation implements GroupResultCalculator {

	private int numIterations = 10000;
	
	@Override
	public Set<GroupResult> calculateGroupResults(int numTeams) {
		
		//Group results are added to this set as they are calculated
		Set<GroupResult> groupResults = []
		
		//Teams are represented by numbers: 0,1,2,3...
		List matches = calculateMatches(numTeams)
		List rounds = calculateRounds(matches, numTeams)
		
		(1..numIterations).each {playGroup rounds,groupResults,numTeams}

		return groupResults
	}
	
	private List calculateMatches(int numTeams){
		
		ICombinatoricsVector<Integer> originalVector = Factory.createVector(0..<numTeams)
		Generator<Integer> generator = Factory.createSimpleCombinationGenerator(originalVector, 2 as Integer)
		
		generator.generateAllObjects().collect{it.getVector()}
	}
	
	private List calculateRounds(List matches, int numTeams){
		
		ICombinatoricsVector<Integer[]> originalVector = Factory.createVector(matches as Integer[][])
		Generator<Integer[]> generator = Factory.createSimpleCombinationGenerator(originalVector, numTeams/2 as Integer)
		
		List roundList = generator.generateAllObjects().collect{it.getVector()}
		roundList.findAll {filterOutInvalidRound it}
		
		/*List<Integer[][]> list = new ArrayList<>()
		list.add([[0,1],[2,3]] as Integer[][])
		list.add([[0,2],[1,3]] as Integer[][])
		list.add([[0,3],[1,2]] as Integer[][])*/
		//[[[0,1],[2,3]],[[0,2],[1,3]],[[0,3],[1,2]]] 
		//list
	}
	
	private boolean filterOutInvalidRound(List round){
		
		Boolean[] listOfOccurrencesByTeam = new Boolean[round.size()*2]
		for(match in round)
			for(team in match)
				if(listOfOccurrencesByTeam[team]==null)
					listOfOccurrencesByTeam[team] = true
				else
					return false
		
		return true				
	}
	
	
	
	private void playGroup(List rounds, Set<GroupResult> groupResults, int numTeams){
		
		Integer[][] resultsTable = new Integer[numTeams][rounds.size()]
		
		rounds.eachWithIndex {round,i -> playRound(round,i++,resultsTable)}		
		
		groupResults.add(new GroupResult(resultsTable))
	}
	
	private def playRound(List round, int roundNumber, Integer[][] resultsTable){
		
		round.each{playMatch it as List,roundNumber,resultsTable}
		
	}
	
	private void playMatch(List match, int roundNumber, Integer[][] resultsTable){
		
		//result only can take one of the values: -1,0,1 to indicate loss,draw,win respectively
		int result = getMatchResult()

		resultsTable[match[0]][roundNumber] = result
		resultsTable[match[1]][roundNumber] = -result
		
	}
	
	private int getMatchResult(){
		
		int upperLimit = 9 //multiple of 3
		Random random = new Random()
		int num = random.nextInt(upperLimit)
		if(num<upperLimit/3)
			return -1
		else if(num<upperLimit/3*2)
			return 0
		else
			return 1
	}
	
	/*
	public void setNumIterations(int numInterations){
		this.numIterations = numIterations;
	}*/

}
