package fjab.worldcup.util

import groovy.test.GroovyAssert

import java.util.function.Function
import java.util.function.UnaryOperator

import org.junit.Before
import org.junit.Test


class GenericsArrayImplTest extends GroovyAssert{
	
	//Identity operator
	private UnaryOperator<Integer> identityOperator = {it}
	//Parity operator
	private UnaryOperator<Integer> parityOperator = {it%2}
	//Radius extractor function
	private Function<Circle,Integer> radiusExtractorOperator = {it.radius}
	
	private GenericsArrayImpl objUnderTest;
	
	@Before
	public void setup(){
		objUnderTest = new GenericsArrayImpl();
	}
	
	@Test
	void resultingListContainsIntegers(){
		
		//given
		Circle c1 = new Circle(radius:1);
		Circle c2 = new Circle(radius:1);
		Circle c3 = new Circle(radius:2);
		List<Circle> array = [c1,c2,c3]
		
		//when
		def result = objUnderTest.convertList(array, radiusExtractorOperator)
		
		//then
		result.every({it instanceof Integer})

	}
	
	@Test
	void resultingArrayContainsIntegers(){
		
		//given
		Circle c1 = new Circle(radius:1);
		Circle c2 = new Circle(radius:1);
		Circle c3 = new Circle(radius:2);
		Circle[] array = [c1,c2,c3]
		
		//when
		def result = objUnderTest.convertArray(array, radiusExtractorOperator, {new Integer[array.length]})
		
		//then
		result.every({it instanceof Integer})

	}
	
	@Test
	void groupIntegersByValue(){
		
		//given
		Integer[] array = [1,2,3,2,3,2]
		
		//when
		def map = objUnderTest.groupElements(array, identityOperator)
		
		//then
		assert map[1].size==1
		assert map[2].size==3
		assert map[3].size==2
	}
	
	@Test
	void groupIntgersByParity(){
		
		//given
		Integer[] array = [1,2,3,4,5,6]
		
		//when
		def map = objUnderTest.groupElements(array, parityOperator)
		
		//then
		assert map.containsKey(0)
		assert map.containsKey(1)
		
		assert map[0]==[2,4,6]
		assert map[1]== [1,3,5]
	}
	
	@Test
	void groupCirclesByRadius(){
		
		//given
		Circle c1 = new Circle(radius:1);
		Circle c2 = new Circle(radius:1);
		Circle c3 = new Circle(radius:2);
		Circle[] array = [c1,c2,c3]
		
		//when
		def map = objUnderTest.groupElements(array, radiusExtractorOperator)
		
		//then
		assert map.containsKey(1)
		assert map.containsKey(2)
		
		assert map[1]==[c1,c2]
		assert map[2]== [c3]
	}
	
	@Test
	void greatestNumberOfRepetitionsIs3(){
		
		//given
		Integer[] array = [1,2,3,2,4,3,2]
		
		//when
		def repetitions = objUnderTest.getGreatestNumberOfRepetitions(array)
		
		//then
		assert 3==repetitions
	}
	
	@Test
	void resultIsAnArray(){
		
		//given
		List<Integer> list = [1,2,3]
		
		//when
		def resultingArray = objUnderTest.convertListToArray(list, {new Integer[list.size()]})
		
		//then		
		assert resultingArray instanceof Integer[]
		assertArrayEquals(resultingArray, [1,2,3] as Integer[])
	}
	
	@Test
	void moveColumnFrom1To3(){
		
		//given
		int[][] matrix = [[0,0],[1,1],[2,2],[3,3],[4,4]];
		
		//when
		objUnderTest.moveElementFromTo(matrix, 1, 3);
		
		//then
		assertArrayEquals([[0,0],[2,2],[3,3],[1,1],[4,4]] as int[][],matrix);
	}
	
	@Test
	void moveElementFrom1To3(){
		
		//given
		Integer[] matrix = [0,1,2,3,4];
		
		//when
		objUnderTest.moveElementFromTo(matrix, 1, 3);
		
		//then
		assertArrayEquals([0,2,3,1,4] as int[],matrix);
	}
	
	@Test
	void moveElementFrom3To0(){
		
		//given
		Integer[] matrix = [0,1,2,3,4];
		
		//when
		objUnderTest.moveElementFromTo(matrix, 3, 0);
		
		//then
		assertArrayEquals([3,0,1,2,4] as int[],matrix);
	}
	
	@Test
	void moveElementFrom3To3(){
		
		//given
		Integer[] matrix = [0,1,2,3,4];
		
		//when
		objUnderTest.moveElementFromTo(matrix, 3, 3);
		
		//then
		assertArrayEquals([0,1,2,3,4] as int[],matrix);
	}
	
	private class Circle{
		Integer radius;
	}

}
