/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package relativity.math.worldlines;

import relativity.math.Constants;
import relativity.util.FourMatrix;
import relativity.util.FourVector;

/**
 * Simple metric that defines flat spacetime. The metric tensor at any point in spacetime is equal to the -1,1,1,1 Minkowski metric.
 * @author Calvin
 */
public class MinkowskiMetric implements Metric {

    @Override
    public boolean isPolar() {
        return false;
    }
    
    @Override
    public FourMatrix metricTensor(FourVector x) {
        return Constants.minkowskiMetric;
    }
    
}
