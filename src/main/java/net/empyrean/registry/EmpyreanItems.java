package net.empyrean.registry;

import io.wispforest.owo.registration.reflect.ItemRegistryContainer;
import net.empyrean.item.impl.weapon.AspectOfTheEnd;
import net.minecraft.world.item.Item;

@SuppressWarnings("unused")
public class EmpyreanItems implements ItemRegistryContainer {
    // Not creating a private constructor here because owo needs a default public ctor
    public static final Item ASPECT_OF_THE_END = new AspectOfTheEnd();
}
