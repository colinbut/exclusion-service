/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.exclusion.service;

import org.apache.commons.lang3.StringUtils;

public class ParameterValidator {

    public static boolean isValidParameters(String ssn, String dateOfBirth){
        if (StringUtils.isBlank(ssn) || StringUtils.isBlank(dateOfBirth)) {
            return false;
        }

        return true;
    }
}
