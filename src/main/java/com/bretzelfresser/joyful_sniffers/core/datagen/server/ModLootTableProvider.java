package com.bretzelfresser.joyful_sniffers.core.datagen.server;

import com.bretzelfresser.joyful_sniffers.core.init.BlockInit;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModLootTableProvider extends LootTableProvider {
    public ModLootTableProvider(DataGenerator p_124437_) {
        super(p_124437_);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return ImmutableList.of(
                Pair.of(ModBlockLoot::new, LootContextParamSets.BLOCK)
        );
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
        map.forEach((id, table) -> LootTables.validate(validationtracker, id, table));
    }

    public static final class ModBlockLoot extends BlockLoot {
     private List<Block> knownBlocks = Lists.newArrayList();

        @Override
        protected void addTables() {
            dropSelf(BlockInit.ALGAE.get());
            dropWhenSilkTouch(BlockInit.SNIFFER_EGG.get());
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return this.knownBlocks;
        }


        @Override
        protected void add(Block block, LootTable.Builder builder) {
            super.add(block, builder);
            this.knownBlocks.add(block);
        }

    }
}
