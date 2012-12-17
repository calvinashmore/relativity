/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package relativity.math.worldlines;

import relativity.math.Constants;
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
        double dt = 1.0/steps;
        
        for (int i = 0; i < steps; i++) {
            double t0 = (double)i/steps;
            double t1 = (double)(i+1)/steps;
            
            // TODO: clean up so not evaluating twice
            FourVector pos0 = line.evaluate(t0);
            FourVector pos1 = line.evaluate(t1);
            
            FourMatrix tensor = metric.metricTensor(pos0);
            FourVector delta = FourVector.subtract(pos1, pos0);
            Scalar ds2 = FourMatrix.metricProduct(delta, tensor, delta);
            double dTau = Math.sqrt(-ds2.getValue())/Constants.c.getValue();
            tau += dTau;
        }
        
        return new Scalar(tau, Unit.time);
    }
    
}
