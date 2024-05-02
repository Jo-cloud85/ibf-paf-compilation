# Build multi-stage

# First container --------------------------------------------------------

# 'builder' is the alias necessary in multi-stage environment
FROM openjdk:21-jdk-bullseye AS builder

# dir where it will contain your src and target
# everything after the WORKDIR directory will be appended to /app
WORKDIR /app

# copy all required files to build the app into the image app folder
COPY mvnw .
COPY pom.xml .
COPY src src
COPY .mvn .mvn

# Copy the static files into the Docker image
COPY src/main/resources/static/json/comment.json /app/src/main/resources/static/json
COPY src/main/resources/static/json/game.json /app/src/main/resources/static/json
COPY src/main/resources/static/sql/schema.sql /app/src/main/resources/static/sql

# use run command to run maven command to build the package, exclude unit testing
# download maven dependencies and build your jar file into target folder
# target/paf-revision-0.0.1-SNAPSHOT.jar 
RUN ./mvnw package -Dmaven.test.skip=true

# Second container ----------------------------------------------------

# run container
FROM openjdk:21-jdk-bullseye

WORKDIR /app_run

COPY --from=builder /app/target/paf-revision-0.0.1-SNAPSHOT.jar .
# Copy the static files from the builder stage into the final image
COPY --from=builder /app/src/main/resources/static/json /app_run/src/main/resources/static/json
COPY --from=builder /app/src/main/resources/static/sql /app_run/src/main/resources/static/sql

# run
# ENV OPENWEATHERMAP_KEY=abc123
ENV PORT=3000

EXPOSE ${PORT}

# --start-period=5s: The duration docker is going to wait before Docker checks the health
# If return 0, it is healthy
# If return 1, it is unhealthy
# HEALTHCHECK --interval=30s --timeout=30s --start-period=5s --retries=3 CMD [ "executable" ]
HEALTHCHECK --interval=30s --timeout=30s --start-period=5s --retries=3 CMD curl http://127.0.0.1:${PORT}/healthz || exit 1

# If you have renamed above, then the jar file must be the renamed one
# And if this is not multi-stage, you don't need the 'target/', just jar file
ENTRYPOINT SERVER_PORT=${PORT} java -jar paf-revision-0.0.1-SNAPSHOT.jar