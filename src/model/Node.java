package model;

import java.util.ArrayList;
import java.util.List;

public class Node {
	private int value;
	private int depth;
	private State gameState;
	private Cell bestMove = null;
//	private List<Node> children = new ArrayList<Node>();

	public Node(State state) {
		this.gameState = new State(state);
		this.depth = 0;
		this.value = 0;	
//		this.setValue(getHeuristics());
	}

	// Get children of the current node
	public List<Node> getSuccessors() {
		// Enter your code here
		List<Node> r = new ArrayList<Node>();
		ArrayList<State> states = this.gameState.nextStatesPossible();
		for (State state : states) {
			Node node = new Node(state);
			
			r.add(node);
		
		}
		return r;
	}

	public boolean isTerminal() {
		// Enter your code here
//		return this.children.isEmpty();
		return this.getSuccessors().isEmpty();
	}

//	public void buildGameTree() {
//		ArrayList<State> states = this.gameState.nextStatesPossible();
//		for (State state : states) {
//			Node node = new Node(state);
//			node.setLevel(this.level + 1);
//
//			if (node.level == deepChild + 1)
//				break;
//
//			this.children.add(node);
//			// System.out.println(node);
//
//			node.buildGameTree();
//		}
//	}

	public int getHeuristics() {
		return gameState.getHeuristics();
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	@Override
	public String toString() {
		return gameState + "level: " + depth + "\nH: " + getValue() + "\nLast Move: " + getLastMove().toString2();
	}

	public Cell getLastMove() {
		return gameState.getLastMove();
	}

	public int getDepth() {
		// TODO Auto-generated method stub
		return this.depth;
	}

	public State getGameState() {
		return gameState;
	}

	public Cell getBestMove() {
		return bestMove;
	}

	public void setBestMove(Cell bestMove) {
		this.bestMove = bestMove;
	}
}
