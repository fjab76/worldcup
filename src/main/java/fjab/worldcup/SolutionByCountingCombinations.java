package fjab.worldcup;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

import fjab.worldcup.api.GroupResult;
import fjab.worldcup.api.GroupResultCalculator;
import fjab.worldcup.util.IntegerMatrix;
import fjab.worldcup.util.SingleTeamResult;

/**
 * This implementation is based on the use of combinatorial methods. The results of a game are represented by GAME_RESULT:
 * loss (-1), draw (0) and win (1).
 * For the discussion of this implementation, let's assume that numTeamsPerGroup=4.
 * 
 * Since each team play 3 games (numTeamsPerGroup-1), the potential combinations of results for a team are:
 * {1,1,1},{1,1,0},{1,1,-1},{1,0,0} and so on.
 * More precisely, these are combinations with repetition of 3 elements taken in groups of 3 so in total 
 * there are 10 different combinations.
 * 
 * However, no all the individual combinations are possible when a team is added to a group as these combinations must be in 
 * agreement with other teams' combinations.
 * For instance, a combination such as {{1,1,1},{1,1,1},{1,1,1},{1,1,1}} would not be possible since no all teams can
 * win. On the contrary, a value 1 for a team must induce a value -1 for another and a value 0 for a team must induce
 * a value 0 for another.
 * 
 * 
 * Description of the implementation given by this class:
 * 1.obtain all the possible combinations of results within a group by combining all the possible combinations of 
 * an individual team (10) in groups of 4 (number of teams per group). From a mathematical point of view, these are
 * combinations with repetition of 10 elements taken in groups of 4 (=715)
 * 2.filter combinations to get those that overall have an even number of 0s and the number of 1s equals
 * the number of -1s. This is a necessary condition for a combination to be valid 
 * 3.check each combination to see if the distribution of results among teams is valid. Since the
 * success of the algorithm to check the distribution of results depends on the order in which
 * the results of each team are given, the algorithm is applied as many times as the number of 
 * teams (each time the order of the teams is rotated to have a different team at the beginning)
 *
 */
public class SolutionByCountingCombinations implements GroupResultCalculator {
	
	/**
	 * Array representing the results of a game, namely: loss (-1), draw (0) and win (1)
	 */
	private static final Integer[] GAME_RESULTS = {-1,0,1};
	
	@Override
	public Set<GroupResult> calculateGroupResults(final int numTeamsPerGroup) {
						
		//If numTeamsPerGroup=4 then the number of combinations of the results of a team is 10:
		//{-1,-1,-1},{-1,-1,0},{-1,-1,1} and so on
		final Integer[][] individualResultCombinations = SingleTeamResult.calculateSingleTeamResults(numTeamsPerGroup);		
		
		//The number of combinations in a group when considering all the possible individual combinations of results
		//is a combination with repetition of individualResultCombinations elements taken in groups of numTeamsPerGroup.
		//For instance, when numTeamsPerGroup=4 then individualResultCombinations=10 and the number of all the possible
		//combinations is 715
		//All the invalid combinations must be taken away from the total number of possible combinations.
		
		ICombinatoricsVector<Integer[]> originalVector = Factory.createVector(individualResultCombinations);
		Generator<Integer[]> generator = Factory.createMultiCombinationGenerator(originalVector, numTeamsPerGroup);
		
		Set<GroupResult> groupResultCombinationsFiltered = generator.generateAllObjects().stream()
				  .map(ICombinatoricsVector::getVector)
				  .map(x -> IntegerMatrix.convertIntegerListToIntegerArray(x))
				  .map(x -> this.createGroupResult(x))
				  .filter(x -> x!=null)
				  .collect(Collectors.toSet());
		
		return groupResultCombinationsFiltered;

	}
	
	private GroupResult createGroupResult(Integer[][] matrix){
		
		try {
			return new GroupResult(matrix,true);
		} 
		catch (IllegalArgumentException e) {
			return null;
		}
	}
	
	private boolean filter1(int[][] combination){
		
		int[][] temp = new int[combination.length][];
		for(int j=0; j<combination.length; j++){
			temp[j] = Arrays.copyOf(combination[j], combination[j].length);
		}
		
		int nullValue = 2;
		for(int j=0; j<temp.length-1; j++){
			for(int result=-1; result<=1; result++){
				final int r = result;
				int count = (int) IntStream.of(temp[j]).filter(x -> x==r).count();
				if(count>0){
					int counter = 0;
					for(int k=j+1; k<temp.length; k++){
						for(int m=0; m<temp[k].length; m++){
							if(result==-temp[k][m]){
								counter++;
								temp[k][m] = nullValue;
								break;
							}
						}
						if(counter==count) break;
					}
					if(counter!=count) return false;
				}
			}
		}
		return true;
	}
	
	
	/*private void sortArrayByMostFrequentElement(Integer[][] matrix) {
		
		int mostFrequentElement = IntegerMatrix.findMostFrequentElement(matrix,false);
		int index = IntegerMatrix.indexOfFirstColumnWithElement(matrix, mostFrequentElement);
		IntegerArray.moveElementFromTo(matrix, index, 1);
		sortArrayByInducedOrder(matrix[0], mostFrequentElement);
		
	}*/

	

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
	
	private int[][] getIndividualResultCombinations(int numTeamsPerGroup){
					
		ICombinatoricsVector<Integer> originalVector = Factory.createVector(GAME_RESULTS);
		Generator<Integer> generator = Factory.createMultiCombinationGenerator(originalVector, numTeamsPerGroup-1);
		
		int[][] resultCombinations = new int[(int) generator.getNumberOfGeneratedObjects()][numTeamsPerGroup-1];		
		int combinationNumber = 0;
		for(ICombinatoricsVector<Integer> comb : generator){
			resultCombinations[combinationNumber++] = comb.getVector().stream().mapToInt(Integer::intValue).toArray();
		}
		
		return resultCombinations;
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
	
	
	

}
 