package fjab.worldcup.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class GenericsArrayImplTest{
	
	//Identity operator
	private UnaryOperator<Integer> identityOperator = it -> it;
	//Parity operator
	private UnaryOperator<Integer> evenNumberOperator = it -> it%2;
	//Radius extractor function
	private Function<Circle,Integer> radiusExtractorOperator = it -> it.radius;
	
	private GenericsArrayImpl objUnderTest;
	
	@Before
	public void setup(){
		objUnderTest = new GenericsArrayImpl();
	}
	
	@Test
	public void resultingListContainsIntegers(){
		
		//given
		Circle c1 = new Circle(1);
		Circle c2 = new Circle(1);
		Circle c3 = new Circle(2);
		List<Circle> array = Arrays.asList(c1,c2,c3);
		
		//when
		List<Integer> result = objUnderTest.convertList(array, radiusExtractorOperator);
		
		//then
		Assert.assertTrue(result.stream().allMatch(x -> x instanceof Integer));

	}
	
	@Test
	public void resultingArrayContainsIntegers(){
		
		//given
		Circle c1 = new Circle(1);
		Circle c2 = new Circle(1);
		Circle c3 = new Circle(2);
		Circle[] array = {c1,c2,c3};
		
		//when
		Integer[] result = objUnderTest.convertArray(array, radiusExtractorOperator, Integer[]::new);
		

	}
	
	@Test
	public void groupIntegersByValue(){
		
		//given
		Integer[] array = {1,2,3,2,3,2};
		
		//when
		Map<Integer, List<Integer>> map = objUnderTest.groupElements(array, identityOperator);
		
		//then
		Assert.assertEquals(1, map.get(1).size());
		Assert.assertEquals(3, map.get(2).size());
		Assert.assertEquals(2, map.get(3).size());
	}
	
	@Test
	public void groupIntegersByParity(){
		
		//given
		Integer[] array = {1,2,3,4,5,6};
		
		//when
		Map<Integer, List<Integer>> map = objUnderTest.groupElements(array, evenNumberOperator);
		
		//then
		Assert.assertTrue(map.containsKey(0));
		Assert.assertTrue(map.containsKey(1));
		
		Assert.assertArrayEquals(new Integer[]{2,4,6}, map.get(0).toArray());
		Assert.assertArrayEquals(new Integer[]{1,3,5}, map.get(1).toArray());
	}
	
	@Test
	public void groupCirclesByRadius(){
		
		//given
		Circle c1 = new Circle(1);
		Circle c2 = new Circle(1);
		Circle c3 = new Circle(2);
		Circle[] array = {c1,c2,c3};
		
		//when
		Map<Integer, List<Circle>>  map = objUnderTest.groupElements(array, radiusExtractorOperator);
		
		//then
		Assert.assertTrue(map.containsKey(1));
		Assert.assertTrue(map.containsKey(2));
		
		Assert.assertArrayEquals(new Circle[]{c1,c2}, map.get(1).toArray());
		Assert.assertArrayEquals(new Circle[]{c3}, map.get(2).toArray());
	}
	
	@Test
	public void greatestNumberOfRepetitionsIs3(){
		
		//given
		Integer[] array = {1,2,3,2,4,3,2};
		
		//when
		int repetitions = objUnderTest.getGreatestNumberOfRepetitions(array);
		
		//then
		Assert.assertEquals(3, repetitions);
	}
	
	@Test
	public void resultIsAnArray(){
		
		//given
		List<Integer> list = Arrays.asList(1,2,3);
		
		//when
		Integer[] resultingArray = objUnderTest.convertListToArray(list, Integer[]::new);
		
		//then				
		Assert.assertArrayEquals(new Integer[]{1,2,3}, resultingArray);
	}
	
	@Test
	public void moveColumnFrom1To3(){
		
		//given
		int[][] matrix = {{0,0},{1,1},{2,2},{3,3},{4,4}};
		
		//when
		objUnderTest.moveElementFromTo(matrix, 1, 3);
		
		//then
		Assert.assertArrayEquals(new Integer[][]{{0,0},{2,2},{3,3},{1,1},{4,4}},matrix);
	}
	
	@Test
	public void moveElementFrom1To3(){
		
		//given
		Integer[] matrix = {0,1,2,3,4};
		
		//when
		objUnderTest.moveElementFromTo(matrix, 1, 3);
		
		//then
		Assert.assertArrayEquals(new Integer[]{0,2,3,1,4},matrix);
	}
	
	@Test
	public void moveElementFrom3To0(){
		
		//given
		Integer[] matrix = {0,1,2,3,4};
		
		//when
		objUnderTest.moveElementFromTo(matrix, 3, 0);
		
		//then
		Assert.assertArrayEquals(new Integer[]{3,0,1,2,4},matrix);
	}
	
	@Test
	public void moveElementFrom3To3(){
		
		//given
		Integer[] matrix = {0,1,2,3,4};
		
		//when
		objUnderTest.moveElementFromTo(matrix, 3, 3);
		
		//then
		Assert.assertArrayEquals(new Integer[]{0,1,2,3,4},matrix);
	}
	
	private class Circle{
		
		private Circle(Integer radius){
			this.radius = radius;
		}
		
		Integer radius;
	}

}
