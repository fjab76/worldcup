package fjab.worldcup

import fjab.worldcup.api.GroupResult
import fjab.worldcup.api.GroupResultCalculator
import fjab.worldcup.util.SingleTeamResult;

import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

class SolutionBySimulation implements GroupResultCalculator {

	//Setting default value: the higher the number of iterations, the more accurate the result
	private int numIterations = 10000;
	
	@Override
	public Set<GroupResult> calculateGroupResults(int numTeams) {
		
		//Group results are added to this set as they are calculated
		Set<GroupResult> groupResults = []
		
		//Teams are represented by numbers: 0,1,2,3...
		//List matches = calculateMatches(numTeams)
		List rounds = calculateRounds(numTeams)
		
		(1..numIterations).each {playGroup rounds,groupResults,numTeams}

		return groupResults
	}
	
	private List calculateMatches(int numTeams){
		
		ICombinatoricsVector<Integer> originalVector = Factory.createVector(0..<numTeams)
		Generator<Integer> generator = Factory.createSimpleCombinationGenerator(originalVector, 2 as Integer)
		
		generator.generateAllObjects().collect{it.getVector()}
	}
	
	private List calculateRounds2(List matches, int numTeams){
		
		ICombinatoricsVector<Integer[]> originalVector = Factory.createVector(matches as Integer[][])
		//We are assuming that the number of teams is even
		Generator<Integer[]> generator = Factory.createSimpleCombinationGenerator(originalVector, numTeams/2 as Integer)
		
		List roundList = generator.generateAllObjects().collect{it.getVector()}
		roundList.findAll {filterOutInvalidRound it}
	}
	
	/**
	 * Calculate the matches to be played in each round.
	 * Note: the number of rounds equals the number of teams minus 1
	 * The returned element is a list like this: 
	 * [[[0, 1], [2, 3], [4, 5]], [[0, 2], [1, 5], [3, 4]], ...],
	 * which means:
	 * 1st round matches: 0-1, 2-3, 4-5
	 * 2nd round matches: 0-2, 1-5, 3-4 
	 * and so on.
	 * @param numTeams
	 * @return
	 */
	public List calculateRounds(int numTeams){
		
		//List to keep count of the matches already taken. A match between team 0 and 1 is
		//represented by the value matrixOfMatches[0][1]
		//We just need half the matrix to represent all the possible matches as 0 vs 1 is the
		//same as 1 vs 0 (the order is irrelevant). Therefore, the number of matches is:
		//(numTeams * numTeams - numTeams)/2 (as a team cannot play itself).
		//Then, the number of rounds is given by: number of matches/(numTeams/2)
		
		List matrixOfMatches = new Boolean[numTeams][numTeams];		
		
		//Dividing by 2 is ok since numTeams must be an even number
		int numMatches = (numTeams * numTeams - numTeams)/2;
		int numRounds = numMatches/(numTeams/2);
		List rounds = [];
		
		while(numRounds!=rounds.size()) {
			/*List round = []
			List takenTeam = Boolean[numTeams]
			
			(numTeams-1..1).each {i ->
				(i+1..numTeams).each {j -> 
					if(!matrixOfMatches[i][j] && !takenTeam[j]){
						round.add([i,j])
						takenTeam[i] = true
						takenTeam[j] = true
						matrixOfMatches[i][j] = true
					}
				}
			}
			if(!takenTeam.find {!it}){
				rounds.add(round)
			}*/
			selectMatches(rounds, [], matrixOfMatches, numTeams)
		}
		
		return rounds;
	}
	
	private void selectMatches(List rounds, List forbiddenMatches, List matrixOfMatches, int numTeams){
		
		List round = []
		List takenTeam = new Boolean[numTeams]
		/*List localCopyOfMatrixOfMatches = (0..matrixOfMatches.size()){i ->
			(0..matrixOfMatches[i].size()){j ->
				localCopyOfMatrixOfMatches[i][j] = matrixOfMatches[i][j]
			}
		}*/
		
		for(int i=numTeams-2; i>=0; i--){
		//(numTeams-2..0).each {i ->
			for(int j=i+1; j<=numTeams-1; j++){
			//(i+1..numTeams-1).each {j ->
				if(!matrixOfMatches[i][j] && !takenTeam[j] && (forbiddenMatches[0]!=i || forbiddenMatches[1]!=j)){
					round.add([i,j])
					takenTeam[i] = true
					takenTeam[j] = true
					matrixOfMatches[i][j] = true
					break
				}
			}
		}
		if(takenTeam.findAll().size()==takenTeam.size()){
			rounds.add(round)
		}
		else{
			//undo changes
			round.each {
				matrixOfMatches[it[0]][it[1]] = null;
			}
			
			selectMatches(rounds, round.last(), matrixOfMatches, numTeams)
		}
	}
	
	/**
	 * Invalid rounds are those in which the same team plays more than one match
	 */
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
		
		rounds.eachWithIndex {round,i -> playRound(round,i,resultsTable)}		
		
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
	
	
	public void setNumIterations(int numIterations){
		this.numIterations = numIterations;
	}

}
