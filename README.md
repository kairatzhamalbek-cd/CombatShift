# 🎮 Combat Shift

Combat Shift is a 2D top-down action game built with Java and LibGDX, where the player fights waves of enemies, switches combat strategies, and collects power-ups to survive.

## 🧩 Gameplay

The player controls a character in an arena and must survive against increasingly difficult waves of enemies.

Key features:
- Wave-based enemy system  
- Dynamic combat strategies  
- Temporary buffs (speed, fire damage, shield)  
- Increasing difficulty  

The core mechanic is **adaptive combat**, where the player must change tactics depending on the situation.

## 🏗️ Architecture & Design Patterns

This project demonstrates several software design patterns:

- Factory Method – enemy creation  
- Strategy – different attack behaviors  
- Command – player input handling  
- Observer – UI updates  
- State – player and game states  
- Decorator – buffs and power-ups  
- Facade – game system coordination  
- Singleton – global managers  

## 🛠️ Tech Stack

- Java  
- LibGDX  
- Gradle  

## 📂 Project Structure

- `core` – main game logic  
- `lwjgl3` – desktop launcher  

## 🎯 Project Goal

This project is developed as part of a university course on Software Design Patterns, focusing on applying design patterns in a real game scenario.

## 🚧 Status

In development
