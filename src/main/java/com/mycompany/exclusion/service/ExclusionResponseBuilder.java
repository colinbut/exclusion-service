/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.exclusion.service;

import javax.ws.rs.core.Response;

public class ExclusionResponseBuilder {


    public static Response buildBlackListedResponse() {
        ExclusionResource exclusionResource = new ExclusionResource();
        exclusionResource.setStatusDescription(ExclusionStatusDescriptionConstant.BLACKLISTED_DESCRIPTION);
        return Response.status(Response.Status.OK).entity(exclusionResource).build();
    }

    public static Response buildNotBlackListedResponse() {
        ExclusionResource exclusionResource = new ExclusionResource();
        exclusionResource.setStatusDescription(ExclusionStatusDescriptionConstant.NOT_BLACKLISTED_DESCRIPTION);
        return Response.status(Response.Status.OK).entity(exclusionResource).build();
    }
}
