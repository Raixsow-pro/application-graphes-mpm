# Application de création et gestion de graphes MPM
Cette application permet de créer, visualiser et gérer des graphes MPM (Méthode des Potentiels Métra), principalement utilisés pour la gestion de projets en entreprise. 
Elle permet d'analyser les dépendances entre tâches, de calculer un ou plusieurs chemin(s) critique(s), et de visualiser la structure du projet.

## ✨ Fonctionnalités principales
- Création de tâches et de dépendances entre elles
- Génération et gestion de graphes MPM
- Calcul automatique du chemin critique
- Interface utilisateur (console ou graphique selon version)
- Visualisation structurée des graphes (selon l'implémentation)

## 🛠️ Langage de programmation
- **Langage** : Java
- **Compilation** : via un fichier `compile.list` et la commande `javac`
- **Exécution** : standard Java via la classe principale

## 🚀 Installation et exécution

### 1. Compilation
Assurez-vous que le fichier `compile.list` contient la liste de tous les fichiers source à compiler.
Depuis le dossier contenant vos fichiers `.java` :
```bash
javac @compile.list -d ../class
```

### 2. Exécution
Un répertoire `class` vient, normalement, d'être créé. Assurez que ce soit le cas.
Déplacez-vous dans ce répertoire et exécutez la commande:
```bash
java MPM.Controleur
```
