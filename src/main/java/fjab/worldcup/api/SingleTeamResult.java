package fjab.worldcup.api;

import java.util.HashMap;
import java.util.Map;

import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

import fjab.worldcup.util.MatrixUtil;

/**
 * Singleton representing the results of a single team when considered as part of a group
 */
public final class SingleTeamResult {
	
	/**
	 * Array representing the results of a game, namely: loss (-1), draw (0) and win (1)
	 */
	public static final Integer[] GAME_RESULTS = {-1,0,1};
	
	/**
	 * Map of all the possible results of a single team according to the number of teams
	 * 
	 * For instance, if number of teams is 4 then the number of possible results for a team is 10:
	 * {-1,-1,-1},{-1,-1,0},{-1,-1,1} and so on
	 */
	private Map<Integer,Integer[][]> singleTeamResultsByGroupSize = new HashMap<>();
	
	private static final SingleTeamResult instance = new SingleTeamResult();
	
	private SingleTeamResult(){}
	
	public Integer[][] getSingleTeamResults(int numTeams){
		
		Integer[][] singleTeamResults = singleTeamResultsByGroupSize.get(numTeams);
		if(singleTeamResults==null){
			singleTeamResults = calculateSingleTeamResults(numTeams);
			singleTeamResultsByGroupSize.put(numTeams, singleTeamResults);
			return singleTeamResults;
		}
		else
			return singleTeamResults;
	}
	
	private Integer[][] calculateSingleTeamResults(int numTeams) {
		
		ICombinatoricsVector<Integer> originalVector = Factory.createVector(GAME_RESULTS);
		Generator<Integer> generator = Factory.createMultiCombinationGenerator(originalVector, numTeams-1);
		
		Integer[][] teamResults = new Integer[(int) generator.getNumberOfGeneratedObjects()][numTeams-1];		
		int j = 0;
		for(ICombinatoricsVector<Integer> comb : generator)
			teamResults[j++] = MatrixUtil.convertListToArray(comb.getVector());					
		
		return teamResults;
	}

	public static SingleTeamResult getInstance() {
		return instance;
	}

}
