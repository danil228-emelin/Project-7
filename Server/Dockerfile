FROM openjdk:17
COPY ./target/Server-Lab6-jar-with-dependencies.jar /tmp
COPY people.xml /tmp
WORKDIR /tmp
ENV COLLECTION_PATH /tmp/people.xml
CMD java -jar 'Server-Lab6-jar-with-dependencies.jar'
