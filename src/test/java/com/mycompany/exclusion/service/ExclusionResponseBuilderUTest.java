/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.exclusion.service;

import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.junit.Assert.*;

public class ExclusionResponseBuilderUTest {

    @Test
    public void testBuildBlackListedResponse() {
        Response response = ExclusionResponseBuilder.buildBlackListedResponse();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(ExclusionResponseBuilder.BLACKLISTED_DESCRIPTION,
            ((ExclusionResource) response.getEntity()).getStatusDescription());
    }

    @Test
    public void testBuildNotBlackListedResponse() {
        Response response = ExclusionResponseBuilder.buildNotBlackListedResponse();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(ExclusionResponseBuilder.NOT_BLACKLISTED_DESCRIPTION,
            ((ExclusionResource) response.getEntity()).getStatusDescription());
    }

}
