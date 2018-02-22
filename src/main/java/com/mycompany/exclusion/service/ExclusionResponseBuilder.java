/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.exclusion.service;

import javax.ws.rs.core.Response;

public class ExclusionResponseBuilder {

    public static final String BLACKLISTED_DESCRIPTION = "BLACKLISTED";
    public static final String NOT_BLACKLISTED_DESCRIPTION = "NOT BLACKLISTED";

    public static Response buildBlackListedResponse() {
        ExclusionResource exclusionResource = new ExclusionResource();
        exclusionResource.setStatusDescription(BLACKLISTED_DESCRIPTION);
        return Response.status(Response.Status.OK).entity(exclusionResource).build();
    }

    public static Response buildNotBlackListedResponse() {
        ExclusionResource exclusionResource = new ExclusionResource();
        exclusionResource.setStatusDescription(NOT_BLACKLISTED_DESCRIPTION);
        return Response.status(Response.Status.OK).entity(exclusionResource).build();
    }
}
