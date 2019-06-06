FROM openjdk:11-stretch #start with openjdk 11 image

COPY dev/Ambi-like_PC /usr/src/Ambi-like_PC #copy the projet on container
COPY dev/Ambi-like_rp /usr/src/Ambi-like_rp

WORKDIR /usr/src/Ambi-like_PC
RUN mkdir /usr/jar
RUN mkdir /usr/jarf
RUN mkdir bin
RUN javac -cp "libs/*" -d bin -sourcepath src src/ch/hearc/Main.java #compile main program
RUN jar cMf alpc.jar bin/* #make jar
RUN cp alpc.jar /usr/jar/

WORKDIR /usr/src/Ambi-like_rp
RUN mkdir bin
RUN javac -proc:none -cp "lib/*" -d bin -sourcepath src src/ch/hearc/Main.java #compile raspberry program
RUN jar cMf alrp.jar bin/* #make jar
RUN cp alrp.jar /usr/jar/