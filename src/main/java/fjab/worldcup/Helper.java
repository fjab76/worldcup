package fjab.worldcup;

import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

import fjab.worldcup.api.GroupResult;
import fjab.worldcup.util.IntegerMatrix;
import fjab.worldcup.util.SingleTeamResult;

/**
 * Class encapsulating methods used during the design of the main algorithm
 *
 */
public class Helper {
	
	/**
	 * Provides an upper limit to the number of results that can happen in a group with numTeams
	 * @param numTeams Number of teams in the group
	 * @return int Upper limit, an integer greater than 0
	 */
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
