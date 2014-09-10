package fjab.worldcup.api;

import java.util.List;
import java.util.Map;

/**
 * Defines operations on an unidimensional array of Integers
 */
public interface IntegerArray {
	
	Map<Integer,List<Integer>> groupElements(Integer[] array);
	
	Integer getMostFrequentNumberOfRepetitions(Integer[] array);
	
	Integer getMostFrequentNumberOfRepetitions(Map<Integer, List<Integer>> map);
	
	Integer[] convertIntToIntegerArray(int[] array);
	
	Integer[] convertListToArray(List<Integer> list);
	
	/**
	 * Concatenates array1 and array2
	 * @param array1 Array of Integers
	 * @param array2 Array of Integers
	 * @return Integer[] New array of Integers containing the elements of array1 plus the elements of array2
	 */
	Integer[] concatArrays(Integer[] array1, Integer[] array2);

}
