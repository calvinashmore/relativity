/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package relativity.math.worldlines;

import java.util.ArrayList;
import java.util.List;
import relativity.math.FTLException;
import relativity.math.FourPosition;
import relativity.util.*;

/**
 *
 * @author Calvin
 */
public class PiecewiseWorldLine extends WorldLine {
    
    private List<Pair<Double, FourVector>> elements;
    
    /**
     * Construct a finite piecewise world line along the specified elements in euclidian coordinates.
     * Elements are expected to be in proper order: the interpolation value should be 0 for the first element and 1 for the last.
     * Vector components must have units of distance.
     * If any segment represents a faster than light interval, a FTLException will be thrown.
     * @param elements 
     */
    public PiecewiseWorldLine(List<Pair<Double, FourVector>> elements) {
        
        // validate: 
        // all elements are distance units
        // ensure that no segment is FTL,
        // ensure that the points are in order
        // ensure that length is >= 2
        // ensure that t element of first is zero, and last is one.
        
        for (Pair<Double, FourVector> pair : elements) {
            if(!pair.getRight().getUnit().equals(Unit.distance))
                throw new MismatchedUnitException(pair.getRight().getUnit(), Unit.distance);
        }
        
        if(elements.size() < 2)
            throw new IllegalArgumentException("Must have at least two elements in list of pairs");
        
        if(elements.get(0).getLeft() != 0 || elements.get(elements.size()-1).getLeft() != 1)
            throw new IllegalArgumentException("Piecewise list must begin at zero and end at one");
        
        double lastT = 0;
        for(int i=1;i<elements.size();i++) {
            double currentT = elements.get(i).getLeft();
            
            if(currentT <= lastT)
                throw new IllegalArgumentException("elements are not in order");
            
            FourVector v1 = elements.get(i-1).getRight();
            FourVector v2 = elements.get(i).getRight();
            
            FourVector delta = FourVector.subtract(v2, v1);
            if(FourPosition.minkowskiS2(delta).getValue() >= 0)
                throw new FTLException();
        }
        
        this.elements = new ArrayList(elements);
    }

    /**
     * note: t does not represent time here, it represents the interpolation value
     * @param t
     * @return 
     */
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
