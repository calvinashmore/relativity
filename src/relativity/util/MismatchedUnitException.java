/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package relativity.util;

/**
 *
 * @author Calvin
 */
public class MismatchedUnitException extends RuntimeException {

    public MismatchedUnitException(String message) {
        super(message);
    }

    public MismatchedUnitException(Unit a, Unit b) {
        super("Mismatched units: " + a + ", " + b);
    }
}
