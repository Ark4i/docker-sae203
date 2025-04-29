# Use a base image with Java
FROM openjdk:17

# Set working directory inside container
WORKDIR /app

# Copy Morpion source code and resources into the container
COPY game/Morpion /app

# Compile all Java files
RUN javac *.java

# Set default command to run the server (can override for client)
CMD ["java", "ServeurMorpion"]