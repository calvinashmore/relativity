/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package relativity.util;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Calvin
 */
public class UnitTest {
    
    @Test
    public void testMultiply() {
        
        assertEquals(Unit.distance, Unit.multiply(Unit.velocity, Unit.time));
        assertEquals(Unit.energy, Unit.multiply(Unit.mass,  Unit.multiply(Unit.velocity, Unit.velocity)));
        
        System.out.println(Unit.energy);
    }
    
    @Test
    public void testSqrt1() {
        assertEquals(Unit.energy, Unit.multiply(Unit.energy, Unit.energy).sqrt());
    }
    
    @Test(expected=MismatchedUnitException.class)
    public void testSqrt2() {
        Unit.energy.sqrt();
    }
}
