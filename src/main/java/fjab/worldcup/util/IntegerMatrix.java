package fjab.worldcup.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Utility class with operations on a bidimensional array of Integers
 */
public class IntegerMatrix {
	
	/**
	 * Do not let any client create an instance
	 */
	private IntegerMatrix(){}
	
	/**
	 * Returns the element of matrix with the least number of occurrences
	 * @param matrix Bidimensional array of Integers
	 * @return Integer The element of matrix with the least number of occurrences
	 */
	public static Integer findScarcestElement(Integer[][] matrix){
		
		Map<Integer,List<Integer>> map = groupElements(matrix);
		
		//The initial value of the reduce function may be any value of matrix
		return map.keySet().stream()
		                   .reduce(matrix[0][0], (result,element) -> {if(map.get(element).size()<map.get(result).size()) result = element;return result;});
		
	}
	
	/**
	 * Groups the occurrences of the same type: all the elements with the same value are added to a list and mapped to said value
	 * 
	 * @param matrix Bidimensional array of Integers whose elements are to be grouped
	 * @return Map<Integer,List<Integer>> Map of each different element in matrix to a list of all the elements with the same value
	 */
	public static Map<Integer,List<Integer>> groupElements(Integer[][] matrix){
		
		return Arrays.stream(matrix)
				  .flatMap(x -> Arrays.stream(x))
				  .collect(Collectors.groupingBy(Integer::intValue));		
	}
	
	/**
	 * Given a bidimensional array of Integers, this method returns the index of the first column that contains element
	 * 
	 * @param matrix Bidimensional array of Integers
	 * @param element Element of matrix to be searched
	 * @return int Index of the first column that contains element. If the element is not in matrix, returns -1
	 */
	public static int indexOfFirstColumnWithElement(Integer[][] matrix, Integer element){
		
		return IntStream.range(0, matrix.length)
		         .filter(x -> Stream.of(matrix[x]).filter(y -> y==element).count()>0)
		         .findFirst()
		         .orElse(-1);
					
	}
	
	public static Integer[][] convertToArray(List<int[]> list){
		
		Integer[][] combination = new Integer[list.size()][];
		
		for(int j=0; j<list.size(); j++){
			combination[j] = new Integer[list.get(j).length];
			for(int k=0; k<list.get(j).length; k++)
				combination[j][k] = list.get(j)[k];
		}
			
		return combination;
	}
	
	/**
	 * Copies the elements of a list of arrays into a matrix (bidimensional array)
	 * @param original Bidimensional array to be copied
	 * @return Integer[][] New bidimensional array containing a copy of each element in the list
	 */
	public static Integer[][] copyListArraysToMatrix(List<Integer[]> list){
		
		Integer[][] matrix = new Integer[list.size()][];
		
		for(int j=0; j<list.size(); j++){
			matrix[j] = new Integer[list.get(j).length];
			for(int k=0; k<list.get(j).length; k++)
				matrix[j][k] = list.get(j)[k];
		}
			
		return matrix;
	}
	
	
	public static <T> void moveElementFromTo(T[] matrix, int from, int to) {		
		
		if(from<0 || from>matrix.length-1 || to<0 || to>matrix.length-1)
			throw new IllegalArgumentException();
		
		T fromElement = matrix[from];
		
		if(to>from)	
			for(int j=from; j<to; j++)
				matrix[j] = matrix[j+1];
		else
			for(int j=from; j>to; j--)
				matrix[j] = matrix[j-1];
					
		matrix[to] = fromElement;
	}
	
	public static void putLinkedResultAtTheBeginning(int[] array, int m) {
		
		int linkedElement = array[m];
		for(int j=m; j>0; j--){
			array[j] = array[j-1];
		}
		array[0] = linkedElement;		
	}
	
	public static Integer[] convertIntToIntegerArray(int[] array){
		
		return (Integer[]) IntStream.of(array).boxed().toArray();
	}
	
	public static Integer[][] convertIntToIntegerMatrix(int[][] matrix){
		
		return (Integer[][]) Stream.of(matrix).map(x -> IntStream.of(x).boxed()).toArray();
	}
	
	public static Integer[] convertListToArray(List<Integer> list){
		
		Integer[] array = new Integer[list.size()];
		int index = 0;
		for(Integer obj : list)
			array[index++] = obj;
		
		return array;
	}
	
	public static int[][] convertIntegerToIntMatrix(Integer[][] matrix){
		
		int[][] array = new int[matrix.length][];
		
		for(int j=0; j<matrix.length; j++)
			array[j] = Arrays.stream(matrix[j]).mapToInt(x -> x).toArray();
		
		return array;
	}
	
	public static void sortArrayByInducedOrder(Integer[] array, int orderInducingElement) {
		Arrays.sort(array, (o1,o2) -> {
			if(((Integer) o1).intValue()==orderInducingElement) return -1;
			else if(((Integer) o2).intValue()==orderInducingElement) return 1;
			else return ((Integer) o1).compareTo(((Integer) o2));
		});
	}
	
	/**
	 * Concatenates array1 and array2
	 * @param array1 Array of Integers
	 * @param array2 Array of Integers
	 * @return Integer[] New array of Integers containing the elements of array1 plus the elements of array2
	 */
	public static Integer[] concatArrays(Integer[] array1, Integer[] array2) {
		
		Integer[] result = new Integer[array1.length+array2.length];
		System.arraycopy(array1, 0, result, 0, array1.length);
		System.arraycopy(array2, 0, result, array1.length, array2.length);
		
		return result;
	}

	/**
	 * Returns a new array with no null elements
	 * @param array Array of Integers with null values
	 * @return Integer[] New array of Integers with no null values. If the original array contains x
	 * null values, the new array dimension is reference.length-x. If the original array does not contain
	 * null values, the new array returned contains the same values as the original one.
	 */
	public static Integer[] removeNullElements(Integer[] array) {
		
		int numNulls = countNumberOfNulls(array);
		if(numNulls==0)
			return Arrays.copyOf(array, array.length);
		
		Integer[] newArray = new Integer[array.length-numNulls];

		int k = 0;
		for(int j=0; j<array.length; j++)
			if(array[j]!=null)
				newArray[k++] = array[j];
		
		return newArray;
	}
	
	
	public static int countNumberOfNulls(Integer[] array) {
		
		return (int) Arrays.stream(array)
			  .filter(x -> x==null)
			  .count();
	}
	
	/**
	 * Copies the elements of the argument passed in into a new object
	 * @param original Bidimensional array of Integers to be copied
	 * @return Integer[][] New bidimensional array containing a copy of each element of original
	 */
	public static Integer[][] deepCopy(Integer[][] original){
		
		Integer[][] copy = new Integer[original.length][];
		for(int j=0; j<original.length; j++){
			copy[j] = new Integer[original[j].length];
			for(int k=0; k<original[j].length; k++){
				copy[j][k] = original[j][k];
			}
		}
		return copy;
	}

}
