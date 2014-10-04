package fjab.worldcup.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import fjab.worldcup.api.IntegerArray;

/**
 * Java implementation of IntegerArray
 */
public class IntegerArrayImpl implements IntegerArray{
		
	@Override
	public Integer[] convertIntToIntegerArray(int[] array){
		
		return IntStream.of(array).boxed().toArray(Integer[]::new);
	}
	

	/**
	 * Concatenates array1 and array2
	 * @param array1 Array of Integers
	 * @param array2 Array of Integers
	 * @return Integer[] New array of Integers containing the elements of array1 plus the elements of array2
	 */
	@Override
	public Integer[] concatArrays(Integer[] array1, Integer[] array2) {
		
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
	@Override
	public Integer[] removeNullElements(Integer[] array) {
		
		return Stream.of(array).filter(x -> x!=null).toArray(Integer[]::new);
	}

	public static void sortArrayStartingWithElement(Integer[] array, int startElement) {
		Arrays.sort(array, (o1,o2) -> {
			if(((Integer) o1).intValue()==startElement) return -1;
			else if(((Integer) o2).intValue()==startElement) return 1;
			else return ((Integer) o1).compareTo(((Integer) o2));
		});
	}

}
