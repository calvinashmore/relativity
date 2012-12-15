/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package relativity.math;

import static relativity.math.Constants.c;
import relativity.util.*;

/**
 * utility methods for handling momentum vectors
 * @author Calvin
 */
public class FourMomentum {
    
    private FourMomentum() {}
    
    // this works for massive particles, but not zero-rest-mass particles?
    // what do we need to do to get those to work?
    
    // add and subtract should work linearly because of the gamma term
    public static FourVector fourMomentum(FourVector velocity, Scalar mass) {
        if(!mass.getUnit().equals(Unit.mass))
            throw new MismatchedUnitException(Unit.mass, mass.getUnit());
        FourVelocity.validate(velocity);
        
        double gamma = velocity.getV0()/c.getValue();
        
        return new FourVector(
                gamma*mass.getValue()*c.getValue(), 
                gamma*mass.getValue()*velocity.getV1(), 
                gamma*mass.getValue()*velocity.getV2(), 
                gamma*mass.getValue()*velocity.getV3(), Unit.momentum);
    }
    
    // this is Lorentz invariant
    public static Scalar properMass(FourVector momentum) {
        if(!momentum.getUnit().equals(Unit.momentum))
            throw new MismatchedUnitException(Unit.momentum, momentum.getUnit());
        
        Scalar metricProduct = FourMatrix.metricProduct(momentum, Constants.minkowskiMetric, momentum);
        return Scalar.divide(metricProduct.sqrt(), c);
    }
    
//    public static FourVector velocity(FourVector momentum) {
//        if(!momentum.getUnit().equals(Unit.momentum))
//            throw new MismatchedUnitException(Unit.momentum, momentum.getUnit());
//        
////        double gamma = momentum.getV0() / Constants.c.getValue();
////        return new FourVector(gamma, momentum.getV1()/gamma, momentum.getV2()/gamma, momentum.getV3()/gamma, Unit.velocity);
//    }
    
}
