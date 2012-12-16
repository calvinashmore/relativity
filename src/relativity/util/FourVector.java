/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package relativity.util;

import java.util.Arrays;
import java.util.Objects;

/**
 *
 * @author Calvin
 */
public class FourVector {
    public static final double EPSILON = 1e-4;
    
    private double v[] = new double[4];
    private Unit unit;

    public FourVector(double v0, double v1, double v2, double v3, Unit unit) {
        v[0] = v0;
        v[1] = v1;
        v[2] = v2;
        v[3] = v3;
        this.unit = unit;
    }
    
    // no accessors that expose v array
    public double getV0() {return v[0];}
    public double getV1() {return v[1];}
    public double getV2() {return v[2];}
    public double getV3() {return v[3];}

    public Unit getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return String.format("<%f,%f,%f,%f> %s",v[0],v[1],v[2],v[3],unit.toString());
    }
    
    public static Scalar fourDot(FourVector x, FourVector y) {
        assertEqualUnits(x, y);
        return new Scalar(x.v[0]*y.v[0]+x.v[1]*y.v[1]+x.v[2]*y.v[2]+x.v[3]*y.v[3], x.unit);
    }
    
    public static Scalar threeDot(FourVector x, FourVector y) {
        assertEqualUnits(x, y);
        return new Scalar(x.v[1]*y.v[1]+x.v[2]*y.v[2]+x.v[3]*y.v[3], x.unit);
    }
    
    /**
     * euclidean norm
     * @return 
     */
    public Scalar fourNorm() {
        return new Scalar(Math.sqrt(v[0]*v[0]+v[1]*v[1]+v[2]*v[2]+v[3]*v[3]), unit);
    }
    
    public Scalar threeNorm() {
        return new Scalar(Math.sqrt(v[1]*v[1]+v[2]*v[2]+v[3]*v[3]), unit);
    }
    
    public static FourVector add(FourVector x, FourVector y) {
        assertEqualUnits(x, y);
        return new FourVector(x.v[0]+y.v[0], x.v[1]+y.v[1], x.v[2]+y.v[2], x.v[3]+y.v[3], x.unit);
    }
    
    public static FourVector subtract(FourVector x, FourVector y) {
        assertEqualUnits(x, y);
        return new FourVector(x.v[0]-y.v[0], x.v[1]-y.v[1], x.v[2]-y.v[2], x.v[3]-y.v[3], x.unit);
    }
    
    public static FourVector multiply(FourVector x, Scalar y) {
        return new FourVector(x.v[0]*y.getValue(), x.v[1]*y.getValue(), x.v[2]*y.getValue(), x.v[3]*y.getValue(), Unit.multiply(x.unit, y.getUnit()));
    }
    public static FourVector multiply(FourVector x, double y) {
        return new FourVector(x.v[0]*y, x.v[1]*y, x.v[2]*y, x.v[3]*y, x.unit);
    }


    private static void assertEqualUnits(FourVector x, FourVector y) {
        if (!x.unit.equals(y.unit)) {
            throw new MismatchedUnitException(x.unit, y.unit);
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FourVector other = (FourVector) obj;
        double threshold = 0;
        for(int i=0;i<v.length; i++)
            threshold += Math.abs(this.v[i]-other.v[i]);
        if(threshold >= EPSILON)
            return false;

//        if (!Arrays.equals(this.v, other.v)) {
//            return false;
//        }
        if (!Objects.equals(this.unit, other.unit)) {
            return false;
        }
        return true;
    }

    // do I need to do something weird with hashCode due to epsilon based equality test?
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Arrays.hashCode(this.v);
        hash = 37 * hash + Objects.hashCode(this.unit);
        return hash;
    }

    /**
     * returns a four vector with the vector terms reversed
     * @return 
     */
    public FourVector threeNegate() {
        return new FourVector(v[0], -v[1], -v[2], -v[3], unit);
    }
}
