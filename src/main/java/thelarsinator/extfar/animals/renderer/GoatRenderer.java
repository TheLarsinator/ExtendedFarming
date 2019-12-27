package thelarsinator.extfar.animals.renderer;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import thelarsinator.extfar.Reference;
import thelarsinator.extfar.animals.entity.GoatEntity;
import thelarsinator.extfar.animals.model.GoatModel;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class GoatRenderer extends MobRenderer<GoatEntity, GoatModel> {
    private static final ResourceLocation GOAT_TEXTURE = new ResourceLocation(Reference.MODID, "textures/animals/goat.png");

    private GoatRenderer(EntityRendererManager rendererManager) {
        super(rendererManager, new GoatModel(), 0.5f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull GoatEntity entity) {
        return GOAT_TEXTURE;
    }

    @Override
    protected boolean canRenderName(GoatEntity entity) {
        return entity.hasCustomName();
    }

    public static class RenderFactory implements IRenderFactory<GoatEntity> {

        @Override
        public EntityRenderer<? super GoatEntity> createRenderFor(EntityRendererManager manager) {
            return new GoatRenderer(manager);
        }
    }
}
