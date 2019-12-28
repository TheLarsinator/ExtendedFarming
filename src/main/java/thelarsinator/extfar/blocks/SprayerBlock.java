package thelarsinator.extfar.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import thelarsinator.extfar.tileentity.SprayerTileEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

import static net.minecraft.block.FarmlandBlock.MOISTURE;

public class SprayerBlock extends HorizontalBlock {
    private static final VoxelShape SHAPE = VoxelShapes.or(
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D),
            Block.makeCuboidShape(7.0D, 0.0D, 7.0D, 9.0D, 12.0D, 9.0D));

    public static final BooleanProperty IS_ACTIVE = BooleanProperty.create("is_active");

    public SprayerBlock() {
        super(Properties.create(Material.WOOD).hardnessAndResistance(2.0f));
        this.setDefaultState(this.stateContainer.getBaseState().with(IS_ACTIVE, false));

    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new SprayerTileEntity();
    }

    @SuppressWarnings("deprecation")
    @Override
    @Nonnull
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING);
        builder.add(IS_ACTIVE);
    }

    @Override
    public boolean isVariableOpacity() {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        worldIn.addParticle(ParticleTypes.SPLASH, pos.getX() + 0.7D, pos.getY()+0.8F, pos.getZ() + 0.7D, (double)0.1, 0.0D, (double)0.1);
        worldIn.setBlockState(pos, state.with(IS_ACTIVE, !state.get(IS_ACTIVE)));
        super.onBlockClicked(state, worldIn, pos, player);
    }

    @Nonnull
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onReplaced(BlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, BlockState newState, boolean isMoving) {
            super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    private final int sprayRange = 5;

    /**
     * Ticks the block if it's been scheduled
     */
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World world, BlockPos pos, Random rand) {
        if(!world.getBlockState(pos).get(IS_ACTIVE)){
            return;
        }
        //Update farmland
        for (int i = -sprayRange; i < sprayRange; i++) {
            for (int k = -sprayRange; k < sprayRange; k++) {
                BlockPos blockPos = new BlockPos(pos.getX() + i, pos.getY() - 1, pos.getZ() + k);
                BlockState blockState = world.getBlockState(blockPos);
                if (blockState.getBlock() == Blocks.FARMLAND) {
                    world.setBlockState(blockPos, blockState.with(MOISTURE, 7));
                }
            }
        }

        //Spawn particles for awesomeness
        for(float a = -5.0F; a <= 5F; ++a)
        {
            for(float c = -5.0F; c <= 5.0F; ++c)
            {
                world.addParticle(ParticleTypes.SPLASH, pos.getX() + 0.5D, pos.getY()+0.8F, pos.getZ() + 0.5D, (double)a/10, 0.0D, (double)c/10);
            }
        }
    }
}
