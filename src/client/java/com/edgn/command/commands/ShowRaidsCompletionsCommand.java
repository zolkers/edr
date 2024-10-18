package com.edgn.command.commands;

import com.edgn.command.Command;
import com.edgn.utils.ChatUtils;
import com.edgn.utils.WynnApiUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.util.Formatting;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ShowRaidsCompletionsCommand extends Command {
    @Override
    public void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(ClientCommandManager.literal("edr")
                .requires((source) -> source.hasPermissionLevel(0))
                .then(ClientCommandManager.argument("player", StringArgumentType.word())
                        .executes((context) -> {
                            String playerName = StringArgumentType.getString(context, "player");
                            return run(playerName);
                        })));

    }

    private static int run(String playerName) {
        runApi(playerName);
        return 0;
    }


    private static void runApi(String playerName) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.schedule(() -> {
            try {
                String request = "https://api.wynncraft.com/v3/player/" + playerName;
                String result = WynnApiUtils.getStringFromURL(request);

                JsonObject jsonObject = JsonParser.parseString(result).getAsJsonObject();
                JsonObject globalData = jsonObject.getAsJsonObject("globalData");
                JsonObject raids = globalData.getAsJsonObject("raids");

                JsonObject raidsList = raids.getAsJsonObject("list");

                int tnaCompletion = raidsList.has("The Nameless Anomaly") ? raidsList.get("The Nameless Anomaly").getAsInt() : 0;
                int tccCompletion = raidsList.has("The Canyon Colossus") ? raidsList.get("The Canyon Colossus").getAsInt() : 0;
                int nolCompletion = raidsList.has("Orphion's Nexus of Light") ? raidsList.get("Orphion's Nexus of Light").getAsInt() : 0;
                int nogCompletion = raidsList.has("Nest of the Grootslangs") ? raidsList.get("Nest of the Grootslangs").getAsInt() : 0;
                int totalRaids = raids.has("total") ? raids.get("total").getAsInt() : 0;

                JsonObject ranking = jsonObject.has("ranking") ? jsonObject.getAsJsonObject("ranking") : new JsonObject();
                int nolRankingCompletion = ranking.has("nolCompletion") ? ranking.get("nolCompletion").getAsInt() : 0;
                int nogRankingCompletion = ranking.has("nogCompletion") ? ranking.get("nogCompletion").getAsInt() : 0;
                int tnaRankingCompletion = ranking.has("tnaCompletion") ? ranking.get("tnaCompletion").getAsInt() : 0;
                int tccRankingCompletion = ranking.has("tccCompletion") ? ranking.get("tccCompletion").getAsInt() : 0;

                ChatUtils.addChatMessageWithGoldTag(Formatting.GOLD + "Raid completions of " + Formatting.RED + playerName + ":");
                ChatUtils.addChatMessageWithListFormat(Formatting.AQUA + "TNA: " + Formatting.RESET + Formatting.YELLOW + tnaCompletion + Formatting.RESET + Formatting.GOLD + " times " + Formatting.YELLOW + "(#" + tnaRankingCompletion + ")");
                ChatUtils.addChatMessageWithListFormat(Formatting.AQUA + "TCC: " + Formatting.RESET + Formatting.YELLOW + tccCompletion + Formatting.RESET + Formatting.GOLD + " times " + Formatting.YELLOW + "(#" + tccRankingCompletion + ")");
                ChatUtils.addChatMessageWithListFormat(Formatting.AQUA + "NOL: " + Formatting.RESET + Formatting.YELLOW + nolCompletion + Formatting.RESET + Formatting.GOLD + " times " + Formatting.YELLOW + "(#" + nolRankingCompletion + ")");
                ChatUtils.addChatMessageWithListFormat(Formatting.AQUA + "NOTG: " + Formatting.RESET + Formatting.YELLOW + nogCompletion + Formatting.RESET + Formatting.GOLD + " times " + Formatting.YELLOW + "(#" + nogRankingCompletion + ")");
                ChatUtils.addChatMessageWithListFormat(Formatting.AQUA + "TOTAL: " + Formatting.RESET + Formatting.YELLOW + totalRaids + Formatting.RESET + Formatting.GOLD + " times " + Formatting.YELLOW);

            } catch (Exception e) {
                ChatUtils.addChatMessageWithDefaultTag("Error fetching raid completions for player " + playerName + "the player either doesnt exist or the API is down");
            }
        }, 1, TimeUnit.MILLISECONDS);

        scheduler.shutdown();

    }
}
