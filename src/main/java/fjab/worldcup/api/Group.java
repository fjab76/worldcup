package fjab.worldcup.api;

import java.util.Set;

/**
 * Interface providing functionality to deal with groups
 *
 */
public interface Group {
	
	Set<GroupResult> calculateGroupResults(int numTeamsPerGroup);
}
