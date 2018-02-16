# Exclusion Service

A microservice that encapsulates a blacklist of excluded users.

to run:

```bash
mvn -Djetty.http.port=[port number] jetty:run
```

where port number is a port you specify (by default its on port 1234 if you don't specify)

Built with:

- Java 6
- JAX-RS (Jersey as the implementation)
- Jedis (a Redis client java library)
- Jetty (a lightweight web server/container)

Maven build tool. 

Application is packaged up in a war file which then gets run in Jetty by using the maven jetty plugin.
