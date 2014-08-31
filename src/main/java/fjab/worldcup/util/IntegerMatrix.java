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
	public static Integer[][] copyListOfArraysToMatrix(List<Integer[]> list){
		
		Integer[][] matrix = new Integer[list.size()][];
		
		for(int j=0; j<list.size(); j++){
			matrix[j] = new Integer[list.get(j).length];
			for(int k=0; k<list.get(j).length; k++)
				matrix[j][k] = list.get(j)[k];
		}
			
		return matrix;
	}
	
	
	public static Integer[][] convertIntToIntegerMatrix(int[][] matrix){
		
		return (Integer[][]) Stream.of(matrix).map(x -> IntStream.of(x).boxed()).toArray();
	}
	
	public static int[][] convertIntegerToIntMatrix(Integer[][] matrix){
		
		int[][] array = new int[matrix.length][];
		
		for(int j=0; j<matrix.length; j++)
			array[j] = Arrays.stream(matrix[j]).mapToInt(x -> x).toArray();
		
		return array;
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

	/**
	 * Finds the least frequent element in matrix. If there is more than one element in this situation and
	 * strictMode is true, it returns null.
	 * @param matrix Bidimensional array of Integers
	 * @param strictMode Flag to set up the search in strict mode. In strict mode, only one element can be
	 * the least frequent
	 * @return Integer Returns the least frequent element. Null if strictMode is true and there is more than
	 * one element being the least frequent element.
	 */
	public static Integer findLeastFrequentElement(Integer[][] matrix, boolean strictMode){
		
		Map<Integer,List<Integer>> map = groupElements(matrix);
		
		if(strictMode){
			
			//Checking if there is more than one element with the same minimum frequency			
			int[] numElementsArray = map.values().stream()
									.mapToInt(x -> x.size())
									.toArray();		
			
			int min = numElementsArray[0];
			for(int j=1; j<numElementsArray.length; j++){
				
				int numElements = (Integer) numElementsArray[j];
				if(numElements<min)
					min = numElements;
				else if(numElements==min){					
					return null;
				}					
			}
		}
		
		//The initial value of the reduce function may be any value of matrix
		return map.keySet().stream()
		                   .reduce(matrix[0][0], (result,element) -> {if(map.get(element).size()<map.get(result).size()) result = element;return result;})
		                   .intValue();
		
	}
	
	public static Integer findMostFrequentElement(Integer[][] matrix, boolean strictMode){
		
		Map<Integer,List<Integer>> map = groupElements(matrix);
		
		if(strictMode){
			
			//Checking if there is more than one element with the same minimum frequency			
			int[] numElementsArray = map.values().stream()
									.mapToInt(x -> x.size())
									.toArray();		
			
			int max = numElementsArray[0];
			for(int j=1; j<numElementsArray.length; j++){
				
				int numElements = (Integer) numElementsArray[j];
				if(numElements>max)
					max = numElements;
				else if(numElements==max){					
					return null;
				}					
			}
		}
		
		//The initial value of the reduce function may be any value of matrix
		return map.keySet().stream()
		                   .reduce(matrix[0][0], (result,element) -> {if(map.get(element).size()>map.get(result).size()) result = element;return result;})
		                   .intValue();
		
	}

	/**
	 * Finds the column with the least number of repeat elements. In case there is more than one column meeting
	 * the condition, the first one is returned
	 * @param matrix Bidimensional array of Integers
	 * @return Index of the column with the least number of repeat elements.
	 */
	public static Integer findColumnWithLeastRepeatElements(Integer[][] matrix){		
		
		//grouping different elements of each column
		List<Map<Integer, List<Integer>>> list = Arrays.stream(matrix)
											      .map(x -> IntegerArray.groupElements(x))
											      .collect(Collectors.toList());
		
		//searching the column with the least repeat elements of the same type
		return IntStream.range(0, list.size()).
				reduce(0, (result,element) -> {if(IntegerArray.getGreatestNumberOfRepetitions(list.get(element)).compareTo(IntegerArray.getGreatestNumberOfRepetitions(list.get(result)))==-1) result=element;return result;});
	}


	public static Integer[][] trimMatrix(Integer[][] matrix) {
					
		//Removing first column and first row
		
		//In this case, it is not possible to trim the matrix
		if(matrix.length<=2){
			return null;
		}
		
		Integer[][] trimmedMatrix = new Integer[matrix.length-1][];
		for(int j=0; j<trimmedMatrix.length; j++){
			trimmedMatrix[j] = Arrays.copyOfRange(matrix[j+1],1,matrix[j+1].length);
		}
		
		return trimmedMatrix;
	}


	public static int findFirstColumnContainingValue(Integer[][] matrix, int searchedValue, int columnIndexUpperLimit) {		
		
		int[] searchedValueOccurrences = IntStream.range(1, columnIndexUpperLimit+1)
								        .map(x -> (int)Stream.of(matrix[x])
								       		            .filter(y -> y.equals(searchedValue))
								       		            .count())
								        .toArray();
		
		int maxPosition = IntStream.range(0, searchedValueOccurrences.length)
		.reduce(0, (maximumPosition,element) -> {if(searchedValueOccurrences[element]>searchedValueOccurrences[maximumPosition]) maximumPosition=element;return maximumPosition;});
		
		return searchedValueOccurrences[maxPosition]==0?-1:maxPosition+1;
	}


	public static Integer[][] replaceValue(Integer[][] matrix, Integer oldValue, Integer newValue){
		
		Integer[][] replacement = new Integer[matrix.length][];
		for(int j=0; j<matrix.length; j++){
			replacement[j] = new Integer[matrix[j].length];
			for(int k=0; k<matrix[j].length; k++){
				replacement[j][k] = matrix[j][k].equals(oldValue)?newValue:matrix[j][k];
			}
		}
		return replacement;
	}

}
