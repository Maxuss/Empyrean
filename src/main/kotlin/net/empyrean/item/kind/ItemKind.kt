package net.empyrean.item.kind

enum class ItemKind {
    SWORD,
    PICKAXE,
    SHOVEL,
    WINGS,

    MATERIAL,
    MINERAL
    ;

    val named: String get() = if (this == MATERIAL) "" else name.replace("_", " ")
}