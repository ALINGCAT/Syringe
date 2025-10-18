package io.github.alingcat.syringe;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(SyringeMod.MODID)
public class SyringeMod {
    public static final String MODID = "syringe";

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final RegistryObject<Item> SYRINGE = ITEMS.register("syringe", () -> new SyringeItem(new Item.Properties().stacksTo(1)));

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final RegistryObject<CreativeModeTab> TAB = CREATIVE_MODE_TABS.register("Syringe", () -> {
        var builder = CreativeModeTab.builder();
        builder.title(Component.translatable("item.syringe.syringe"));
        builder.icon(() -> new ItemStack(SYRINGE.get()));
        builder.displayItems((parameters, output) -> output.accept(SYRINGE.get()));
        return builder.build();
    });

    public SyringeMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(bus);
        CREATIVE_MODE_TABS.register(bus);
        bus.addListener(this::client_setup);
    }

    private void client_setup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> ItemProperties.register(SyringeMod.SYRINGE.get(), new ResourceLocation(SyringeMod.MODID, "empty"),
            (stack, level, entity, seed) -> SyringeItem.isEmpty(stack) ? 1.0F : 0.0F));
    }
}
