package net.empyrean.item.kind

enum class ItemKind {
    // Weapons
    BOW, SHORTBOW,
    CROSSBOW, REPEATER,
    GUN,
    SWORD, DAGGER,
    STAFF, SCEPTER,

    // Tools
    PICKAXE, DRILL,
    AXE, SAW,
    SHOVEL,

    // Accessories
    ACCESSORY, CHARM,
    BELT, CLOAK,
    AGLET, RING,
    WINGS,

    // Armor
    HELMET,
    CHESTPLATE,
    LEGGINGS,
    BOOTS,

    // Materials
    MATERIAL, MINERAL,
    ;

    val named: String get() = if (this == MATERIAL) "" else name.replace("_", " ")

    companion object {
        val BOWS = arrayOf(BOW, SHORTBOW, CROSSBOW, REPEATER, GUN)

        val STAVES = arrayOf(STAFF, SCEPTER)
        val SWORDS = arrayOf(SWORD, DAGGER)
        val WEAPONS = arrayOf(*BOWS, *STAVES, *SWORDS)

        val TOOLS = arrayOf(PICKAXE, DRILL, AXE, SAW, SHOVEL)

        val HELD = arrayOf(*WEAPONS, *TOOLS)

        val ACCS = arrayOf(ACCESSORY, CHARM, BELT, CLOAK, AGLET, WINGS, RING)

        val ARMOR = arrayOf(HELMET, CHESTPLATE, LEGGINGS, BOOTS)

        val MATERIALS = arrayOf(MATERIAL, MINERAL)
    }
}