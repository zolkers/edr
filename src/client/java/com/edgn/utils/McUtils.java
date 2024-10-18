package com.edgn.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;

public class McUtils {

    public static MinecraftClient getMc() {
        return MinecraftClient.getInstance();
    }
    public static ClientPlayerEntity getPlayer() {
        return McUtils.getMc().player;
    }
    public static ClientWorld getWorld() {
        return McUtils.getMc().world;
    }

    public static ClientPlayNetworkHandler networkHandler() {
        return MinecraftClient.getInstance().getNetworkHandler();
    }
}
