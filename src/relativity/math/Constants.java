/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package relativity.math;

import relativity.util.FourMatrix;
import relativity.util.Scalar;
import relativity.util.Unit;

/**
 *
 * @author Calvin
 */
public class Constants {
    
    // static util class
    private Constants() {}
    
    public static Scalar c = new Scalar(10000, Unit.velocity);
    public static Scalar cSquared = Scalar.multiply(c, c);
    
    public static FourMatrix minkowskiMetric = FourMatrix.diagonal(-1, 1, 1, 1, Unit.none);
}
