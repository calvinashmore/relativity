/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package relativity.math.worldlines;

import relativity.util.FourMatrix;
import relativity.util.FourVector;

/**
 *
 * @author Calvin
 */
public interface Metric {
    
    /**
     * Takes a point in timespace and returns the metic tensor at that point.
     * g = metricTensor(x);
     * ds^2 = FourMatrix.metricProduct(dx,g,dx);
     * @param x must have unit distance
     * @return 
     */
    public FourMatrix metricTensor(FourVector x);
}
