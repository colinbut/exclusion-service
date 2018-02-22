/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.exclusion.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import redis.clients.jedis.Jedis;

import javax.ws.rs.core.Response;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ParameterValidator.class})
public class ExclusionServiceUTest {

    @Mock
    private Jedis jedis;

    @InjectMocks
    private ExclusionService classInTest = new ExclusionService();

    @Test
    public void testInvalidParametersSupplied() {

        PowerMockito.mockStatic(ParameterValidator.class);
        PowerMockito.when(ParameterValidator.isValidParameters(Matchers.anyString(), Matchers.anyString())).thenReturn(false);

        Response response = classInTest.validate("ssn", "dob");
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void testValidateWithBlacklistedSsnAndDobShouldReturnBlacklistedResponse() {

        String ssn = "###-0000-###-0001";
        String dob = "2018-01-30";

        PowerMockito.mockStatic(ParameterValidator.class);
        PowerMockito.when(ParameterValidator.isValidParameters(Matchers.anyString(), Matchers.anyString())).thenReturn(true);

        Mockito.when(jedis.hget("blacklist", ssn)).thenReturn(dob);

        Response response = classInTest.validate(ssn, "2018-01-30");

        assertEquals(200, response.getStatus());
        assertEquals("BLACKLISTED", ((ExclusionResource)response.getEntity()).getStatusDescription());
    }

    @Test
    public void testValidateWithNonBlacklistedSsnAndDobShouldReturnNonBlacklistedResponse() {
        String ssn = "###-0000-###-0002";
        String dob = "2018-01-30";

        PowerMockito.mockStatic(ParameterValidator.class);
        PowerMockito.when(ParameterValidator.isValidParameters(Matchers.anyString(), Matchers.anyString())).thenReturn(true);

        Mockito.when(jedis.hget("blacklist", ssn)).thenReturn(dob);

        Response response = classInTest.validate(ssn, "2018-02-30");

        assertEquals(200, response.getStatus());
        assertEquals("NOT BLACKLISTED", ((ExclusionResource)response.getEntity()).getStatusDescription());
    }
}
