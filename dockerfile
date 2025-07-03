FROM eclipse-temurin:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Install curl and maven for health checks and building
RUN apk update && apk add --no-cache curl maven && rm -rf /var/cache/apk/*

# Copy pom.xml first
COPY pom.xml ./

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy the source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Create a non-root user for security
RUN addgroup -g 1001 -S appuser && adduser -u 1001 -S appuser -G appuser

# Change ownership of the app directory
RUN chown -R appuser:appuser /app

# Switch to non-root user
USER appuser

# Expose the port the app runs on
EXPOSE 8081

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8081/api/auth/health || exit 1

# Run the application
CMD ["java", "-jar", "target/exam-duty-system-0.0.1-SNAPSHOT.jar"]