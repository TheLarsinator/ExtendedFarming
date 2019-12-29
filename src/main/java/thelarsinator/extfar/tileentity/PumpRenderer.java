package thelarsinator.extfar.tileentity;

import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PumpRenderer extends TileEntityRenderer<PumpTileEntity> {
    @SuppressWarnings("deprecation")
    @Override
    public void render(PumpTileEntity tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage){

    }
}
