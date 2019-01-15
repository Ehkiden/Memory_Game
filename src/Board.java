/*
Project:    Program 00
File:       Board.java
Class:      CS 335
Author:     Jared Rigdon
Date:       9/21/2018
Purpose:    Builds the card array using the icons found in the res folder and randomizes them on creation.

*/

import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Board
{
    // Array to hold board cards
    private FlippableCard cards[];

    // Resource loader
    private ClassLoader loader = getClass().getClassLoader();

    public Board(int size, ActionListener AL)
    {
        // Allocate and configure the game board: an array of cards
        cards = new FlippableCard[size];

        // Fill the Cards array
        int imageIdx = 1;
        for (int i = 0; i < size; i++) {

            // Load the front image from the resources folder
            String imgPath = "res/match" + imageIdx + "card.jpg";
            ImageIcon img = new ImageIcon(loader.getResource(imgPath));

            // Setup one card at a time
            FlippableCard c = new FlippableCard(img);
            c.addActionListener(AL);
            c.setID(imageIdx);
            // Add them to the array
            cards[i] = c;

            if(i % 2 != 0){ //We only want two cards to have the same image, so change the index on every odd i
                imageIdx++;  // get ready for the next pair of cards
            }
        }
        // call the function to randomize the array
        randCards();
    }

    //for each card in the array, add that card to the container
    public void fillBoardView(JPanel view) {
        for (FlippableCard c : cards) {
            view.add(c);
        }
    }

    //randomizes and resets the cards
    public void resetBoard() {
        //randomize the cards
        randCards();

        //reset all cards to face down and enabled
        for (int i=0;i<25;i++){
            cards[i].setEnabled(true);
            cards[i].showFront();
            cards[i].hideFront();
        }

    }

    //randomize the cards in the array
    public void randCards(){
        Random rand = ThreadLocalRandom.current();

        for (int  i = cards.length-1; i>0;i--){
            int index=rand.nextInt(i+1);
            FlippableCard x;
            //assign a temp var to hold current index to perform swap
            x=cards[index];
            cards[index]=cards[i];
            cards[i]=x;
        }
    }
}
