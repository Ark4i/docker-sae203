FROM debian:latest
COPY .*

RUN javac -encoding UTF-8 ./game/Morpion/ServerMorpion.java

EXPOSE 80
