package com.bretzelfresser.joyful_sniffers.core.init;

import com.bretzelfresser.joyful_sniffers.JoyfulSniffers;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static final class Blocks{

        public static final TagKey<Block> SNIFFER_GROUND = mod("sniffer_ground");

        public static TagKey<Block> mod(String name){
            return BlockTags.create(JoyfulSniffers.modLoc(name));
        }
    }
}
