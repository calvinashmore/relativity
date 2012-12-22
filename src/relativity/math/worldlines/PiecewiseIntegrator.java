/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package relativity.math.worldlines;

import relativity.math.Constants;
import relativity.math.Polar;
import relativity.util.FourMatrix;
import relativity.util.FourVector;
import relativity.util.Scalar;
import relativity.util.Unit;

/**
 *
 * @author Calvin
 */
public class PiecewiseIntegrator implements Integrator {

    private int steps;

    public PiecewiseIntegrator(int steps) {
        this.steps = steps;
    }
    
    
    /**
     * Returns the elapsed proper time of an observer along this path, observed from an inertial reference frame.
     * @param line
     * @param metric
     * @return 
     */
    @Override
    public Scalar integrate(WorldLine line, Metric metric) {
        
        double tau = 0;
        //double dt = 1.0/steps;
        
        FourVector pos0 = line.evaluate(0);
        boolean posPolar = pos0.getUnit().isPolar();
        
        for (int i = 0; i < steps; i++) {
            //double t0 = (double)i/steps;
            double t1 = (double)(i+1)/(steps+1);
            
            // TODO: clean up so not evaluating twice
            //FourVector pos0 = line.evaluate(t0);
            FourVector pos1 = line.evaluate(t1);
            
            FourMatrix tensor;
            FourVector delta;
            
            if(posPolar == metric.isPolar()) {
                tensor = metric.metricTensor(pos0);
                delta = FourVector.subtract(pos1, pos0);
            } else if(metric.isPolar()) {
                FourVector polarPos0 = Polar.fromEuclidean(pos0);
                FourVector polarPos1 = Polar.fromEuclidean(pos1);
                tensor = metric.metricTensor(polarPos1);
                delta = FourVector.subtract(polarPos1, polarPos0);
            } else {
                FourVector nonpolarPos0 = Polar.toEuclidean(pos0);
                FourVector nonpolarPos1 = Polar.toEuclidean(pos1);
                tensor = metric.metricTensor(nonpolarPos1);
                delta = FourVector.subtract(nonpolarPos1, nonpolarPos0);
            }
            
            Scalar ds2 = FourMatrix.metricProduct(delta, tensor, delta);
            double dTau = Math.sqrt(-ds2.getValue())/Constants.c.getValue();
            tau += dTau;
            
            pos0 = pos1;
        }
        
        return new Scalar(tau, Unit.time);
    }
    
}
