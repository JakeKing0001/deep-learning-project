package com.example;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Gui extends JFrame implements ActionListener{

    private static JButton resetButton = new JButton("Reset");
    private static JButton hintButton = new JButton("Hide Move");
    private static JButton returnButton = new JButton("Return");
    private ChessBoard chessBoard;
    private ImageIcon image = new ImageIcon("src/main/resources/iconChess.png");

    public Gui(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
        this.setIconImage(image.getImage());
        resetButton.setSize(50, 50);
        hintButton.addActionListener(this);
        resetButton.addActionListener(this);
        returnButton.addActionListener(this);
        setLayout(new GridLayout(1, 3));
        add(resetButton);
        add(hintButton);
        add(returnButton);
        setSize(400, 400);
        setLocation(1110, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Retrieves the text of the hint button.
     *
     * @return the text of the hint button
    */
    protected  static String getTextHintButton() {
        return hintButton.getText();
    }

    /**
     * This method is called when an action event occurs. It checks the source of the event and performs
     * different actions based on the source.
     *
     * @param  e  the ActionEvent that occurred
    */
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == resetButton) {
            chessBoard.resetBoard();
        }
        if(e.getSource() == hintButton) {
            hintButton.setText(hintButton.getText().equals("Hide Move") ? "Show Move" : "Hide Move");
        }
        if(e.getSource() == returnButton) {
            WebPageViewer.webPageViewer.setVisible(true);
            this.setVisible(false);
            if (ChessBoard.isBotted) {
                WebPageViewer.copyChessBoard(false);
            } else {
                WebPageViewer.copyChessBoard(true);
            }
            WebPageViewer.turnCount = ChessBoard.turnCount;
            chessBoard.setVisible(false);
        }
    }
}
