/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.exclusion.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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

    private Jedis jedis;

    @PostConstruct
    private void setUp(){
        String host = System.getenv("REDIS_HOST");
        String port = System.getenv("REDIS_PORT");

        LOG.info("Connecting to Redis host={} on port={}", host, port);

        jedis = new Jedis(host, Integer.parseInt(port));
    }

    @PreDestroy
    private void tearDown() {
        if (jedis.isConnected()) {
            LOG.info("Destroying Jedis: Closing Jedis client resource");
            jedis.close();
        } else {
            LOG.info("Destroying Jedis: Jedis client is not connected to Redis Server");
        }
    }

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

        if (isNotValidParameters(ssn, dateOfBirth)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        LOG.info("Validating whether SSN={} with DOB={} is blacklisted", ssn, dateOfBirth);

        boolean blackListed = false;

        String dob = jedis.hget("blacklist", ssn);

        if (dateOfBirth.equals(dob)) {
            blackListed = true;
            LOG.info("SSN={} with DOB={} is blacklisted");
        }

        return blackListed ? ExclusionResponseBuilder.buildBlackListedResponse()
            : ExclusionResponseBuilder.buildNotBlackListedResponse();
    }

    private boolean isNotValidParameters(String ssn, String dateOfBirth) {
        return !ParameterValidator.isValidParameters(ssn, dateOfBirth);
    }

}
