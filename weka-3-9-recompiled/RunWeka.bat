@echo off
set _cmd=%1
set _java=javaw
if "%_cmd%"=="" set _cmd=default
if "%_cmd%"=="-h" set _java=java
%_java% -classpath . RunWeka -i .\RunWeka.ini -w .\weka.jar -c %_cmd% "%2"

