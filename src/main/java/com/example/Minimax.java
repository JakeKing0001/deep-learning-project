package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.ImageIcon;

public class Minimax {
    private static final int BPAWN = -10;
    private static final int WPAWN = 10;
    private static final int BKNIGHT = -30;
    private static final int WKNIGHT = 30;
    private static final int BBISHOP = -30;
    private static final int WBISHOP = 30;
    private static final int BROOK = -50;
    private static final int WROOK = 50;
    private static final int BQUEEN = -90;
    private static final int WQUEEN = 90;
    private static final int BKING = -900;
    private static final int WKING = 900;

    private final static int DEPTH = 3;
    private static ImageIcon pieceReplaced;
    private static ImageIcon pieceMoved;
    protected static Cell[][] copyCells = new Cell[8][8];
    private static List<String> numeri = Arrays.asList("8", "7", "6", "5", "4", "3", "2", "1");
    private static List<String> letters = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h");
    private static List <String> possibleMoves = new ArrayList<String>();

    // Define the evaluation function to assign scores to different board positions
    private static int evaluatePosition() {
        int totalScore = 0;
        totalScore += BPAWN * numberOfBlackPawnsOnBoard();
        totalScore += WPAWN * numberOfWhitePawnsOnBoard();
        totalScore += BKNIGHT * numberOfBlackKnightsOnBoard();
        totalScore += WKNIGHT * numberOfWhiteKnightsOnBoard();
        totalScore += BBISHOP * numberOfBlackBishopsOnBoard();
        totalScore += WBISHOP * numberOfWhiteBishopsOnBoard();
        totalScore += BROOK * numberOfBlackRooksOnBoard();
        totalScore += WROOK * numberOfWhiteRooksOnBoard();
        totalScore += BQUEEN * numberOfBlackQueensOnBoard();
        totalScore += WQUEEN * numberOfWhiteQueensOnBoard();
        totalScore += BKING * numberOfBlackKingsOnBoard();
        totalScore += WKING * numberOfWhiteKingsOnBoard();

        return totalScore;
    }

    // Example helper methods to count the number of pieces on the board
    private static int numberOfWhiteKingsOnBoard() {
        int cont = 0;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(ChessBoard.cells[i][j].getIcon() == null) continue;
                if(ChessBoard.cells[i][j].getIcon().toString().contains("WhiteKi")) {
                    cont++;
                }
            }
        }
        return cont;
    }

    private static int numberOfBlackKingsOnBoard() {
        int cont = 0;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(ChessBoard.cells[i][j].getIcon() == null) continue;
                if(ChessBoard.cells[i][j].getIcon().toString().contains("BlackKi")) {
                    cont++;
                }
            }
        }
        return cont;
    }

    private static int numberOfWhiteQueensOnBoard() {
        int cont = 0;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(ChessBoard.cells[i][j].getIcon() == null) continue;
                if(ChessBoard.cells[i][j].getIcon().toString().contains("WhiteQue")) {
                    cont++;
                }
            }
        }
        return cont;
    }

    private static int numberOfBlackQueensOnBoard() {
        int cont = 0;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(ChessBoard.cells[i][j].getIcon() == null) continue;
                if(ChessBoard.cells[i][j].getIcon().toString().contains("BlackQue")) {
                    cont++;
                }
            }
        }
        return cont;
    }

    private static int numberOfWhiteRooksOnBoard() {
        int cont = 0;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(ChessBoard.cells[i][j].getIcon() == null) continue;
                if(ChessBoard.cells[i][j].getIcon().toString().contains("WhiteRoo")) {
                    cont++;
                }
            }
        }
        return cont;
    }

    private static int numberOfBlackRooksOnBoard() {
        int cont = 0;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(ChessBoard.cells[i][j].getIcon() == null) continue;
                if(ChessBoard.cells[i][j].getIcon().toString().contains("BlackRoo")) {
                    cont++;
                }
            }
        }
        return cont;
    }

    private static int numberOfWhiteBishopsOnBoard() {
        int cont = 0;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(ChessBoard.cells[i][j].getIcon() == null) continue;
                if(ChessBoard.cells[i][j].getIcon().toString().contains("WhiteBish")) {
                    cont++;
                }
            }
        }
        return cont;
    }

    private static int numberOfBlackPawnsOnBoard() {
        int cont = 0;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(ChessBoard.cells[i][j].getIcon() == null) continue;
                if(ChessBoard.cells[i][j].getIcon().toString().contains("BlackPaw")) {
                    cont++;
                }
            }
        }
        return cont;
    }

    private static int numberOfWhitePawnsOnBoard() {
        int cont = 0;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(ChessBoard.cells[i][j].getIcon() == null) continue;
                if(ChessBoard.cells[i][j].getIcon().toString().contains("WhitePaw")) {
                    cont++;
                }
            }
        }
        return cont;
    }

    private static int numberOfBlackKnightsOnBoard() {
        int cont = 0;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(ChessBoard.cells[i][j].getIcon() == null) continue;
                if(ChessBoard.cells[i][j].getIcon().toString().contains("BlackKnig")) {
                    cont++;
                }
            }
        }
        return cont;
    }

    private static int numberOfWhiteKnightsOnBoard() {
        int cont = 0;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(ChessBoard.cells[i][j].getIcon() == null) continue;
                if(ChessBoard.cells[i][j].getIcon().toString().contains("WhiteKnig")) {
                    cont++;
                }
            }
        }
        return cont;
    }

    private static int numberOfBlackBishopsOnBoard() {
        int cont = 0;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(ChessBoard.cells[i][j].getIcon() == null) continue;
                if(ChessBoard.cells[i][j].getIcon().toString().contains("BlackBisho")) {
                    cont++;
                }
            }
        }
        return cont;
    }

    /**
     * Calls the Minimax algorithm to find the best move.
     *
     * @return  the best move as a String
    */
    protected static String minimaxMove() {
        // Call the Minimax algorithm to find the best move

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                copyCells[i][j] = new Cell(i, j);
                if(ChessBoard.cells[i][j].getIcon() == null) continue;
                copyCells[i][j].setIcon(ChessBoard.cells[i][j].getIcon());
            }
        }

        int depth = DEPTH ; // Adjust the depth of search
        int bestScore = Integer.MIN_VALUE;
        String bestMove = null;
        possibleMoves = generatePossibleMoves();

        for (String move : possibleMoves) {
            // Apply each move to a hypothetical board state
            applyMove(move);
            // Call Minimax recursively to evaluate this move
            int score = minimax(move, depth, false); // Assuming it's opponent's turn
            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }

            // Undo the move to revert to the original board state
            undoMove(move);
        }
        ChessBoard.setAllCellBackground();
        ChessBoard.setFalseCellsChoosed();
        ChessBoard.setFalseCellsKilled();
        System.out.println("Best move: " + bestMove);
        return bestMove;
    } // Inizializza con le dimensioni appropriate
    private static int minimax(String move, int depth, boolean maximizingPlayer) {
        int maxEval, minEval, eval;

        if(depth == 0 || ChessBoard.isUnderCheckMate()) {
            return evaluatePosition();
        }

        if(maximizingPlayer) {
            maxEval = Integer.MIN_VALUE;
            possibleMoves = generatePossibleMoves();
            for (String possiblemove : possibleMoves) {
                applyMove(possiblemove);
                eval = minimax(possiblemove, depth - 1, false);
                maxEval = Math.max(maxEval, eval);
                undoMove(possiblemove);
            }
            return maxEval;
        } else {
            minEval = Integer.MAX_VALUE;
            possibleMoves = generatePossibleMoves();
            for (String possibleMove : possibleMoves) {
                applyMove(possibleMove);
                eval = minimax(possibleMove, depth - 1, true);
                minEval = Math.min(minEval, eval);
                undoMove(possibleMove);
            }
            return minEval;
        }
    }


    // Example methods for generating and applying/undoing moves
    protected static List<String> generatePossibleMoves() {
        ChessBoard.setFalseCellsChoosed();
        List<String> moves = new ArrayList<>();

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(ChessBoard.cells[i][j].getIcon() == null) continue;
                if(ChessBoard.cells[i][j].getIcon().toString().contains("Black")) {
                    ChessBoard.cells[i][j].setChoosed(true);
                    Piece.showMove(i, j, false);
                    if(ChessBoard.isCellsChoosed() ||ChessBoard.isCellsKillable()) {
                        for(int k = 0; k < 8; k++) {
                            for(int l = 0; l < 8; l++) {
                                if(ChessBoard.cells[k][l].isSubChoosed() || ChessBoard.cells[k][l].isKillable()) {
                                    moves.add(ChessBoard.cells[i][j].getCoordinate()+ "" + ChessBoard.cells[k][l].getCoordinate());
                                }
                            }
                        }
                    }
                    ChessBoard.setFalseCellsChoosed();
                    ChessBoard.setAllCellBackground();
                    ChessBoard.setFalseCellsKilled();
                }
            }
        }

        return moves;
    }

    /**
     * Applies a move to the chess board by updating the board state.
     *
     * @param  move  the move to be applied, in the format "startRowstartCol-endRowendCol"
     * @return       void
    */
    protected static void applyMove(String move) {
        // Implement logic to apply the move to the board
        int startRow = numeri.indexOf(String.valueOf(move.substring(1, 2)));
        int startCol = letters.indexOf(String.valueOf(move.substring(0, 1)));
        int endRow = numeri.indexOf(String.valueOf(move.substring(3, 4)));
        int endCol = letters.indexOf(String.valueOf(move.substring(2, 3)));

        // Update the board state
        ImageIcon movedPiece = (ImageIcon) ChessBoard.cells[startRow][startCol].getIcon();
        ChessBoard.cells[startRow][startCol].setIcon(null);
        ChessBoard.cells[endRow][endCol].setIcon(movedPiece);

        ChessBoard.setAllCellBackground();
        ChessBoard.setFalseCellsChoosed();
        ChessBoard.setFalseCellsKilled();
    }

    /**
     * Undoes a move by reverting the board state to the state before the move was applied.
     *
     * @param  move  the move to be undone, in the format "startRowstartCol-endRowendCol"
     * @return       void
    */
    protected static void undoMove(String move) {
        // Implement logic to undo the move and revert the board state
        int endRow = numeri.indexOf(String.valueOf(move.substring(1, 2)));
        int endCol = letters.indexOf(String.valueOf(move.substring(0, 1)));
        int startRow = numeri.indexOf(String.valueOf(move.substring(3, 4)));
        int startCol = letters.indexOf(String.valueOf(move.substring(2, 3)));

        // Update the board state
        ChessBoard.cells[endRow][endCol].setIcon(pieceMoved);
        ChessBoard.cells[startRow][startCol].setIcon((pieceReplaced != null)? pieceReplaced : null);
        ChessBoard.setAllCellBackground();
        ChessBoard.setFalseCellsChoosed();
        ChessBoard.setFalseCellsKilled();
    }

}
