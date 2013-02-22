package project.model;

import junit.framework.Assert;
import junit.framework.TestCase;

public class RangeTEST extends TestCase {

	
	public void testDoubleInRange() {
		
		double min = 10;
		
		double max = 100;
		Range testcase1 = new Range(min, max);
		
		double gen = testcase1.getDoubleInRange();
		System.out.println(gen);
		Assert.assertTrue(min < gen);
		Assert.assertTrue(max > gen);
		
	}
}
