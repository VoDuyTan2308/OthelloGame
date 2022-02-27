package model;

public class AlfaBetaMax extends AlfaBeta{

	public Cell execute(Node node, int depth) {
		node.setDepth(0);
		int v = maxValue(node, Integer.MIN_VALUE, Integer.MAX_VALUE, depth);
		System.out.println("depth: " + depth + ", h = " + v);
		return node.getBestMove();
	}
}
