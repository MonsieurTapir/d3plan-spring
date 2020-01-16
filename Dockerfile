FROM openjdk:8-jre-alpine
VOLUME /tmp
RUN mkdir /work
COPY . /work
WORKDIR /work
#RUN /work/gradlew build --no-daemon
RUN mv /work/build/libs/*.jar /work/app.jar 
CMD /work/launcher.sh