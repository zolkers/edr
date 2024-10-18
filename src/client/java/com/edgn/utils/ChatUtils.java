package com.edgn.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ChatUtils {
    private static final String DEFAULT_TAG = Formatting.DARK_GREEN + "[" + Formatting.GREEN + "MELON" + Formatting.DARK_GREEN + "] " + Formatting.RESET;
    private static final String GOLD_TAG = Formatting.GOLD + "[" + Formatting.YELLOW + "MELON" + Formatting.GOLD + "] " + Formatting.RESET;
    public static void addChatMessageWithDefaultTag(String message) { addChatMessage(DEFAULT_TAG + message);}
    public static void addChatMessageWithGoldTag(String message) { addChatMessage((GOLD_TAG + message));}
    public static void addChatMessageWithListFormat(String message) { addChatMessage(Formatting.GOLD + " - " + message);}
    public static void addChatMessage(String s) {
        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal(s));

    }
}
