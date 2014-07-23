package fjab.worldcup;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.*;

/**
 * Immutable object representing the results in a group
 */
public final class GroupResult {
	
	private final int[][] results;
	//private final int[] points;
	
	public GroupResult(int[][] result){
		this.results = result; 
		
		//normalising results for each team by sorted them in ascending order according to the convention 
		//established by ProblemConstraints.INDIVIDUAL_TEAM_COMBINATIONS
		for(int j=0; j<results.length; j++){
			Arrays.sort(results[j]);
		}
		
		//normalising the order of the teams in ascending order according to the position of their result combinations
		//in ProblemConstraints.INDIVIDUAL_TEAM_COMBINATIONS				
		Arrays.sort(results, (o1,o2) ->  indexOfElement(o1,ProblemConstraints.INDIVIDUAL_TEAM_COMBINATIONS)-indexOfElement(o2,ProblemConstraints.INDIVIDUAL_TEAM_COMBINATIONS));
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
				
		/*StringBuilder builder = new StringBuilder();
		builder.append("GroupResult [results=");
		for(int j=0; j<results.length; j++){
			builder.append("{");
			for(int k=0; k<results[j].length; k++){
				builder.append(results[j][k]);
				if(k<results[j].length-1) builder.append(",");
			}
			builder.append("}");
			if(j<results.length-1) builder.append(",");
		}

		builder.append("]");
		return builder.toString();*/
	}
	
	
	

}
