javac -encoding UTF-8 ./game/*.java
screen -S work -d -m bash -c 'cd /docker-sae203/game && java ServeurDames'
