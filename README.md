# Calculatrice Client/Serveur en Java — Échange de CC et d'objets (TP2)

## Description

Ce projet prolonge la calculatrice distribuée développée dans le TP1 en changeant la **nature des données échangées** entre le client et le serveur. Au lieu d'envoyer des types primitifs binaires (`int`, `double`, `boolean`), le client envoie désormais soit des **chaînes de caractères** (CC), soit un **objet Java sérialisé**. Le serveur reçoit, parse ou désérialise, effectue le calcul, puis renvoie le résultat au client. Le projet est organisé en deux activités : **2‑1** (échange de CC) et **2‑2** (échange d'objets).

## Structure du projet

```
src/
├── clientPackage/
│   ├── Client_2_1.java     → envoie l'opération sous forme de chaînes de caractères
│   └── Client_2_2.java     → envoie un objet Operation
├── serverPackage/
│   ├── Server_2_1.java     → parse les chaînes et calcule
│   └── Server_2_2.java     → désérialise l'objet et calcule
└── commonPackage/
    └── Operation.java      → objet Serializable partagé entre client et serveur
```

## Activités

### Activité 2‑1 — Échange de chaînes de caractères (CC)

Le client envoie l'opération sous forme de **trois lignes de texte** : le premier opérande, l'opérateur, puis le deuxième opérande. Le serveur lit les trois lignes, convertit les opérandes en `double`, valide l'opérateur, effectue le calcul, puis renvoie le résultat sous forme de chaîne.

Points clés :
- Utilisation de `BufferedReader` / `PrintWriter` pour lire et écrire du texte.
- Le serveur doit **parser** ce qu'il reçoit (`Double.parseDouble`, `switch` sur l'opérateur).
- Le serveur **valide** la syntaxe (opérateur inconnu, division par zéro, opérande non numérique).
- Le résultat est renvoyé sous forme de `String` (résultat numérique ou message d'erreur).

### Activité 2‑2 — Échange d'objets

Le client envoie un **objet `Operation`** (qui implémente `Serializable`) contenant les deux opérandes et l'opérateur. Le serveur désérialise l'objet, extrait ses champs via des getters, effectue le calcul, puis renvoie le résultat sous forme de `String`.

Points clés :
- La classe `Operation` implémente `Serializable` et possède un `serialVersionUID`.
- Utilisation de `ObjectOutputStream` / `ObjectInputStream` au lieu des flux texte.
- La classe `Operation` doit être disponible **sur les deux machines** avec le même nom qualifié (`commonPackage.Operation`).
- Les méthodes `readObject()` / `writeObject()` lancent également `ClassNotFoundException`, d'où la signature `throws` étendue.

## Protocole d'échange

### Activité 2‑1 (CC)

| Étape | Sens | Type | Contenu |
|-------|------|------|---------|
| 1 | Client → Serveur | ligne texte | premier opérande (ex. `55`) |
| 2 | Client → Serveur | ligne texte | opérateur (ex. `*`) |
| 3 | Client → Serveur | ligne texte | deuxième opérande (ex. `12`) |
| 4 | Serveur → Client | ligne texte | résultat ou `Erreur : ...` |

### Activité 2‑2 (objet)

| Étape | Sens | Type | Contenu |
|-------|------|------|---------|
| 1 | Client → Serveur | objet sérialisé | `Operation(a, op, b)` |
| 2 | Serveur → Client | objet sérialisé | `String` (résultat ou erreur) |

## Exécution

1. Sur la machine serveur, récupérer son adresse IP (`ipconfig` sous Windows, `ifconfig` / `ip a` sous Linux) et mettre à jour `ipServeur` dans le client.
2. Lancer le serveur :
   ```bash
   java serverPackage.Server_2_1
   ```
   ou
   ```bash
   java serverPackage.Server_2_2
   ```
3. Lancer le client correspondant sur l'autre machine :
   ```bash
   java clientPackage.Client_2_1
   ```
   ou
   ```bash
   java clientPackage.Client_2_2
   ```

## Exemple

```
Premier opérande : 55
Opérateur (+ - * /) : *
Deuxième opérande : 12
Réponse : 660.0
```

```
Premier opérande : 7
Opérateur (+ - * /) : /
Deuxième opérande : 0
Réponse : Erreur : Division par zéro
```

## Concepts abordés

- Échange de chaînes de caractères via sockets TCP (`BufferedReader`, `PrintWriter`)
- Sérialisation et désérialisation d'objets Java (`Serializable`, `ObjectInputStream`, `ObjectOutputStream`)
- Parsing et validation côté serveur
- Protocole requête/réponse client‑serveur
- Gestion des erreurs (syntaxe, division par zéro, opérateur inconnu)
- Partage d'une classe commune entre client et serveur

## Environnement

- JDK ≥ 1.8
- Eclipse / IntelliJ IDEA / VS Code
- Git

## Livrables

- Lien du repository GitHub
- Ce fichier `README.md`