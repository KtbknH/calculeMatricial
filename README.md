# 📐 ProgSc — Programmation Scientifique : Algèbre Linéaire Numérique en Java

Bibliothèque Java d'algèbre linéaire numérique réalisée dans le cadre du cours de **Programmation Scientifique** à l'Université Le Havre Normandie. Le projet implémente de zéro les structures matricielles, les résolutions de systèmes linéaires, l'interpolation polynomiale par moindres carrés et les splines cubiques, avec visualisation graphique via XChart.

---

## 📋 Présentation

Ce projet couvre progressivement les notions fondamentales du calcul numérique matriciel :

1. **Structures de base** — Matrices, vecteurs, opérations (produit, transposée, inverse, normes, conditionnement)
2. **Résolution de systèmes linéaires** — Systèmes triangulaires, diagonaux, factorisation LDR (Helder), algorithme de Thomas
3. **Matrices de Hilbert** — Étude du mauvais conditionnement et de l'instabilité numérique
4. **Modélisation polynomiale** — Ajustement par moindres carrés avec visualisation XChart
5. **Splines cubiques** — Interpolation par splines cubiques naturelles via l'algorithme de Thomas

---

## 🖼️ Résultats et Démonstrations

### Interpolation polynomiale (ModPoly)

Ajustement polynomial par la méthode des moindres carrés sur un jeu de points de support, avec affichage de la courbe interpolée :

![Modèle Polynomial — Interpolation par moindres carrés](docs/images/modpoly_interpolation.png)

### Splines cubiques

Interpolation par splines cubiques naturelles sur différents jeux de données :

**Courbe parabolique** — Spline sur des points formant une parabole :

![Spline cubique — Courbe parabolique](docs/images/spline_parabole.png)

**Courbe sinusoïdale** — Spline sur des points formant une sinusoïde (21 points de support) :

![Spline cubique — Courbe sinusoïdale](docs/images/spline_sinusoide.png)

### Matrices de Hilbert — Instabilité numérique

Analyse du conditionnement des matrices de Hilbert de taille 2 à 7, montrant l'explosion du conditionnement (27 → 28 375 → ...) et la dégradation du produit H × H⁻¹ par rapport à l'identité :

![Résultats Hilbert — Conditionnement croissant](docs/images/hilbert_resultats.png)

### Factorisation LDR (Helder) et Thomas

Résolution de systèmes linéaires via la factorisation LDR et l'algorithme de Thomas pour matrices tridiagonales, avec vérification des normes L1, L2 et L∞ :

![Résultats Thomas & Helder](docs/images/thomas_helder_resultats.png)

---

## 🏗️ Architecture du projet

```
ProgSc/
├── AlgLin/                              # Package principal
│   ├── Matrice.java                     # Classe de base (opérations matricielles, inverse, normes)
│   ├── Vecteur.java                     # Vecteur (hérite de Matrice, colonne unique)
│   │
│   ├── SysLin.java                      # Classe abstraite — système linéaire
│   ├── SysDiagonal.java                 # Résolution système diagonal
│   ├── SysTriangSup.java               # Résolution système triangulaire supérieur
│   ├── SysTriangInf.java               # Résolution système triangulaire inférieur
│   ├── SysTriangSupUnite.java           # Triangulaire supérieur unitaire
│   ├── SysTriangInfUnite.java           # Triangulaire inférieur unitaire
│   │
│   ├── Helder.java                      # Factorisation LDR (Gauss)
│   ├── Mat3Diag.java                    # Matrice tridiagonale (stockage compact 3×n)
│   ├── Thomas.java                      # Algorithme de Thomas (systèmes tridiagonaux)
│   │
│   ├── Hilbert.java                     # Matrices de Hilbert + analyse conditionnement
│   ├── ModPoly.java                     # Modélisation polynomiale (moindres carrés)
│   ├── Splines.java                     # Splines cubiques naturelles
│   │
│   └── IrregularSysLinException.java    # Exception pour systèmes irréguliers
│
├── point.txt ... point7.txt             # Fichiers de points de support (X, Y)
├── matrice.txt / vecteur.txt            # Données de test matricielles
│
├── CompteRenduHilbert.pdf               # Rapport — Matrices de Hilbert
├── CompteRenduModPoly.pdf               # Rapport — Modélisation polynomiale
├── CompteRenduTP4.pdf                   # Rapport — Mat3Diag & Thomas
├── CompteRenduTP5.pdf                   # Rapport — Splines cubiques
│
├── xchart-3.8.8/                        # Bibliothèque XChart (visualisation graphique)
│   ├── xchart-3.8.8.jar
│   └── xchart-demo-3.8.8.jar
│
└── docs/images/                         # Captures d'écran des résultats
```

---

## 🧩 Hiérarchie des classes

```
Matrice
├── Vecteur
├── Hilbert
└── Mat3Diag

SysLin (abstract)
├── SysDiagonal
├── SysTriangSup
│   └── SysTriangSupUnite
├── SysTriangInf
│   └── SysTriangInfUnite
├── Helder          (factorisation LDR)
└── Thomas          (algorithme tridiagonal)

ModPoly             (moindres carrés, standalone)
Splines             (splines cubiques, utilise Thomas + Mat3Diag)
```

---

## 🔬 Concepts mathématiques implémentés

### Opérations matricielles (Matrice.java)

| Opération | Méthode | Description |
|---|---|---|
| Produit matriciel | `Matrice.produit(A, B)` | Produit A × B |
| Transposée | `transpose()` | Matrice transposée Aᵀ |
| Inverse | `inverse()` | Inversion via factorisation LDR |
| Norme L1 | `norme()` | Max des sommes absolues par colonne |
| Norme L∞ | `normeInfinie()` | Max des sommes absolues par ligne |
| Conditionnement | `conditionnement()` | κ(A) = ‖A‖ × ‖A⁻¹‖ |

### Factorisation LDR — Helder (Gauss)

Décomposition A = L × D × R où L est triangulaire inférieure unitaire, D est diagonale, et R est triangulaire supérieure unitaire. Permet la résolution de Ax = b en trois étapes : Ly = b, Dz = y, Rx = z.

### Algorithme de Thomas

Résolution en O(n) des systèmes tridiagonaux Ax = b, utilisé notamment pour le calcul des coefficients des splines cubiques. Stockage compact de la matrice tridiagonale dans `Mat3Diag` (3 lignes : sous-diagonale, diagonale, sur-diagonale).

### Modélisation polynomiale (ModPoly)

Ajustement d'un polynôme de degré m à n points de support par la **méthode des moindres carrés** : résolution du système normal Aᵀ·A·c = Aᵀ·y via la factorisation LDR.

### Splines cubiques naturelles (Splines)

Interpolation C² par morceaux : sur chaque intervalle [xᵢ, xᵢ₊₁], la spline est un polynôme de degré 3. Les coefficients sont calculés en résolvant un système tridiagonal via l'algorithme de Thomas, avec conditions aux limites naturelles (S''(x₀) = S''(xₙ) = 0).

---

## ⚙️ Prérequis

- **Java** 8+
- **XChart 3.8.8** (inclus dans le projet)

## 🚀 Compilation et exécution

```bash
# Compiler tout le projet
javac -cp xchart-3.8.8/xchart-3.8.8.jar AlgLin/*.java

# Exécuter les différents modules
java -cp .:xchart-3.8.8/xchart-3.8.8.jar AlgLin.Hilbert          # Matrices de Hilbert
java -cp .:xchart-3.8.8/xchart-3.8.8.jar AlgLin.ModPoly          # Modélisation polynomiale
java -cp .:xchart-3.8.8/xchart-3.8.8.jar AlgLin.Splines          # Splines cubiques
java -cp .:xchart-3.8.8/xchart-3.8.8.jar AlgLin.Helder           # Factorisation LDR
java -cp .:xchart-3.8.8/xchart-3.8.8.jar AlgLin.Thomas           # Algorithme de Thomas
```

### Format des fichiers de points

Les fichiers `point*.txt` suivent le format :

```
<nombre_de_points>
<x1> <x2> ... <xn>
<y1> <y2> ... <yn>
```

---

## 📚 Technologies

- **Java** — Langage principal
- **XChart 3.8.8** — Bibliothèque de visualisation graphique (courbes, scatter plots)
- **Calcul numérique** — Algorithmes d'algèbre linéaire implémentés from scratch

---
