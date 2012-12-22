/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package relativity.math;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import relativity.util.FourVector;

/**
 *
 * @author Calvin
 */
public class PolarTest {

    @Test
    public void testPolarInvertability() {
        FourVector vectors[] = {
            FourPosition.fourPosition(10, 5, 4, 23),
            FourPosition.fourPosition(8, 1, 0, 0),
            FourPosition.fourPosition(0, 0, 0, 0),
        };

        for (FourVector v : vectors) {
            assertEquals(v, Polar.toEuclidean(Polar.fromEuclidean(v)));
        }
    }
}
