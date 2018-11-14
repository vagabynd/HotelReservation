#!/usr/bin/env bash
rm targetDeploy/*.jar

mvn clean install -DskipTests
cp ./controller/target/*.jar -d targetDeploy/
cp ./dao/target/*.jar -d targetDeploy/
cp ./model/target/*.jar -d targetDeploy/
cp ./service/target/*.jar -d targetDeploy/
cp ./service-api/target/*.jar -d targetDeploy/
cp /home/yauheni_rotar/Documents/Projects/Hotel/jars/*.jar -d targetDeploy/


