package com.bingo;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

@SuppressWarnings("unchecked")
public class ClientTest {
    public static void main(String[] args) throws Exception {

        ExternalPluginManager.loadBuiltin(BingoScapePlugin.class);
        RuneLite.main(args);

    }
}