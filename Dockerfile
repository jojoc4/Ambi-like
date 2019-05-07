# This file is a template, and might need editing before it works on your project.
FROM openjdk:11-stretch

COPY dev/Ambi-like_PC /usr/src/Ambi-like_PC
COPY dev/Ambi-like_rp /usr/src/Ambi-like_rp

WORKDIR /usr/src/Ambi-like_PC
RUN mkdir bin
RUN javac -cp "libs/*" -d bin -sourcepath src src/ch/hearc/Main.java

WORKDIR /usr/src/Ambi-like_rp
RUN mkdir bin
RUN javac -proc:none -cp "lib/*" -d bin -sourcepath src src/ch/hearc/Main.java 

# TODO make .jar and put in artifact
