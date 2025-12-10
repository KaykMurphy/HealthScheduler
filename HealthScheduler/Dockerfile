FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests


FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

#Copia apenas o arquivo JAR gerado na etapa anterior
COPY --from=builder /app/target/*.jar app.jar

#Define o comando padrão que será executado sempre que o contêiner for iniciado.
ENTRYPOINT ["java", "-jar", "app.jar"]
