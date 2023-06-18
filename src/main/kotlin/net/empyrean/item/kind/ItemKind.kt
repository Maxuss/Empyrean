package net.empyrean.item.kind

enum class ItemKind {
    SWORD,
    PICKAXE,
    SHOVEL,
    WINGS,

    ;

    val named: String = name.replace("_", " ")
}