package thelarsinator.extfar.core.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import thelarsinator.extfar.registry.EntityRegistry;

public class ClientProxy implements IProxy {

    @Override
    public void init() {
        //Register Entities
        EntityRegistry.registerEntityRenderers();
    }

    @Override
    public World getClientWorld() {
        return Minecraft.getInstance().world;
    }

    @Override
    public PlayerEntity getClientPlayer() {
        return Minecraft.getInstance().player;
    }
}
