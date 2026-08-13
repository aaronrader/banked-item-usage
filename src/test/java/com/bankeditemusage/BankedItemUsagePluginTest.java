package com.bankeditemusage;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class BankedItemUsagePluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(BankedItemUsagePlugin.class);
		RuneLite.main(args);
	}
}