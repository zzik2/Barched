![68747470733a2f2f63646e2e6d6f6472696e74682e636f6d2f646174612f6361636865645f696d616765732f303063306362646430633932613766613936393037326166623938623132666133663937666436662e6a706567](https://github.com/user-attachments/assets/38c6f383-a549-47b9-8945-b06462855674)

# Preview

## Mobs
<img width="3840" height="2019" alt="2026-01-29_08 41 04" src="https://github.com/user-attachments/assets/8ef98dc9-561b-4e88-98a6-18e6edabefa8" />

<img width="3840" height="2019" alt="2026-01-29_08 40 49" src="https://github.com/user-attachments/assets/aee967dd-a2a1-4c28-b85c-975691cb3f8b" />

## In-Game
![stab-firstperson (1)](https://github.com/user-attachments/assets/ee62d425-cf3c-4b6c-a910-246d1482f345)
![stab-thirdperson (1)](https://github.com/user-attachments/assets/7e8b2efc-3cf1-41bd-bdce-e6497228a955)
![kinetic-firstperson (1)](https://github.com/user-attachments/assets/8073773e-cada-4b0f-9ee1-023f85f4d98b)
![kinetic-thirdperson (1)](https://github.com/user-attachments/assets/45df1cfd-a9c6-42e6-b9f4-e5be13db4d35)
![zombie_horse - Trim (1)](https://github.com/user-attachments/assets/0d5eb3e3-045d-42fc-8474-21ad7da1cc6f)
![2026-01-29 08-49-26 - Trim (1)](https://github.com/user-attachments/assets/284b0215-3e6a-48ca-99ec-aa5b0a6e3a12)

## Spears
<img width="690" height="124" alt="image" src="https://github.com/user-attachments/assets/761a43dd-e9b8-4cfb-b855-54b4eb3db792" />

## Spawn Eggs
<img width="306" height="128" alt="image" src="https://github.com/user-attachments/assets/c64e7308-c316-4bf1-817e-43fe30527a6b" />

## Lunge Enchantment
<img width="848" height="351" alt="image" src="https://github.com/user-attachments/assets/49503472-7236-45df-8144-64d4f1d081eb" />



## Summary
Backporting Parched, Camel Husk, Zombie Horse, Spears and more features in 1.21.11 to 1.21.1!

This port is written similarly to Mojang code, with most of the code written in Mixins.
and namespace is ``minecraft:``. so even if you upgrade your Minecraft version, your entities and items will remain!

tbh, I brought the 1.21.11 version of the feature as it is, so I don't have much more to explain lol. just add 12,661 lines of code and remove 451 lines 🤡

## Q&A
**Q. Do you have any plans to port this to another version?**

A. I don't have any plans right now, support for versions 1.21 and below may be a little late or difficult.

---

**Q. Why did you do this?**

A. Because I just started developing Minecraft, and it was a perfect project to understand Minecraft development and source code. 🫠

## Configuration
Config file: `config/barched.json`

| Option                            | Default | Description                                                          |
|-----------------------------------|---------|----------------------------------------------------------------------|
| `camelHuskSpawnChance`            | 10      | Chance (0-100%) for a Camel husk jockey                              |
| `zombifiedPiglinSpearSpawnChance` | 5       | Chance (0-100%) for a Zombified Piglin's Spear Equipped Spawn Chance |
| `piglinOverrideSpearSpawnChance`  | 0       | Chance (0-100%) for a Override Piglin's Spear Equipped Spawn Chance  |
| `zombieOverrideSpearSpawnChance`  | 0       | Chance (0-100%) for a Override Zombie's Spear Equipped Spawn Chance  |

## Dependencies
**Architectury**([CurseForge](https://www.curseforge.com/minecraft/mc-mods/architectury-api/files/all?page=1&pageSize=20&version=1.21.1&showAlphaFiles=hide), [Modrinth](https://modrinth.com/mod/architectury-api/versions?g=1.21.1))

**Cloth Config API**([CureForge](https://www.curseforge.com/minecraft/mc-mods/cloth-config/files/all?page=1&pageSize=20&version=1.21.1&showAlphaFiles=hide), [Modrinth](https://modrinth.com/mod/cloth-config/versions?g=1.21.1))

## Optional Dependencies
I highly recommend using this mod 😊

**ModelGapFix**([CurseForge](https://www.curseforge.com/minecraft/mc-mods/model-gap-fix/files/all?page=1&pageSize=20&version=1.21.1&showAlphaFiles=hide), [Modrinth](https://modrinth.com/mod/modelfix/versions?g=1.21.1))
