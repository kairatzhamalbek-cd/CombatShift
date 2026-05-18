# 🎮 Combat Shift

**2D Top-Down Wave Survival Game** built with **libGDX** (Java)

![Game Screenshot](https://via.placeholder.com/800x400?text=Combat+Shift+Screenshot)  
*(Скриншот будет добавлен позже)*

## 📝 Project Description

**Combat Shift** is a fast-paced 2D top-down survival game where the player must fight through increasingly difficult enemy waves across three distinct locations: **Forest**, **Desert**, and **Winter**.

The player manages health and stamina, uses a **7-slot weapon inventory** (switched with keys 1–7), and adapts their combat style as the difficulty grows. Between waves there is a 25-second break for recovery.

---

## ✨ Key Features

- **Three unique locations** with different visuals and environmental effects (water slowdown, lava damage)
- **Wave-based combat system** (3 rounds per location)
- **7-slot inventory** with weapon selection (keys **1–7**)
- **Dynamic attack radius** — changes depending on equipped weapon
- **Advanced player mechanics**: Dash (Space), Defense (RMB), Projectile attack (LMB)
- **Save / Load** system (Continue from last location & round)
- **Full main menu**, settings, and death screen
- **All 8 required Design Patterns** implemented

---

## 🎮 Controls

| Action                    | Input                     |
|--------------------------|---------------------------|
| Movement                 | WASD                      |
| Attack (Projectile)      | Left Mouse Button (LMB)   |
| Dash (consumes Stamina)  | Space                     |
| Defend                   | Right Mouse Button (RMB)  |
| Start Wave               | F                         |
| Select Weapon            | Keys **1 – 7**            |
| Pause                    | Esc                       |

---

## 🧩 Design Patterns Used

| Pattern            | Purpose                                      |
|--------------------|----------------------------------------------|
| Factory Method     | Enemy creation                               |
| Strategy           | Different enemy attack behaviors             |
| Command            | Player input handling                        |
| Observer           | UI updates (HP, Stamina, Timer, etc.)        |
| State              | Player and game states                       |
| Decorator          | Buffs and power-ups                          |
| Facade             | Game system coordination                     |
| Singleton          | Global managers (GameManager, AssetManager)  |

---

## 🛠️ Tech Stack

- **Language:** Java
- **Framework:** libGDX
- **Build Tool:** Gradle
- **IDE:** IntelliJ IDEA
- **Version Control:** GitHub
- **Project Management:** Trello

---

## 📂 Project Structure
