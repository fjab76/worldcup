package fjab.worldcup;

import java.util.Set;
import java.util.stream.Collectors;

import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

import fjab.worldcup.api.GroupResult;
import fjab.worldcup.api.GroupResultCalculator;
import fjab.worldcup.util.IntegerMatrix;
import fjab.worldcup.util.SingleTeamResult;

/**
 * This implementation is based on the use of combinatorics: counting all possible combinations of results. 
 * 
 * The results of a game are represented by loss (-1), draw (0) and win (1).
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
 * win. On the contrary, a value 1 for a team induces a value -1 for another and a value 0 for a team induces
 * a value 0 for another.
 * 
 * 
 * Description of the implementation:
 * 1.obtain all the possible combinations of results within a group by combining all the possible combinations of 
 * an individual team (10) in groups of 4 (number of teams per group). From a mathematical standpoint, these are
 * combinations with repetition of 10 elements taken in groups of 4 (=715)
 * 2.create an object of type GroupResult with the remaining combinations. If the object is valid, keep the combination. Otherwise,
 * discard it. 
 *
 */
public class SolutionByCountingCombinations implements GroupResultCalculator {	
	
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
				  .map(x -> IntegerMatrix.copyListOfArraysToMatrix(x))
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
				  .map(x -> IntegerMatrix.copyListOfArraysToMatrix(x))
				  .filter(x -> GroupResult.checkElementsIdentity(x))
				  .filter(x -> GroupResult.checkElementsBalance(x))
				  .count();
		
		return upperLimit;
	}
}
 