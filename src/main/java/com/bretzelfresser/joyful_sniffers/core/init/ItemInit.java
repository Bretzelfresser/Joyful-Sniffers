package com.bretzelfresser.joyful_sniffers.core.init;

import com.bretzelfresser.joyful_sniffers.JoyfulSniffers;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, JoyfulSniffers.MODID);

    public static final RegistryObject<ForgeSpawnEggItem> SNIFFER_SPAWN_EGG = ITEMS.register("sniffer_spawn_egg", () -> new ForgeSpawnEggItem(() -> EntityInit.SNIFFER.get(), 0x8c2d31, 0x488456, new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC)));

}
