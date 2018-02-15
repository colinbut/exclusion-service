/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.exclusion.endpoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/exclusion")
public class ExclusionRestEndpoint {

    @GET
    @Path("/users")
    public Response getExcludedUsers() {
        return Response.status(200).entity("Success").build();
    }

}
