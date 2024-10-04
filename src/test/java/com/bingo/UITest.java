package com.bingo;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;


public class UITest
{

	private static class TestBingoScapePlugin extends BingoScapePlugin
	{
		@Override
		public void startUp() throws Exception
		{
			super.startUp();
		}

		@Override
		public void shutDown() throws Exception
		{
			super.shutDown();
		}
	}

	@InjectMocks
	private TestBingoScapePlugin plugin;

	@BeforeEach
	public void setUp() throws Exception
	{
		MockitoAnnotations.openMocks(this);
		plugin.startUp();
	}
}