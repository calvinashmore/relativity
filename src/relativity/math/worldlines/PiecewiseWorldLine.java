/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package relativity.math.worldlines;

import java.util.ArrayList;
import java.util.List;
import relativity.util.FourVector;
import relativity.util.Pair;
import relativity.util.Scalar;

/**
 *
 * @author Calvin
 */
public class PiecewiseWorldLine extends WorldLine {
    
    private List<Pair<Double, FourVector>> elements;

    public PiecewiseWorldLine(List<Pair<Double, FourVector>> elements) {
        
        // validate: 
        // ensure that no segment is FTL,
        // ensure that the points are in order
        // ensure that length is >= 2
        // ensure that t element of first is zero, and last is one.
        
        this.elements = new ArrayList(elements);
    }

    @Override
    public FourVector evaluate(double t) {
        if(t<0 || t>1)
            throw new IllegalArgumentException("t out of range: "+t);
        
        int span = 0;
        for (int i = 1; i < elements.size(); i++) {
            Pair<Double, FourVector> element = elements.get(i);
            if(element.getLeft() > t) {
                span = i-1;
                break;
            }
        }
        
        double t0 = elements.get(span).getLeft();
        double t1 = elements.get(span+1).getLeft();
        FourVector v0 = elements.get(span).getRight();
        FourVector v1 = elements.get(span+1).getRight();
        
        // interpolation value
        double alpha = t-t0/(t1-t0);
        
        return FourVector.add(
                FourVector.multiply(v0, 1-alpha), 
                FourVector.multiply(v1, alpha));
    }
}
