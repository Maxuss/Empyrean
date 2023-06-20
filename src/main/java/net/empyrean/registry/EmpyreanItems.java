package net.empyrean.registry;

import dev.emi.trinkets.api.TrinketItem;
import dev.emi.trinkets.api.client.TrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import io.wispforest.owo.registration.reflect.ItemRegistryContainer;
import net.empyrean.item.impl.weapon.AspectOfTheEnd;
import net.empyrean.item.impl.weapon.TestItem;
import net.empyrean.item.impl.wings.FledglingWings;
import net.minecraft.world.item.Item;

import java.lang.reflect.Field;

@SuppressWarnings("unused")
public class EmpyreanItems implements ItemRegistryContainer {
    // Not creating a private constructor here because owo needs a default public ctor
    public static final Item ASPECT_OF_THE_END = new AspectOfTheEnd();
    public static final Item COSMILIUM_INGOT = new TestItem();
    public static final Item FLEDGLING_WINGS = new FledglingWings();
}
