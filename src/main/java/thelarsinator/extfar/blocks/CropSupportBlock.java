package thelarsinator.extfar.blocks;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import thelarsinator.extfar.registry.BlockRegistry;
import thelarsinator.extfar.registry.ItemRegistry;

import java.util.HashMap;

import static net.minecraft.block.CropsBlock.AGE;
import static thelarsinator.extfar.blocks.WateringBlockBase.IS_ACTIVE;

public class CropSupportBlock extends BushBlock
{
    public static final BooleanProperty HAS_NET = BooleanProperty.create("has_net");

    public CropSupportBlock() {
        super(Properties.create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0F).sound(SoundType.PLANT));
        this.setDefaultState(this.stateContainer.getBaseState().with(HAS_NET, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(HAS_NET);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.down()).getBlock() == Blocks.FARMLAND;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack itemStack = player.getHeldItem(handIn);

        if(itemStack.getItem().equals(ItemRegistry.net) && !state.get(HAS_NET)) {
            worldIn.setBlockState(pos, state.with(HAS_NET, true));
            itemStack.shrink(1);
            return true;
        }

        //TODO: Tried to set up a map from item to block and to this by looking up the match, but the items arent initialized when the block is registered causing null-entries.
        else if(itemStack.getItem().equals(ItemRegistry.beans)){
            acceptPlant(state, BlockRegistry.beans, worldIn, pos, player);
            itemStack.shrink(1);
            return true;
        } else if(itemStack.getItem().equals(ItemRegistry.chilli_pepper)){
            acceptPlant(state, BlockRegistry.chilli_pepper, worldIn, pos, player);
            itemStack.shrink(1);
            return true;
        }
        return false;
    }

    protected void acceptPlant(BlockState state, CropBlock block, World worldIn, BlockPos pos, PlayerEntity player){
        worldIn.setBlockState(pos, block.getDefaultState(), 3);
        if(state.get(HAS_NET)){
            if (!player.inventory.addItemStackToInventory(new ItemStack(ItemRegistry.net))) {
                player.dropItem(new ItemStack(ItemRegistry.net), false);
            }
        }
    }
}
