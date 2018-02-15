/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.exclusion.service;

/**
 * Service to offer validation of a user against a 'blacklist'.
 * Blacklisted users fail validation
 *
 * @author colin
 */
public interface ExclusionService {

    /**
     * validates a user against a black list using date of birth and social security number as an identifier
     *
     * @param dob the user's date of birth (ISO 8601) format
     * @param ssn the user's social security number (US)
     * @return true if the user could be validated and is not blacklisted, false if the user is blacklisted and therefore failed
     * validation
     */
    boolean validate(String dob, String ssn);
}
