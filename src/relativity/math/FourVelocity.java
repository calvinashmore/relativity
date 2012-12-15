/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package relativity.math;

import static relativity.math.Constants.c;
import relativity.util.*;

/**
 * utility methods for handling velocity vectors
 * @author Calvin
 */
public class FourVelocity {
    
    private FourVelocity() {}
    
    // this works for massive particles, but not zero-rest-mass particles?
    // what do we need to do to get those to work?
    
    public static FourVector fourVelocity(double vx, double vy, double vz) {
        FourVector v = new FourVector(gammaHelper(vx, vy, vz)*c.getValue(), vx, vy, vz, Unit.velocity);
        validate(v);
        return v;
    }
    
    // TODO: commutativity test
    public static FourVector add(FourVector a, FourVector b) {
        validate(a);
        validate(b);
        
        FourMatrix invAFrame = lorentzTransform(a.threeNegate());
        return FourMatrix.multiply(invAFrame, b);
        
        //        double g = gamma(a).getValue();
        //        double dot = FourVector.threeDot(a, b).getValue();
        //        double c2 = c.getValue()*c.getValue();
        //
        //        double A = 1/(1+dot/c2);
        //        double B = 1+(g*dot)/(c2*(1+g));
        //        return fourVelocity(
        //                A*(B*a.getV1()+b.getV1()/g),
        //                A*(B*a.getV2()+b.getV2()/g),
        //                A*(B*a.getV3()+b.getV3()/g));
    }
        
    
    // TODO: tests
    // test invariance of Lorentz constants: 
    public static FourMatrix lorentzTransform(FourVector boost) {
        validate(boost);
        Scalar gamma = gamma(boost);
        FourVector beta = FourVector.multiply(boost, c.invert());
        Scalar betaMag = beta.threeNorm();
        
        double g = gamma.getValue();
        double g1 = gamma.getValue() - 1;
        double bx = beta.getV1();
        double by = beta.getV2();
        double bz = beta.getV3();
        double bb = betaMag.getValue()*betaMag.getValue();
        
        return new FourMatrix(
                g,      -g*bx,          -g*by,          -g*bz, 
                -g*bx,  1+g1*bx*bx/bb,  g1*bx*by/bb,    g1*bx*bz/bb, 
                -g*by,  g1*bx*by/bb,    1+g1*by*by/bb,  g1*by*bz/bb, 
                -g*bz,  g1*bx*bz/bb,    g1*by*bz/bb,    1+g1*bz*bz/bb, 
                gamma.getUnit());
    }
    
    /**
     * throws a FTLException if the speed is faster than light.
     */
    public static Scalar gamma(FourVector v) {
        if(!v.getUnit().equals(Unit.velocity)) {
            throw new MismatchedUnitException(Unit.velocity, v.getUnit());
        }
        
        double g = gammaHelper(v.getV1(), v.getV2(), v.getV3());
        
        if(Double.isInfinite(g) || Double.isNaN(g))
            throw new FTLException();
        
        return new Scalar(g, Unit.none);
    }
    
    private static double gammaHelper(double vx, double vy, double vz) {
        double vSquare = vx*vx + vy*vy + vz*vz;
        double cSquare = c.getValue()*c.getValue();
        return 1/Math.sqrt(1-vSquare/cSquare);
    }
    
    /**
     * Checks the unit, ensures the velocity is less than c, and
     * ensures the gamma parameter is correct on v as a 4 velocity vector.
     */
    public static void validate(FourVector v) throws FTLException{
        if(!v.getUnit().equals(Unit.velocity))
            throw new MismatchedUnitException(Unit.velocity, v.getUnit());
        
        Scalar g = gamma(v);
        
        if(v.getV0() != g.getValue()*c.getValue()) {
            throw new IllegalStateException("proper time parameter is not correct on "+v);
        }
    }
}
