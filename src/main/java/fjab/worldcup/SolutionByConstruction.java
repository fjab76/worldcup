package fjab.worldcup;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

import fjab.worldcup.api.GroupResult;
import fjab.worldcup.api.GroupResultCalculator;
import fjab.worldcup.api.SingleTeamResult;
import fjab.worldcup.util.IntegerMatrix;

public class SolutionByConstruction implements GroupResultCalculator {
	

	@Override
	public Set<GroupResult> calculateGroupResults(int numTeams) {
		
		//Group results are added to this set as they are calculated
		Set<GroupResult> groupResults = new HashSet<>();
		
		//Initial call to the iterative function that calculates all the group results
		calculateTeamGroupResult(numTeams, groupResults, SingleTeamResult.calculateSingleTeamResults(numTeams), new Integer[numTeams][numTeams-1], 0);
				
		return groupResults;
	}

	/**
	 * Iterative function with the actual implementation of the algorithm.
	 * On each iteration, the function calculates the results corresponding to a team (all the possible results for a single
	 * team are tried out). The results obtained for a team induce results in the other teams (0 induces 0 and any other value
	 * induces its opposite). Therefore, when trying possible results for a team, only those results compatible with the
	 * values induced by the other teams are valid)
	 * 
	 * @param numTeams
	 * @param results Set where the calculated results are stored
	 * @param singleTeamResults Bidimensional array of Integers representing all the possible results of a single team
	 * @param groupResult Bidimensional array of Integers used to calculate each of the possible group results. On each iteration,
	 * a different column is calculated
	 * @param team Number of the team whose results must be calculated (starting with 0 and ending with numTeams-1)
	 */
	private void calculateTeamGroupResult(int numTeams, Set<GroupResult> results, Integer[][] singleTeamResults,Integer[][] groupResult, int team) {
		
		for(int k=0; k<singleTeamResults.length; k++){
			
			Integer[] teamResults = null;
			if(team==0)
				teamResults = Arrays.copyOf(singleTeamResults[k], singleTeamResults[k].length);
			else
				teamResults = IntegerMatrix.matchArrays(groupResult[team], singleTeamResults[k]);
			
			if(teamResults!=null){
				groupResult[team] = teamResults;
				for(int m=team+1; m<groupResult.length; m++)
					groupResult[m][team] = -groupResult[team][m-1];				
				
				if(team==numTeams-1)
					results.add(new GroupResult(groupResult));
				else
					calculateTeamGroupResult(numTeams, results, singleTeamResults, groupResult, team+1);
				
				//Initialising values for next iteration
				for(int m=team; m<groupResult.length-1; m++){
					groupResult[team][m] = null;					
					groupResult[m+1][team] = null;
				}	
			}
		}
	}


	@Override
	public int calculateUpperLimitOfResults(int numTeams) {
		
		//The number of combinations in a group when considering all the possible results for a single team
		//is a combination with repetition of the number of individual results taken in groups of as many
		//elements as the number of teams.
		//For instance, when numTeams=4 then the number of potential results for a team is 10 and the number of 
		//all the possible combinations is 715. This could be an upper limit.
		//
		//A more precise upper limit can be calculated by discarding those combinations that are not compliant
		//with this rules:
		//
		//1.the number of 0s must be even
		//2.the number of 1s must be equal to the number of -1s
		
		ICombinatoricsVector<Integer[]> originalVector = Factory.createVector(SingleTeamResult.calculateSingleTeamResults(numTeams));
		Generator<Integer[]> generator = Factory.createMultiCombinationGenerator(originalVector, numTeams);
		
		int upperLimit = (int) generator.generateAllObjects().stream()
				  .map(ICombinatoricsVector::getVector)
				  .map(x -> IntegerMatrix.copyListArraysToMatrix(x))
				  .filter(x -> GroupResult.checkElements(x))
				  .count();
		
		return upperLimit;
	}
	
	
}
