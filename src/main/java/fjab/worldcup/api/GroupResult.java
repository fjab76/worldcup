package fjab.worldcup.api;

import static fjab.worldcup.util.SingleTeamResult.GAME_RESULTS;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import fjab.worldcup.util.IntegerArray;
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
	
	public GroupResult(Integer[][] groupResult) {
		this(groupResult,true);
	}

	/**
	 * When an object is created, the following actions are carried out to make sure that a valid object is created:
	 * 1.validaton of the array dimensions
	 * 2.validation of the identity of the elements (the only values allowed are -1,0,1)
	 * 3.validation of the elements balance (for each element in the array there must be another element with the opposite value)
	 * 4.validation of the elements distribution (two elements of a column cannot have their partners in the same column)
	 * 5.normalisation of the elements in the array by sorting the columns and the elements in the columns as specified above)
	 * @param groupResult
	 */
	public GroupResult(Integer[][] groupResult, boolean strictMode) {
		
		if(strictMode){
			if(!checkArrayDimensions(groupResult)){
				throw new IllegalArgumentException("The array dimensions must be n x m with m=n-1");
			}
			
			if(!checkElementsIdentity(groupResult)){
				throw new IllegalArgumentException("The only elements allowed in the array are 1,0 and -1");
			}
			
			if(!checkElementsBalance(groupResult)){
				throw new IllegalArgumentException("The balance of elements is wrong");
			}
			
			if(!checkElementsDistribution(groupResult)){
				throw new IllegalArgumentException("The distribution of elements is wrong");
			}
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
	
	

	
	
	/**
	 * Checks that the matrix complies with the following rules:
	 * 1.all of the elements must be one of the values contained in GroupResults.GAME_RESULTS (-1,0,1)
	 * 2.the number of 0s must be even
	 * 3.the number of 1s must be equal to the number of -1s
	 * @param matrix Bidimensional array of Integers to be checked
	 * @return boolean True if the matrix complies with all the rules. False otherwise.
	 */
	/*public static boolean checkElements(Integer[][] matrix){

		//All elements must be one of these values: -1,0,1
		if(Stream.of(matrix).flatMap(x -> Stream.of(x)).filter(x -> (Arrays.binarySearch(GAME_RESULTS, x)==-1)).count()!=0){
			return false;
		}
		
		//The number of 0s must be even
		//The number of 1s must be equal to the number of -1s
		Map<Integer,List<Integer>> map = IntegerMatrix.groupElements(matrix);		
		int num0s = map.get(GAME_RESULTS[1])!=null?map.get(GAME_RESULTS[1]).size():0;	
		int num1s = map.get(GAME_RESULTS[2])!=null?map.get(GAME_RESULTS[2]).size():0;
		int numMinus1s = map.get(GAME_RESULTS[0])!=null?map.get(GAME_RESULTS[0]).size():0;
		
		if(!(num0s/2*2==num0s && num1s==numMinus1s)){
			throw new IllegalArgumentException("The balance of elements in the array is wrong");
		}
		
		return true;
	}*/
	
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
	
	/**
	 * Checks if the combination of results in a group passed as an argument is a valid combination. It is a valid combination if for
	 * every win (1) found in a team there is a loss (-1) in another and viceversa. Likewise, for every draw (0) in a team
	 * there must be a draw (0) in another.
	 * 
	 * @param combination 2-dimensional array representing the results in a group
	 * @return boolean True if the given combination is valid. Otherwise, false.
	 */
	static boolean checkElementsDistribution(Integer[][] originalCombination) {				
		
		//Copying original array into other object
		Integer[][] combination = new Integer[originalCombination.length][];
		for(int j=0; j<combination.length; j++){
			combination[j] = Arrays.copyOf(originalCombination[j], originalCombination[j].length);
		}				
		
		sortArray(combination);
		
		//Looping over elements of 0-th column
		for(int i=0; i<combination[0].length; i++){
			int searchedValue = -combination[0][i];
			
			int columnIndexUpperLimit = combination.length-1-i;
			int columnIndex = IntegerMatrix.findFirstColumnContainingValue(combination,searchedValue,columnIndexUpperLimit);
			//As soon as a result in one team fails to find its partner in another team, we can conclude that the combination
			//is not valid
			if(columnIndex==-1){
				return false;
			}
			
			IntegerArray.sortArrayStartingWithElement(combination[columnIndex],searchedValue);
			IntegerArray.moveElementFromTo(combination, columnIndex, columnIndexUpperLimit);
		}
		
		Integer[][] trimmedCombination = IntegerMatrix.trimMatrix(combination);
		if(trimmedCombination!=null){
			return checkElementsDistribution(trimmedCombination);
		}
		else{
			return combination[0][0].equals(-combination[1][0]);			
		}				
	}


	private static void sortArray(Integer[][] matrix) {
		
		Integer scarcestElement = IntegerMatrix.findLeastFrequentElement(IntegerMatrix.replaceValue(matrix,-1,1), true);
		int index;
		if(scarcestElement==null){
			
			Arrays.sort(matrix, (x,y) -> IntegerArray.getGreatestNumberOfRepetitions(x).compareTo(IntegerArray.getGreatestNumberOfRepetitions(y)));
			
			//index = MatrixUtil.findMostBalancedColumn(matrix);	
			scarcestElement = IntegerMatrix.findLeastFrequentElement(IntegerMatrix.replaceValue(matrix,-1,1), false);
		}
		else{
			index = IntegerMatrix.indexOfFirstColumnWithElement(matrix, scarcestElement);
			IntegerArray.moveElementFromTo(matrix, index, 0);
		}
				
		IntegerArray.sortArrayStartingWithElement(matrix[0], scarcestElement);		
	}

}
