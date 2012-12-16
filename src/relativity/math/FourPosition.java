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
    
    /**
     * A 4 position represents an event in spacetime. The actual coordinate values depend on the reference frame in which the event is observed.
     * @param t
     * @param x
     * @param y
     * @param z
     * @return 
     */
    public static FourVector fourPosition(double t, double x, double y, double z) {
        return new FourVector(t * Constants.c.getValue(), x, y, z, Unit.distance);
    }
    
    /**
     * Returns the S^2 spacetime invariant of the position coordinate. This is invariant under Lorentz transformation.
     * When we take the difference between two FourPositions, the sign of the s2 value represents whether the displacement is spacelike (s2>0), timelike (s2<0), or lightlike (s2==0)
     * @param a
     * @return 
     */
    public static Scalar s2(FourVector a) {
        if(!a.getUnit().equals(Unit.distance))
            throw new MismatchedUnitException(a.getUnit(), Unit.distance);
        
        return new Scalar(
                -a.getV0()*a.getV0() + a.getV1()*a.getV1() + a.getV2()*a.getV2() + a.getV3()*a.getV3(), 
                Unit.multiply(Unit.distance,Unit.distance));
    }
    
}
