package com.bingo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UITest {

    // Create a subclass that exposes startUp() and shutDown()
    private static class TestBingoPlugin extends BingoPlugin {
        @Override
        public void startUp() throws Exception {
            super.startUp();
        }

        @Override
        public void shutDown() throws Exception {
            super.shutDown();
        }
    }

    @InjectMocks
    private TestBingoPlugin plugin;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        plugin.startUp();
    }

    @Test
    public void testIsStartedBoolean() {
        assertTrue(plugin.isStarted);
    }

    @Test
    public void testShutdown() throws Exception {
        plugin.shutDown();
        assertFalse(plugin.isStarted);
    }
}
