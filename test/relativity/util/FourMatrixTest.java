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
public class FourMatrixTest {
    
    private FourMatrix a = new FourMatrix(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, Unit.none);
    private FourMatrix identity = FourMatrix.diagonal(1, 1, 1, 1, Unit.none);
    
    private FourVector v = new FourVector(5, 8, 11, 34, Unit.velocity);
    
    @Test
    public void identityTest() {
        assertEquals(a, FourMatrix.multiply(identity, a));
        assertEquals(a, FourMatrix.multiply(a, identity));
        assertEquals(v, FourMatrix.multiply(identity, v));
    }
}
