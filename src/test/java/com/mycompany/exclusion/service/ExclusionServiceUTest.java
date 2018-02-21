/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.exclusion.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import redis.clients.jedis.Jedis;

import javax.ws.rs.core.Response;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ExclusionServiceUTest {

    @Mock
    private Jedis jedis;

    @InjectMocks
    private ExclusionService classInTest = new ExclusionService();

    @Test
    public void testNullSSNParameterSupplied() {
        Response response = classInTest.validate(null, "2018-01-30");
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void testEmptySSNParameterSupplied() {
        Response response = classInTest.validate("", "2018-01-30");
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void testWhitespaceSSNParameterSupplied() {
        Response response = classInTest.validate("  ", "2018-01-30");
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void testNullDateOfBirthParameterSupplied() {
        Response response = classInTest.validate("###-0000-###-001", null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void testEmptyDateOfBirthParameterSupplied() {
        Response response = classInTest.validate("###-0000-###-001", "");
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void testWhitespaceDateOfBirthParameterSupplied() {
        Response response = classInTest.validate("###-0000-###-001", "   ");
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void testValidateWithBlacklistedSsnAndDobShouldReturnBlacklistedResponse() {

        String ssn = "###-0000-###-0001";
        String dob = "2018-01-30";

        Mockito.when(jedis.hget("blacklist", ssn)).thenReturn(dob);

        Response response = classInTest.validate(ssn, "2018-01-30");

        assertEquals(200, response.getStatus());
        assertEquals("BLACKLISTED", response.getEntity());
    }

    @Test
    public void testValidateWithNonBlacklistedSsnAndDobShouldReturnNonBlacklistedResponse() {
        String ssn = "###-0000-###-0002";
        String dob = "2018-01-30";

        Mockito.when(jedis.hget("blacklist", ssn)).thenReturn(dob);

        Response response = classInTest.validate(ssn, "2018-02-30");

        assertEquals(200, response.getStatus());
        assertEquals("NOT BLACKLISTED", response.getEntity());
    }
}
