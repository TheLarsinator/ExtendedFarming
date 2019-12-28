package thelarsinator.extfar.registry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.MilkBucketItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static thelarsinator.extfar.Reference.MODID;

@SuppressWarnings({"unused", "WeakerAccess"})
@ObjectHolder(MODID)
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemRegistry {

    //Goat items
    public static final Item goat_milk_bucket = null;

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        registry.register(new MilkBucketItem(new Item.Properties().group(ItemGroup.MISC).maxStackSize(1)).setRegistryName(MODID, "goat_milk_bucket"));
    }
}
