package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.AlfaBeta;
import model.AlfaBetaMax;
import model.AlfaBetaMin;
import model.Board;
import model.Cell;
import model.Node;
import model.State;
import view.OthelloGUI;

public class OthelloGame extends JFrame {
	private static final long serialVersionUID = 1L;

	private static OthelloGUI panel;
	public static State state = new State();
	static AlfaBeta alfaBeta = new AlfaBetaMax();

	public OthelloGame() {
		super("Othello - AI_14");

		panel = new OthelloGUI(state);
		add(panel, BorderLayout.CENTER);

		setSize(800, 720);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		addActionListener();
	}

	public void addActionListener() {
		OthelloGUI.computerAttackButton.addActionListener(new Action());
		OthelloGUI.computerDefenceButton.addActionListener(new Action());
		for (int row = 0; row < Board.SIZE; row++)
			for (int col = 0; col < Board.SIZE; col++)
				OthelloGUI.cells[row][col].addActionListener(new Action());
	}

	class Action implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == OthelloGUI.computerAttackButton) {
				state = new State();
				reloadBoard();
				reloadScore();
				AlfaBetaMax.depthLimit = 5;
				alfaBeta = new AlfaBetaMax();
				JOptionPane.showMessageDialog(null, "In live, out die!", "Computer attack strategy",
						JOptionPane.INFORMATION_MESSAGE);
			} else if (e.getSource() == OthelloGUI.computerDefenceButton) {
				state = new State();
				reloadBoard();
				reloadScore();
				AlfaBetaMax.depthLimit = 5;
				alfaBeta = new AlfaBetaMin();
				JOptionPane.showMessageDialog(null, "Simple as a joke!", "Computer defence strategy",
						JOptionPane.INFORMATION_MESSAGE);
			} else if (state.getCurrentPlayer() == Cell.PLAYER) {
				// lay nuoc di tu su kien click vao move.
				for (int row = 0; row < Board.SIZE; row++) {
					for (int col = 0; col < Board.SIZE; col++)
						if (e.getSource() == OthelloGUI.cells[row][col]
								&& state.nextMovesPossible().contains(state.getBoard().getCell(row, col))) {
							state.move(row, col);
							break;
						}
				}
				reloadBoard();
				reloadScore();
				if (state.endGame()) {
					notifyEndGame();
					return;
				}

				// luot cua may
				while (state.getCurrentPlayer() == Cell.COMPUTER) {
					if (!state.nextMovesPossible().isEmpty()) {
						// neu may co nuoc di thi may danh.
						computerPlay();
						reloadBoard();
						reloadScore();

						if (state.endGame()) {
							notifyEndGame();
							return;
						} else if (state.nextMovesPossible().isEmpty()) {
							// may danh xong, neu nguoi k co nuoc di thi chuyen lai may.
							state.setCurrentPlayer(Cell.COMPUTER);
							reloadBoard();
						}
					}
					// neu may k co nuoc di thi chuyen luot cho nguoi, ve lai board.
					else {
						state.setCurrentPlayer(Cell.PLAYER);
						reloadBoard();
						return;
					}
				}
			}
		}

		public void computerPlay() {

			int depth = AlfaBeta.depthLimit;
			if (AlfaBeta.depthLimit == 1)
				AlfaBeta.depthLimit = 5;
			else
				AlfaBeta.depthLimit -= 2;

			int rest = state.getComputerScore() + state.getPlayerScore();
			if (rest >= 64 - 11) 
				AlfaBetaMax.depthLimit = 11;

			Cell bestMove = alfaBeta.execute(new Node(state), depth);
			
			state.move(bestMove.getRow(), bestMove.getCol());
		}

		public void reloadBoard() {
			for (int row = 0; row < Board.SIZE; row++) {
				for (int col = 0; col < Board.SIZE; col++) {
					OthelloGUI.cells[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
					if (state.getBoard().getCell(row, col).getValue() == Cell.PLAYER) {
						try {
							Image img = ImageIO.read(getClass().getResource("../images/dark.png"));
							OthelloGUI.cells[row][col].setIcon(new ImageIcon(img));
						} catch (IOException ex) {
						}
					} else if (state.getBoard().getCell(row, col).getValue() == Cell.COMPUTER) {
						try {
							Image img = ImageIO.read(getClass().getResource("../images/light.png"));
							OthelloGUI.cells[row][col].setIcon(new ImageIcon(img));
						} catch (IOException ex) {
						}
					} else if (state.nextMovesPossible().contains(state.getBoard().getCell(row, col))) {
						try {
							Image img = ImageIO.read(getClass().getResource("../images/legalMoveIcon.png"));
							OthelloGUI.cells[row][col].setIcon(new ImageIcon(img));
						} catch (IOException ex) {
						}
					} else
						OthelloGUI.cells[row][col].setIcon(null);

					if (state.getLastMove() != null && state.getLastMove().equals(state.getBoard().getCell(row, col)))
						OthelloGUI.cells[row][col].setBorder(BorderFactory.createLineBorder(Color.GREEN));
				}
			}
		}

		public void reloadScore() {
			OthelloGUI.playerScore.setText("Player : " + state.getPlayerScore() + "   ");
			OthelloGUI.computerScore.setText("Computer : " + state.getComputerScore() + "   ");
		}

		public void notifyEndGame() {
			if (state.getPlayerScore() > state.getComputerScore())
				JOptionPane.showMessageDialog(null, "God borned AlfaBeta, why still borned you?!!!", "You win", JOptionPane.PLAIN_MESSAGE);
			else if (state.getPlayerScore() < state.getComputerScore())
				JOptionPane.showMessageDialog(null, "Lose this battle, combat other!!!", "Computer Win", JOptionPane.PLAIN_MESSAGE);
			else
				JOptionPane.showMessageDialog(null, "Draw!", "Game Finished", JOptionPane.PLAIN_MESSAGE);

		}
	}
	
	public static void main(String[] args) {
		new OthelloGame();
	}
}
