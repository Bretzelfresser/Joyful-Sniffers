package com.bretzelfresser.joyful_sniffers.core.datagen.client;

import com.bretzelfresser.joyful_sniffers.JoyfulSniffers;
import com.bretzelfresser.joyful_sniffers.core.init.BlockInit;
import com.bretzelfresser.joyful_sniffers.core.init.ItemInit;
import com.bretzelfresser.joyful_sniffers.core.init.ModCreativeTabs;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLangProvider extends LanguageProvider {
    public ModLangProvider(DataGenerator gen) {
        super(gen, JoyfulSniffers.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(BlockInit.SNIFFER_EGG.get(), "Sniffer Egg");
        add(ItemInit.SNIFFER_SPAWN_EGG.get(), "Sniffer SPawn Egg");
        add(ModCreativeTabs.EGGS, "Eggs");
    }

    protected void add(CreativeModeTab group, String key){
        add(group.getRecipeFolderName(), key);
    }
}
