package net.empyrean.item.prefix

import net.empyrean.item.kind.ItemKind
import net.empyrean.player.Stats
import net.empyrean.registry.EmpyreanRegistries
import net.minecraft.core.Registry

object EmpyreanPrefixes {
    // https://terraria.fandom.com/wiki/Modifiers
    // https://calamitymod.wiki.gg/wiki/Modifiers

    // <editor-fold desc="Accessories">
    val HARD = register("hard", Prefix(ItemKind.ACCS, Stats.of {
        defense = 1f
    }))
    val GUARDING = register("guarding", Prefix(ItemKind.ACCS, Stats.of {
        defense = 2f
        damageReduction = 0.02f
    }))
    val ARMORED = register("armored", Prefix(ItemKind.ACCS, Stats.of {
        defense = 3f
        damageReduction = 0.03f
    }))
    val WARDING = register("warding", Prefix(ItemKind.ACCS, Stats.of {
        defense = 4f
        damageReduction = 0.04f
    }))

    val PRECISE = register("precise", Prefix(ItemKind.ACCS, Stats.of {
        critChance = .02f
    }))
    val ACCURATE = register("accurate", Prefix(ItemKind.ACCS, Stats.of {
        critChance = .03f
        critDamage = .02f
    }))
    val LUCKY = register("lucky", Prefix(ItemKind.ACCS, Stats.of {
        critChance = .04f
        critDamage = .05f
    }))

    val JAGGED = register("jagged", Prefix(ItemKind.ACCS, Stats.of {
        damageMul = .01f
    }))
    val SPIKED = register("spiked", Prefix(ItemKind.ACCS, Stats.of {
        damageMul = .02f
    }))
    val ANGRY = register("angry", Prefix(ItemKind.ACCS, Stats.of {
        damageMul = .03f
        critChance = .01f
    }))
    val MENACING = register("menacing", Prefix(ItemKind.ACCS, Stats.of {
        damageMul = .04f
        critDamage = .1f
    }))

    val BRISK = register("brisk", Prefix(ItemKind.ACCS, Stats.of {
        movementSpeed = .01f
    }))
    val FLEETING = register("fleeting", Prefix(ItemKind.ACCS, Stats.of {
        movementSpeed = .03f
        flightSpeed = .01f
    }))
    val HASTY = register("hasty", Prefix(ItemKind.ACCS, Stats.of {
        movementSpeed = .04f
        flightSpeed = .02f
    }))
    val QUICK = register("quick", Prefix(ItemKind.ACCS, Stats.of {
        movementSpeed = .06f
        flightSpeed = .04f
    }))

    val WILD = register("wild", Prefix(ItemKind.ACCS, Stats.of {
        attackSpeed = .01f
    }))
    val RASH = register("rash", Prefix(ItemKind.ACCS, Stats.of {
        attackSpeed = .02f
        acceleration = .01f
    }))
    val INTERPID = register("interpid", Prefix(ItemKind.ACCS, Stats.of {
        attackSpeed = .04f
        acceleration = .03f
    }))
    val VIOLENT = register("violent", Prefix(ItemKind.ACCS, Stats.of {
        attackSpeed = .06f
        acceleration = .05f
    }))

    val MYSTIC = register("mystic", Prefix(ItemKind.ACCS, Stats.of {
        maxMana = 10f
    }))
    val VIGOROUS = register("vigorous", Prefix(ItemKind.ACCS, Stats.of {
        maxMana = 20f
    }))
    val ARCANE = register("arcane", Prefix(ItemKind.ACCS, Stats.of {
        maxMana = 30f
    }))
    // </editor-fold>

    // <editor-fold desc="Weapons + Tools">
    val KEEN = register("keen", Prefix(ItemKind.HELD, Stats.of {
        critChance = .03f
    }))
    val SUPERIOR = register("superior", Prefix(ItemKind.HELD, Stats.of {
        critChance = .03f
        damageMul = .1f
        knockback = .1f
    }))
    val FORCEFUL = register("forceful", Prefix(ItemKind.HELD, Stats.of {
        knockback = .15f
        damageMul = .05f
    }))
    val BROKEN = register("broken", Prefix(ItemKind.HELD, Stats.of {
        damageMul = -.3f
        knockback = -.2f
    }))
    val SHODDY = register("shoddy", Prefix(ItemKind.HELD, Stats.of {
        damageMul = -.1f
        knockback = -.15f
    }))
    val DAMAGED = register("damaged", Prefix(ItemKind.HELD, Stats.of {
        damageMul = -.15f
    }))
    val HURTFUL = register("hurtful", Prefix(ItemKind.HELD, Stats.of {
        damageMul = .15f
        knockback = .05f
    }))
    val STRONG = register("strong", Prefix(ItemKind.HELD, Stats.of {
        knockback = .15f
    }))
    val UNPLEASANT = register("unpleasant", Prefix(ItemKind.HELD, Stats.of {
        damageMul = .05f
        critDamage = .15f
    }))
    val WEAK = register("weak", Prefix(ItemKind.HELD, Stats.of {
        knockback = -.02f
        critDamage = -.01f
    }))
    val RUTHLESS = register("ruthless", Prefix(ItemKind.HELD, Stats.of {
        damageMul = .18f
        knockback = -.07f
    }))
    val GODLY = register("godly", Prefix(ItemKind.HELD, Stats.of {
        damageMul = .15f
        critChance = .05f
        knockback = .15f
    }))
    val DEMONIC = register("demonic", Prefix(ItemKind.HELD, Stats.of {
        damageMul = .15f
        critChance = .05f
    }))
    val ZEALOUS = register("zealous", Prefix(ItemKind.HELD, Stats.of {
        critChance = .05f
        knockback = -0.2f
    }))

    val ACCELERATED = register("accelerated", Prefix(ItemKind.WEAPONS, Stats.of {
        attackSpeed = .15f
        acceleration = .02f
    }))
    val DEADLY = register("deadly", Prefix(ItemKind.WEAPONS, Stats.of {
        damageMul = .12f
        attackSpeed = .08f
    }))
    val AGILE = register("agile", Prefix(ItemKind.WEAPONS, Stats.of {
        attackSpeed = .2f
        knockback = -.1f
    }))
    val NIMBLE = register("nimble", Prefix(ItemKind.WEAPONS, Stats.of {
        attackSpeed = .05f
        knockback = -.05f
    }))
    val MURDEROUS = register("murderous", Prefix(ItemKind.WEAPONS, Stats.of {
        damageMul = .03f
        attackSpeed = .06f
        critChance = .05f
    }))
    val SLOW = register("slow", Prefix(ItemKind.WEAPONS, Stats.of {
        knockback = .2f
        attackSpeed = -.15f
    }))
    val SLUGGISH = register("sluggish", Prefix(ItemKind.WEAPONS, Stats.of {
        attackSpeed = -.2f
    }))
    val LAZY = register("lazy", Prefix(ItemKind.WEAPONS, Stats.of {
        attackSpeed = -.08f
    }))
    val ANNOYING = register("annoying", Prefix(ItemKind.WEAPONS, Stats.of {
        attackSpeed = -.1f
        knockback = .2f
    }))
    val NASTY = register("nasty", Prefix(ItemKind.WEAPONS, Stats.of {
        damageMul = .05f
        attackSpeed = .1f
        critChance = .02f
        knockback = -.1f
    }))

    val SAVAGE = register("savage", Prefix(ItemKind.SWORDS, Stats.of {
        damageMul = .1f
        knockback = .15f
    }))
    val SHARP = register("sharp", Prefix(ItemKind.SWORDS, Stats.of {
        damageMul = .15f
        critChance = .02f
    }))
    val DULL = register("dull", Prefix(ItemKind.SWORDS, Stats.of {
        damageMul = -.15f
        knockback = .15f
        critChance = -.02f
    }))
    val LIGHT = register("light", Prefix(ItemKind.HELD, Stats.of {
        attackSpeed = .25f
        knockback = -.4f
    }))
    val LEGENDARY = register("legendary", Prefix(ItemKind.HELD, Stats.of {
        damageMul = .15f
        attackSpeed = .12f
        critDamage = .2f
        knockback = .1f
    }))

    val SIGHTED = register("sighted", Prefix(ItemKind.BOWS, Stats.of {
        damageMul = .1f
        critChance = .2f
    }))
    val RAPID = register("rapid", Prefix(ItemKind.BOWS, Stats.of {
        attackSpeed = .2f
        knockback = -.1f
    }))
    val FRENZYING = register("frenzying", Prefix(ItemKind.BOWS, Stats.of {
        attackSpeed = .1f
        damageMul = .1f
        knockback = .1f
    }))
    val AWKWARD = register("awkward", Prefix(ItemKind.BOWS, Stats.of {
        attackSpeed = -.1f
        knockback = -.2f
    }))
    val LETHARGIC = register("lethargic", Prefix(ItemKind.BOWS, Stats.of {
        attackSpeed = -.2f
    }))
    val UNREAL = register("unreal", Prefix(ItemKind.BOWS, Stats.of {
        damageMul = .15f
        attackSpeed = .1f
        critChance = .05f
        critDamage = .2f
        knockback = .2f
    }))

    val ADEPT = register("adept", Prefix(ItemKind.STAVES, Stats.of {
        manaCostMul = -.15f
    }))
    val MASTERFUL = register("masterful", Prefix(ItemKind.STAVES, Stats.of {
        damageMul = .15f
        manaCostMul = -.15f
        knockback = .05f
    }))
    val INEPT = register("inept", Prefix(ItemKind.STAVES, Stats.of {
        manaCostMul = .15f
    }))
    val DERANGED = register("deranged", Prefix(ItemKind.STAVES, Stats.of {
        manaCostMul = .2f
        damageMul = .1f
    }))
    val TABOO = register("taboo", Prefix(ItemKind.STAVES, Stats.of {
        attackSpeed = .1f
        manaCostMul = .1f
        knockback = .15f
    }))
    val ENCHANTED = register("enchanted", Prefix(ItemKind.STAVES, Stats.of {
        attackSpeed = -.1f
        damageMul = .1f
        manaCostMul = -.1f
    }))
    val MANIC = register("manic", Prefix(ItemKind.STAVES, Stats.of {
        attackSpeed = .2f
        damageMul = -.1f
        manaCostMul = -.05f
    }))
    val DEPRESSED = register("depressed", Prefix(ItemKind.STAVES, Stats.of {
        attackSpeed = -.15f
        damageMul = -.1f
    }))
    val MYTHICAL = register("mythical", Prefix(ItemKind.STAVES, Stats.of {
        damageMul = .15f
        attackSpeed = .1f
        critChance = .05f
        critDamage = .2f
        manaCostMul = -.2f
    }))
    // </editor-fold>

    private fun register(name: String, pfx: Prefix) {
        Registry.register(EmpyreanRegistries.PREFIX, "empyrean:$name", pfx)
    }
}