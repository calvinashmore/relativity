/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package relativity.math;

import relativity.util.FourMatrix;
import relativity.util.FourVector;
import relativity.util.MismatchedUnitException;
import relativity.util.Scalar;
import relativity.util.Unit;

/**
 *
 * @author Calvin Ashmore
 */
public class FourPosition {
    private FourPosition() {}
    
    public static FourVector fourPosition(double t, double x, double y, double z) {
        return new FourVector(t * Constants.c.getValue(), x, y, z, Unit.distance);
    }
    
    public static Scalar displacement(FourVector a, FourVector b) {
        if(!a.getUnit().equals(Unit.distance))
            throw new MismatchedUnitException(a.getUnit(), Unit.distance);
        if(!b.getUnit().equals(Unit.distance))
            throw new MismatchedUnitException(b.getUnit(), Unit.distance);
        
        return FourMatrix.metricProduct(a, Constants.minkowskiMetric, b);
    }
}
