/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package relativity.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import static relativity.math.Constants.c;
import relativity.math.FourPosition;
import relativity.util.FourMatrix;
import relativity.util.FourVector;
import relativity.util.Unit;

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
    
    /**
     * Einsteinian velocitiy addition is only commutative when velocities are parallel.
     */
    @Test
    public void testAdditionCommutativity() {
        FourVector a = FourVelocity.fourVelocity(c.getValue()*.9, 0, 0);
        FourVector b = FourVelocity.fourVelocity(c.getValue()*.3, 5*u, 0);
        FourVector d = FourVelocity.fourVelocity(c.getValue()*.5, 0, 0);
        
        assertNotSame(FourVelocity.add(a,b), FourVelocity.add(b,a));
        assertEquals(FourVelocity.add(a,d), FourVelocity.add(d,a));
    }
    
    @Test 
    public void testLorentzInvertable() {
        FourVector a = FourVelocity.fourVelocity(c.getValue()*.3, 5*u, 0);
        FourVector b = a.threeNegate();
        
        FourMatrix lA = FourVelocity.lorentzTransform(a);
        FourMatrix lB = FourVelocity.lorentzTransform(b);
        
        assertEquals(FourMatrix.multiply(lA, lB), FourMatrix.diagonal(1, 1, 1, 1, Unit.none));
    }
    
    @Test
    public void testLorentzS2Invariant() {
        FourVector a = FourPosition.fourPosition(5, c.getValue()*.9, 0, 0);
        
        FourVector v = FourVelocity.fourVelocity(c.getValue()*.1, 0, 8*u);
        FourMatrix lV = FourVelocity.lorentzTransform(v);
        
        FourVector a1 = FourMatrix.multiply(lV, a);
        
        assertEquals(FourPosition.minkowskiS2(a), FourPosition.minkowskiS2(a1));
    }
}
