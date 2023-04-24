# <img style="height: 7vh; width: auto;" src="https://repository-images.githubusercontent.com/526660913/d4667242-a661-4ad4-8d59-3ab41787f400"> StarCosmetics

> Adanved, Flexible & Customizable Cosmetics Plugin, developed for Minecraft 1.9 and above.

## ⭐ Express Yourself.
<details>
    <summary>About</summary>

StarCosmetics is an innovative, advanced, feature-packed and customizable cosmetics plugin, created by GamerCoder.

Featuring things from Projectile Trails, Particle Rings, Custom Structures, Pets, Sound Effects, and more, StarCosmetics is the best cosmetics plugin for your server.
</details>

---

## 📚 Features
- **Projectile Trails** - Create items, particles, entities, and more that follow your projectiles around.
- **Particle Rings** - Create particle rings for your players to use.
- **Custom Structures** - Spawn temporary structures that players can play around!
- **Pets** - Spawn a best friend to play along with!
- **Sound Effects** - Create sound effects for your players to use, on any triggered event.

---

## 📓 Changelog

📖 v1.1.1 - April 23, 2023
- Gradle Updates
- Added New Cosmetics, Pets, and Structures
  - Added More ParticleShape Shapes
- Heavy Optimization
- Pet Cosmetics Options
  - Your pets (tameables and StarCosmetics pets) can now have the same Particle Shapes as you!
- Minor Improvements 

🎩 v1.1.0 - March 25, 2023 | "Happy Hats"
- **Converted Project Build to Gradle**
- Add Support & Features for MC 1.19.4
- Created StarCosmetics Hats
  - Section in /cosmetics allowing players to equip pre-made Hats
  - Will automatically dismount anything in their helmet slot
  - Hat objects not obtainable in Inventories or as Entities
- Created Lodestone Pillar Structure
- Heavy Optimization & Manual Minimization

🐄 v1.0.2 - Jan 16, 2023
- Added 6 new Structures, 2 new pets, and other additions
- Added Configurability for Entity, Block, and Item Trail Disappear Times
- Created a Setting to manage placement of the Pet
- Fixed GUI sounds being heard by other players

💽 v1.0.1 - Dec 22, 2022
- Fixed issues with loading on pre-1.19 versions
- Added more cosmetic content

💽 v1.0.0 - Dec 17, 2022

First Release of StarCosmetics

---

## 🔮 Future Features

#### v1.2.0
- [ ] Custom Player Soundtracks
- [ ] Custom Structures using StarCosmetics Structure Files (.scs)

---

## 💻 StarCosmetics API
![GitHub](https://img.shields.io/github/license/GamerCoder215/StarCosmetics)
[![GitHub branch checks state](https://github.com/GamerCoder215/StarCosmetics/actions/workflows/build.yml/badge.svg)](https://github.com/GamerCoder215/StarCosmetics/actions/workflows/build.yml)
[![](https://jitpack.io/v/GamerCoder215/StarCosmetics.svg)](https://jitpack.io/#GamerCoder215/StarCosmetics)
[![](https://jitci.com/gh/GamerCoder215/StarCosmetics/svg)](https://jitci.com/gh/GamerCoder215/StarCosmetics)
![GitHub release (latest by date)](https://img.shields.io/github/v/release/GamerCoder215/StarCosmetics?style=plastic)
[![Build Status](https://ci.codemc.io/job/gamercoder215/job/StarCosmetics/badge/icon)](https://ci.codemc.io/job/gamercoder215/job/StarCosmetics/)

**JavaDocs:** [https://gamercoder215.github.io/StarCosmetics](https://gamercoder215.github.io/StarCosmetics)

### Installation

<details>
    <summary>Maven</summary>

```xml
<project>

    <repositories>
        <repository>
            <id>codemc-releases</id>
            <url>https://repo.codemc.io/repository/maven-releases/</url>
        </repository>
    </repositories>
    
    <dependencies>
        <dependency>
            <groupId>me.gamercoder215.starcosmetics</groupId>
            <artifactId>starcosmetics-api</artifactId>
            <version>[VERSION]</version>
        </dependency>
    </dependencies>
    
</project>
```
</details>

<details>
    <summary>Gradle (Groovy)</summary>

```gradle
repositories {
    maven { url 'https://repo.codemc.io/repository/maven-releases/' }
}

dependencies {
    implementation 'me.gamercoder215.starcosmetics:starcosmetics-api:[VERSION]'
}
```
</details>

<details>
    <summary>Gradle (Kotlin DSL)</summary>

```kotlin
repositories {
    maven(url = "https://repo.codemc.io/repository/maven-releases/")
}

dependencies {
    implementation('me.gamercoder215.starcosmetics:starcosmetics-api:[VERSION]')
}
```
</details>

---
## 🗒️ Usage
Download the plugin on our Spigot Page, and drop it in to your Spigot/Paper "plugins" folder.

---

## 📷 Screenshots

<img src="https://media.discordapp.net/attachments/894254760075603980/1089413119933161523/2023-03-25_23.57.33.png" title="Stripped Bamboo Hat" alt="Stripped Bamboo Hat">

<img src="https://media.discordapp.net/attachments/894254760075603980/1089413119538909224/2023-03-25_23.57.19.png" title="Hats GUI" alt="Hats GUI">

<img src="https://media.discordapp.net/attachments/894254760075603980/1053917691632693288/2022-12-18_00.10.32.png" title="Tardigrade Pet" alt="Tardigrade pet">

<img src="https://media.discordapp.net/attachments/894254760075603980/1053917533369028678/2022-12-02_20.27.25.png" title="Conduit Ring" alt="Conduit Ring">

<img src="https://media.discordapp.net/attachments/894254760075603980/1053917692278616074/2022-12-18_00.11.33.png" title="Planks Trail" alt="Planks Trail">

<img src="https://media.discordapp.net/attachments/894254760075603980/1053917691917910107/2022-12-18_00.10.55.png" title="Mud Ring" alt="Mud Ring">

<img src="https://media.discordapp.net/attachments/830852440273322044/1039015346989973634/image.png" title="Trident Projectile Trail" alt="Trident Projectile Trail">

<img src="https://media.discordapp.net/attachments/894254760075603980/1044073473770799114/image.png" title="Anvil Ground Trail" alt="Anvil Ground Trail">