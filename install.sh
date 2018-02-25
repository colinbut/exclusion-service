#!/bin/bash

############################################################################
#
# Author: Colin But - colin.but@outlook.com
#
# Bash Script to automate the creation of deployment artefacts
#
# version 1.0
#
############################################################################

baseDir=$PWD
exclusionServiceArtefact=exclusion-service.war
dockerImageTag=exclusion-service:1.0-SNAPSHOT
dockerImageArtefact=exclusion-service-1.0-SNAPSHOT.tar.gz

cd docker;
if [ -f ${dockerImageArtefact} ]; then
    echo "Removing existing ${dockerImageArtefact} artefact";
    rm -v ${dockerImageArtefact};
else
    echo "No existing ${dockerImageArtefact} exists - proceed as normal";
fi

if [ ! -f ${baseDir}/target/${exclusionServiceArtefact} ]; then
    echo "${exclusionServiceArtefact} artefact does not exist - creating it";
    cd ${baseDir};
    mvn clean install
    cd -
fi

echo "Copying built artefact ${exclusionServiceArtefact} to deployment directory";
cp -v ${baseDir}/target/${exclusionServiceArtefact} .


docker build -t ${dockerImageTag} .

docker save -o ${dockerImageArtefact} ${dockerImageTag}

echo "Removing ${exclusionServiceArtefact} artefact";
rm -v ${exclusionServiceArtefact};



