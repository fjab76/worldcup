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
		
		//Algorithm
		//Teams are represented by numbers: 0,1,2,3...
		List<Integer[]>  matches = calculateMatches(numTeams)
		List<Integer[][]> rounds = calculateRounds(matches, numTeams)
		
		(1..numIterations).each {playGroup rounds,groupResults,numTeams}

		return groupResults
	}
	
	private List<Integer[]> calculateMatches(int numTeams){
		
		ICombinatoricsVector<Integer> originalVector = Factory.createVector(0..<numTeams)
		Generator<Integer> generator = Factory.createSimpleCombinationGenerator(originalVector, 2 as Integer)
		
		generator.generateAllObjects().collect{it.getVector().toArray()}
	}
	
	private List<Integer[][]> calculateRounds(List<Integer[]> matches, int numTeams){
		
		/*ICombinatoricsVector<Integer[]> originalVector = Factory.createVector(matches as Integer[][])
		Generator<Integer[]> generator = Factory.createSimpleCombinationGenerator(originalVector, numTeams/2 as Integer)
		
		generator.generateAllObjects().collect{it.getVector().toArray()} as List<Integer[][]>*/
		
		/*List<Integer[][]> list = new ArrayList<>()
		list.add([[0,1],[2,3]] as Integer[][])
		list.add([[0,2],[1,3]] as Integer[][])
		list.add([[0,3],[1,2]] as Integer[][])*/
		[[[0,1],[2,3]],[[0,2],[1,3]],[[0,3],[1,2]]] as List<Integer[][]>
		//list
	}
	
	
	
	private void playGroup(List<Integer[][]> rounds, Set<GroupResult> groupResults, int numTeams){
		
		Integer[][] resultsTable = new Integer[numTeams][rounds.size()]
		//rounds.eachWithIndex {Integer[][] round, int i -> playRound(round,i,resultsTable)}
		
		int i = 0;
		for(Integer[][] round in rounds){
			playRound(round,i++,resultsTable)
		}
		
		groupResults.add(new GroupResult(resultsTable))
	}
	
	private def playRound(Integer[][] round, int roundNumber, Integer[][] resultsTable){
		
		round.each{playMatch it,roundNumber,resultsTable}
		
	}
	
	private void playMatch(Integer[] match, int roundNumber, Integer[][] resultsTable){
		
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
	

	@Override
	public int calculateUpperLimitOfResults(int numTeams) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void setNumIterations(int numInterations){
		this.numIterations = numIterations;
	}

}
