# Aspanion Cognitivo 🎗️🎮

**Aspanion Cognitivo** is an educational and cognitive stimulation mobile app developed to support the children and "valientes" (brave ones) of the **Aspanion Association**. Through adapted classic games, we aim to promote attention, memory, and creativity in a fun, friendly environment.

---

## ⚠️ Disclaimer: Built with Heart (Not a Pro Dev! 😅)
**Full disclosure:** I am NOT a professional developer. This project was born from a desire to help, and I’ve done my absolute best to put it together. 
* The code might be a bit "experimental" (okay, maybe a lot).
* If you find a bug or a better way to do something, please be kind! 
* Pull requests and help from "real" developers are more than welcome to make this app better for the kids.

> "The code might have its quirks, but the intention is 100% bug-free." ❤️

---

## 👦👧 Protagonists
The app features **Martín and Lola**, the official avatars who guide the children through their adventure, making the experience feel like home.

## 🕹️ Included Games

The application adapts its difficulty across three age ranges:

1.  **Memory 🧠:** Classic pair-finding game with animals and space icons. Includes a "Play Again" button that reshuffles everything.
2.  **Color by Numbers 🎨:** A pixel-art game where children paint by numbers. 
    * **The Magic Touch:** Once finished, the pixelated grid vanishes to reveal a beautiful, high-quality illustration!
    * **Animal Zoo:** Includes a Chick, Frog, Fox, Elephant, Panda, and a detailed Lion for the older kids.
3.  **Animal Sudoku 🦁:** A logic challenge using animal icons for younger kids and numbers for the experts.

## 🛠️ Installation & Setup

The project is developed in **Kotlin** using **Jetpack Compose**.

### Requirements
* Android Studio Ladybug or higher.
* JDK 17.

### 🖼️ Resource Setup (CRITICAL)
Since I cannot upload copyrighted assets, you must manually add these to the `app/src/main/res/drawable` folder:

* `logo_aspanion.png`: Official logo.
* `martin_y_lola.png`: The main characters.
* **Reward Images:** `pollito_real.png`, `rana_real.png`, `zorro_real.png`, `elefante_real.png`, `panda_real.png`, `leon_real.png`.



## 🚀 Technologies
* **Language:** [Kotlin](https://kotlinlang.org/)
* **UI Framework:** [Jetpack Compose](https://developer.android.com/jetpack/compose)
* **Logic:** Basic State Management (doing my best!).

## 📄 License
This project is licensed under the **Apache License 2.0**. It’s open, it’s free, and it’s for the kids. See the [LICENSE](LICENSE) file for details.

## 📂 Project Structure
```bash
├── MainActivity.kt
├── navigation
│   └── ControladorNavegacion.kt
└── ui
    ├── components
    │   └── BotonMenu.kt
    ├── inicio
    │   └── PantallaInicioAspanion.kt
    ├── juegos
    │   ├── memory
    │   │   ├── JuegoMemory.kt
    │   │   └── model
    │   │       └── Carta.kt
    │   ├── pintar
    │   │   ├── JuegoPintarNumeros.kt
    │   │   ├── PantallaSeleccionPlantilla.kt
    │   │   ├── data
    │   │   │   └── PlantillasProvider.kt
    │   │   └── model
    │   │       ├── CeldaPintar.kt
    │   │       └── PlantillaPintar.kt
    │   └── sudoku
    │       ├── JuegoSudoku.kt
    │       └── model
    │           └── CeldaSudoku.kt
    ├── menu
    │   └── PantallaMenuJuegos.kt
    └── theme
        ├── Color.kt
        ├── Theme.kt
        └── Type.kt
```


---
Developed with ❤️ for the brave ones at **Aspanion**.
