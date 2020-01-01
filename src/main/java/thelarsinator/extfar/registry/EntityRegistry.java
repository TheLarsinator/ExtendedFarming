package thelarsinator.extfar.registry;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;
import thelarsinator.extfar.Reference;
import thelarsinator.extfar.animals.entity.GoatEntity;
import thelarsinator.extfar.animals.renderer.GoatRenderer;

import java.util.EnumSet;

import static net.minecraft.entity.EntityClassification.CREATURE;
import static net.minecraft.world.biome.Biome.Category.*;

@SuppressWarnings({"unused", "unchecked"})
@ObjectHolder(Reference.MODID)
@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityRegistry {
    public static final EntityType<GoatEntity> goat = (EntityType<GoatEntity>) EntityType.Builder.create(GoatEntity::new, CREATURE)
            .size(0.4f, 0.9f).build("").setRegistryName(Reference.MODID,"goat");

    private static final EnumSet<Biome.Category> goat_biomes = EnumSet.of(FOREST, PLAINS);

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
        IForgeRegistry<EntityType<?>> registry = event.getRegistry();
        registry.register(goat);

        for (Biome biome : ForgeRegistries.BIOMES) {
            if (goat_biomes.contains(biome.getCategory())) {
                biome.getSpawns(goat.getClassification()).add(new Biome.SpawnListEntry(goat, 4, 2, 4));
            }
        }
    }

    @SubscribeEvent
    public static void registerSpawnEggs(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        registry.register(new SpawnEggItem(goat, 0x817700, 0xffffff,
                new Item.Properties().group(ExtfarItemGroup.EXT_FAR)).setRegistryName(Reference.MODID, "goat_spawn_egg"));
    }

    public static void registerEntityRenderers(){
        RenderingRegistry.registerEntityRenderingHandler(GoatEntity.class, new GoatRenderer.RenderFactory());
    }
}
