#!/bin/bash

baseDir=$PWD

cd docker;
if [ -f exclusion-service.war ]; then
    echo 'Removing existing exclusion-service.war artefact';
    rm -v exclusion-service.war;
else
    echo 'No existing exclusion-service.war exists - proceed as normal';
fi

if [ ! -f ${baseDir}/target/exclusion-service.war ]; then
    echo 'exclusion-service.war artefact does not exist - creating it';
    cd ${baseDir};
    mvn clean install
    cd -
fi

echo 'Copying built artefact exclusion-service.war to deployment directory';
cp -v ${baseDir}/target/exclusion-service.war .


docker build -t exclusion-service:1.0-SNAPSHOT .



