package fjab.worldcup.util

import fjab.worldcup.api.IntegerArray


/**
 * Groovy implementation of IntegerArray
 */
class IntegerArrayGImpl implements IntegerArray{
	
	@Override
	Map<Integer,List<Integer>> groupElements(Integer[] array){
		
		array.groupBy([{it}])
	}
	
	@Override
	Integer getMostFrequentNumberOfRepetitions(Integer[] array){
		
		return getMostFrequentNumberOfRepetitions(groupElements(array));
	}
	
	@Override
	Integer getMostFrequentNumberOfRepetitions(Map<Integer, List<Integer>> map){

		map.values().collect({it.size}).max()
	}
	
	@Override
	Integer[] convertIntToIntegerArray(int[] array){
		
		array.collect({Integer.valueOf(it)});
	}

	@Override
	public Integer[] convertListToArray(List<Integer> list) {
		
		list.collect({Integer.valueOf(it)});
	}

	@Override
	public Integer[] concatArrays(Integer[] array1, Integer[] array2) {
		
		array1 + array2
	}

}
