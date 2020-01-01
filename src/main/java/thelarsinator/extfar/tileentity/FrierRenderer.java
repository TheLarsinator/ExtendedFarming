package thelarsinator.extfar.tileentity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import sun.security.util.Debug;
import thelarsinator.extfar.registry.ItemRegistry;

import static thelarsinator.extfar.blocks.FrierBlock.*;

@OnlyIn(Dist.CLIENT)
public class FrierRenderer extends TileEntityRenderer<FrierTileEntity> {
    @SuppressWarnings("deprecation")
    @Override
    public void render(FrierTileEntity tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage){
        BlockState state = tileEntityIn.getBlockState();

        GlStateManager.pushMatrix();
        GlStateManager.translatef((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);
        GlStateManager.rotatef(90 , 1.0F, 0.0F, 0.0F);

        GlStateManager.scalef(0.6f, 0.6f, 0.6f);
        if(state.get(HAS_FRIES)) {
            Item item = ItemRegistry.raw_fries;
            float floating_height = 0f;
            if(state.get(COOK_LEVEL) == MAX_COOK_LEVEL){
                floating_height = 0.3f;
                item = ItemRegistry.fries;
            }
            GlStateManager.translatef(0F, 0F, -floating_height);
            Minecraft.getInstance().getItemRenderer().renderItem(new ItemStack(item, 1), ItemCameraTransforms.TransformType.FIXED);
            GlStateManager.translatef(0F, 0F, floating_height);
        }
        if(state.get(HAS_OIL)) {

            GlStateManager.translatef(0F, 0F, -(0.25f));
            GlStateManager.scalef(1.42f, 1.42f, 1.42f);

            Minecraft.getInstance().getItemRenderer().renderItem(new ItemStack(Blocks.YELLOW_STAINED_GLASS_PANE, 1), ItemCameraTransforms.TransformType.FIXED);
        }
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }
}
