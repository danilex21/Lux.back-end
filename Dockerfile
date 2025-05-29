# Etapa de build
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa de execução
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Configuração para o Render
ENV JAVA_OPTS="-Xmx512m -Xss512k -XX:CICompilerCount=2 -Dfile.encoding=UTF-8"
ENV PORT=10000
EXPOSE 10000

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dserver.port=$PORT -jar /app/app.jar"]
