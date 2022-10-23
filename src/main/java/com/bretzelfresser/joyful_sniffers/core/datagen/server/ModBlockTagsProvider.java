package com.bretzelfresser.joyful_sniffers.core.datagen.server;

import com.bretzelfresser.joyful_sniffers.JoyfulSniffers;
import com.bretzelfresser.joyful_sniffers.core.init.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(DataGenerator p_i244820_1_, @Nullable ExistingFileHelper p_i244820_3_) {
        super(p_i244820_1_, JoyfulSniffers.MODID, p_i244820_3_);
    }

    @Override
    protected void addTags() {
        tag(ModTags.Blocks.SNIFFER_GROUND).add(Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.PODZOL, Blocks.MYCELIUM, Blocks.COARSE_DIRT, Blocks.ROOTED_DIRT, Blocks.MUD, Blocks.SAND, Blocks.GRAVEL, Blocks.DIRT_PATH);
    }
}
