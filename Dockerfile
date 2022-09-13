FROM openjdk:11.0.16
EXPOSE 8081
ADD target/APPRENTICES-beta-posts-comments-0.0.1-SNAPSHOT.jar APPRENTICES-beta-posts-comments-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/APPRENTICES-beta-posts-comments-0.0.1-SNAPSHOT.jar"]
