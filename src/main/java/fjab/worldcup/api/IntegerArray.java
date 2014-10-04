package fjab.worldcup.api;

import java.util.List;

/**
 * Defines operations on an unidimensional array of Integers
 */
public interface IntegerArray {
	
	Integer[] convertIntToIntegerArray(int[] array);	
	
	/**
	 * Concatenates array1 and array2
	 * @param array1 Array of Integers
	 * @param array2 Array of Integers
	 * @return Integer[] New array of Integers containing the elements of array1 plus the elements of array2
	 */
	Integer[] concatArrays(Integer[] array1, Integer[] array2);
	
	/**
	 * Returns a new array with no null elements
	 * @param array Array of Integers with null values
	 * @return Integer[] New array of Integers with no null values. If the original array contains x
	 * null values, the new array dimension is reference.length-x. If the original array does not contain
	 * null values, the new array returned contains the same values as the original one.
	 */
	Integer[] removeNullElements(Integer[] array);

}
