package net.empyrean.registry;

import io.wispforest.owo.registration.reflect.ItemRegistryContainer;
import net.empyrean.item.impl.weapon.AspectOfTheEnd;
import net.minecraft.world.item.Item;

@SuppressWarnings("unused")
public class EmpyreanItems implements ItemRegistryContainer {
    private EmpyreanItems() {

    }

    public static final Item ASPECT_OF_THE_END = new AspectOfTheEnd();
}
