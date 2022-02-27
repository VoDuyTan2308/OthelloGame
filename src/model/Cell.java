package model;

public class Cell {
	private int row;
	private int col;
	private int value;

	public static final int PLAYER = 0;
	public static final int COMPUTER = 1;
	public static final int EMPTY_CELL = 2;

	public Cell(int x, int y, int value) {
		this.setRow(x);
		this.setCol(y);
		this.setValue(value);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

//	@Override
	public String toString2() {
		return "[" + row + "," + col + "]" + String.valueOf(value);
	}

	@Override
	public String toString() {
//		return String.valueOf(value);
		return "[" + row + "," + col + "]" + String.valueOf(value);
	}
}
