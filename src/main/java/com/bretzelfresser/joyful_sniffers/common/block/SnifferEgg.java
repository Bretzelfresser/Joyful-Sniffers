package com.bretzelfresser.joyful_sniffers.common.block;

import com.bretzelfresser.joyful_sniffers.common.entity.Sniffer;
import com.bretzelfresser.joyful_sniffers.core.init.BlockInit;
import com.bretzelfresser.joyful_sniffers.core.init.EntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Comparator;

public class SnifferEgg extends Block implements SimpleWaterloggedBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty HATCH = IntegerProperty.create("hatch", 0, 4);

    public SnifferEgg() {
        super(BlockBehaviour.Properties.of(Material.EGG).strength(4).noOcclusion().randomTicks());
        this.registerDefaultState(this.stateDefinition.any().setValue(HATCH, 0).setValue(WATERLOGGED, false));
    }


    @Override
    public FluidState getFluidState(BlockState p_51581_) {
        return p_51581_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_51581_);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
        if (shouldUpdateHatchLevel(level)) {
            int i = state.getValue(HATCH);
            System.out.println(HATCH.getPossibleValues().stream().max(Comparator.naturalOrder()).get());
            if (i < HATCH.getPossibleValues().stream().max(Comparator.naturalOrder()).get()) {
                level.playSound((Player) null, pos, SoundEvents.TURTLE_EGG_CRACK, SoundSource.BLOCKS, 0.7F, 0.9F + rand.nextFloat() * 0.2F);
                level.setBlock(pos, state.setValue(HATCH, Integer.valueOf(i + 1)), 2);
            } else {
                level.playSound((Player) null, pos, SoundEvents.TURTLE_EGG_HATCH, SoundSource.BLOCKS, 0.7F, 0.9F + rand.nextFloat() * 0.2F);
                level.removeBlock(pos, false);

                level.levelEvent(2001, pos, Block.getId(state));
                Sniffer sniffer = EntityInit.SNIFFER.get().create(level);
                sniffer.setBaby(true);
                sniffer.moveTo((double) pos.getX() + 0.3D +  0.2D, pos.getY(), (double) pos.getZ() + 0.3D, 0.0F, 0.0F);
                level.addFreshEntity(sniffer);
            }
        }
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return Block.box(3, 0, 3, 13, 13, 13);
    }

    private boolean canDestroyEgg(Level level, Entity entity) {
        if (!(entity instanceof Turtle) && !(entity instanceof Bat)) {
            if (!(entity instanceof LivingEntity)) {
                return false;
            } else {
                return entity instanceof Player || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(level, entity);
            }
        } else {
            return false;
        }
    }

    private void destroyEgg(Level level, BlockPos pos, BlockState state, Entity destroyer, int prob) {
        if (this.canDestroyEgg(level, destroyer)) {
            if (!level.isClientSide && level.random.nextInt(prob) == 0 && state.is(BlockInit.SNIFFER_EGG.get())) {
                level.playSound(null, pos, SoundEvents.TURTLE_EGG_BREAK, SoundSource.BLOCKS, 0.7F, 0.9F + level.random.nextFloat() * 0.2F);
                level.destroyBlock(pos, false);
            }
        }
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity destroyer) {
        this.destroyEgg(level, pos, state, destroyer, 100);
        super.stepOn(level, pos, state, destroyer);
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity destroyer, float damage) {
        this.destroyEgg(level, pos, state, destroyer, 3);
        super.fallOn(level, state, pos, destroyer, damage);
    }

    private boolean shouldUpdateHatchLevel(Level p_57766_) {
        float f = p_57766_.getTimeOfDay(1.0F);
        if (f < 0.69F && f > 0.65F) {
            return true;
        } else {
            //return p_57766_.random.nextInt((int) (500f / 7f)) == 0;
            return true;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED, HATCH);
    }
}
