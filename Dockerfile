FROM node:22-alpine AS frontend-build
WORKDIR /workspace/frontend
COPY frontend/package.json frontend/package-lock.json ./
RUN npm ci --no-audit --no-fund
COPY frontend/ ./
RUN npm run build

FROM maven:3.9.9-eclipse-temurin-17 AS backend-build
WORKDIR /workspace
COPY backend/pom.xml backend/pom.xml
COPY backend/src backend/src
COPY --from=frontend-build /workspace/frontend/dist /workspace/frontend/dist
WORKDIR /workspace/backend
RUN mvn -B package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=backend-build /workspace/backend/target/trustconnect-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/app.jar"]