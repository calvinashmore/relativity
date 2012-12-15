/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package relativity.util;

import java.util.Objects;

/**
 * Immutable type.
 *
 * @author Calvin
 */
public class Scalar {

    private double value;
    private Unit unit;
    public static final Scalar one = new Scalar(1, Unit.none);

    public Scalar(double value, Unit unit) {
        this.value = value;
        this.unit = unit;
    }

    public Unit getUnit() {
        return unit;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }

    public static Scalar add(Scalar x, Scalar y) {
        if (!x.unit.equals(y.unit)) {
            throw new MismatchedUnitException(x.unit, y.unit);
        }
        return new Scalar(x.value + y.value, x.unit);
    }

    public static Scalar subtract(Scalar x, Scalar y) {
        if (!x.unit.equals(y.unit)) {
            throw new MismatchedUnitException(x.unit, y.unit);
        }
        return new Scalar(x.value - y.value, x.unit);
    }

    public static Scalar multiply(Scalar x, Scalar y) {
        return new Scalar(x.value * y.value, Unit.multiply(x.unit, y.unit));
    }

    public static Scalar divide(Scalar x, Scalar y) {
        return new Scalar(x.value / y.value, Unit.divide(x.unit, y.unit));
    }

    public Scalar invert() {
        return divide(one, this);
    }
    
    public Scalar sqrt() {
        return new Scalar(Math.sqrt(value), unit.sqrt());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Scalar other = (Scalar) obj;
//        if (Double.doubleToLongBits(this.value) != Double.doubleToLongBits(other.value)) {
//            return false;
//        }
        if(Math.abs(this.value - other.value) >= FourVector.EPSILON)
            return false;
        if (!Objects.equals(this.unit, other.unit)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.value) ^ (Double.doubleToLongBits(this.value) >>> 32));
        hash = 67 * hash + Objects.hashCode(this.unit);
        return hash;
    }
}
