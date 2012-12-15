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
public class FourMatrix {
    private double v[] = new double[16];
    private Unit unit;
    
    public FourMatrix(
            double v00, double v01, double v02, double v03,
            double v10, double v11, double v12, double v13,
            double v20, double v21, double v22, double v23,
            double v30, double v31, double v32, double v33,
            Unit unit) {
        v[0] = v00; 
        v[1] = v01;
        v[2] = v02;
        v[3] = v03;
        v[4] = v10; 
        v[5] = v11;
        v[6] = v12;
        v[7] = v13;
        v[8] = v20; 
        v[9] = v21;
        v[10] = v22;
        v[11] = v23;
        v[12] = v30; 
        v[13] = v31;
        v[14] = v32;
        v[15] = v33;
        this.unit = unit;
    }
    
    double getElement(int i, int j) {
        if(i<0 || i>=4 || j<0 || j>=4)
            throw new ArrayIndexOutOfBoundsException();
        return v[i+4*j];
    }

    public Unit getUnit() {
        return unit;
    }
    
    public static FourMatrix diagonal(double v00, double v11, double v22, double v33, Unit unit) {
        return new FourMatrix(v00, 0, 0, 0, 0, v11, 0, 0, 0, 0, v22, 0, 0, 0, 0, v33, unit);
    }
    
    public static Scalar metricProduct(FourVector u, FourMatrix a, FourVector v) {
        FourVector vv = multiply(a,v);
        return new Scalar(
                u.getV0()*vv.getV0() + u.getV1()*vv.getV1() + u.getV2()*vv.getV2() + u.getV3()*vv.getV3(), 
                Unit.multiply(u.getUnit(), Unit.multiply(a.getUnit(), v.getUnit())));
    }
    
    public static FourMatrix multiply(FourMatrix a, FourMatrix b) {
        return new FourMatrix(
                multiplyHelper(a, b, 0, 0), multiplyHelper(a, b, 1, 0), multiplyHelper(a, b, 2, 0), multiplyHelper(a, b, 3, 0),
                multiplyHelper(a, b, 0, 1), multiplyHelper(a, b, 1, 1), multiplyHelper(a, b, 2, 1), multiplyHelper(a, b, 3, 1),
                multiplyHelper(a, b, 0, 2), multiplyHelper(a, b, 1, 2), multiplyHelper(a, b, 2, 2), multiplyHelper(a, b, 3, 2),
                multiplyHelper(a, b, 0, 3), multiplyHelper(a, b, 1, 3), multiplyHelper(a, b, 2, 3), multiplyHelper(a, b, 3, 3),
                Unit.multiply(a.getUnit(), b.getUnit()));
    }
    
    public static FourMatrix multiply(Scalar x, FourMatrix a) {
        return new FourMatrix(
                x.getValue()*a.getElement(0,0), x.getValue()*a.getElement(1,0), x.getValue()*a.getElement(2,0), x.getValue()*a.getElement(3,0), 
                x.getValue()*a.getElement(0,1), x.getValue()*a.getElement(1,1), x.getValue()*a.getElement(2,1), x.getValue()*a.getElement(3,1), 
                x.getValue()*a.getElement(0,2), x.getValue()*a.getElement(1,2), x.getValue()*a.getElement(2,2), x.getValue()*a.getElement(3,2), 
                x.getValue()*a.getElement(0,3), x.getValue()*a.getElement(1,3), x.getValue()*a.getElement(2,3), x.getValue()*a.getElement(3,3), 
                Unit.multiply(x.getUnit(), a.getUnit()));
    }
    
    public static FourVector multiply(FourMatrix a, FourVector v) {
        return new FourVector(
                a.getElement(0, 0)*v.getV0() + a.getElement(1, 0)*v.getV1() + a.getElement(2, 0)*v.getV2() + a.getElement(3, 0)*v.getV3(),
                a.getElement(0, 1)*v.getV0() + a.getElement(1, 1)*v.getV1() + a.getElement(2, 1)*v.getV2() + a.getElement(3, 1)*v.getV3(),
                a.getElement(0, 2)*v.getV0() + a.getElement(1, 2)*v.getV1() + a.getElement(2, 2)*v.getV2() + a.getElement(3, 2)*v.getV3(),
                a.getElement(0, 3)*v.getV0() + a.getElement(1, 3)*v.getV1() + a.getElement(2, 3)*v.getV2() + a.getElement(3, 3)*v.getV3(),
                Unit.multiply(a.getUnit(), v.getUnit()));
    }
    
    private static double multiplyHelper(FourMatrix a, FourMatrix b, int i, int j) {
        return  a.getElement(i, 0)*b.getElement(0, j) +
                a.getElement(i, 1)*b.getElement(1, j) +
                a.getElement(i, 2)*b.getElement(2, j) +
                a.getElement(i, 3)*b.getElement(3, j);
    }

    @Override
    public String toString() {
        return String.format(
                "[%f %f %f %f]\n" +
                "[%f %f %f %f]\n" +
                "[%f %f %f %f]\n" +
                "[%f %f %f %f] %s\n", 
                v[0], v[1], v[2], v[3],
                v[4], v[5], v[6], v[7],
                v[8], v[9], v[10], v[11],
                v[12], v[13], v[14], v[15], unit.toString());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FourMatrix other = (FourMatrix) obj;
        
        double threshold = 0;
        for(int i=0;i<v.length; i++)
            threshold += Math.abs(this.v[i]-other.v[i]);
        if(threshold >= FourVector.EPSILON)
            return false;
        
//        if (!Arrays.equals(this.v, other.v)) {
//            return false;
//        }
        if (!Objects.equals(this.unit, other.unit)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Arrays.hashCode(this.v);
        hash = 23 * hash + Objects.hashCode(this.unit);
        return hash;
    }
}
