package fjab.worldcup.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MatrixUtil {
	
	public static int findScarcestElement(Integer[][] matrix){
		
		Map<Integer,List<Integer>> map = groupElements(matrix);
		
		//The initial value of the reduce function may be any value of matrix
		return map.keySet().stream()
		                   .reduce(matrix[0][0], (result,element) -> {if(map.get(element).size()<map.get(result).size()) result = element;return result;})
		                   .intValue();
		
	}
	
	public static Map<Integer,List<Integer>> groupElements(Integer[][] matrix){
		
		return Arrays.stream(matrix)
				  .flatMap(x -> Arrays.stream(x))
				  .collect(Collectors.groupingBy(Integer::intValue));		
	}
	
	public static int indexOfFirstColumnWithElement(Integer[][] matrix, int element){
		
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
	
	public static Integer[][] convertListIntegersToArray(List<Integer[]> list){
		
		Integer[][] combination = new Integer[list.size()][];
		
		for(int j=0; j<list.size(); j++){
			combination[j] = new Integer[list.get(j).length];
			for(int k=0; k<list.get(j).length; k++)
				combination[j][k] = list.get(j)[k];
		}
			
		return combination;
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
	
	public static Integer[] matchArrays(Integer[] reference, Integer[] arrayToBeMatched){
		
		if(reference.length!=arrayToBeMatched.length)
			return null;
		
		Integer[] arrayWithoutNullsOriginal = removeNullElements(reference);
		Integer[] arrayWithoutNulls = Arrays.copyOf(arrayWithoutNullsOriginal, arrayWithoutNullsOriginal.length);

		Arrays.sort(arrayWithoutNulls);
		Arrays.sort(arrayToBeMatched);
		
		if(arrayWithoutNulls.length==arrayToBeMatched.length){
			return Arrays.equals(arrayWithoutNulls, arrayToBeMatched)?reference:null;			
		}
		
		//Searching for a match

		int initialMatchingIndex = -1;
		int finalMatchingIndex = -1;
		for(int j=0,i=0; j<arrayToBeMatched.length && i<arrayWithoutNulls.length; j++){

			if(arrayToBeMatched[j].equals(arrayWithoutNulls[i])){
				i++;
				if(initialMatchingIndex==-1)
					initialMatchingIndex = j;
				finalMatchingIndex = j;
			}
			else if(!arrayToBeMatched[j].equals(arrayWithoutNulls[i]) && i>0){
				return null;
			}
		}
		
		if(initialMatchingIndex==-1 || finalMatchingIndex-initialMatchingIndex!=arrayWithoutNulls.length-1)
			return null;
		else{
			Integer[] preArray = Arrays.copyOfRange(arrayToBeMatched, 0, initialMatchingIndex);
			Integer[] postArray = Arrays.copyOfRange(arrayToBeMatched, finalMatchingIndex+1, arrayToBeMatched.length);
			Integer[] combinedArray = concatArrays(preArray,postArray);
			Arrays.sort(combinedArray);
			return concatArrays(arrayWithoutNullsOriginal,combinedArray);
		}		
	}

	public static Integer[] concatArrays(Integer[] preArray, Integer[] postArray) {
		
		Integer[] result = new Integer[preArray.length+postArray.length];
		System.arraycopy(preArray, 0, result, 0, preArray.length);
		System.arraycopy(postArray, 0, result, preArray.length, postArray.length);
		
		return result;
	}

	/**
	 * Returns a new array with no null elements
	 * @param reference Array of Integers with null values
	 * @return Integer[] New array of Integers with no null values. If the original array contains x
	 * null values, the new array dimension is reference.length-x. If the original array does not contain
	 * null values, the new array returned contains the same values as the original one.
	 */
	public static Integer[] removeNullElements(Integer[] reference) {
		
		int numNulls = countNumberOfNulls(reference);
		if(numNulls==0)
			return Arrays.copyOf(reference, reference.length);
		
		Integer[] newArray = new Integer[reference.length-numNulls];

		int k = 0;
		for(int j=0; j<reference.length; j++)
			if(reference[j]!=null)
				newArray[k++] = reference[j];
		
		return newArray;
	}
	
	public static int countNumberOfNulls(Integer[] reference) {
		
		int numNulls = 0;
		
		for(int j=0; j<reference.length; j++)
			if(reference[j]==null)
				numNulls++;
		
		return numNulls;
	}

}