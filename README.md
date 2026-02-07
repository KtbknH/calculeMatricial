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

### Matrices de Hilbert — Instabilité numérique

Les matrices de Hilbert sont un cas d'école du mauvais conditionnement en algèbre linéaire numérique. H[i][j] = 1/(i+j+1). Plus la taille augmente, plus le conditionnement explose et l'inversion devient instable.

#### Taille 2 — κ(H) = 27

![Hilbert taille 2](docs/images/hilbert_taille2.png)

#### Taille 3 — κ(H) = 748

![Hilbert taille 3](docs/images/hilbert_taille3.png)

#### Taille 4 — κ(H) ≈ 28 375

![Hilbert taille 4](docs/images/hilbert_taille4.png)

#### Taille 5 — κ(H) ≈ 943 656

![Hilbert taille 5](docs/images/hilbert_taille5.png)

#### Taille 6 — κ(H) ≈ 2.9 × 10⁷

![Hilbert taille 6](docs/images/hilbert_taille6.png)

#### Taille 7 à 15 — Système irrégulier (inversion impossible)

À partir de la taille 7, le pivot de la factorisation LDR tombe en dessous de l'EPSILON (10⁻⁶) et le système est détecté comme irrégulier :

<details>
<summary>📸 Cliquer pour voir les résultats tailles 7 à 15</summary>

**Taille 7** :
![Hilbert taille 7](docs/images/hilbert_taille7.png)

**Taille 8** :
![Hilbert taille 8](docs/images/hilbert_taille8.png)

**Taille 9** :
![Hilbert taille 9](docs/images/hilbert_taille9.png)

**Taille 10** :
![Hilbert taille 10](docs/images/hilbert_taille10.png)

**Taille 11** :
![Hilbert taille 11](docs/images/hilbert_taille11.png)

**Taille 12** :
![Hilbert taille 12](docs/images/hilbert_taille12.png)

**Taille 13** :
![Hilbert taille 13](docs/images/hilbert_taille13.png)

**Taille 14** :
![Hilbert taille 14](docs/images/hilbert_taille14.png)

**Taille 15** :
![Hilbert taille 15](docs/images/hilbert_taille15.png)

</details>

#### Synthèse du conditionnement

| Taille | Conditionnement κ(H) | Résultat |
|:---:|---:|---|
| 2 | 27 | ✅ Inversion réussie |
| 3 | 748 | ✅ Inversion réussie |
| 4 | 28 375 | ✅ Inversion réussie (erreurs ≈ 10⁻¹⁵) |
| 5 | 943 656 | ✅ Inversion réussie (erreurs ≈ 10⁻¹³) |
| 6 | 2.9 × 10⁷ | ✅ Inversion réussie (erreurs ≈ 10⁻¹¹) |
| 7 | — | ❌ Système irrégulier |
| 8–15 | — | ❌ Système irrégulier |

> **Observation** : Le conditionnement croît exponentiellement. Dès la taille 7, la factorisation LDR échoue avec un EPSILON de 10⁻⁶. En augmentant l'EPSILON à 10⁻²⁴, on pourrait atteindre des tailles plus grandes mais avec des résultats numériquement douteux.

---

### Factorisation LDR (Helder) et Algorithme de Thomas

Résolution d'un système linéaire 3×3 via la factorisation LDR, avec vérification que les normes L1, L2 et L∞ de la différence sont toutes à 0.0 (résolution exacte) :

![Helder — Résolution LDR](docs/images/thomas_helder_resolution.png)

Vérification du produit matrice × vecteur sur un système d'ordre 3 :

![Helder — Produit matrice-vecteur](docs/images/thomas_helder_produit.png)

---

### Splines cubiques

Interpolation par splines cubiques naturelles sur différents jeux de données, visualisée avec XChart. Les points bleus (●) représentent les points de support originaux, la courbe orange (→) montre l'interpolation par spline :

#### Fichier `point.txt` — Courbe sinusoïdale amortie (15 points)

![Spline — point.txt](docs/images/spline_point.png)

#### Fichier `point1.txt` — Courbe cubique (4 points)

![Spline — point1.txt](docs/images/spline_point1.png)

#### Fichier `point2.txt` — Courbe parabolique (5 points)

![Spline — point2.txt](docs/images/spline_point2.png)

#### Fichier `point3.txt` — Courbe sinusoïdale (11 points)

![Spline — point3.txt](docs/images/spline_point3.png)

#### Fichier `point4.txt` — Courbe sinusoïdale double période (21 points)

![Spline — point4.txt](docs/images/spline_point4.png)

#### Fichier `point5.txt` — Courbe sinusoïdale amortie (15 points)

![Spline — point5.txt](docs/images/spline_point5.png)

#### Fichier `point6.txt` — Signal haute fréquence (101 points)

![Spline — point6.txt](docs/images/spline_point6.png)

#### Fichier `point7.txt` — Signal carré / créneau (101 points)

![Spline — point7.txt](docs/images/spline_point7.png)

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
    ├── hilbert_taille2.png ... hilbert_taille15.png
    ├── thomas_helder_resolution.png
    ├── thomas_helder_produit.png
    └── spline_point.png ... spline_point7.png
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
