/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package relativity.math.worldlines;

import relativity.math.Constants;
import relativity.util.*;

/**
 *
 * @author Calvin
 */
public class SchwarzchildMetric implements Metric {

    private Scalar m;

    public SchwarzchildMetric(Scalar mass) {
        if(!mass.getUnit().equals(Unit.mass))
            throw new MismatchedUnitException(mass.getUnit(), Unit.mass);
        
        m = Scalar.divide(Scalar.multiply(mass, Constants.G),Constants.cSquared);
    }
    
    
    @Override
    public boolean isPolar() {
        return true;
    }

    /**
     * This is singular at r = 2m
     * @param x
     * @return 
     */
    @Override
    public FourMatrix metricTensor(FourVector x) {
        double r = x.getV1();
        double theta = x.getV2();
        double v = 1-2*m.getValue()/r;
        return FourMatrix.diagonal(-v, 1/v, r*r, Math.pow(r*Math.sin(theta),2), Unit.none);
    }
    
}
