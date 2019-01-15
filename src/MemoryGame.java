/*
Project:    Program 00
File:       MemoryGame.java
Purpose:    Builds the main constructors, containers, panels, etc. that function as the core mechanics for the program
Class:      CS 335
Author:     Jared Rigdon
Date:       9/21/2018
Purpose:    Build a stand-alone Java program that will allow a user to play the Memory game.  The graphical user
            interface should support the basic elements of the game:  start, restart, play of the game itself,
            recording the number of guesses so far, and detecting the termination state.

            NOTE: The cat-like jpg (match13card.jpg) is the "happy face" for this game. Also, all but 2 icons are in
            Norway

References: Used template "memory-game-template" provided by (at least last modified by) Kristina Gessel found in the
            CS 335 Canvas files.

 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MemoryGame extends JFrame implements ActionListener {
    // Core game play objects
    private Board gameBoard;
    private FlippableCard prevCard1, prevCard2;

    // Labels to display game info
    private JLabel errorLabel, pairLabel;

    // layout objects: Views of the board and the label area
    private JPanel boardView, labelView;

    // Record keeping counts and times
    private int errorCount = 0, pairsFound = 0;

    public MemoryGame() {
        // Call the base class constructor
        super("Norway Memory Game");

        // Allocate the interface elements
        JButton restart = new JButton("Start/Restart");
        JButton quit = new JButton("Quit");
        pairLabel = new JLabel("Pairs: " + pairsFound);
        errorLabel = new JLabel("Errors: " + errorCount);

        //quits the current program
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });

        // Allocate two major panels to hold interface
        labelView = new JPanel();  // used to hold labels
        boardView = new JPanel();  // used to hold game board

        // get the content pane, onto which everything is eventually added
        Container c = getContentPane();

        // Setup the game board with cards
        gameBoard = new Board(25, this);

        // Add the game board to the board layout area
        boardView.setLayout(new GridLayout(5, 5, 2, 0));
        boardView.setPreferredSize(new Dimension(800, 700));
        gameBoard.fillBoardView(boardView);

        // Add required interface elements to the "label" JPanel
        labelView.setLayout(new GridLayout(1, 4, 2, 2));
        labelView.add(quit);
        labelView.add(restart);
        labelView.add(pairLabel);
        labelView.add(errorLabel);

        // Both panels should now be individually layed out
        // Add both panels to the container
        c.add(labelView, BorderLayout.NORTH);
        c.add(boardView, BorderLayout.SOUTH);

        setSize(1000, 800);
        setVisible(true);
    }

    /* Handle anything that gets clicked and that uses MemoryGame as an
     * ActionListener */
    public void actionPerformed(ActionEvent e) {
        // Get the currently clicked card from a click event
        FlippableCard currCard = (FlippableCard) e.getSource();

        currCard.showFront();
        //check if the card is the wildcard
        if (currCard.id() == 13) {
            //if it is, then keep front showing and dont increase count when clicked
            currCard.setEnabled(false);
            if (prevCard1!=null){prevCard1.hideFront(); }
            if (prevCard2!=null){prevCard2.hideFront(); }
            prevCard1=null;
            prevCard2=null;
        } else {
            //check if the player has picked a second card yet
            if(prevCard1!=null && prevCard2==null){
                if(prevCard1!=currCard){    //see if the player clicks the same card
                    prevCard2=currCard;
                    prevCard2.showFront();
                    if(prevCard1.id()==prevCard2.id()){     //match
                        //increase pairs found and disable the buttons
                        pairsFound++;
                        pairLabel.setText("Pairs: " + pairsFound);
                        prevCard1.setEnabled(false);
                        prevCard2.setEnabled(false);

                        //check if all pairs are found
                        if(pairsFound==12){
                            //display they have won
                            JOptionPane.showMessageDialog(labelView,"Congrats you win!");
                            restartGame();  //restart
                        }
                    }
                    else{       //no match
                        errorCount++;
                        errorLabel.setText("Errors: " + (errorCount));   //increase guesses made
                    }
                }
            }
            else{
                //hold the bad match until the user clicks on the first next card match
                if (prevCard2!=null){
                    //null out the prevCard vars and flip the card over
                    prevCard1.hideFront();
                    prevCard2.hideFront();
                    prevCard1=null;
                    prevCard2=null;
                }
                prevCard1=currCard;
                prevCard1.showFront();
            }
        }
    }

    //resets all variables, boards, etc.
    private void restartGame() {
        pairsFound = 0;
        errorCount = 0;
        pairLabel.setText("Pairs: " + pairsFound);
        errorLabel.setText("Errors: " + errorCount);
        prevCard1=null;
        prevCard2=null;

        // Clear the boardView and have the gameBoard generate a new layout
        boardView.removeAll();
        gameBoard.resetBoard();
        gameBoard.fillBoardView(boardView);
    }

    public static void main(String args[]) {
        MemoryGame M = new MemoryGame();
        M.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

}