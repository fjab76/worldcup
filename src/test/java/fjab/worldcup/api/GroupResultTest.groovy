package fjab.worldcup.api

import groovy.test.GroovyAssert

import org.junit.Ignore
import org.junit.Test

class GroupResultTest extends GroovyAssert {
	
	private final Integer[][] combination1 = [[-1,-1,0],[1,0,1],[1,0,1],[0,-1,-1]];
	
	@Ignore("not ready yet")
	@Test
	public void elementDistributionOfComb1IsOk(){
		
		assert GroupResult.checkElementsDistribution(combination1)
	}

}
