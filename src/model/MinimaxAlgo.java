package model;

import java.util.List;

public class MinimaxAlgo {
	// function MAXIMIN-DECISION(state) returns an action
	public Cell execute(Node node) {
		// Enter your code here
		int v = maxValue(node);
		System.out.println(v);
		
		List<Node> children = node.getSuccessors();
		for (Node child : children) 
			if(child.getValue() == v)
				return child.getLastMove();
		
		return null;
	}

	// function MAX-VALUE(state) returns a utility value
	// if TERMINAL-TEST(state) then return UTILITY(state)
	// v <- Integer.MIN_VALUE
	// for each s in SUCCESSORS(state) do
	// v <- MAX(v, MIN-VALUE(s))
	// return v
	public int maxValue(Node node) {
		// Enter your code here
		if (node.isTerminal())
			return node.getValue();
		else {
			int v = Integer.MIN_VALUE;

			List<Node> children = node.getSuccessors();

			for (Node s : children) 
				v = Math.max(v, minValue(s));
			
			node.setValue(v);
			return v;
		}
	}

	// function MIN-VALUE(state) returns a utility value
	// if TERMINAL-TEST(state) then return UTILITY(state)
	// v <- Integer.MAX_VALUE
	// for each s in SUCCESSORS(state) do
	// v <- MIN(v, MAX-VALUE(s))
	// return v
	public int minValue(Node node) {

		if (node.isTerminal())
			return node.getValue();
		else {
			int v = Integer.MAX_VALUE;

			List<Node> children = node.getSuccessors();

			for (Node s : children) 
				v = Math.min(v, maxValue(s));
			
			node.setValue(v);
			return v;
		}

	}
}
