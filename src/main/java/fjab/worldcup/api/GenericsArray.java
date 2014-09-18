package fjab.worldcup.api;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.IntFunction;

public interface GenericsArray {
	
	/**
	 * Convert a list of T objects in a list of U objects.
	 * @param from Original list
	 * @param func Function used to convert T objects into U objects
	 * @return List<U> List resulting of applying the function func to the objects of the original list
	 */
	<T, U> List<U> convertList(List<T> from, Function<T, U> func);

	/**
	 * Convert an array of T objects in an array of U objects.
	 * @param from Original array
	 * @param func unction used to convert T objects into U objects
	 * @param generator 
	 * @return U[] Array resulting of applying the function func to the objects of the original list
	 */
	<T, U> U[] convertArray(T[] from, Function<T, U> func, IntFunction<U[]> generator);
	
	/**
	 * Group the elements of an array taking as grouping criteria the given unary operator
	 * @param array Original array
	 * @param groupingFunction Unary operator used to determine the criteria to group the elements
	 * @return Map<K,List<T>> Map whose keys are the values resulting from applying the classification function to the input elements, 
	 * and whose corresponding values are Lists containing the input elements which map to the associated key under the classification function
	 */
	<T, K> Map<K,List<T>> groupElements(T[] array, Function<T,K> classificationFunction);
	
	/**
	 * Convert a list of T objects to an array of T objects 
	 * @param list Original list
	 * @return T[] Array containing the elements of the list
	 */
	<T> T[] convertListToArray(List<T> list, IntFunction<T[]> generator);
	
	/**
	 * Calculates the number of repetitions of the most frequent element in the array
	 * @param array Original array
	 * @return Maximum number of repetitions
	 */
	<T> int getGreatestNumberOfRepetitions(T[] array);
	
	/**
	 * Calculates the size of the longest list in the map
	 * @param map Map of objects to a list of objects
	 * @return int Size of the longest list in the map
	 */
	<K,T> int sizeOfLongestList(Map<K, List<T>> map);
	
	/**
	 * Concatenates array1 and array2
	 * @param <T>
	 * @param array1 Array 1
	 * @param array2 Array 2
	 * @return T[] New array of containing the elements of array1 plus the elements of array2
	 */
	<T> T[] concatArrays(T[] array1, T[] array2);
	
	/**
	 * Returns a new array with no null elements
	 * @param <T>
	 * @param array Array with null values
	 * @return T[] New array with no null values. If the original array contains x
	 * null values, the new array dimension is reference.length-x. If the original array does not contain
	 * null values, the new array returned contains the same values as the original one.
	 */
	<T> T[] removeNullElements(T[] array);
	
	<T> void moveElementFromTo(T[] matrix, int from, int to);


}
