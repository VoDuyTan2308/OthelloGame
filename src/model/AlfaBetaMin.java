package model;

public class AlfaBetaMin extends AlfaBeta{
	public Cell execute(Node node, int depth) {
		node.setDepth(0);
		int v = minValue(node, Integer.MIN_VALUE, Integer.MAX_VALUE, depth);
		System.out.println("depth: " + depth + ", h = " + v);
		return node.getBestMove();
	}
}
