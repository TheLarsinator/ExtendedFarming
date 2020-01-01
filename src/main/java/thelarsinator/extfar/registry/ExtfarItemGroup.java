package thelarsinator.extfar.registry;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ExtfarItemGroup extends ItemGroup {
    public static final ItemGroup EXT_FAR = new ExtfarItemGroup();

    ExtfarItemGroup() {
        super("extfar");
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(BlockRegistry.sprayer);
    }
}
