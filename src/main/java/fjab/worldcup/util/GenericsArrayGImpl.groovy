package fjab.worldcup.util

import java.util.function.Function
import java.util.function.IntFunction
import java.util.stream.Collectors

import fjab.worldcup.api.GenericsArray

class GenericsArrayGImpl implements GenericsArray {

	@Override
	public <T, U> List<U> convertList(List<T> from, Function<T, U> func) {
		
		//using java constructs because from.collect(func) does not work as java Function does not
		//cast to groovy Closure
		from.stream().map(func).collect(Collectors.toList());
	}

	@Override
	public <T, U> U[] convertArray(T[] from, Function<T, U> func, IntFunction<U[]> generator) {
		
		//using java constructs because from.collect(func) does not work as java Function does not
		//cast to groovy Closure
		return Arrays.stream(from).map(func).toArray(generator);
	}

	@Override
	public <T, K> Map<K, List<T>> groupElements(T[] array, Function<T, K> classificationFunction) {
		
		//using java constructs because from.groupBy([classificationFunction]) does not work as java Function does not
		//cast to groovy Closure
		Arrays.stream(array).collect(Collectors.groupingBy(classificationFunction));
	}

	@Override
	public <T> T[] convertListToArray(List<T> list, IntFunction<T[]> generator) {
		
		list.toArray(generator)
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> int getGreatestNumberOfRepetitions(T[] array) {
		
		return sizeOfLongestList(groupElements(array,{it}));
	}

	@Override
	public <K, T> int sizeOfLongestList(Map<K, List<T>> map) {
		
		map.values().collect({it.size}).max()
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] concatArrays(T[] array1, T[] array2) {
		
		array1 + array2
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] removeNullElements(T[] array) {
		
		array.findAll({it!=null})
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
