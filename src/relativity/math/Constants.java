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
    
    /**
     * Treating this as an arbitrary constant for now...
     */
    public static Scalar c = new Scalar(10000, Unit.velocity);
    public static Scalar G = new Scalar(1e-5, Unit.divide(Unit.multiply(Unit.multiply(Unit.velocity, Unit.velocity),Unit.distance),Unit.mass));
    public static Scalar cSquared = Scalar.multiply(c, c);
    
    public static FourMatrix minkowskiMetric = FourMatrix.diagonal(-1, 1, 1, 1, Unit.none);
}
