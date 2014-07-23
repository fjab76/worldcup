package fjab.worldcup;

import java.util.HashSet;
import java.util.stream.*;
import java.util.List;
import java.util.Set;

import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;


/**
 * This implementation is based on the use of combinatorial methods.
 * As stated in ProblemConstraints.java, no all the individual combinations are possible when a team is added to
 * a group as these combinations must be in agreement with other teams' combinations.
 * 
 * 
 * Description of the implementation given by this class:
 * 1.obtain all the possible combinations of results within a group by combining all the possible combinations of 
 * an individual team (10) in groups of 4 (number of teams per group). From a mathematical point of view, these are
 * combinations with repetition of 10 elements taken in groups of 4 (=715)
 * 2.discard those combinations that are not allowed
 * 3.normalise the allowed combinations
 *
 */
public class CombinatorialImpl implements Group {
	
	
	@Override
	public Set<GroupResult> calculateGroupResults(int numTeamsPerGroup) {
		
		Set<GroupResult> groupResults = new HashSet<>();
		final int[][] individualTeamCombinations = ProblemConstraints.INDIVIDUAL_TEAM_COMBINATIONS;
		
		//The number of potential fee combinations is a combination with repetition of 10 elements taken in groups of 4 
		//(715 possible combinations) 
		//CombinationGenerator<int[]> generator = new CombinationGenerator<int[]>(individualTeamCombinations, numTeamsPerGroup);
		ICombinatoricsVector<int[]> originalVector = Factory.createVector(individualTeamCombinations);
		Generator<int[]> generator = Factory.createMultiCombinationGenerator(originalVector, numTeamsPerGroup);
		assert generator.getNumberOfGeneratedObjects()==715;
		for(ICombinatoricsVector<int[]> comb : generator){
			List<int[]> combinationList = comb.getVector();
			int[][] combination = new int[combinationList.size()][combinationList.get(0).length];
			for(int j=0; j<combinationList.size(); j++){
				for(int k=0; k<combinationList.get(j).length; k++){
					combination[j][k] = combinationList.get(j)[k];
				}
			}
			////////////////////
			//Integer[][] combination = (Integer[][]) combinationList.stream().map(result -> IntStream.of(result).mapToObj(Integer::valueOf)).toArray();			
			
			
			///////////////////////
			boolean isAllowedCombination = normaliseCombination(combination);
			if(isAllowedCombination){				
				groupResults.add(new GroupResult(combination));
			}
		}
		
		return groupResults;
	}
	
	
	/**
	 * Normalises the combination of results obtained by different teams in a group by reducing it to a normal form. 
	 * If the given combination is a normal form itself, it remains the same.
	 * According to the discussion on ProblemConstraints.FREE_COMBINATIONS:
	 * 1.a normal form has its elements sorted in ascending order
	 * 2.there are combinations that are not allowed and cannot be reduced to a normal form 
	 * 
	 * @param combination Mutable object representing the combination of results obtained by different teams in a group. 
	 * At the end of the process, this object must contain a normal form.
	 * @return boolean True if it is possible to reduce the given combination to a normal form. Otherwise, false.
	 */
	boolean normaliseCombination(int[][] combination){
		
		//Checking preconditions
		for(int i=0; i<combination.length; i++){
			for(int j=0; j<combination[i].length; j++){
				assert combination[i][j]==-1 || combination[i][j]==0 || combination[i][j]==1 : "values of the array must be -1,0 or 1";
			}
		}
		
		assert combination.length-1==combination[0].length : "number of results must be equal to number of teams minus 1";
		
		
		//Checking if the given combination is allowed
		boolean isAllowedCombination = checkAllowedCombination(combination);
		
		if(isAllowedCombination){		
			//normalising results for each team by sorted them in ascending order according to the convention 
			//established by ProblemConstraints.freeCombinations
			/*for(int j=0; j<combination.length; j++){
				Arrays.sort(combination[j]);
			}*/
			
			//normalising the order of the teams in ascending order according to the position of their result combinations
			//in ProblemConstraints.freeCombinations				
//			Arrays.sort(combination, (o1,o2) ->  findIndex(o1)-findIndex(o2));
			
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * Checks if the combination of results passed as an argument is a valid combination. It is a valid combination if for
	 * every win (1) found in a team there is a loss (-1) in another and viceversa. Likewise, for every draw (0) in a team
	 * there must be a draw (0) in another.
	 * @param combination
	 * @return
	 */
	private boolean checkAllowedCombination(int[][] combination) {
		
		//Counter of linked results for each team
		//When found, linked results are always placed at the beginning of the corresponding array of results
		int[] counter = new int[combination.length];
		for(int j=0; j<combination.length; j++){
			counter[j] = 0;
		}
		
		//Looping over teams
		for(int i=0; i<combination.length-1; i++){
			
			int numTeamsToSearchForLinkedElement = combination.length-1;
			
			//Looping over results of i-th team
			for(int j=counter[i]; j<combination[i].length; j++){
				
				boolean linkedResultFound = false;
				
				//Looping over remaining teams to search linked results				
				linkedResult:for(int k=i+1; k<=numTeamsToSearchForLinkedElement; k++){										
					
					//Looping over results of k-th team to find linked result
					for(int m=counter[k]; m<combination[k].length; m++){
						
						if(combination[k][m]==-combination[i][j]){
							counter[k]++;							
							putLinkedResultAtTheBeginning(combination[k],m);
							
							if(k<numTeamsToSearchForLinkedElement){
								putTeamAtTheEnd(combination,counter,k);
							}
							numTeamsToSearchForLinkedElement--;
							
							linkedResultFound = true;
							break linkedResult;
						}
					}					
				}
				
				if(!linkedResultFound) return false;
				
				/*if(!linkedResultFound){
			
					//This point is reached if no linked result has been found among other team's results
					//In this case, we must change the first non-linked result that we find to get a linked result
					linkedResult:for(int k=i+1; k<=numTeamsToSearchForLinkedElement; k++){
						
						//Looping over results of k-th team to find linked result
						for(int m=counter[k]; m<combination[k].length; m++){
							
							combination[k][m]=-combination[i][j];
							
							counter[k]++;							
							putLinkedResultAtTheBeginning(combination[k],m);
							
							if(k<numTeamsToSearchForLinkedElement){
								putTeamAtTheEnd(combination,counter,k);
							}
							
							numTeamsToSearchForLinkedElement--;
							
							break linkedResult;						
						}					
					}				
				}*/
			}
		}
		return true;
	}

	private int findIndex(int[] array) {
		
		for(int k=0; k<ProblemConstraints.INDIVIDUAL_TEAM_COMBINATIONS.length; k++){
			boolean equals = true;
			for(int m=0; m<ProblemConstraints.NUMBER_OF_GAMES_PER_TEAM; m++){
				equals = array[m] == ProblemConstraints.INDIVIDUAL_TEAM_COMBINATIONS[k][m];
				if(!equals) break;					
			}
			if(equals) return k;
		}
		
		assert false : "this point should never be reached";
		return -1;
	}

	private void putTeamAtTheEnd(int[][] combination, int[] counter, int k) {

		for(int j=k; j<combination.length-1; j++){
			//swapping j and j+1 values
			int[] jplus1 = combination[j+1];
			combination[j+1] = combination[j];
			combination[j] = jplus1;
			
			int jplus1_2 = counter[j+1];
			counter[j+1] = counter[j];
			counter[j] = jplus1_2;
		}
		
	}

	private void putLinkedResultAtTheBeginning(int[] array, int m) {
		
		int linkedElement = array[m];
		for(int j=m; j>0; j--){
			array[j] = array[j-1];
		}
		array[0] = linkedElement;		
	}

}
 