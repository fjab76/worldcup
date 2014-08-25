package fjab.worldcup;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MatrixUtil {
	
	public static Integer findScarcestElement(Integer[][] matrix, boolean strictMode){
		
		Map<Integer,List<Integer>> map = groupElements(matrix);
		
		if(strictMode){
			//Checking if there are elements with the same maximum frequency
			
			int[] numElementsArray = map.values().stream()
									.mapToInt(x -> x.size())
									.toArray();		
			
			int max = numElementsArray[0];
			for(int j=1; j<numElementsArray.length; j++){
				
				int numElements = (Integer) numElementsArray[j];
				if(numElements>max)
					max = numElements;
				else if(numElements==max){
					//There is no strict maximum
					return null;
				}
					
			}
		}
		
		//The initial value of the reduce function may be any value of matrix
		return map.keySet().stream()
		                   .reduce(matrix[0][0], (result,element) -> {if(map.get(element).size()<map.get(result).size()) result = element;return result;})
		                   .intValue();
		
	}
	
	public static int findMostFrequentElement(Integer[][] matrix){
		
		Map<Integer,List<Integer>> map = groupElements(matrix);
		
		//The initial value of the reduce function may be any value of matrix
		return map.keySet().stream()
		                   .reduce(matrix[0][0], (result,element) -> {if(map.get(element).size()>map.get(result).size()) result = element;return result;})
		                   .intValue();
		
	}
	
	public static Map<Integer,List<Integer>> groupElements(Integer[][] matrix){
		
		return Arrays.stream(matrix)
				  .flatMap(x -> Arrays.stream(x))
				  .collect(Collectors.groupingBy(Integer::intValue));		
	}
	
	public static Map<Integer,List<Integer>> groupElements(Integer[] matrix){
		
		return Arrays.stream(matrix)
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
	
	public static Integer findMostBalancedColumn(Integer[][] matrix){		
		
		//grouping different elements of each column
		List<Map<Integer, List<Integer>>> list = Arrays.stream(matrix)
											      .map(x -> groupElements(x))
											      .collect(Collectors.toList());
		
		//searching the column with the least repeat elements of the same type
		return IntStream.range(0, list.size()).
				reduce(0, (result,element) -> {if(getGreatestNumberOfRepetitions(list.get(element)).compareTo(getGreatestNumberOfRepetitions(list.get(result)))==-1) result=element;return result;});
	}
	
	public static Integer getGreatestNumberOfRepetitions(Integer[] array){		
		
		return getGreatestNumberOfRepetitions(groupElements(array));
	}
	
	public static Integer getGreatestNumberOfRepetitions(Map<Integer, List<Integer>> map){
		
		return map.values().stream()
					.mapToInt(x -> x.size())
					.max().getAsInt();

	}

}
