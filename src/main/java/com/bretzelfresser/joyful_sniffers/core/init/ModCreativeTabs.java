package com.bretzelfresser.joyful_sniffers.core.init;

import com.bretzelfresser.joyful_sniffers.JoyfulSniffers;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public class ModCreativeTabs {

    public static final ModTab EGGS = createMod("eggs", () -> BlockInit.SNIFFER_EGG.get());

    private static ModTab createMod(String name, Supplier<ItemLike> icon) {
        return new ModTab(JoyfulSniffers.MODID + "." + name, icon);
    }

    private static class ModTab extends CreativeModeTab {

        private final Supplier<ItemLike> icon;

        public ModTab(String label, Supplier<ItemLike> icon) {
            super(label);
            this.icon = icon;
        }

        @Override
        public ItemStack makeIcon() {
            return icon.get().asItem().getDefaultInstance();
        }
    }
}
