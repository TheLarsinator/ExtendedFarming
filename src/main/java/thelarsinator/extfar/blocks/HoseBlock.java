package thelarsinator.extfar.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import thelarsinator.extfar.tileentity.SprayerTileEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HoseBlock extends WateringBlockBase {
    private static final VoxelShape SHAPE = VoxelShapes.or(
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D));

    public static final BooleanProperty OMHOOG = BooleanProperty.create("omhoog");
    public static final BooleanProperty OMLAAG = BooleanProperty.create("omlaag");

    public HoseBlock() {
        super(Properties.create(Material.WOOD).hardnessAndResistance(2.0f));
        this.setDefaultState(this.stateContainer.getBaseState().with(IS_ACTIVE, false).with(WEST_CON,
                false).with(EAST_CON, false).with(NORTH_CON, false).with(SOUTH_CON, false).with(OMHOOG, false).with(OMLAAG, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(OMHOOG);
        builder.add(OMLAAG);
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

    @SuppressWarnings("deprecation")
    @Override
    @Nonnull
    public BlockState updatePostPlacement(@Nonnull BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
                                          BlockPos currentPos, BlockPos facingPos) {
        BlockState tempState = checkForConnections(stateIn, worldIn.getWorld(), currentPos);
        return checkWaterPressure(tempState, worldIn.getWorld(), currentPos);
    }

    protected BlockState checkWaterPressure(@Nonnull BlockState stateIn, @Nonnull World world, @Nonnull BlockPos pos){
        int highestPressure = 0;
        if(stateIn.get(NORTH_CON)){
            int pres = world.getBlockState(pos.north()).get(WATER_PRESSURE);
            if(world.getBlockState(pos.north()).get(WATER_PRESSURE) > highestPressure){
                highestPressure = pres;
            }
        }
        if(stateIn.get(SOUTH_CON)){
            int pres = world.getBlockState(pos.south()).get(WATER_PRESSURE);
            if(world.getBlockState(pos.south()).get(WATER_PRESSURE) > highestPressure){
                highestPressure = pres;
            }
        }
        if(stateIn.get(EAST_CON)){
            int pres = world.getBlockState(pos.east()).get(WATER_PRESSURE);
            if(world.getBlockState(pos.east()).get(WATER_PRESSURE) > highestPressure){
                highestPressure = pres;
            }
        }
        if(stateIn.get(WEST_CON)){
            int pres = world.getBlockState(pos.west()).get(WATER_PRESSURE);
            if(world.getBlockState(pos.west()).get(WATER_PRESSURE) > highestPressure){
                highestPressure = pres;
            }
        }
        if(stateIn.get(OMHOOG)){
            int pres = world.getBlockState(pos.up()).get(WATER_PRESSURE);
            if(world.getBlockState(pos.up()).get(WATER_PRESSURE) > highestPressure){
                highestPressure = pres;
            }
        }
        if(stateIn.get(OMLAAG)){
            int pres = world.getBlockState(pos.down()).get(WATER_PRESSURE);
            if(world.getBlockState(pos.down()).get(WATER_PRESSURE) > highestPressure){
                highestPressure = pres;
            }
        }
        int water_pressure = Math.max(highestPressure-1, 0);
        boolean is_active = water_pressure > 0;
        return stateIn.with(WATER_PRESSURE, water_pressure).with(IS_ACTIVE, is_active);
    }

    protected BlockState checkForConnections(@Nonnull BlockState stateIn, @Nonnull World world, @Nonnull BlockPos pos){
        return stateIn
                .with(NORTH_CON, checkIfConnection(world, pos.north()))
                .with(SOUTH_CON, checkIfConnection(world, pos.south()))
                .with(EAST_CON, checkIfConnection(world, pos.east()))
                .with(WEST_CON, checkIfConnection(world, pos.west()))
                .with(OMHOOG, checkIfConnection(world, pos.up()))
                .with(OMLAAG, checkIfConnection(world, pos.down()));
    }
}
