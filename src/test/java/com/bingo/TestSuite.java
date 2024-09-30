package com.bingo;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({UITest.class})
public class TestSuite {
}
