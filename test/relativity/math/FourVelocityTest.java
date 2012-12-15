/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package relativity.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import static relativity.math.Constants.c;
import relativity.util.FourVector;

/**
 *
 * @author Calvin
 */
public class FourVelocityTest {
    
    // a unit that is << c
    private double u = c.getValue() / 1000;
    
    @Test(expected=FTLException.class)
    public void testFTLException() {
        FourVelocity.fourVelocity(2*c.getValue(), 0, 0);
    }
    
    @Test
    public void testAdditionZero() {
        FourVector a = FourVelocity.fourVelocity(1*u, 0, 0);
        FourVector b = a.threeNegate();
        FourVector z = FourVelocity.fourVelocity(0, 0, 0);
        
        assertEquals(z, FourVelocity.add(a, b));
    }
    
    @Test
    public void testAdditionSubLuminal() {
        FourVector a = FourVelocity.fourVelocity(c.getValue()*.9, 0, 0);
        assertTrue(FourVelocity.add(a, a).threeNorm().getValue() < c.getValue());
    }
    
    @Test
    public void testAdditionCommutativity() {
        FourVector a = FourVelocity.fourVelocity(c.getValue()*.9, 0, 0);
        FourVector b = FourVelocity.fourVelocity(c.getValue()*.3, 5*u, 0);
        
        assertEquals(FourVelocity.add(a,b), FourVelocity.add(b,a));
    }
    
}
