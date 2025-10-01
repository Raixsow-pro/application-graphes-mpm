# Application de création et de gestion de graphes MPM
Cette application permet de créer, visualiser et gérer des graphes MPM (Méthode des Potentiels Métra), principalement utilisés pour la gestion de projets en entreprise. 
Elle permet d'analyser les dépendances entre tâches, de calculer un ou plusieurs chemin(s) critique(s), et de visualiser la structure du projet.

## ✨ Fonctionnalités principales
- Création, modification et suppression de tâches
- Calcul automatique du chemin critique
- Interface Utilisateur Graphique
- Implémentation d'un système de date pour gérer les délais (En nombre de jours ou en daate)

## 🛠️ Langage de programmation
- **Langage**     : Java
- **Compilation** : via un fichier `compile.list`
- **Exécution**   : via la classe principale `Controleur`

## 🖼️ Prévisualisation de l'application

![Capture](./images/MPM.png)

## 🚀 Installation et exécution

### 1. Compilation
Assurez-vous que le fichier `compile.list` contient la liste de tous les fichiers source à compiler.
Depuis le dossier contenant vos fichiers `.java` :
- Pour les terminaux Linux:
```bash
javac @compile.list -d ../class
```
- Pour les terminaux Windows:
```bash
javac (Get-content compile.list) -d ../class  
```

### 2. Exécution
Un répertoire `class` vient, normalement, d'être créé. Assurez-vous que ce soit le cas.
Déplacez-vous dans ce répertoire avec la commande:
```bash
cd ../class
```

## 👪 Collaborateurs du projet

et exécutez la commande:
```bash
java MPM.Controleur
```
