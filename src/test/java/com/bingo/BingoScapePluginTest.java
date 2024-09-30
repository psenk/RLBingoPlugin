package com.bingo;

import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

public class BingoScapePluginTest
{
	public static void main(String[] args) throws Exception
	{

		Launcher launcher = LauncherFactory.create();
		SummaryGeneratingListener listener = new SummaryGeneratingListener();
		launcher.registerTestExecutionListeners(listener);

		LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request().selectors(DiscoverySelectors.selectPackage("com.bingo")).build();

		launcher.execute(request);
		listener.getSummary().printTo(System.console().writer());

		if (listener.getSummary() != null)
		{
			System.out.println("All tests passed.");
		}
		else
		{
			System.out.println("Some tests failed.");
		}
	}
}