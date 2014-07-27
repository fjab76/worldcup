package fjab.worldcup;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.*;

/**
 * Immutable object representing the results in a group.
 * Two group results are similar if they only differ in the order of the team results and in the order of the results for a
 * given team. To simplify the implementation of equals method, when a new object is created, the results are normalised
 * by sorting the results for each team in ascending order and sorting the teams in ascending order according to the position
 * of their results in the array teamResults.
 */
public final class GroupResult {
	
	private final int[][] results;
	//private final int[] points;
	
	/**
	 * 
	 * @param groupResult Array of integers representing the group results
	 * @param teamResults Array of integers containing all the possible results for an individual team. It is passed into the
	 * constructor to serve as a reference to normalise the order of the elements in groupResult.
	 */
	public GroupResult(int[][] groupResult, int[][] teamResults){
		
		//The elements of groupResult are stored in a new int[][] (to avoid that the program that creates the object keeps any
		//reference to the internal representation of this object)
		results = new int[groupResult.length][];
		
		for(int j=0; j<groupResult.length; j++){
			results[j] = new int[groupResult[j].length];
			for(int k=0; k<groupResult[j].length; k++){
				results[j][k] = groupResult[j][k]; 
			}
		}
		
		//normalising results for each team by sorting them in ascending order
		for(int j=0; j<results.length; j++){
			Arrays.sort(results[j]);
		}
		
		//normalising the order of the teams in ascending order according to the position of their result combinations
		//in teamResults				
		Arrays.sort(results, (o1,o2) ->  indexOfElement(o1,teamResults)-indexOfElement(o2,teamResults));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(results);		
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GroupResult other = (GroupResult) obj;
		if (!Arrays.deepEquals(results, other.results))
			return false;
		return true;
	}
	
	
	
	private int indexOfElement(int[] element, int[][] target) {
		
		for(int k=0; k<target.length; k++){
			boolean equals = true;
			for(int m=0; m<element.length; m++){
				equals = element[m] == target[k][m];
				if(!equals) break;					
			}
			if(equals) return k;
		}
		
		assert false : "this point should never be reached";
		return -1;
	}

	@Override
	public String toString() {
		return Stream.of(results)
			  .map(result -> IntStream.of(result).mapToObj(Integer::valueOf).map(s -> s.toString()).collect(Collectors.joining(",","{","}")))
			  .collect(Collectors.joining(","));					
	}
	
	
	

}
