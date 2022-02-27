package model;

import java.util.ArrayList;
import java.util.HashSet;

public class State {
	private Board board;
	private Cell lastMove;
	private int playerScore;
	private int computerScore;

	private int currentPlayer;

	public State() {
		this.board = new Board();
		this.currentPlayer = Cell.PLAYER;
		this.setScore();
	}

	public State(Board board, int currentPlayer) {
		this.board = board;
		this.currentPlayer = currentPlayer;
		this.setScore();
	}

	public State(State state) {
		this.board = new Board();

		for (int i = 0; i < Board.SIZE; i++)
			for (int j = 0; j < Board.SIZE; j++)
				this.board.getCell(i, j).setValue(state.board.getValue(i, j));
		this.currentPlayer = state.currentPlayer;
		this.setScore();
		this.lastMove = state.lastMove;

	}

	public HashSet<Cell> nextMovesPossible() {
		return nextMovesPossible(currentPlayer);
	}

	public HashSet<Cell> nextMovesPossible(int player) {
		HashSet<Cell> r = new HashSet<Cell>();
		for (int row = 0; row < Board.SIZE; row++)
			for (int col = 0; col < Board.SIZE; col++)
				if (this.board.getCell(row, col).getValue() == player)
					r.addAll(nextMovesPossibleOfCell(row, col, player));

		return r;
	}

	public HashSet<Cell> nextMovesPossibleOfCell(int row, int col, int player) {
		return alliances(row, col, Cell.EMPTY_CELL, player);
	}

	public HashSet<Cell> alliances(int row, int col, int value, int player) {
		// value: o trong
		// value: quan cua minh.
		HashSet<Cell> r = new HashSet<Cell>();
		// co cua doi thu
		int rival = Math.abs(player - 1);

		// huong Bac
		if (row > 1) {
			int x = row;
			int y = col;
			while (x > 1 && this.board.getValue(--x, y) == rival) {
				if (this.board.getValue(x - 1, y) == value)
					r.add(this.board.getCell(x - 1, y));
			}
		}

		// huong Nam
		if (row < Board.SIZE - 2) {
			int x = row;
			int y = col;
			while (x < Board.SIZE - 2 && this.board.getValue(++x, y) == rival) {
				if (this.board.getValue(x + 1, y) == value)
					r.add(this.board.getCell(x + 1, y));
			}
		}

		// huong Tay
		if (col > 1) {
			int x = row;
			int y = col;
			while (y > 1 && this.board.getValue(x, --y) == rival) {
				if (this.board.getValue(x, y - 1) == value)
					r.add(this.board.getCell(x, y - 1));
			}
		}

		// huong Dong
		if (col < Board.SIZE - 2) {
			int x = row;
			int y = col;
			while (y < Board.SIZE - 2 && this.board.getValue(x, ++y) == rival) {
				if (this.board.getValue(x, y + 1) == value)
					r.add(this.board.getCell(x, y + 1));
			}
		}

		// huong Tay Bac
		if (row > 1 && col > 1) {
			int x = row;
			int y = col;
			while (x > 1 && y > 1 && this.board.getValue(--x, --y) == rival) {
				if (this.board.getValue(x - 1, y - 1) == value)
					r.add(this.board.getCell(x - 1, y - 1));
			}
		}

		// huong Tay Nam
		if (row < Board.SIZE - 2 && col > 1) {
			int x = row;
			int y = col;
			while (x < Board.SIZE - 2 && y > 1 && this.board.getValue(++x, --y) == rival) {
				if (this.board.getValue(x + 1, y - 1) == value)
					r.add(this.board.getCell(x + 1, y - 1));
			}
		}

		// huong Dong Bac
		if (row > 1 && col < Board.SIZE - 2) {
			int x = row;
			int y = col;
			while (x > 1 && y < Board.SIZE - 2 && this.board.getValue(--x, ++y) == rival) {
				if (this.board.getValue(x - 1, y + 1) == value)
					r.add(this.board.getCell(x - 1, y + 1));
			}
		}

		// huong Dong Nam
		if (row < Board.SIZE - 2 && col < Board.SIZE - 2) {
			int x = row;
			int y = col;
			while (x < Board.SIZE - 2 && y < Board.SIZE - 2 && this.board.getValue(++x, ++y) == rival) {
				if (this.board.getValue(x + 1, y + 1) == value)
					r.add(this.board.getCell(x + 1, y + 1));
			}
		}

		return r;
	}

	// alliances to reversi
	public HashSet<Cell> alliances(int row, int col) {
		return alliances(row, col, currentPlayer, currentPlayer);
	}

	public ArrayList<State> nextStatesPossible() {
		ArrayList<State> states = new ArrayList<State>();

		HashSet<Cell> nextMovesPossible = nextMovesPossible();

		for (Cell cell : nextMovesPossible) {
			State state = new State(this);
			state.move(cell.getRow(), cell.getCol());

			state.currentPlayer = Math.abs(currentPlayer - 1);

			states.add(state);
		}

		return states;
	}

	public void move(int row, int col) {
		this.setLastMove(board.getCell(row, col));

		board.setValue(board.getCell(row, col), currentPlayer);

		HashSet<Cell> cells = alliances(row, col);
		// lat cac quan co
		for (Cell cell : cells)
			reversi(board.getCell(row, col), cell);

		this.currentPlayer = Math.abs(currentPlayer - 1);
		setScore();
	}

	public void reversi(Cell cellA, Cell cellB) {
		// 2 quan cung hang
		if (cellA.getRow() == cellB.getRow()) {
			int start = cellA.getCol() < cellB.getCol() ? cellA.getCol() : cellB.getCol();
			int end = cellA.getCol() < cellB.getCol() ? cellB.getCol() : cellA.getCol();

			for (int i = start + 1; i < end; i++)
				this.board.getCell(cellA.getRow(), i).setValue(currentPlayer);
		}

		// 2 quan cung cot
		if (cellA.getCol() == cellB.getCol()) {
			int start = cellA.getRow() < cellB.getRow() ? cellA.getRow() : cellB.getRow();
			int end = cellA.getRow() < cellB.getRow() ? cellB.getRow() : cellA.getRow();

			for (int i = start + 1; i < end; i++)
				this.board.getCell(i, cellA.getCol()).setValue(currentPlayer);
		}

		// 2 quan cung duong cheo phu
		if (cellA.getRow() + cellA.getCol() == cellB.getRow() + cellB.getCol()) {
			int startRow = cellA.getRow() < cellB.getRow() ? cellA.getRow() : cellB.getRow();
			int endRow = cellA.getRow() < cellB.getRow() ? cellB.getRow() : cellA.getRow();

			int startCol = cellA.getCol() < cellB.getCol() ? cellA.getCol() : cellB.getCol();
			int endCol = cellA.getCol() < cellB.getCol() ? cellB.getCol() : cellA.getCol();

			for (int i = startRow + 1; i < endRow; i++)
				for (int j = startCol + 1; j < endCol; j++)
					if (i + j == cellA.getRow() + cellA.getCol())
						this.board.getCell(i, j).setValue(currentPlayer);
		}

		// 2 quan cung duong cheo chinh
		if (cellA.getRow() - cellA.getCol() == cellB.getRow() - cellB.getCol()) {
			int startRow = cellA.getRow() < cellB.getRow() ? cellA.getRow() : cellB.getRow();
			int endRow = cellA.getRow() < cellB.getRow() ? cellB.getRow() : cellA.getRow();

			int startCol = cellA.getCol() < cellB.getCol() ? cellA.getCol() : cellB.getCol();
			int endCol = cellA.getCol() < cellB.getCol() ? cellB.getCol() : cellA.getCol();

			for (int i = startRow + 1; i < endRow; i++)
				for (int j = startCol + 1; j < endCol; j++)
					if (i - j == cellA.getRow() - cellA.getCol())
						this.board.getCell(i, j).setValue(currentPlayer);
		}
	}

	// tinh diem hien tai cua nguoi choi
	public void setScore() {
		computerScore = 0;
		playerScore = 0;
		for (int i = 0; i < Board.SIZE; i++)
			for (int j = 0; j < Board.SIZE; j++) {
				if (this.board.getValue(i, j) == Cell.PLAYER)
					playerScore++;
				if (this.board.getValue(i, j) == Cell.COMPUTER)
					computerScore++;
			}
	}

	public boolean endGame() {
		if (nextMovesPossible(Cell.COMPUTER).isEmpty() && nextMovesPossible(Cell.PLAYER).isEmpty())
			return true;

		return false;
	}

	public int getHeuristics() {
		setScore();
		// H theo so quan co
		int h = computerScore - playerScore;

//		// H theo doi phuong co nuoc di co hai cho minh hay khong?
//
//		for (Cell move : nextMovesPossible()) {
//			if (move.getRow() == Board.SIZE - 1 || move.getRow() == 0)
//				if (getCurrentPlayer() == Cell.COMPUTER)
//					h += 2;
//				else
//					h -= 2;
//
//			if (move.getCol() == Board.SIZE - 1 || move.getCol() == 0)
//				if (getCurrentPlayer() == Cell.COMPUTER)
//					h += 2;
//				else
//					h -= 2;
//		}

		// H theo canh, goc
		if (lastMove.getRow() == Board.SIZE - 1 || lastMove.getRow() == 0)
			if (getCurrentPlayer() == Cell.COMPUTER)
				h += 15;
			else
				h -= 15;

		if (lastMove.getCol() == Board.SIZE - 1 || lastMove.getCol() == 0)
			if (getCurrentPlayer() == Cell.COMPUTER)
				h += 15;
			else
				h -= -15;

		// H voi truong hop game ket thuc
		if (endGame() && computerScore > playerScore)
			h = 64;
		if (endGame() && computerScore < playerScore)
			h = -64;

		return h;
	}

	@Override
	public String toString() {
		return "State \n" + board;
	}

	public Cell getLastMove() {
		return lastMove;
	}

	public void setLastMove(Cell lastMove) {
		this.lastMove = lastMove;
	}

	public Board getBoard() {
		return board;
	}

	public int getPlayerScore() {
		return playerScore;
	}

	public int getComputerScore() {
		return computerScore;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(int player) {
		this.currentPlayer = player;
	}
}
