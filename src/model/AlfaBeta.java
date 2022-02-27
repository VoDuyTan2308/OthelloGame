package model;

import java.util.List;

public abstract class AlfaBeta {
	public static int depthLimit = 5;
	
	public abstract Cell execute(Node node, int depth);
	
	public int maxValue(Node node, int alpha, int beta, int depth) {
		if (node.isTerminal() || node.getDepth() == depth) {
			return utility(node);
		} else {
			int v = Integer.MIN_VALUE;
			List<Node> children = node.getSuccessors();

			for (Node child : children) {
				child.setDepth(node.getDepth() + 1);

				int n = minValue(child, alpha, beta, depth);
				if (v < n) {
					v = n;
					node.setBestMove(child.getLastMove());
				}

				if (v >= beta) {

					node.setValue(v);
					return v;
				}
				alpha = Math.max(alpha, v);
			}

			node.setValue(v);
			return v;
		}
	}

	public int minValue(Node node, int alpha, int beta, int depth) {
		if (node.isTerminal() || node.getDepth() == depth) {
			return utility(node);
		} else {
			int v = Integer.MAX_VALUE;
			List<Node> children = node.getSuccessors();

			for (Node child : children) {
				child.setDepth(node.getDepth() + 1);

				int n = maxValue(child, alpha, beta, depth);
				if (v > n) {
					v = n;
					node.setBestMove(child.getLastMove());
				}

				if (v <= alpha) {
					node.setBestMove(child.getLastMove());
					node.setValue(v);
					return v;
				}
				beta = Math.min(beta, v);
			}

			node.setValue(v);
			return v;
		}
	}

	private int utility(Node node) {
		return node.getGameState().getHeuristics();
	}
}
