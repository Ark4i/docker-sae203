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