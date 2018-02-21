/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.exclusion.service;

import org.junit.Test;


import static org.junit.Assert.*;

public class ParameterValidatorUTest {

    @Test
    public void testNullSSNParameterSupplied() {
        boolean isValid = ParameterValidator.isValidParameters(null, "2018-01-30");
        assertFalse(isValid);
    }

    @Test
    public void testEmptySSNParameterSupplied() {
        boolean isValid = ParameterValidator.isValidParameters("", "2018-01-30");
        assertFalse(isValid);
    }

    @Test
    public void testWhitespaceSSNParameterSupplied() {
        boolean isValid = ParameterValidator.isValidParameters("  ", "2018-01-30");
        assertFalse(isValid);
    }

    @Test
    public void testNullDateOfBirthParameterSupplied() {
        boolean isValid = ParameterValidator.isValidParameters("###-0000-###-001", null);
        assertFalse(isValid);
    }

    @Test
    public void testEmptyDateOfBirthParameterSupplied() {
        boolean isValid = ParameterValidator.isValidParameters("###-0000-###-001", "");
        assertFalse(isValid);
    }

    @Test
    public void testWhitespaceDateOfBirthParameterSupplied() {
        boolean isValid = ParameterValidator.isValidParameters("###-0000-###-001", "   ");
        assertFalse(isValid);
    }

    @Test
    public void testCorrectFormatParameters() {
        boolean isValid = ParameterValidator.isValidParameters("###-0000-###-001", "2018-01-01");
        assertTrue(isValid);
    }
}
