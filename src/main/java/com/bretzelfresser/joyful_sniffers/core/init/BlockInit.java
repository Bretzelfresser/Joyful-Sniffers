package com.bretzelfresser.joyful_sniffers.core.init;

import com.bretzelfresser.joyful_sniffers.JoyfulSniffers;
import com.bretzelfresser.joyful_sniffers.common.block.AlgaeBlock;
import com.bretzelfresser.joyful_sniffers.common.block.DoublePlantBlock;
import com.bretzelfresser.joyful_sniffers.common.block.SnifferEgg;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;
import java.util.function.Supplier;

public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, JoyfulSniffers.MODID);

    public static final RegistryObject<SnifferEgg> SNIFFER_EGG = register("sniffer_egg", SnifferEgg::new, CreativeModeTab.TAB_MISC);
    public static final RegistryObject<Block> ALGAE = register("algae", AlgaeBlock::new, b -> new PlaceOnWaterBlockItem(b, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<DoublePlantBlock> SNIFF_SEEDS = register("sniff_spores", () -> new DoublePlantBlock(BlockBehaviour.Properties.of(Material.PLANT).instabreak().randomTicks().noCollission().noOcclusion().sound(SoundType.CROP)), CreativeModeTab.TAB_BUILDING_BLOCKS);


    public static final <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, CreativeModeTab tab){
        return register(name, blockSupplier, () -> new Item.Properties().tab(tab));
    }

    public static final <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, Supplier<Item.Properties> properties){
        return register(name, blockSupplier, b -> new BlockItem(b, properties.get()));
    }

    public static final <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, Function<Block, Item> blockItemFunction){
        RegistryObject<T> block = BLOCKS.register(name, blockSupplier);
        ItemInit.ITEMS.register(name, () -> blockItemFunction.apply(block.get()));
        return block;
    }
}
