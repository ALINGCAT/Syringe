package io.github.alingcat.syringe;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class SyringeColorHandler {
    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register(new SyringeItemColor(), SyringeMod.SYRINGE.get());
    }

    private static class SyringeItemColor implements ItemColor {
        @Override
        public int getColor(@NotNull ItemStack stack, int tintIndex) {
            return tintIndex == 1 ? PotionUtils.getColor(PotionUtils.getMobEffects(stack)) : 0xFFFFFF;
        }
    }
}
