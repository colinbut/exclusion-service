# Exclusion Service

A microservice that encapsulates a blacklist of excluded users.

to run:

```bash
mvn -Djetty.port=[port number] jetty:run
```

Built with:

- Java 6
- JAX-RS (Jersey as the implementation)
- Jedis (a Redis client java library)
- Jetty (a lightweight web server/container)

Maven build tool. 

Application is packaged up in a war file which then gets run in Jetty by using the maven jetty plugin.
