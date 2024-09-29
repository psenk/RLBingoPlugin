package com.bingo;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class BingoPluginTest
{
	public static void main(String[] args) throws Exception
	{
		/*
		 * ExternalPluginManager.loadBuiltin(BingoPlugin.class);
		 * RuneLite.main(args);
		 */

		Result result = JUnitCore.runClasses(TestSuite.class);

		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}

		System.out.println(result.wasSuccessful());
	}
}