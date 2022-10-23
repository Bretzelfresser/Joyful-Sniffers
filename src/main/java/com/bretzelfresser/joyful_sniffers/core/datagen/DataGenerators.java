package com.bretzelfresser.joyful_sniffers.core.datagen;

import com.bretzelfresser.joyful_sniffers.JoyfulSniffers;
import com.bretzelfresser.joyful_sniffers.core.datagen.client.ModBlockStatesProvider;
import com.bretzelfresser.joyful_sniffers.core.datagen.client.ModItemModelsProvider;
import com.bretzelfresser.joyful_sniffers.core.datagen.client.ModLangProvider;
import com.bretzelfresser.joyful_sniffers.core.datagen.server.ModLootTableProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;

@Mod.EventBusSubscriber(modid = JoyfulSniffers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();

        if (event.includeClient())
            gatherClientData(gen, helper);
        if (event.includeServer())
            gatherServerData(gen, helper);
        try {
            gen.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void gatherClientData(DataGenerator gen, ExistingFileHelper helper) {
        gen.addProvider(true, new ModBlockStatesProvider(gen, helper));
        gen.addProvider(true, new ModItemModelsProvider(gen, helper));
    }

    private static void gatherServerData(DataGenerator gen, ExistingFileHelper helper) {
        gen.addProvider(true, new ModLangProvider(gen));
        gen.addProvider(true, new ModLootTableProvider(gen));
    }
}
