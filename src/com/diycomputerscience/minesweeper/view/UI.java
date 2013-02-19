package com.diycomputerscience.minesweeper.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.plaf.ColorUIResource;

import com.diycomputerscience.minesweeper.Board;
import com.diycomputerscience.minesweeper.PersistenceException;
import com.diycomputerscience.minesweeper.RandomMineInitializationStrategy;
import com.diycomputerscience.minesweeper.Square;
import com.diycomputerscience.minesweeper.UncoveredMineException;

public class UI extends JFrame {
	
	private Board board;
	private OptionPane optionPane;
	private JPanel panel;
	
	public UI(Board board, OptionPane optionPane) {
		// set this.board to the injected Board
		this.board = board;
		this.optionPane = optionPane;
		
		// Set the title to "Minesweeper"
		this.setTitle("Minesweeper");
				
		this.panel = new JPanel();
		
		// Set the name of the panel to "MainPanel" 
		panel.setName("MainPanel");
		
		// Set the layout of panel to GridLayout. Be sure to give it correct dimensions
		panel.setLayout(new GridLayout(Board.MAX_ROWS, Board.MAX_COLS));
		
		// add squares to the panel
		this.layoutSquares(panel);
		// add panel to the content pane
		this.getContentPane().add(this.panel);
		
		// set the menu bar
		this.setJMenuBar(buildMenuBar());
		
		// validate components
		//this.validate();
	}
	
	public void load(Board board) {
		this.board = board;
		
		this.getContentPane().removeAll();
		this.invalidate();
		
		this.panel = new JPanel();		
		// Set the name of the panel to "MainPanel" 
		panel.setName("MainPanel");		
		// Set the layout of panel to GridLayout. Be sure to give it correct dimensions
		panel.setLayout(new GridLayout(Board.MAX_ROWS, Board.MAX_COLS));
		
		// add squares to the panel
		this.layoutSquares(panel);
		// add panel to the content pane
		
		this.getContentPane().add(this.panel);
		this.validate();		
	}
	
	private void layoutSquares(JPanel panel) {		
		final Square squares[][] = this.board.getSquares();
					
		for(int row=0; row<Board.MAX_ROWS; row++) {
			for(int col=0; col<Board.MAX_COLS; col++) {
				final JButton squareUI = new JButton();
				squareUI.setName(row+","+col);
				final int theRow = row;
				final int theCol = col;
				squareUI.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent me) {
						// invoke the appropriate logic to affect this action (left or right mouse click)
						if(SwingUtilities.isLeftMouseButton(me)) {
							try {
								UI.this.board.getSquares()[theRow][theCol].uncover();												
							} catch(UncoveredMineException ume) {
								squareUI.setBackground(Color.RED);
								String title = "That was a mine. You have lost the game. Would you like to play again ?";
								String msg = "Game Over";
								int answer = optionPane.userConfirmation(UI.this, title, msg, JOptionPane.YES_NO_OPTION);
								if(answer == JOptionPane.YES_OPTION) {
									Board board = new  Board(new RandomMineInitializationStrategy());
									load(board);
								} else {
									UI.this.dispose();
								}
							}
						} else if(SwingUtilities.isRightMouseButton(me)) {
							UI.this.board.getSquares()[theRow][theCol].mark();
						}
						// display the new state of the square
						updateSquareUIDisplay(squareUI, UI.this.board.getSquares()[theRow][theCol]);						
					}
				});
				updateSquareUIDisplay(squareUI, UI.this.board.getSquares()[row][col]);
				panel.add(squareUI);
			}
		}
	}
	
	private void updateSquareUIDisplay(JButton squareUI, Square square) {
		if(square.getState().equals(Square.SquareState.UNCOVERED)) {
			if(square.isMine()) {
				squareUI.setBackground(ColorUIResource.RED);
			} else {
				squareUI.setText(String.valueOf(square.getCount()));
			}							
		} else if(square.getState().equals(Square.SquareState.MARKED)) {
			squareUI.setText("");
			squareUI.setBackground(ColorUIResource.MAGENTA);
		} else if(square.getState().equals(Square.SquareState.COVERED)) {
			squareUI.setText("");
			squareUI.setBackground(new ColorUIResource(238, 238, 238));
		}
	}
	
	private JMenuBar buildMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu file = new JMenu("File");
		JMenuItem fileSave = new JMenuItem("Save");
		fileSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					UI.this.board.save();
				} catch(PersistenceException pe) {
					System.out.println("Could not save the game" + pe);
				}
			}		
		});
		JMenuItem fileLoad = new JMenuItem("Load");
		fileLoad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					UI.this.load(UI.this.board = Board.load());
				} catch(PersistenceException pe) {
					System.out.println("Could not load game from previously saved state");
				}
			}			
		});
		JMenuItem close = new JMenuItem("Close");
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {			
				System.exit(0);
			}		
		});
		file.add(fileSave);
		file.add(fileLoad);
		file.add(close);
		menuBar.add(file);
		
		JMenu help = new JMenu("Help");
		JMenuItem helpAbout = new JMenuItem("About");
		help.add(helpAbout);
		menuBar.add(help);
		
		return menuBar;
	}

	
	public static UI build(Board board, OptionPane optionPane) {
		UI ui = new UI(board, optionPane);
		ui.setSize(300, 400);
		ui.setVisible(true);
		ui.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		return ui;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				build(new Board(new RandomMineInitializationStrategy()), new SwingOptionPane());
			}
					
		});		
	}

}
