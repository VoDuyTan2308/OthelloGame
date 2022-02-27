package view;

import java.awt.*;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import model.Board;
import model.Cell;
import model.State;

public class OthelloGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	JPanel panelBoard;
	JPanel panelButton;

	public static JLabel computerScore;
	public static JLabel playerScore;

	public static JButton computerDefenceButton;
	public static JButton computerAttackButton;
	public static JButton[][] cells;

	public OthelloGUI( State state) {
		super(new BorderLayout());
		setPreferredSize(new Dimension(800, 700));
		setLocation(0, 0);

		placeNewGamePanel();

		placeBoardPanel(state);

		placeScorePanel(state);
	}

	public void placeNewGamePanel() {
		// PANEL SOUTH: newGame button
		panelButton = new JPanel(new GridBagLayout());
		panelButton.setPreferredSize(new Dimension(800, 60));
		GridBagConstraints constraints = new GridBagConstraints();

		computerAttackButton = new JButton();
		computerAttackButton.setPreferredSize(new Dimension(104, 42));
		try {
			Image img = ImageIO.read(getClass().getResource("../images/hard.png"));
			computerAttackButton.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
		}

		computerDefenceButton = new JButton();
		computerDefenceButton.setPreferredSize(new Dimension(104, 42));
		try {
			Image img = ImageIO.read(getClass().getResource("../images/easy.png"));
			computerDefenceButton.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
		}

		constraints.gridx = 0;
		constraints.gridy = 1;
		panelButton.add(computerDefenceButton, constraints);
		constraints.gridx = 1;
		constraints.gridy = 1;
		panelButton.add(computerAttackButton, constraints);

		add(panelButton, BorderLayout.SOUTH);
	}

	public void placeBoardPanel(State state) {
		// PANEL CENTER: BOARD GAME
		// panelBoard
		panelBoard = new JPanel(new GridLayout(Board.SIZE, Board.SIZE));
		cells = new JButton[Board.SIZE][Board.SIZE];

		// create buttons
		for (int row = 0; row < Board.SIZE; row++)
			for (int col = 0; col < Board.SIZE; col++) {
				cells[row][col] = new JButton();
				cells[row][col].setSize(50, 50);
				cells[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));

				// set image button
				if (state.getBoard().getCell(row, col).getValue() == Cell.PLAYER) {
					try {
						Image img = ImageIO.read(getClass().getResource("../images/dark.png"));
						cells[row][col].setIcon(new ImageIcon(img));
					} catch (IOException ex) {
					}
				} else if (state.getBoard().getCell(row, col).getValue() == Cell.COMPUTER) {
					try {
						Image img = ImageIO.read(getClass().getResource("../images/light.png"));
						cells[row][col].setIcon(new ImageIcon(img));
					} catch (IOException ex) {
					}
				} else if (row == 2 && col == 4 || row == 3 && col == 5 || row == 4 && col == 2
						|| row == 5 && col == 3) {
					try {
						Image img = ImageIO.read(getClass().getResource("../images/legalMoveIcon.png"));
						cells[row][col].setIcon(new ImageIcon(img));
					} catch (IOException ex) {
					}
				}

				panelBoard.add(cells[row][col]);
			}
		add(panelBoard, BorderLayout.CENTER);
	}

	public void placeScorePanel(State state) {
		// PANEL EAST: SCORE PANEL
		JPanel scorePanel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		scorePanel.setPreferredSize(new Dimension(210, 800));

		// dark chess
		JLabel dark = new JLabel();
		try {
			Image img = ImageIO.read(getClass().getResource("../images/dark.png"));
			dark.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
		}

		// light chess
		JLabel light = new JLabel();
		try {
			Image img = ImageIO.read(getClass().getResource("../images/light.png"));
			light.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
		}

		playerScore = new JLabel();
		playerScore.setText(" Player : " + state.getPlayerScore() + "  ");
		playerScore.setFont(new Font("Serif", Font.BOLD, 22));

		computerScore = new JLabel();
		computerScore.setText(" Computer : " + state.getComputerScore() + "  ");
		computerScore.setFont(new Font("Serif", Font.BOLD, 22));

		// position label in gridBagLayout
		constraints.gridx = 0;
		constraints.gridy = 1;
		scorePanel.add(dark, constraints);
		constraints.gridx = 1;
		constraints.gridy = 1;
		scorePanel.add(playerScore, constraints);

		constraints.gridx = 0;
		constraints.gridy = 2;
		scorePanel.add(light, constraints);
		constraints.gridx = 1;
		constraints.gridy = 2;
		scorePanel.add(computerScore, constraints);

		add(scorePanel, BorderLayout.EAST);
	}
}
