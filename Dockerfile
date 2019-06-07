#start with openjdk 11 image
FROM openjdk:11-stretch 

#copy the projets on container
COPY dev/Ambi-like_PC /usr/src/Ambi-like_PC
COPY dev/Ambi-like_rp /usr/src/Ambi-like_rp

WORKDIR /usr/src/Ambi-like_PC
RUN mkdir /usr/jar
RUN mkdir /usr/jarf
RUN mkdir bin
#compile main program
RUN javac -cp "libs/*" -d bin -sourcepath src src/ch/hearc/Main.java 
#make jar
RUN jar cMf alpc.jar bin/* 
RUN cp alpc.jar /usr/jar/

WORKDIR /usr/src/Ambi-like_rp
RUN mkdir bin
#compile raspberry program
RUN javac -proc:none -cp "lib/*" -d bin -sourcepath src src/ch/hearc/Main.java
#make jar
RUN jar cMf alrp.jar bin/* 
RUN cp alrp.jar /usr/jar/