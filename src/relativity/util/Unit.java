/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package relativity.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author Calvin
 */
public class Unit {

    public static final Unit none = new Unit(Collections.<UnitPrim, Integer>emptyMap());
    public static final Unit distance = new Unit(UnitPrim.distance);
    public static final Unit mass = new Unit(UnitPrim.mass);
    public static final Unit time = new Unit(UnitPrim.time);
    public static final Unit velocity = divide(distance, time);
    public static final Unit acceleration = divide(velocity, time);
    public static final Unit momentum = multiply(velocity, mass);
    public static final Unit force = multiply(mass, acceleration);
    public static final Unit energy = multiply(force, distance);
    
    public static final Unit distancePolar = new Unit(UnitPrim.distance);
    static {
        distancePolar.polar = true;
    }

    private enum UnitPrim {

        distance("m"), mass("kg"), time("s");
        private String unit;

        private UnitPrim(String unit) {
            this.unit = unit;
        }

        public String getUnit() {
            return unit;
        }
    }
    private Map<UnitPrim, Integer> elements;
    private boolean polar = false;

    private Unit(UnitPrim prim) {
        elements = Collections.singletonMap(prim, 1);
    }
    
    private Unit(Map<UnitPrim, Integer> elements) {
        this.elements = elements;
    }

    public boolean isPolar() {
        return polar;
    }
    
    public static Unit multiply(Unit u1, Unit u2) {
        if(u1.isPolar() || u2.isPolar()) throw new MismatchedUnitException("can't multiply polar coords");
        Map<UnitPrim, Integer> elements = new HashMap<>(u1.elements);
        for (Map.Entry<UnitPrim, Integer> entry : u2.elements.entrySet()) {
            UnitPrim key = entry.getKey();
            int value = entry.getValue();
            if(elements.containsKey(key)) {
                
                int newValue = elements.get(key) + value;
                if(newValue == 0)
                    elements.remove(key);
                else
                    elements.put(key, newValue);
                
            } else {
                elements.put(key, value);
            }
        }
        return new Unit(elements);
    }
    
    public static Unit divide(Unit u1, Unit u2) {
        return multiply(u1, u2.invert());
    }
    
    public Unit invert() {
        if(isPolar()) throw new MismatchedUnitException("can't invert polar coords");
        Map<UnitPrim, Integer> newElements = new HashMap<>();
        for (Map.Entry<UnitPrim, Integer> entry : this.elements.entrySet()) {
            newElements.put(entry.getKey(), -entry.getValue());
        }
        return new Unit(newElements);
    }
    
    public Unit sqrt() {
        Map<UnitPrim, Integer> newElements = new HashMap<>();
        for (Map.Entry<UnitPrim, Integer> entry : this.elements.entrySet()) {
            int value = entry.getValue();
            if(value % 2 != 0)
                throw new MismatchedUnitException("cannot take a square root of a non-square unit "+this);
            newElements.put(entry.getKey(), entry.getValue()/2);
        }
        return new Unit(newElements);
    }
    
    public boolean isScalar() {
        return elements.isEmpty();
    }

    @Override
    public String toString() {
        
        if(isPolar()) return "polar";
        
        StringBuilder s = new StringBuilder();
        boolean once = false;
        for (Map.Entry<UnitPrim, Integer> entry : elements.entrySet()) {
            if(once)
                s.append(" ");
            
            s.append(entry.getKey().unit);
            if(entry.getValue() != 1) {
                s.append("^").append(entry.getValue());
            }
            
            once = true;
        }
        return s.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Unit other = (Unit) obj;
        if (!Objects.equals(this.elements, other.elements)) {
            return false;
        }
        if (this.polar != other.polar) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.elements);
        hash = 59 * hash + (this.polar ? 1 : 0);
        return hash;
    }
    
}
