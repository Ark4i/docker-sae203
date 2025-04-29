# 🎮 Projet Morpion Multijoueur

Bienvenue sur le projet **Morpion multijoueur** réalisé en Java et Docker ! Ce projet met en œuvre un jeu de Morpion (Tic-Tac-Toe) en réseau, avec une interface graphique et une architecture client-serveur.

---

## 🧱 Architecture du Projet

Le projet est divisé en plusieurs composants :

- `ServeurMorpion.java` : gère les connexions et relaie les coups entre les joueurs.
- `ClientMorpion.java` : représente chaque joueur et permet de jouer via une interface graphique.
- `Morpion.java` : logique du jeu et gestion de l'IHM.
- Dockerfile : création d'une image avec serveur et clients Java.
- `docker-compose.yml` *(optionnel)* : orchestrer les conteneurs (si autorisé).

---

## 🚀 Fonctionnement

### 🔗 Communication Client-Serveur

- Le serveur écoute sur le port `5000`.
- Le premier client connecté est `joueur 1 (X)`, le second est `joueur 2 (O)`.
- Les coups sont envoyés par socket TCP.
- Chaque client met à jour son interface après chaque coup.

### 🐳 Docker

Chaque joueur et le serveur sont lancés dans des conteneurs Docker sur le même réseau (`morpion-net`) :

```bash
# Construction de l'image
docker build -t morpion .

# Lancement du serveur
docker run -dit --name serveur_morpion --network morpion-net morpion java ServeurMorpion

# Lancement des clients
docker run -it --name client1_morpion --network morpion-net morpion java ClientMorpion serveur_morpion
docker run -it --name client2_morpion --network morpion-net morpion java ClientMorpion serveur_morpion



# Exemple Dockerfile – Création d'une image Docker

Ce projet est un exemple simple de **Dockerfile** associé à **GitHub** pour déployer un serveur web basé sur l'image officielle ```httpd```.

## Prérequis

- Assurez-vous que Docker est installé sur votre machine :
```bash
docker --version
```

## Instructions

1. **Cloner le dépôt :**
```bash
git clone git@github.com:juanluck/exempleDockerfile.git
```

2. **Accéder au dossier du projet :**
```bash
cd exempleDockerfile
```

3. **Construire l'image Docker :**
```bash
docker build -t <nom-de-votre-image> .
```
*(Remplacez `<nom-de-votre-image>` par le nom que vous souhaitez donner à votre image.)*

4. **Lancer un conteneur à partir de l'image :**
```bash
docker run -d -p 8080:80 <nom-de-votre-image>
```

5. **Vérifier que l'application fonctionne :**
- Ouvrez votre navigateur et allez à l'adresse : [http://localhost:8080](http://localhost:8080)

6. **Lister les conteneurs en cours d'exécution :**
```bash
docker ps
```

La sortie ressemblera à :
```bash
CONTAINER ID   IMAGE           COMMAND              CREATED          STATUS          PORTS                                   NAMES
b8f8f406b03c   httpd-juanlu    "httpd-foreground"    30 minutes ago   Up 30 minutes   0.0.0.0:8080->80/tcp, :::8080->80/tcp   quirky_tesla
```

7. **Arrêter le conteneur :**
```bash
docker stop b8f8f406b03c
```
*(Vous pouvez aussi utiliser le **nom** du conteneur, par exemple `quirky_tesla`.)*

8. **Supprimer le conteneur (optionnel) :**
```bash
docker rm b8f8f406b03c
```

## Remarque

- Vous pouvez utiliser soit l'**ID** du conteneur, soit son **nom** pour exécuter les commandes Docker (comme `stop` et `rm`).


