package thelarsinator.extfar.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import thelarsinator.extfar.tileentity.SprayerTileEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class PumpBlock extends WateringBlockBase {
    private static final VoxelShape SHAPE = VoxelShapes.or(
            Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D),
            Block.makeCuboidShape(7.0D, 4.0D, 7.0D, 9.0D, 12.0D, 9.0D),
            Block.makeCuboidShape(7.0D, 4.0D, 7.0D, 9.0D, 12.0D, 9.0D),
            Block.makeCuboidShape(-2.0D, 12.0D, 7.0D, 18.0D, 13.0D, 9.0D),
            Block.makeCuboidShape(7.0D, 12.0D, -2.0D, 9.0D, 13.0D, 18.0D));

    public PumpBlock() {
        super(Properties.create(Material.WOOD).hardnessAndResistance(2.0f));
        this.setDefaultState(this.stateContainer.getBaseState().with(IS_ACTIVE, false).with(WEST_CON,
                false).with(EAST_CON, false).with(NORTH_CON, false).with(SOUTH_CON, false)
                .with(EAST_PUMP, false).with(WEST_PUMP, false).with(NORTH_PUMP, false).with(SOUTH_PUMP, false));
    }

    public static final BooleanProperty EAST_PUMP = BooleanProperty.create("east_pump");
    public static final BooleanProperty WEST_PUMP = BooleanProperty.create("west_pump");
    public static final BooleanProperty NORTH_PUMP = BooleanProperty.create("north_pump");
    public static final BooleanProperty SOUTH_PUMP = BooleanProperty.create("south_pump");
    public static final BooleanProperty OMLAAG = BooleanProperty.create("omlaag");

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(EAST_PUMP);
        builder.add(WEST_PUMP);
        builder.add(NORTH_PUMP);
        builder.add(SOUTH_PUMP);
        builder.add(OMLAAG);
    }

    @SuppressWarnings("deprecation")
    @Override
    @Nonnull
    public BlockState updatePostPlacement(@Nonnull BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
                                          BlockPos currentPos, BlockPos facingPos) {
        BlockState temp = checkForConnections(stateIn, worldIn.getWorld(), currentPos);
        return checkForWater(temp, worldIn.getWorld(), currentPos);
    }

    protected BlockState checkForWater(@Nonnull BlockState stateIn, @Nonnull World world, @Nonnull BlockPos pos){
        boolean north_pump = checkIfWater(world, pos.north());
        boolean south_pump = checkIfWater(world, pos.south());
        boolean east_pump = checkIfWater(world, pos.east());
        boolean west_pump = checkIfWater(world, pos.west());
        boolean is_active = north_pump | south_pump | east_pump | west_pump;
        int water_pressure = is_active ? 15 : 0;

        return stateIn
                .with(NORTH_PUMP, north_pump)
                .with(SOUTH_PUMP, south_pump)
                .with(EAST_PUMP, east_pump)
                .with(WEST_PUMP, west_pump)
                .with(IS_ACTIVE, is_active)
                .with(WATER_PRESSURE, water_pressure);
    }

    protected boolean checkIfWater(@Nonnull World world, @Nonnull BlockPos pos){
        return world.getBlockState(pos.down()).getBlock().equals(Blocks.WATER);
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

    /**
     * Ticks the block if it's been scheduled
     */
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World world, BlockPos pos, Random rand) {
        if(!world.getBlockState(pos).get(IS_ACTIVE)){
            return;
        }
        if(stateIn.get(NORTH_CON)){
            world.setBlockState(pos.north(), world.getBlockState(pos.north()).with(IS_ACTIVE, true), 3);
        }
        if(stateIn.get(SOUTH_CON)){
            world.setBlockState(pos.south(), world.getBlockState(pos.south()).with(IS_ACTIVE, true), 3);
        }
        if(stateIn.get(EAST_CON)){
            world.setBlockState(pos.east(), world.getBlockState(pos.east()).with(IS_ACTIVE, true), 3);
        }
        if(stateIn.get(WEST_CON)){
            world.setBlockState(pos.west(), world.getBlockState(pos.west()).with(IS_ACTIVE, true), 3);
        }
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
                .with(OMLAAG, checkIfConnection(world, pos.down()));
    }
}
