package com.example;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class ChessBoard extends JFrame {

    private JPanel grid;
    private JLabel chessboard;
    private JPanel colorMove;

    private ImageIcon image = new ImageIcon("src/main/resources/iconChess.png");

    protected static Cell[][] cells = new Cell[8][8];
    private static JLayeredPane layeredPane = new JLayeredPane();
    protected static ArrayList<Point> subChoosedMove = new ArrayList<>();
    protected static ArrayList<Point> killableMove = new ArrayList<>();
    protected static ArrayList<Point> pieceAvailable = new ArrayList<>();
    protected static JLabel[][] colors = new JLabel[8][8];
    private int[] numbers = { 8, 7, 6, 5, 4, 3, 2, 1 };
    private static String[] letters = { "a", "b", "c", "d", "e", "f", "g", "h" };
    protected static boolean state = true;
    protected static boolean isBotted;
    private Piece piece;
    private boolean firstMove = false;
    protected static int turnCount = 0;
    private int x, y;
    protected static boolean moved = false;
    private Icon privateIcon;

    private MouseAdapter mouseAdapter = new MouseAdapter() {
        /**
         * Overrides the mousePressed method from the MouseListener interface.
         * This method is called when a mouse button is pressed.
         *
         * @param  e  the MouseEvent object that contains information about the mouse event
        */
        @Override
        public void mousePressed(MouseEvent e) {
            handleMousePress(e);
        }
    };

    /**
        * Overrides the paint method from the Component class. This method is called when the component needs to be painted.
        * If the chessbot is enabled, it performs the following actions:
        * - If it is the player's turn, it generates a move using the Minimax algorithm, applies the move, sets the background of all cells,
        *   resets the chosen and killed cells, checks for promotion, increments the turn count.
        * It then iterates over all cells and if a cell is chosen, it shows the possible moves and prints the cell's coordinate.
        * Finally, it sets the king as moved and checks for promotion.
        *
        * @param  g  the Graphics object used for painting
    */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(isBotted) {

            if(turnCount % 2 == 1) {
                String move = Minimax.minimaxMove();
                for(int i = 0; i < 8; i++) {
                    for(int j = 0; j < 8; j++) {
                        if(Minimax.copyCells[i][j].getIcon() != null) {
                            ChessBoard.cells[i][j].setIcon(Minimax.copyCells[i][j].getIcon());
                        }
                    }
                }
                Minimax.applyMove(move);
                ChessBoard.setAllCellBackground();
                ChessBoard.setFalseCellsChoosed();
                ChessBoard.setFalseCellsKilled();
                checkPromotion();
                turnCount++;
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (cells[i][j].isChoosed()) {
                    Piece.showMove(i, j, (turnCount % 2 == 0) ? true : false);
                    System.out.println(cells[i][j].getCoordinate());
                }
            }
        }
        setTrueKingMoved();
        checkPromotion();
    }

    public ChessBoard(boolean isBotted) {
        ChessBoard.isBotted = isBotted;
        this.setIconImage(image.getImage());
        initializeBoard();
        setupUI();
    }

    public ChessBoard(int x) {

    }

    /**
     * Resets the chess board by reinitializing the cells, clearing the selected and killed cells,
     * resetting the turn count, and redrawing the chessboard.
    */
    protected void resetBoard() {
        setAllCellBackground(); // Reimposta il background delle celle
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                setInitialPieces(i, j);     // Reimposta i pezzi iniziali
            }
        }
        setFalseCellsChoosed(); // Reimposta le celle selezionate
        setFalseCellsKilled();  // Reimposta le celle uccise
        turnCount = 0;          // Reimposta il conteggio dei turni
        repaint();              // Ridisegna la scacchiera
    }

    /**
     * Initializes the chess board by creating the necessary components and setting up the chessboard.
     *
     * This method creates and initializes the grid panel, chessboard label, and color move panel.
     * It also sets the layout of the grid panel to a 8x8 grid layout and the layout of the color move panel
     * to a 8x8 grid layout. Finally, it calls the setChessBoard method to set up the chessboard.
     *
     * @return  void
    */
    protected void initializeBoard() {
        grid = new JPanel();
        chessboard = new JLabel();
        colorMove = new JPanel();
        colorMove.setLayout(new GridLayout(8, 8));
        grid.setLayout(new GridLayout(8, 8));
        setChessBoard();
    }

    /**
     * Sets up the user interface for the chess board.
     *
     * This method initializes the size of the JFrame, sets the icon for the chessboard,
     * sets the bounds for the chessboard, grid, and color move panels, adds them to the
     * layered pane, sets the location of the JFrame, sets the default close operation,
     * and makes the JFrame visible.
    */
    private void setupUI() {
        this.setSize(616, 618);
        chessboard.setIcon(new ImageIcon("src/main/resources/ChessBoard.jpeg"));
        chessboard.setBounds(0, 0, 616, 618);
        grid.setBounds(20, 20, 576, 576);
        colorMove.setBounds(20, 20, 576, 576);
        layeredPane.add(chessboard, 2);
        layeredPane.add(grid, 0);
        layeredPane.add(colorMove, 0);
        this.add(layeredPane);
        this.setLocation(400, 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * Sets up the chess board by initializing each cell, color label, and adding
     * them to the grid and color move panels. Also sets the cell and color labels
     * to be transparent and adds a mouse listener to each cell. Finally, sets the
     * frame to be undecorated.
     *
     * @return  void
    */
    private void setChessBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                cells[i][j] = new Cell(i * 72, j * 72);
                colors[i][j] = new JLabel();
                cells[i][j].setCoordinate(letters[j] + numbers[i]);
                setCellBackground(i, j);
                setInitialPieces(i, j);
                repaint();
                cells[i][j].setOpaque(false);
                colors[i][j].setOpaque(false);
                cells[i][j].addMouseListener(mouseAdapter);
                grid.add(cells[i][j]);
                colorMove.add(colors[i][j]);
            }
        }
        colorMove.setOpaque(false);
        grid.setOpaque(false);
        this.setUndecorated(true);
    }

    /**
     * Sets the background color of the cell at the specified row and column.
     *
     * @param  i  the row index of the cell
     * @param  j  the column index of the cell
    */
    private void setCellBackground(int i, int j) {
        Color lightColor = (i + j) % 2 == 0 ? new Color(248, 212, 186, 0) : new Color(151, 94, 77, 0);
        cells[i][j].setBackground(lightColor);
    }

    /**
     * Returns an ImageIcon based on the given mode.
     *
     * @param  i  the row index of the cell
     * @param  j  the column index of the cell
     * @param  mode  the mode to determine the ImageIcon
     * @return      an ImageIcon representing the chosen mode, or null if the mode is invalid
    */
    protected static ImageIcon chooseColor(int i, int j, int mode) {
        if(mode == 0) { // choosed
            return new ImageIcon("src/main/resources/ChoosedMark.png");
        } else if(mode == 1) { // subChoosed
            return new ImageIcon("src/main/resources/SubChoosedMark.png");
        } else if(mode == 2) { // killable
            return new ImageIcon("src/main/resources/KilledMark.png");
        } else if(mode == 3) { // checked
            return new ImageIcon("src/main/resources/CheckedMark.png");
        }
        return null;
    }

    /**
     * Sets the background of all cells in the chessboard to null.
     *
     * This method iterates over each cell in the chessboard and sets the background icon to null.
    */
    protected static void setAllCellBackground() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                colors[i][j].setIcon(null);
            }
        }
    }

    /**
     * Sets the initial pieces on the chessboard based on the given row and column indices.
     *
     * @param  i  the row index of the chessboard
     * @param  j  the column index of the chessboard
    */
    private void setInitialPieces(int i, int j) {
        if (i == 0 || i == 7) {
            piece = new Piece(choosePiece(i, j));
            cells[i][j].setIcon(piece.getIcon());
        } else if (i == 1 || i == 6) {
            piece = new Piece((i == 1) ? ("src/main/resources/BlackPawn.png") : ("src/main/resources/WhitePawn.png"));
            cells[i][j].setIcon(piece.getIcon());
        } else {
            cells[i][j].setIcon(null);
        }
    }

    /**
     * Checks if any pawns on the chessboard have reached the end of the board and should be promoted to a queen.
     * If a pawn is found, it is replaced with a queen of the appropriate color.
     *
     * @throws NullPointerException if any of the cells on the chessboard are null
    */
    protected static void checkPromotion() {
        try {
            for (int i = 0; i < 8; i++) {
                if (cells[0][i].getIcon().toString().contains("WhitePaw")) {
                    cells[0][i].setIcon(new ImageIcon("src/main/resources/WhiteQueen.png"));
                }
                if (cells[7][i].getIcon().toString().contains("BlackPaw")) {
                    cells[7][i].setIcon(new ImageIcon("src/main/resources/BlackQueen.png"));
                }
            }
        } catch (NullPointerException e) {}
    }

    /**
     * Sets the 'choosed' and 'subChoosed' properties of all cells in the chessboard to false.
     * This function is used to reset the state of the chessboard after a move has been made.
    */
    protected static void setFalseCellsChoosed() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                cells[i][j].setChoosed(false);
                cells[i][j].setSubChoosed(false);
            }
        }
    }

    /**
     * Checks if any of the cells in the chessboard have the 'subChoosed' property set to true.
     *
     * @return true if any of the cells have the 'subChoosed' property set to true, false otherwise
    */
    protected static boolean isCellsChoosed() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (cells[i][j].isSubChoosed()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if any of the cells in the chessboard have the 'killable' property set to true.
     *
     * @return true if any of the cells have the 'killable' property set to true, false otherwise
    */
    protected static boolean isCellsKillable() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (cells[i][j].isKillable()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Sets the 'killable' property of all cells in the chessboard to false.
     * This function is used to reset the state of the chessboard after a move has been made.
    */
    protected static void setFalseCellsKilled() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                cells[i][j].setKillable(false);
            }
        }
    }

    /**
     * Handles the mouse press event on the chessboard.
     *
     * @param  e  the MouseEvent representing the mouse press event
    */
    private void handleMousePress(MouseEvent e) {
        if (e.getSource() instanceof Cell) {
            Cell clickedCell = (Cell) e.getSource();
            try {
                piece.setIcon(clickedCell.getIcon());
                if (piece.isWhite()) {
                    firstMove = true;
                }
                if (firstMove) {
                    setFalseCellsChoosed();
                    if (!clickedCell.isKillable()) {
                        clickedCell.setChoosed(true);
                    }
                    if (clickedCell.getY() == 432 || clickedCell.getY() == 72) {
                        moved = false;
                    } else {
                        moved = true;
                    }
                    if (clickedCell.isChoosed()) {
                        privateIcon = clickedCell.getIcon();
                        x = clickedCell._getX();
                        y = clickedCell._getY();
                    }
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {

                            if(clickedCell.isKillable()) {
                                if (cells[i][j]._getX() == x && cells[i][j]._getY() == y && clickedCell.isKillable()) {
                                    cells[i][j].setIcon(null);
                                    turnCount++;
                                }
                            }
                        }
                    }
                    clickedCell.setIcon(privateIcon);
                    setFalseCellsKilled();
                    setAllCellBackground();
                }
            } catch (java.lang.NullPointerException ex) {
                if (clickedCell.isChoosed() || clickedCell.isSubChoosed()) {
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if(j != 0) {
                                if (cells[i][j]._getX() == x && cells[i][j]._getY() == y) {
                                    cells[i][j].setIcon(null);
                                }
                            }
                            if(j != 7) {
                                if (cells[i][j]._getX() == x && cells[i][j]._getY() == y) {
                                    cells[i][j].setIcon(null);
                                }
                            }
                            if (cells[i][j].isChoosed()) {
                                cells[i][j].setIcon(null);
                                turnCount++;
                            }
                        }
                    }
                    clickedCell.setIcon(privateIcon);
                }
                if(isUnderCheckMate()) {
                    JOptionPane.showMessageDialog(null, "Check Mate");
                }
                setFalseCellsChoosed();
                setFalseCellsKilled();
                setAllCellBackground();
            }
        }
        repaint();
    }

    /**
     * Checks if the current player is in a checkmate state.
     *
     * @return true if the current player is in a checkmate state, false otherwise
    */
    protected static boolean isUnderCheckMate()  {
        if(Piece.isUnderAttack(((turnCount % 2 == 0) ? true : false)) == 1) {
            if(Piece.isCheckMate(((turnCount % 2 == 0) ? true : false))) {
                JOptionPane.showMessageDialog(null, "Check Mate");
                return true;
            } else {
                setAllCellBackground();
                setFalseCellsChoosed();
                setFalseCellsKilled();
                return false;
            }
        }
        return false;
    }

    /**
     * Sets the kingMoved flags of the Piece class to true if the corresponding king cell is empty.
     *
     * This method checks if the cells at the positions (0, 4) and (7, 4) in the cells array are empty.
     * If a cell is empty, it sets the corresponding kingMoved flag in the Piece class to true.
     *
     * @param  None
     * @return None
    */
    private void setTrueKingMoved() {
        if(cells[0][4].getIcon() == null) {
            Piece.bKingMoved = true;
        }
        if(cells[7][4].getIcon() == null) {
            Piece.wKingMoved = true;
        }
    }

    /**
     * Creates a new ChessBoard instance with the specified state.
     *
     * @param  state  the state of the ChessBoard (true for white, false for black)
    */
    public static void createChessBoard(boolean state) {
        new ChessBoard(state);
    }

    /**
     * Returns the path to the image file of the chosen chess piece based on the given row and column indices.
     *
     * @param  a  the row index of the chosen piece
     * @param  n  the column index of the chosen piece
     * @return    the path to the image file of the chosen chess piece, or an empty string if no piece is chosen
    */
    private String choosePiece(int a, int n) {
        String colorPrefix = (a < 5) ? "Black" : "White";
        switch (n) {
            case 0:
            case 7:
                return "src/main/resources/" + colorPrefix + "Tower.png";
            case 1:
            case 6:
                return "src/main/resources/" + colorPrefix + "Knight.png";
            case 2:
            case 5:
                return "src/main/resources/" + colorPrefix + "Bishop.png";
            case 3:
                return "src/main/resources/" + colorPrefix + "Queen.png";
            case 4:
                return "src/main/resources/" + colorPrefix + "King.png";
            default:
                return "";
        }
    }
}