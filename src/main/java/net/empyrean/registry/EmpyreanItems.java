package net.empyrean.registry;

import io.wispforest.owo.registration.reflect.ItemRegistryContainer;
import net.empyrean.item.impl.material.MaterialItem;
import net.empyrean.item.impl.weapon.AspectOfTheEnd;
import net.empyrean.item.impl.weapon.MetastaticAxe;
import net.empyrean.item.impl.wings.AngelWings;
import net.empyrean.item.impl.wings.FledglingWings;
import net.empyrean.item.impl.wings.InsaneWings;
import net.empyrean.item.rarity.ItemRarity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.world.item.Item;

@SuppressWarnings("unused")
public class EmpyreanItems implements ItemRegistryContainer {
    // Not creating a private constructor here because owo needs a default public ctor

    // <editor-fold desc="Weapons">
    public static final Item ASPECT_OF_THE_END = new AspectOfTheEnd();
    public static final Item METASTATIC_AXE = new MetastaticAxe();
    // </editor-fold>

    // <editor-fold desc="Wings">
    public static final Item FLEDGLING_WINGS = new FledglingWings();
    public static final Item INSANE_WINGS = new InsaneWings();
    public static final Item ANGEL_WINGS = new AngelWings();
    // </editor-fold>

    // <editor-fold desc="Raw Materials">
    public static final Item RAW_GEYSERITE = new MaterialItem(new FabricItemSettings(), ItemRarity.RARE);
    // </editor-fold>

    // <editor-fold desc="Materials">
    public static final Item COSMILIUM_INGOT = new MaterialItem(new FabricItemSettings(), ItemRarity.STARLIKE);
    public static final Item PRECURSOR_ASHES = new MaterialItem(new FabricItemSettings().fireproof(), ItemRarity.SMOLDERING);
    // </editor-fold>
}
