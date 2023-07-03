# Empyrean progress log

copy+paste: ✅

## 0.2-beta:
1. Introduced numerous stats - ✅:
    * Generic damage
    * Melee damage
    * Ranged damage
    * Magic damage
    * Summon damage
    * Rogue damage
    * Max Health
    * Max Mana
    * Mana regeneration
    * Health regeneration
    * Defense
    * Movement speed
    * Flight speed
    * Flight time
    * Jump height
    * Jump speed
    * Acceleration
    * Damage reduction
    * Crit Chance
    * Crit Damage
    * Mana Cost Multiplier
2. Added stat display to items - ✅
3. Added prefix display to items - ✅
4. Improved wing property display on wings - ✅
5. Players can now compare stats of items by pressing Shift while in inventory. This compares hovered item's stats and stats of item in main hand - ✅
6. Fixed several bugs with item data storage deserialization - ✅
7. Added the `/prefix` command to edit item prefixes - ✅
8. Added numerous prefixes:
   * All prefixes from https://terraria.fandom.com/wiki/Modifiers
   * Most prefixes from https://calamitymod.wiki.gg/wiki/Modifiers
   * Several others: Mystic, Vigorous and Accurate for accessories; Accelerated, Depressed and Enchanted for weapons
9. Added advanced crafting system. You can now demand more than 1 item in a stack for ingredient. Works only in certain workstations - ✅
10. Added Advanced Crafting Table - ✅
11. Fixed numerous bugs - ✅
12. Added general REI support (https://legacy.curseforge.com/minecraft/mc-mods/roughly-enough-items) - ✅
13. Added REI support for advanced crafting - ✅
14. Added texture for advanced crafting table - ✅
15. Improved recipe generation system - ✅
16. Fixed a bug where player statistics were not synchronized with client - ✅
17. Fixed a bug where negative statistics would not format within reforge item tooltip - ✅
18. Negative statistics are now formatted with red color instead of green within reforge item tooltip - ✅
19. Player statistics now also include stats from held item, offhand item, armor and trinkets - ✅
20. Player statistics are now synchronized with client on demand instead of every tick - ✅
22. Vanilla items now can have their own kind, rarity and stats - ✅
23. Vanilla items no longer render vanilla tooltip parts - ✅
24. Prefixes now work on vanilla items - ✅
25. Moved packets from java to kotlin - ✅
26. Added actual functionality to following stats: jump height, jump speed, movement speed - ✅