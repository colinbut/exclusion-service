/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.exclusion.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Service to offer validation of a user against a 'blacklist'.
 * Blacklisted users fail validation
 *
 * @author colin
 */
@Path("/exclusion")
public class ExclusionService {

    private static final Logger LOG = LoggerFactory.getLogger(ExclusionService.class);

    private Jedis jedis = new Jedis();

    /**
     * validates a user against a black list using date of birth and social security number as an identifier
     *
     * @param dob the user's date of birth (ISO 8601) format
     * @param ssn the user's social security number (US)
     * @return true if the user could be validated and is not blacklisted, false if the user is blacklisted and therefore failed
     * validation
     */
    @GET
    @Path("/validate/{ssn}/{dob}")
    public Response validate(@PathParam("ssn") String ssn, @PathParam("dob") String dateOfBirth) {

        if (StringUtils.isBlank(ssn) || StringUtils.isBlank(dateOfBirth)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        LOG.info("Validating whether SSN={} with DOB={} is blacklisted", ssn, dateOfBirth);

        boolean blackListed = false;

        String dob = jedis.hget("blacklist", ssn);

        if (dateOfBirth.equals(dob)) {
            blackListed = true;
            LOG.info("SSN={} with DOB={} is blacklisted");
        }

        jedis.close();

        Response blackListedResponse = Response.status(200).entity("BLACKLISTED").build();
        Response notBlackListedResponse = Response.status(200).entity("NOT BLACKLISTED").build();

        return blackListed ? blackListedResponse : notBlackListedResponse;
    }

}
