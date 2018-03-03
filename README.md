# Exclusion Service

[![Build Status](https://travis-ci.org/colinbut/exclusion-service.svg?branch=master)](https://travis-ci.org/colinbut/exclusion-service)

### Summary 
A microservice that encapsulates a blacklist of excluded users.

### Overview
Built with:

- Java 7
- JAXB
- JAX-RS (Jersey as the implementation)
- Jedis (a Redis client java library)
- Jetty (a lightweight web server/container)

Maven build tool. 

Application is packaged up in a war file which then gets run in Jetty by using the maven jetty plugin.


Built using vanilla Java with Java EE (J2EE)'s JAX-RS technology - using the reference implementation Jersey. It runs inside a Jetty server (a lightweight web container/server). Application connects to
Redis datastore (NoSQL)

Below:

![Image of technology diagram](etc/diagram.png)


### Usage

Hit the endpoint URL*:

```
http://localhost:8080/rest/exclusion/validate/1234566SSN/2018-01-01
```

*_the URL will be slightly different depending on how the microservice is deployed._

You should get back a response like:

```xml
<EXCLUSION-RESOURCE>
    <STATUS-DESCRIPTION>NOT BLACKLISTED</STATUS-DESCRIPTION>
</EXCLUSION-RESOURCE>
```

### Deployment

This section details the different ways to deploy this microservice for usage

#### Local

There are numerous ways to bring up this microservice locally.

##### Web Container - Explode War Deployment

After building the artifact with `mvn clean install` - copy the built exclusion-service.war file
to the deployment directory of the web container.

for example, if using Jetty:

copy `exclusion-service.war` to `/var/lib/jetty/webapps`

or whereever the `webapps` folder is under the Jetty installation.

start Redis Server

note - using this method you would access the URL endpoint as:

```
http://localhost:8080/exclusion-service/rest/exclusion/validate/1234566SSN/2018-01-01
```


##### Maven Jetty Plugin - mvn jetty:run

to run:

start Redis Server. Then:

```bash
mvn -Djetty.http.port=[port number] jetty:run
```

where port number is a port you specify (by default its on port 8080 if you don't specify)

note, using this method you will need to manually set the following environment variables as it uses these env variables to specifiy connection details to Redis.

- REDIS_HOST
- REDIS_PORT

##### Maven Jetty Plugin - mvn jetty:run-forked

start Redis Server. Then:

similar to above command but...

```bash
mvn -Djetty.http.port=[port number] jetty:run-forked
```

with this method, the 2 environment variables are already configured in the `pom.xml` 

##### Dockerize way

Using both Docker and Docker Compose you can bring up this microservice easily.
This deployment method is the way used in QA/Staging. So therefore you can do this way as well
to mimic the deployment setup on your local Dev environment as if it was a (QA/Staging) Test Environment.

```bash
chmod +x; ./install.sh
cd docker
docker-compose up
```


#### QA

[TBD]
#### Staging

[TBD]
#### Production

[TBD]

