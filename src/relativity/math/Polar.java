/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package relativity.math;

import relativity.util.FourVector;
import relativity.util.MismatchedUnitException;
import relativity.util.Unit;

/**
 * Utility methods for moving between polar and euclidian coordinates.
 * @author Calvin
 */
public class Polar {
    private Polar() {}
    
    /**
     * returns a polar coordinate from a euclidian coordinate
     * @param v
     * @return 
     */
    public static FourVector fromEuclidean(FourVector v) {
        if(!v.getUnit().equals(Unit.distance))
            throw new MismatchedUnitException(v.getUnit(), Unit.distance);
        // TODO: check invertability, check coordinate ranges
        double r = v.threeNorm().getValue();
        double theta = Math.acos(v.getV3()/r);
        double phi = Math.atan2(v.getV2(), v.getV1());
        return new FourVector(v.getV0(), r, theta, phi, Unit.distancePolar);
    }
    
    /**
     * returns a euclidian coordinate from a polar one.
     * @param p
     * @return 
     */
    public static FourVector toEuclidean(FourVector p) {
        if(!p.getUnit().equals(Unit.distancePolar))
            throw new MismatchedUnitException(p.getUnit(), Unit.distancePolar);
        double t = p.getV0();
        double r = p.getV1();
        double theta = p.getV2();
        double phi = p.getV3();
        return new FourVector(
                t, r*Math.sin(theta)*Math.cos(phi),
                r*Math.sin(theta)*Math.sin(phi),
                r*Math.cos(theta), Unit.distance);
    }
//    
//    /**
//     * Adds two polar coordinates
//     * @param x
//     * @param y
//     * @return 
//     */
//    public static FourVector add(FourVector x, FourVector y) {
//        
//    }
}
