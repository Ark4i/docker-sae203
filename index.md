

#  Projet Morpion Multijoueur en Java

##  Objectifs

- Développer un **jeu de Morpion multijoueur**.
- Utiliser une architecture **client-serveur** avec **sockets Java**.
- Intégrer **Docker** pour isoler les services.
- Permettre une interface graphique avec **Swing**.
- Collaborer efficacement grâce à **Git**.

---

##  Utilisation de Git

Nous avons utilisé **Git** comme système de gestion de versions pour :

- **Travailler en équipe** en répartissant les tâches sur différentes branches (`serveur`, `client`, `interface`, etc.).
- **Sauvegarder l'évolution** du projet avec des commits réguliers et significatifs.
- **Collaborer via GitHub**, en ouvrant des *pull requests* pour réviser le code avant de le fusionner.
- Résoudre les conflits et maintenir un historique clair et structuré.

Exemple de commandes utilisées :
```bash
git clone https://github.com/Ark4i/Docker-sae203-main.git
git pull
git add .
git commit -m "Ajout de l'interface graphique client"
git push origin client-graphique
```

---

##  Introduction à Docker

Docker nous a permis de :

- **Créer un environnement reproductible** pour le serveur et les clients.

Nous avons appris les bases de Docker grâce au guide suivant :
 [Introduction à Docker - abderzah.github.io](https://abderzah.github.io/introduction-docker/)

---

##  Structure du projet

```
Docker-sae203-main/
├── game/
    └── Morpion/
	    └── ClientMorpion.java
	    └── Morpion.java
	    └── ServeurMorpion.java
├── Dockerfile
├── README.md
└── .git/
```

---

## Docker dans le projet

###  Dockerfile

Le `Dockerfile` compile et exécute le code Java :
```Dockerfile
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
```

###  Lancement

```bash
docker build -t morpion-game . 
docker run --rm -p 5000:5000 morpion-game
```

---

##  Fonctionnement du Jeu

1. Le **serveur** attend les connexions.
2. Les **clients** se connectent et jouent à tour de rôle.
3. Le **serveur** valide les coups et gère les règles du jeu.
4. L’interface **Swing** affiche le plateau à chaque mise à jour.

---

##  Conclusion

Ce projet nous a permis de :

- Pratiquer la **programmation réseau en Java**.
- Découvrir **Docker** pour la conteneurisation.
- Travailler en équipe avec **Git** efficacement.
- Créer une base pour de futurs projets multijoueurs conteneurisés.

---
