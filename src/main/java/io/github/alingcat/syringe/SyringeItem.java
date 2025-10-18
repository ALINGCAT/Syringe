package io.github.alingcat.syringe;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SyringeItem extends Item {
    public SyringeItem(Properties props) {
        super(props);
    }

    public static boolean isEmpty(ItemStack stack) {
        return PotionUtils.getMobEffects(stack).isEmpty();
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        var stack = player.getItemInHand(hand);
        if (SyringeItem.isEmpty(stack)) {
            if (player.getOffhandItem().getItem() instanceof PotionItem) {
                player.startUsingItem(hand);
                return InteractionResultHolder.consume(stack);
            } else return InteractionResultHolder.fail(stack);
        }
        if (player.isShiftKeyDown())
            return InteractionResultHolder.fail(stack);
        player.hurt(player.damageSources().generic(), player.getHealth() > 1.0f ? 1.0f : 0.0f);
        for (var effect : PotionUtils.getMobEffects(stack)) {
            if (effect.getEffect().isInstantenous())
                effect.applyEffect(player);
            else player.addEffect(effect);
        }
        stack.setTag(new CompoundTag());
        return InteractionResultHolder.success(stack);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity user) {
        if (user instanceof Player player) {
            var offhand = player.getOffhandItem();
            if (offhand.getItem() instanceof PotionItem) {
                var tag = offhand.getTag();
                if (tag != null) stack.setTag(tag.copy());
                player.setItemInHand(InteractionHand.OFF_HAND, Items.GLASS_BOTTLE.getDefaultInstance());
            }
        }
        return stack;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 20;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tips, @NotNull TooltipFlag flag) {
        PotionUtils.addPotionTooltip(stack, tips, 1.0f);
    }
}
