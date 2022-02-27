package model;

public class Test {
	public static void main(String[] args) throws CloneNotSupportedException {
//		State state = new State(new Board(), Cell.PLAYER);
//		System.out.println(state);
		
		// test nuoc di co the di, DUNG
//		HashSet<Cell> cells = state.nextMovesPossible();
//		System.out.println(cells);

		// test tim cac nuoc lien minh, DUNG
//		HashSet<Cell> c = state.alliances(3, 5);
//		for (Cell cell : c) {
//			System.out.println(cell);
//		}

//		ArrayList<Cell> cs = new ArrayList<>(cells);
//		// test lat co voi mot nuoc di, DUNG
//		state.move(cs.get(4));
//		System.out.println(state);
		
		// test cac trang thai tiep theo, DUNG
//		System.out.println("=============");
//		ArrayList<State> states = state.nextStatesPossible();
//		for (State stateA : states) {
//			System.out.println(stateA);
//		}	
//		System.out.println("=============");
//		System.out.println(state);
//		
		
		// test cac node successor, DUNG
//		Node init = new Node(state);
//		init.buildGameTree();
	
//		MinimaxAlgo algo = new MinimaxAlgo();
//		Cell r = algo.execute(init);
//		
//		System.out.println(r.toString2());
		
//		AlfaBeta alfaBeta = new AlfaBeta();
//		Cell bestCell = alfaBeta.execute(new Node(state), 5);
//		System.out.println(bestCell);
		
//		System.out.println(state.nextMovesPossible(Cell.PLAYER));
//		System.out.println(state.nextMovesPossible(Cell.COMPUTER));
		
		long price = 123000;
		long priceSale = 110000;
		System.out.println((int)(((double)price - (double) priceSale)/ (double) price * 100));
		
	}
}
