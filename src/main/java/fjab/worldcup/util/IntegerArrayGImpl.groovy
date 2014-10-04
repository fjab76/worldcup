package fjab.worldcup.util

import fjab.worldcup.api.IntegerArray


/**
 * Groovy implementation of IntegerArray
 */
class IntegerArrayGImpl implements IntegerArray{
	
	@Override
	Integer[] convertIntToIntegerArray(int[] array){
		
		array.collect({it});
	}

	@Override
	public Integer[] concatArrays(Integer[] array1, Integer[] array2) {
		
		array1 + array2
	}

	@Override
	public Integer[] removeNullElements(Integer[] array) {
		
		array.findAll({it!=null})
	}

}
