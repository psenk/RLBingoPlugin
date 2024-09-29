package com.bingo;

import net.runelite.api.Client;
import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;
import org.junit.Test;

import static org.junit.Assert.*;

public class BingoPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(BingoPlugin.class);
		RuneLite.main(args);
	}
}