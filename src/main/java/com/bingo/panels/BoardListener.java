package com.bingo.panels;

import com.bingo.bingo.BingoBoard;

public interface BoardListener
{
	void onBoardAdded(BingoBoard board);

	void onBoardRemoved(BingoBoard board);
}
