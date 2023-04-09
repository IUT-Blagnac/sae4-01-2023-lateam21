@echo off

:: Maven package
call mvn clean compile install package

:: Run the app
java -jar ./target/BELOTE-2.0-refactoring-with-dependencies.jar