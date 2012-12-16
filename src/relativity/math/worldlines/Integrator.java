/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package relativity.math.worldlines;

import relativity.util.Scalar;

/**
 *
 * @author Calvin
 */
public interface Integrator {
    
    public Scalar integrate(WorldLine line, Metric metric);
}
