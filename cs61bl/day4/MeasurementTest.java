
import junit.framework.TestCase;

import org.junit.Test;


public class MeasurementTest extends TestCase{

	public void testInit() {
		Measurement m = new Measurement(); 
		m.setMeasurement(0);
		assertTrue(m.getMeasurement() == 0);
	}
	public void testInvalidArgs() {
		Measurement m = new Measurement();  
		m.setMeasurement(-10);
		assertTrue(m.getMeasurement() <0);
	}
	public void testPlus(){
		Measurement m = new Measurement(); 
		Measurement m2 = new Measurement(10);
		m.setMeasurement(10);
		m.plus(m2);
	}
	public void testMinus(){
		Measurement m = new Measurement(); 
	}
	public void testMultiple(){
		Measurement m = new Measurement(); 
	}
	public void testToString(){
		Measurement m = new Measurement(); 
	}

}

