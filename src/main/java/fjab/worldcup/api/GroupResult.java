package fjab.worldcup.api;

import static fjab.worldcup.util.SingleTeamResult.GAME_RESULTS;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import fjab.worldcup.util.IntegerMatrix;
import fjab.worldcup.util.SingleTeamResult;

/**
 * Immutable object representing the result of a group. 
 * In a more abstract way, this object can be defined as below.
 * 
 * OBJECT DEFINITION 
 * This object represents a matrix of n x m dimensions (with m=n-1) whose elements may be 1,0 or -1. 
 * There is no limit to the number of elements of the same type that may be present. There are 2 constraints:
 * 1.each element of a column must have a partner in some other column according to this rule: one element can only pair up 
 * with its negative (-1 with 1, 1 with -1 and 0 with 0) 
 * 2.two elements of a column cannot have their partners in the same column
 * 
 * DEFINITION OF EQUAL 
 * Two objects are equal if they only differ in the order of their columns. Two columns are equal if they only differ in the order of
 * their elements.
 * 
 * To simplify the implementation of "equals" method, when a new object is created the matrix is normalised by: 
 * 1.sorting the elements of its columns in ascending order and 
 * 2.sorting the columns in ascending order according to their position in the corresponding array in singleTeamResultsByGroupSize.
 */
public final class GroupResult {
	
	private final int[][] results;
	
	/**
	 * Map of all the possible results of a single team according to the number of teams
	 * 
	 * For instance, if number of teams is 4 then the number of possible results for a team is 10:
	 * {-1,-1,-1},{-1,-1,0},{-1,-1,1} and so on
	 * 
	 * These values are calculated as they are needed (lazy initialization) and cached for better performance
	 */
	private static Map<Integer,Integer[][]> singleTeamResultsByGroupSize = new HashMap<>();


	// private final int[] points;		

	/**
	 * When an object is created, the following actions are carried out to make sure that a valid object is created:
	 * 1.validaton of the array dimensions
	 * 2.validation of the identity of the elements (the only values allowed are -1,0,1)
	 * 3.validation of the elements balance (for each element in the array there must be another element with the opposite value)
	 * 4.normalisation of the elements in the array by sorting the columns and the elements in the columns as specified above)
	 * 
	 * Important: there is no validation of the elements distribution (after several attempts, no solution has been found). Therefore, it is up to the client making
	 * use of this class to ensure that the created object complies with this rule
	 * 
	 * @param groupResult
	 */
	public GroupResult(Integer[][] groupResult) {
		

		if(!checkArrayDimensions(groupResult)){
			throw new IllegalArgumentException("The array dimensions must be n x m with m=n-1");
		}
		
		if(!checkElementsIdentity(groupResult)){
			throw new IllegalArgumentException("The only elements allowed in the array are 1,0 and -1");
		}
		
		if(!checkElementsBalance(groupResult)){
			throw new IllegalArgumentException("The balance of elements is wrong");
		}

		/*
		 * The elements of groupResult are stored in a new int[][] (to avoid that the program that creates the object 
		 * keeps any reference to the internal representation of this object)
		 */
		int numTeams = groupResult.length;
		results = new int[numTeams][numTeams-1];

		for (int j = 0; j < numTeams; j++) 									
			for (int k = 0; k < numTeams-1; k++) 			
				results[j][k] = groupResult[j][k];

		
		// normalising results for each team by sorting them in ascending order
		for (int j = 0; j < results.length; j++) {
			Arrays.sort(results[j]);
		}

		// normalising the order of the teams in ascending order
		int[][] singleTeamResults = IntegerMatrix.convertIntegerToIntMatrix(getSingleTeamResults(numTeams));
		Arrays.sort(results, (o1, o2) -> indexOfElement(o1, singleTeamResults) - indexOfElement(o2, singleTeamResults));
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

		for (int k = 0; k < target.length; k++) {
			boolean equals = true;
			for (int m = 0; m < element.length; m++) {
				equals = element[m] == target[k][m];
				if (!equals)
					break;
			}
			if (equals)
				return k;
		}

		assert false : "this point should never be reached";
		return -1;
	}

	@Override
	public String toString() {
		return Stream
				.of(results)
				.map(result -> IntStream.of(result).mapToObj(Integer::valueOf).map(s -> s.toString())
						.collect(Collectors.joining(",", "{", "}"))).collect(Collectors.joining(","));
	}
	

	public static boolean checkElementsIdentity(Integer[][] matrix){
		
		//All elements must be one of these values: -1,0,1
		return Stream.of(matrix)
		      .flatMap(x -> Stream.of(x))
		      .filter(x -> (Arrays.binarySearch(GAME_RESULTS, x)==-1))
		      .count()==0;
	}
	
	public static boolean checkElementsBalance(Integer[][] matrix){
		
		//The number of 0s must be even
		//The number of 1s must be equal to the number of -1s
		Map<Integer,List<Integer>> map = IntegerMatrix.groupElements(matrix);		
		int num0s = map.get(GAME_RESULTS[1])!=null?map.get(GAME_RESULTS[1]).size():0;	
		int num1s = map.get(GAME_RESULTS[2])!=null?map.get(GAME_RESULTS[2]).size():0;
		int numMinus1s = map.get(GAME_RESULTS[0])!=null?map.get(GAME_RESULTS[0]).size():0;
		
		return (num0s/2*2==num0s && num1s==numMinus1s);
	}
	
	/**
	 * Checks that matrix dimensions are of the form m X n with n = m-1
	*  @param matrix Bidimensional array of Integers to be checked
	 * @return boolean True if the matrix passes the validation. False otherwise.
	 */
	public static boolean checkArrayDimensions(Integer[][] matrix){
		
		return Stream.of(matrix).filter(x -> x.length!=matrix.length-1).count()==0;
	}
	
	public Integer[][] getSingleTeamResults(int numTeams){
		
		Integer[][] singleTeamResults = singleTeamResultsByGroupSize.get(numTeams);
		if(singleTeamResults==null){
			singleTeamResults = SingleTeamResult.calculateSingleTeamResults(numTeams);
			singleTeamResultsByGroupSize.put(numTeams, singleTeamResults);
			return IntegerMatrix.deepCopy(singleTeamResults);
		}
		else
			return IntegerMatrix.deepCopy(singleTeamResults);
	}

}
