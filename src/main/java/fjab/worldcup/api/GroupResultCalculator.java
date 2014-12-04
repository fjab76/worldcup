package fjab.worldcup.api;

import java.util.Set;

/**
 * Interface with methods to calculate the results that can happen in a group
 */
public interface GroupResultCalculator {
	
	/**
	 * Calculates all the possible results in a group with numTeams
	 * @param numTeams Number of teams in the group
	 * @return Set<GroupResult> Set of objects representing the results of the group
	 * @see GroupResult
	 */
	Set<GroupResult> calculateGroupResults(int numTeams);
}
