
# Projet Morpion Multijoueur en Java

## Objectifs

- Développer un **jeu de Morpion multijoueur**.  
- Utiliser une architecture **client-serveur** avec **sockets Java**.  
- Intégrer **Docker** pour isoler les services.  
- Permettre une interface graphique avec **Swing**.  
- Collaborer efficacement grâce à **Git**.  

---

## Utilisation de Git

### Qu'est-ce que Git ?

**Git** est un système de gestion de versions **décentralisé** utilisé pour suivre les modifications apportées à un projet au fil du temps. Il permet :

- De sauvegarder **l’historique complet** du code source.
- De **travailler à plusieurs** sur un même projet, en évitant les conflits.
- De créer des **branches indépendantes** pour développer des fonctionnalités sans affecter le reste du code.
- De **fusionner**, **revenir en arrière**, et comprendre qui a fait quoi, quand et pourquoi.

Dans notre projet, Git a été indispensable pour collaborer efficacement à plusieurs, synchroniser nos avancements, et garantir la stabilité du code.

---

### Application de Git dans le projet

Nous avons utilisé **Git** pour :

- **Répartir les tâches** sur différentes branches (`serveur`, `client`, `interface`, etc.).
- **Sauvegarder l’évolution** du projet avec des commits clairs et fréquents.
- **Collaborer via GitHub**, en ouvrant des *pull requests* pour valider les changements avant fusion.
- **Gérer les conflits** lorsque plusieurs membres modifient des fichiers en parallèle.

Exemple de commandes utilisées :
```bash
git clone https://github.com/Ark4i/Docker-sae203-main.git
git pull
git add .
git commit -m "Ajout de l'interface graphique client"
git push origin client-graphique
```

---

### Résumé des TP Git réalisés

Au fil de notre apprentissage, nous avons réalisé plusieurs TP pour maîtriser Git :

- **TP1 :** Création d’un dépôt Git local et premiers commits.  
- **TP2 :** Connexion et synchronisation avec un dépôt distant GitHub.  
- **TP3 :** Collaboration en équipe et gestion des branches.  
- **TP4 :** Correction via pull requests sur le projet d’un camarade.  
- **TP5 :** Résolution de conflits et exploration de l’historique Git.

Ces TP nous ont apporté une compréhension pratique de Git, essentielle pour gérer un projet logiciel réel.

---
Bien sûr ! Voici la section **Docker** corrigée **sans le résumé des TPs**, tout en gardant une explication claire et complète de son utilité dans le projet :

---

## Introduction à Docker

### Qu’est-ce que Docker ?

**Docker** est un outil de **virtualisation légère** qui permet de créer des **conteneurs**, c’est-à-dire des environnements isolés contenant tout ce qui est nécessaire pour faire fonctionner une application : le code, les bibliothèques, les dépendances, etc.

Contrairement à une machine virtuelle, un conteneur est plus **léger**, **rapide** et **facile à déployer**, car il partage le noyau du système d’exploitation hôte tout en maintenant son propre espace d’exécution.

### Pourquoi utiliser Docker dans notre projet ?

Dans notre jeu Morpion multijoueur, Docker nous a permis de :

- **Uniformiser l’environnement d’exécution** pour éviter les différences entre les postes des développeurs.
- **Simplifier le déploiement** du serveur et des clients, sans avoir à configurer Java manuellement sur chaque machine.
- **Isoler les services** (serveur et client) pour éviter les conflits.
- **Reproduire facilement le projet** sur n’importe quel système en une seule commande.

---

## Docker dans le projet

### Dockerfile

Nous avons écrit un `Dockerfile` permettant de compiler automatiquement les fichiers Java et d’exécuter le serveur :

```Dockerfile
FROM openjdk:17
WORKDIR /app
COPY game/Morpion /app
RUN javac *.java
CMD ["java", "ServeurMorpion"]
```

Ce fichier indique à Docker d’utiliser une image Java, de copier le code source dans le conteneur, de le compiler, puis d’exécuter le serveur.

---

### Lancement

Une fois le `Dockerfile` en place, nous avons pu lancer le projet avec les commandes suivantes :

```bash
docker build -t morpion-game . 
docker run --rm -p 5000:5000 morpion-game
```

Cela construit une image Docker appelée `morpion-game` puis l’exécute en exposant le port `5000` pour que les clients puissent s’y connecter.

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
