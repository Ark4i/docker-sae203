FROM debian:latest

# Installer Apache et le JDK
RUN apt-get update && \
    apt-get install -y apache2 openjdk-17-jdk && \
    apt-get clean

# Définir le répertoire de travail
WORKDIR /app

# Copier uniquement les fichiers nécessaires
COPY game/Morpion ./game/Morpion
COPY html /var/www/html

# Compiler les fichiers Java utiles
RUN javac -encoding UTF-8 ./game/Morpion/*.java

# Exposer le port pour Apache
EXPOSE 80

# Démarrer Apache
CMD ["apachectl", "-D", "FOREGROUND"]
