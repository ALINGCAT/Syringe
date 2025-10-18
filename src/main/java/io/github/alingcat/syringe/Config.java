package io.github.alingcat.syringe;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static ForgeConfigSpec CommonConfig;
    public static ForgeConfigSpec.IntValue InjectDamage;
    public static ForgeConfigSpec.DoubleValue ReloadPotionTime;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("General Setting").push("general");
        InjectDamage = builder.comment("Using syringe will be token damage.")
                .defineInRange("inject_damage", 1, 0, Integer.MAX_VALUE);
        ReloadPotionTime = builder.comment("How long does syringe to load by potion? (second)")
                        .defineInRange("reload_potion_time", 1.0f, 0.0f, Double.MAX_VALUE);
        builder.pop();
        CommonConfig = builder.build();
    }
}
