package fjab.worldcup.util;

import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

/**
 * Utility class with methods to work with the results of a single team (as opposed to the combination of the results
 * of all the teams in a group)
 */
public final class SingleTeamResult {
	
	/**
	 * Array representing the results of a game, namely: loss (-1), draw (0) and win (1)
	 */
	public static final Integer[] GAME_RESULTS = {-1,0,1};
	
	/**
     * Don't let anyone instantiate this class.
     */
	private SingleTeamResult(){}
	
	public static Integer[][] calculateSingleTeamResults(int numTeams) {
		
		ICombinatoricsVector<Integer> originalVector = Factory.createVector(GAME_RESULTS);
		Generator<Integer> generator = Factory.createMultiCombinationGenerator(originalVector, numTeams-1);
		
		Integer[][] teamResults = new Integer[(int) generator.getNumberOfGeneratedObjects()][numTeams-1];		
		int j = 0;
		for(ICombinatoricsVector<Integer> comb : generator)
			teamResults[j++] = comb.getVector().toArray(new Integer[0]);					
		
		return teamResults;
	}

}
