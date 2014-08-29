package fjab.worldcup;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

import fjab.worldcup.api.GroupResult;
import fjab.worldcup.api.GroupResultCalculator;
import fjab.worldcup.util.IntegerArray;
import fjab.worldcup.util.IntegerMatrix;
import fjab.worldcup.util.SingleTeamResult;

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
				teamResults = checkCompatibility(groupResult[team], singleTeamResults[k]);
			
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
				  .filter(x -> GroupResult.checkElementsIdentity(x))
				  .filter(x -> GroupResult.checkElementsBalance(x))
				  .count();
		
		return upperLimit;
	}

	/**
	 * Checks whether the values of groupResult induced by other team's results are compatible with the values of singleTeamResult
	 * groupResult only contains the values induced by other team's results while the rest of the values are null. singleTeamResult
	 * is compatible with groupResult if the former contains the same values as the latter (excluding the null values)
	 * If the arrays are compatible, a new array is returned containing in first place the common elements and then the remaining
	 * elements of singleTeamResult. Else, null is returned.
	 * 
	 * @param groupResult Array of Integers representing the results of a team induced by other team's results. The rest of the elements
	 * are null
	 * @param singleTeamResult Array of Integers representing one of the possible combinations of results of a team without considering
	 * other team's results
	 * @return New array of Integers representing the results of a team in the group. The results induced by other teams
	 * come first in the same order as in groupResult. If groupResult and singleTeamResult are incompatible, null is returned
	 */
	 Integer[] checkCompatibility(Integer[] groupResult, Integer[] singleTeamResult){
		
		if(groupResult.length!=singleTeamResult.length)
			return null;
		
		//Defensive copy of singleTeamResult to manipulate its elements without altering the original array
		Integer[] singleTeamResultCopy = Arrays.copyOf(singleTeamResult, singleTeamResult.length);		
		
		//Copy of groupResult with no nulls
		Integer[] groupResultWithNoNulls = IntegerArray.removeNullElements(groupResult);
		
		//Defensive copy of groupResultWithNoNulls used to manipulate its elements without altering the original array  
		Integer[] groupResultWithNoNullsCopy = Arrays.copyOf(groupResultWithNoNulls, groupResultWithNoNulls.length);
	
		//Sorting both arrays to make it possible to compare one to another
		Arrays.sort(groupResultWithNoNullsCopy);
		Arrays.sort(singleTeamResultCopy);
		
		if(groupResultWithNoNullsCopy.length==singleTeamResultCopy.length){
			return Arrays.equals(groupResultWithNoNullsCopy, singleTeamResultCopy)?Arrays.copyOf(groupResult, groupResult.length):null;			
		}
		
		//Searching for a match
		int initialMatchingIndex = -1;
		int finalMatchingIndex = -1;
		for(int j=0,i=0; j<singleTeamResultCopy.length && i<groupResultWithNoNullsCopy.length; j++){
	
			if(singleTeamResultCopy[j].equals(groupResultWithNoNullsCopy[i])){
				i++;
				if(initialMatchingIndex==-1)
					initialMatchingIndex = j;
				finalMatchingIndex = j;
			}
			else if(!singleTeamResultCopy[j].equals(groupResultWithNoNullsCopy[i]) && i>0){
				return null;
			}
		}
		
		if(initialMatchingIndex==-1 || finalMatchingIndex-initialMatchingIndex!=groupResultWithNoNullsCopy.length-1)
			return null;
		else{
			//Composing the array to return
			Integer[] preMatch = Arrays.copyOfRange(singleTeamResultCopy, 0, initialMatchingIndex);
			Integer[] postMatch = Arrays.copyOfRange(singleTeamResultCopy, finalMatchingIndex+1, singleTeamResultCopy.length);
			Integer[] nonMatchedElements = IntegerArray.concatArrays(preMatch,postMatch);
			return IntegerArray.concatArrays(groupResultWithNoNulls,nonMatchedElements);
			
		}		
	}
	
	
}
