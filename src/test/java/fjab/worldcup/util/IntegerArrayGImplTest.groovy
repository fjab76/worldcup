package fjab.worldcup.util

import groovy.test.GroovyAssert

import org.junit.Before
import org.junit.Test

class IntegerArrayGImplTest extends GroovyAssert {

	private IntegerArrayGImpl integerArrayImpl;
	
	@Before
	public void setup(){
		integerArrayImpl = new IntegerArrayGImpl();
	}
	
	@Test
	public void mostFrequentNumberOfRepetitionsIs3(){
		
		//given
		Integer[] array = [1,2,3,2,4,3,2]
		
		//when
		def repetitions = integerArrayImpl.getMostFrequentNumberOfRepetitions(array)
		
		//then
		assert 3==repetitions
	}
	
	@Test
	public void arrayElementsAreInteger(){
		
		//given
		int[] array = [1,2,3]
		
		//when
		def newArray = integerArrayImpl.convertIntToIntegerArray(array)
		
		//then
		assert newArray.findAll({it instanceof Integer}).size()==array.size()
		
	}
	
	@Test
	public void arrayElementsAreInteger2(){
		
		//given
		List<Integer> list = [1,2,3]
		
		//when
		def newArray = integerArrayImpl.convertListToArray(list)
		
		//then
		assert newArray.findAll({it instanceof Integer}).size()==list.size()
		
	}
	
	@Test
	public void arrayIs12345(){
		
		//given
		Integer[] array1 = [1,2,3]
		Integer[] array2 = [4,5]
		
		//when
		def newArray = integerArrayImpl.concatArrays(array1, array2)
		
		//then
		assertArrayEquals([1,2,3,4,5] as Integer[],newArray)
		
	}
}
