#!/bin/bash
# My first scrip

echo "Build wetnet-business"
cd /Users/graziellacipolletti/Desktop/sts/sts\ workspace/wetnet-business
mvn clean install -Dmaven.test.skip=true
cd ..
cd wetnet-web
echo "Build wetnet-web"
mvn clean install -Dmaven.test.skip=true
#echo "start jetty"
#mvn jetty:stop
#mvn jetty:run