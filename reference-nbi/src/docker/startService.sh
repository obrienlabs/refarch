#!/bin/bash
cd /opt/app
#-Dlogging.config=config/logback.xml
if [ -z "${java_runtime_arguments}" ]; then
java  -Xms128m -Xmx1536m -jar /opt/app/lib/reference-nbi.jar
else
java  $java_runtime_arguments -jar /opt/app/lib/reference-nbi.jar
fi
