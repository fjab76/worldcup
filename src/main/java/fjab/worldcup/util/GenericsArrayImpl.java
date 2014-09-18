package fjab.worldcup.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

import fjab.worldcup.api.GenericsArray;

public class GenericsArrayImpl implements GenericsArray{
	
	@Override
	public <T, U> List<U> convertList(List<T> from, Function<T, U> func){
	    return from.stream().map(func).collect(Collectors.toList());
	}

	@Override
	public <T, U> U[] convertArray(T[] from, Function<T, U> func, IntFunction<U[]> generator){
	    return Arrays.stream(from).map(func).toArray(generator);
	}
	
	@Override
	public <T, K> Map<K,List<T>> groupElements(T[] array, Function<T,K> classificationFunction){
		
		return Arrays.stream(array).collect(Collectors.groupingBy(classificationFunction));		
	}

	@Override
	public <T> T[] convertListToArray(List<T> list, IntFunction<T[]> generator) {
		
		return list.stream().toArray(generator);
	} 
	
	@Override
	public <K,T> int sizeOfLongestList(Map<K, List<T>> map){
		
		return map.values().stream()
					.mapToInt(x -> x.size())
					.max().getAsInt();
	}

	@Override
	public <T> int getGreatestNumberOfRepetitions(T[] array) {
		
		return sizeOfLongestList(groupElements(array,x -> x));
	}

	@Override
	public <T> T[] concatArrays(T[] array1, T[] array2) {

		throw new UnsupportedOperationException("It is not possible to create generic arrays in Java");
	}

	@Override
	public <T> T[] removeNullElements(T[] array) {

		throw new UnsupportedOperationException("It is not possible to create generic arrays in Java");
	}
	
	@Override
	public <T> void moveElementFromTo(T[] matrix, int from, int to) {		
		
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


}
