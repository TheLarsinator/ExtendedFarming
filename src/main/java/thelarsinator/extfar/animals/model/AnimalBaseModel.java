package thelarsinator.extfar.animals.model;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;

public class AnimalBaseModel<T extends Entity> extends EntityModel<T> {
    /*
        Pair two model parts, and keep them together during rotation
     */
    protected void addChildTo(RendererModel child, RendererModel parent){
        // Move the child to a position that is relative to the parent
        child.rotationPointX -= parent.rotationPointX;
        child.rotationPointY -= parent.rotationPointY;
        child.rotationPointZ -= parent.rotationPointZ;

        child.rotateAngleX -= parent.rotateAngleX;
        child.rotateAngleY -= parent.rotateAngleY;
        child.rotateAngleZ -= parent.rotateAngleZ;
        // Actually add it
        parent.addChild(child);
    }
}
