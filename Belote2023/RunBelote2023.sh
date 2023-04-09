#!/bin/bash

#Maven package
call mvn clean compile package install

#Run the app
java -jar target/Belote2023.jar