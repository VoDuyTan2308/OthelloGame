package model;


public class Board {
	private Cell matrix[][] = new Cell[SIZE][SIZE];
	public static final int SIZE = 8;

	
	public Board() {
		for (int i = 0; i < SIZE; i++)
			for (int j = 0; j < SIZE; j++)
				matrix[i][j] = new Cell(i, j, Cell.EMPTY_CELL);
	
		getCell(3, 3).setValue(Cell.PLAYER);
		getCell(4, 4).setValue(Cell.PLAYER);
		
		getCell(3, 4).setValue(Cell.COMPUTER);
		getCell(4, 3).setValue(Cell.COMPUTER);
		
	//	System.out.println(toString());
	}

	public Cell getCell(int row, int col) {
		return matrix[row][col];
	}
	
	public Cell getCell(Cell cell) {
		return matrix[cell.getRow()][cell.getCol()];
	}
	
	public int getValue(int row, int col) {
		return getCell(row, col).getValue();
	}

	public void setValue(Cell cell, int i) {
		cell.setValue(i);
	}

	@Override
	public String toString() {
		String r = "";
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) 
				r += matrix[i][j] + " ";
			
			r += "\n";
		}	
		return r;
	}
}
