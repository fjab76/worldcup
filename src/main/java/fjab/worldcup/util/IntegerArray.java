package fjab.worldcup.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Utility class with operations on a unidimensional array of Integers
 */
public class IntegerArray {
	
	/**
	 * Do not let any client create an instance
	 */
	private IntegerArray(){}
	
	public static Map<Integer,List<Integer>> groupElements(Integer[] array){
		
		return Arrays.stream(array)
				  .collect(Collectors.groupingBy(Integer::intValue));		
	}
	
	public static Integer getGreatestNumberOfRepetitions(Integer[] array){		
		
		return getGreatestNumberOfRepetitions(groupElements(array));
	}
	
	public static Integer getGreatestNumberOfRepetitions(Map<Integer, List<Integer>> map){
		
		return map.values().stream()
					.mapToInt(x -> x.size())
					.max().getAsInt();

	}

	public static Integer[] convertIntToIntegerArray(int[] array){
		
		return (Integer[]) IntStream.of(array).boxed().toArray();
	}

	public static Integer[] convertListToArray(List<Integer> list){
		
		Integer[] array = new Integer[list.size()];
		int index = 0;
		for(Integer obj : list)
			array[index++] = obj;
		
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
		
		int numNulls = IntegerArray.countNumberOfNulls(array);
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

}
