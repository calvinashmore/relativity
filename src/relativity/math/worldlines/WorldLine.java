/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package relativity.math.worldlines;

import relativity.util.FourMatrix;
import relativity.util.FourVector;

/**
 * Defines a finite world line in spacetime
 * @author Calvin
 */
public abstract class WorldLine {
    
    /**
     * t between 0 and 1
     * @param t
     * @return 
     */
    public abstract FourVector evaluate(double t);
    
    public static WorldLine transform(final WorldLine reference, final FourMatrix transform) {
        return new WorldLine() {

            @Override
            public FourVector evaluate(double t) {
                return FourMatrix.multiply(transform, reference.evaluate(t));
            }
        };
    }
}
