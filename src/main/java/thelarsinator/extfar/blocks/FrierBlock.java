package thelarsinator.extfar.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import thelarsinator.extfar.registry.ItemRegistry;
import thelarsinator.extfar.tileentity.FrierTileEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class FrierBlock extends HorizontalBlock {

    public static final int MAX_COOK_LEVEL = 3;
    public static final BooleanProperty HAS_OIL = BooleanProperty.create("has_oil");
    public static final BooleanProperty HAS_FRIES = BooleanProperty.create("has_fries");
    public static final IntegerProperty COOK_LEVEL = IntegerProperty.create("cook_level", 0, MAX_COOK_LEVEL);

    private static final VoxelShape SHAPE = VoxelShapes.or(
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 13.0D, 1.0D),
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 13.0D, 16.0D),
            Block.makeCuboidShape(15.0D, 0.0D, 0.0D, 16.0D, 13.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 0.0D, 15.0D, 16.0D, 13.0D, 16.0D));

    public FrierBlock() {
        super(Properties.create(Material.IRON).hardnessAndResistance(6.0f).tickRandomly());
    }


    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new FrierTileEntity();
    }

    @Nonnull
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    @Nonnull
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public boolean isVariableOpacity() {
        return true;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite()).with(HAS_OIL, false).with(HAS_FRIES, false).with(COOK_LEVEL, 0);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING);
        builder.add(HAS_OIL);
        builder.add(HAS_FRIES);
        builder.add(COOK_LEVEL);
    }

    private static final VoxelShape INSIDE = makeCuboidShape(2.0D, 1.0D, 2.0D, 14.0D, 13.0D, 14.0D);

    @SuppressWarnings("deprecation")
    public boolean isSolid(BlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return INSIDE;
    }


    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack itemStack = player.getHeldItem(handIn);

        if(itemStack.getItem().equals(ItemRegistry.cannister_canola_oil) && !state.get(HAS_OIL)) {
            worldIn.setBlockState(pos, state.with(HAS_OIL, true), 3);
            itemStack.shrink(1);
            return true;
        }
        if(itemStack.getItem().equals(ItemRegistry.raw_fries) && !state.get(HAS_FRIES)) {
            worldIn.setBlockState(pos, state.with(HAS_FRIES, true).with(COOK_LEVEL, 0), 3);
            itemStack.shrink(1);
            return true;
        } else if(state.get(HAS_FRIES) && state.get(COOK_LEVEL) == MAX_COOK_LEVEL){
            if (!player.inventory.addItemStackToInventory(new ItemStack(ItemRegistry.fries))) {
                player.dropItem(new ItemStack(ItemRegistry.fries), false);
            }
            worldIn.setBlockState(pos, state.with(HAS_FRIES, false).with(COOK_LEVEL, 0), 3);
            return true;
        }
        return false;
    }

    public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
        super.tick(state, worldIn, pos, random);
        int cook_level = state.get(COOK_LEVEL);
        if(cook_level < MAX_COOK_LEVEL && state.get(HAS_FRIES)) {
                worldIn.setBlockState(pos, state.with(COOK_LEVEL, cook_level+1), 3);
        }
    }
}
