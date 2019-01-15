/*
Project:    Program 00
File:       FlippableCard.java
Class:      CS 335
Author:     Jared Rigdon
Date:       9/21/2018
Purpose:    Assigns the current image to a button along with a secondary cover image which is associated by ids
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FlippableCard extends JButton
{
    // Resource loader
    private ClassLoader loader = getClass().getClassLoader();

    // Card front icon
    private Icon front;
    // Card back image
    private Icon back = new ImageIcon(loader.getResource("res/hide_icon.jpg"));

    // ID + Name
    private int id;
    private String customName;

    // Default constructor
    public FlippableCard() { super(); }

    // Constructor with card front initialization
    public FlippableCard(ImageIcon frontImage)
    {
        super();
        front = frontImage;
        super.setIcon(front);
        hideFront();

    }

    // Set the image used as the front of the card
    public void setFrontImage(ImageIcon frontImage) { front = frontImage; }

    // Card flipping functions
    public void showFront() { /* Show the card front */
        super.setIcon(front);
    }
    public void hideFront() { /* Show the card back  */
        super.setIcon(back);
    }

    // Metadata: ID number
    public int id() { return id; }
    public void setID(int i) { id = i; }


}
