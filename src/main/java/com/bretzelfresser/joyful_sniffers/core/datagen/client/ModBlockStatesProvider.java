package com.bretzelfresser.joyful_sniffers.core.datagen.client;

import com.bretzelfresser.joyful_sniffers.JoyfulSniffers;
import com.bretzelfresser.joyful_sniffers.common.block.SnifferEgg;
import com.bretzelfresser.joyful_sniffers.core.init.BlockInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockStatesProvider extends BlockStateProvider {
    public ModBlockStatesProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, JoyfulSniffers.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        createSnifferEgg();
        simpleBlock(BlockInit.ALGAE.get());
        simple(BlockInit.ALGAE.get().asItem());
    }

    private void block(Block block){
        simpleBlock(block);
        simpleBlockItem(block, cubeAll(block));
    }

    private void simple(Item... items) {
        for (Item item : items) {
            String name = ForgeRegistries.ITEMS.getKey(item).getPath();
            itemModels().getBuilder(name).parent(itemModels().getExistingFile(mcLoc("item/generated"))).texture("layer0", "item/" + name);
        }
    }
    private void createSnifferEgg(){
        getVariantBuilder(BlockInit.SNIFFER_EGG.get()).forAllStates(state -> {
            ConfiguredModel.Builder builder = ConfiguredModel.builder();
            String name = "sniffer_egg";
            if (state.getValue(SnifferEgg.HATCH) >= 4){
                name += "_cracked";
            }
            builder.modelFile(existingBlock(name));
            return builder.build();
        });
        simpleBlockItem(BlockInit.SNIFFER_EGG.get(), existingBlock("sniffer_egg"));
    }

    protected ModelFile existingBlock(String path){
        return new ModelFile.ExistingModelFile(modLoc("block/" + path), models().existingFileHelper);
    }
}
