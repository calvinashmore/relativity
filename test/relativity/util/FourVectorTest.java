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
public class FourVectorTest {

    @Test
    public void testAdd() {
        FourVector a = new FourVector(1, 2, 3, 4, Unit.none);
        FourVector b = new FourVector(1, 1, 1, 1, Unit.none);
        FourVector c = new FourVector(2, 3, 4, 5, Unit.none);

        assertEquals(c, FourVector.add(a, b));
    }

    @Test(expected = MismatchedUnitException.class)
    public void testAdd2() {
        FourVector a = new FourVector(1, 2, 3, 4, Unit.mass);
        FourVector b = new FourVector(1, 1, 1, 1, Unit.velocity);
        FourVector.add(a, b);
    }
    
    @Test
    public void testMultiply() {
        FourVector a = new FourVector(1, 2, 3, 4, Unit.velocity);
        Scalar b = new Scalar(10, Unit.mass);
        FourVector c = new FourVector(10, 20, 30, 40, Unit.momentum);

        assertEquals(c, FourVector.multiply(a, b));
        System.out.println(c);
    }
}
