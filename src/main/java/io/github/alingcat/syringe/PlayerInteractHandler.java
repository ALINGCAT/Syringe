package io.github.alingcat.syringe;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.PotionItem;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SyringeMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerInteractHandler {
    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent.RightClickItem event) {
        if (event.getEntity().getMainHandItem().getItem() == SyringeMod.SYRINGE.get()
                && event.getHand() == InteractionHand.OFF_HAND && event.getItemStack().getItem() instanceof PotionItem)
            event.setCanceled(true);
    }
}
