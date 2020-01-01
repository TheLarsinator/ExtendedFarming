package thelarsinator.extfar.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import thelarsinator.extfar.animals.entity.GoatEntity;
import thelarsinator.extfar.registry.ItemRegistry;

import javax.annotation.Nonnull;
import java.util.Random;

public class MilkingStationBlock extends HorizontalBlock {

    public static final int MAX_MILK_LEVEL = 5;

    public static final BooleanProperty HAS_GOAT = BooleanProperty.create("has_goat");
    public static final IntegerProperty MILK_LEVEL = IntegerProperty.create("milk_level", 0, MAX_MILK_LEVEL);

    private static final VoxelShape GUARD_SHAPE = VoxelShapes.or(
            Block.makeCuboidShape(0.0D, 1.0D, 0.0D, 16.0D, 17.0D, 1.0D),
            Block.makeCuboidShape(0.0D, 1.0D, 0.0D, 1.0D, 17.0D, 16.0D),
            Block.makeCuboidShape(15.0D, 1.0D, 0.0D, 16.0D, 17.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 1.0D, 15.0D, 16.0D, 17.0D, 16.0D));

    private static final VoxelShape GUARD_SHAPE_FULL = VoxelShapes.or(
            Block.makeCuboidShape(0.0D, 1.0D, 0.0D, 16.0D, 25.0D, 1.0D),
            Block.makeCuboidShape(0.0D, 1.0D, 0.0D, 1.0D, 25.0D, 16.0D),
            Block.makeCuboidShape(15.0D, 1.0D, 0.0D, 16.0D, 25.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 1.0D, 15.0D, 16.0D, 25.0D, 16.0D));

    private static final VoxelShape LID = Block.makeCuboidShape(0.0D, 19.0D, 0.0D, 16.0D, 20.0D, 16.0D);

    private static final VoxelShape SHAPE = VoxelShapes.or(
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D));

    public MilkingStationBlock() {
        super(Properties.create(Material.ROCK).tickRandomly().hardnessAndResistance(3.0f));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite()).with(HAS_GOAT, false).with(MILK_LEVEL, 0);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack itemstack = player.getHeldItem(handIn);
        int milk_level = state.get(MILK_LEVEL);
        if(milk_level > 0) {
            if (itemstack.getItem() == Items.BUCKET) {
                player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
                itemstack.shrink(1);
                if (itemstack.isEmpty()) {
                    player.setHeldItem(handIn, new ItemStack(ItemRegistry.goat_milk_bucket));
                } else if (!player.inventory.addItemStackToInventory(new ItemStack(ItemRegistry.goat_milk_bucket))) {
                    player.dropItem(new ItemStack(ItemRegistry.goat_milk_bucket), false);
                }
                worldIn.setBlockState(pos, state.with(MILK_LEVEL, milk_level-1), 3);
                return true;
            }
        }

        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if(entityIn instanceof GoatEntity){
            if(!state.get(HAS_GOAT) && state.get(MILK_LEVEL) < MAX_MILK_LEVEL){
                GoatEntity goat = (GoatEntity)entityIn;
                if(goat.getMilkedByMachine(worldIn)){
                    goat.setPosition(pos.getX()+0.5f, pos.getY(), pos.getZ()+0.5f);
                    worldIn.setBlockState(pos, state.with(HAS_GOAT, true), 3);
                }
            }
        }
    }

    /**
     * Ticks the block if it's been scheduled
     */
    @SuppressWarnings("deprecation")
    @Override
    public void tick(BlockState stateIn, World world, BlockPos pos, Random rand) {
        if(stateIn.get(HAS_GOAT)){
            world.setBlockState(pos, stateIn.with(HAS_GOAT, false).with(MILK_LEVEL, Math.min(stateIn.get(MILK_LEVEL) +1, MAX_MILK_LEVEL)), 3);
            //Spawn particles for awesomeness
            for(float a = -1.0F; a <= 1F; ++a)
            {
                for(float c = -1.0F; c <= 1.0F; ++c)
                {
                    world.addParticle(ParticleTypes.ITEM_SNOWBALL, pos.getX() + 0.5D, pos.getY()+0.8F, pos.getZ() + 0.5D, (double)a/10, 0.0D, (double)c/10);
                }
            }
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HAS_GOAT);
        builder.add(MILK_LEVEL);
        builder.add(HORIZONTAL_FACING);
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        VoxelShape out = SHAPE;
        if(state.get(HAS_GOAT)){
            out = VoxelShapes.or(SHAPE, GUARD_SHAPE);
        }
        return out;
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        VoxelShape out = SHAPE;
        if(state.get(HAS_GOAT)){
            out = VoxelShapes.or(out, LID, GUARD_SHAPE_FULL);
        }
        return out;
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

    @Nonnull
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
