FROM maven:3.8-openjdk-18 AS build
COPY ./ /home/app/
RUN mvn -f /home/app/pom.xml package
FROM tomcat:10.1
#ENV dbDriver=com.mysql.cj.jdbc.Driver dbConnectionUrl=jdbc:mysql://mysql:3306/hardware dbUserName=root dpPassword=Matrix12_
COPY --from=build /home/app/target/microprocessors-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/microprocessors.war