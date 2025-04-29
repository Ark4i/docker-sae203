# 🎮 Projet Morpion Multijoueur en Java avec Docker

## 📌 Objectifs

- Créer un jeu de **Morpion** (Tic-Tac-Toe) multijoueur.
- Implémenter une architecture client-serveur avec **sockets** en **Java**.
- Utiliser **Docker** pour l'isolation des services (serveur et clients).
- Gérer l'affichage graphique avec **Swing** ou version console selon besoin.

---

## 🧱 Architecture du Projet

Le projet se compose de plusieurs composants principaux : un serveur, deux clients, et un environnement Docker pour exécuter le tout. Voici l'organisation des fichiers dans le projet :


---

## 🐳 Conteneurisation avec Docker

1. **Serveur** : Gère les connexions des clients et assure la gestion du jeu (validation des coups, vérification de la victoire).
2. **Clients** : Se connectent au serveur et jouent le jeu, en envoyant et recevant des coups.
3. Le réseau privé Docker `morpion-net` permet la communication entre le serveur et les clients.

---

## 🕹️ Fonctionnement

Le jeu se déroule de la manière suivante :

1. **Le serveur** attend deux connexions de clients.
2. **Les clients** se connectent au serveur et commencent à jouer en envoyant leurs mouvements.
3. Le serveur valide les coups et les renvoie aux autres joueurs.
4. L’interface graphique (Swing) affiche le **plateau de jeu** et les mises à jour en temps réel.

---

## 🚀 Commandes principales

Voici les commandes nécessaires pour faire tourner le projet dans Docker :

### 1. **Build de l'image Docker** :

```bash
docker build -t morpion .