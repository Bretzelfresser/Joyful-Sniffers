package com.bretzelfresser.joyful_sniffers.core.datagen.server;

import com.bretzelfresser.joyful_sniffers.JoyfulSniffers;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(DataGenerator p_i244817_1_, BlockTagsProvider p_i244817_2_, @Nullable ExistingFileHelper p_i244817_4_) {
        super(p_i244817_1_, p_i244817_2_, JoyfulSniffers.MODID, p_i244817_4_);
    }

    @Override
    protected void addTags() {
    }
}
