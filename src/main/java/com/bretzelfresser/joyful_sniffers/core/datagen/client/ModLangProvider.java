package com.bretzelfresser.joyful_sniffers.core.datagen.client;

import com.bretzelfresser.joyful_sniffers.JoyfulSniffers;
import com.bretzelfresser.joyful_sniffers.core.init.BlockInit;
import com.bretzelfresser.joyful_sniffers.core.init.ItemInit;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLangProvider extends LanguageProvider {
    public ModLangProvider(DataGenerator gen) {
        super(gen, JoyfulSniffers.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(BlockInit.SNIFFER_EGG.get(), "Sniffer Egg");
        add(ItemInit.SNIFFER_SPAWN_EGG.get(), "Sniffer Spawn Egg");
        add(BlockInit.ALGAE.get(), "Algae");
    }
}
