package com.example;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javax.swing.*;
import java.awt.*;

public class WebPageViewer extends JFrame {

    private Button button;
    private ChessBoard chessBoard = new ChessBoard(false);
    private Gui gui1 = new Gui(chessBoard);
    private static ImageIcon chessBoardUser[][] = new ImageIcon[8][8];
    private static ImageIcon chessBoardAI[][] = new ImageIcon[8][8];
    protected static int turnCount = 0;
    private Button button2;
    private Button buttonOnline;
    private Button buttonMultiPlayer;
    private Button buttonHardAi;
    private Button buttonEasyAi;
    private Pane pane;
    private WebView webView;
    protected static WebPageViewer webPageViewer;

    public WebPageViewer() {
        setTitle("Visualizzatore Pagina Web");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chessBoard.setVisible(false);
        gui1.setVisible(false);

        // Inizializza JavaFX
        initFX();

        // Crea un JFXPanel
        JFXPanel jfxPanel = new JFXPanel();
        getContentPane().add(jfxPanel, BorderLayout.CENTER);

        copyChessBoard(true);
        copyChessBoard(false);

        // Crea un WebView (browser WebKit) di JavaFX
        Platform.runLater(() -> {
            webView = new WebView();
            WebEngine webEngine = webView.getEngine();
            webEngine.load("http://localhost:8080/app/");

            pane = new Pane();
            button = new Button();
            button2 = new Button();

            button.setLayoutX(240);
            button.setId("exit-button");
            button.setLayoutY(335);
            button.setPrefWidth(120);
            button.setPrefHeight(50);
            button.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

            button2.setLayoutX(423);
            button2.setId("exit-button");
            button2.setLayoutY(335);
            button2.setPrefWidth(120);
            button2.setPrefHeight(50);
            button2.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

            addToPane();

            // Utilizza un listener per attendere che la pagina sia completamente caricata
            webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
                if (newState == javafx.concurrent.Worker.State.SUCCEEDED) {
                    // Gli elementi HTML sono pronti
                    setupButtonHoverActions(webEngine);
                }
            });

            button.setOnAction(event -> {
                webEngine.executeScript("change();");
                pane.getChildren().removeAll(webView, button, button2);

                buttonOnline = new Button();
                buttonMultiPlayer = new Button();
                buttonHardAi = new Button();
                buttonEasyAi = new Button();

                buttonOnline.setLayoutX(50);
                buttonOnline.setId("online-button");
                buttonOnline.setLayoutY(335);
                buttonOnline.setPrefWidth(120);
                buttonOnline.setPrefHeight(50);
                buttonOnline.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

                buttonMultiPlayer.setLayoutX(240);
                buttonMultiPlayer.setId("multiplayer-button");
                buttonMultiPlayer.setLayoutY(335);
                buttonMultiPlayer.setPrefWidth(120);
                buttonMultiPlayer.setPrefHeight(50);
                buttonMultiPlayer.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

                buttonHardAi.setLayoutX(430);
                buttonHardAi.setId("hard-ai-button");
                buttonHardAi.setLayoutY(335);
                buttonHardAi.setPrefWidth(120);
                buttonHardAi.setPrefHeight(50);
                buttonHardAi.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

                buttonEasyAi.setLayoutX(620);
                buttonEasyAi.setId("easy-ai-button");
                buttonEasyAi.setLayoutY(335);
                buttonEasyAi.setPrefWidth(120);
                buttonEasyAi.setPrefHeight(50);
                buttonEasyAi.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

                pane.getChildren().addAll(webView, buttonOnline, buttonMultiPlayer, buttonHardAi, buttonEasyAi);
    
                buttonMultiPlayer.setOnAction(event2 -> {
                    changeChessBoard(true);
                    ChessBoard.turnCount = turnCount;
                    ChessBoard.isBotted = false;
                    chessBoard.setVisible(true);
                    gui1.setVisible(true);
                    setVisible(false);
                });

                buttonEasyAi.setOnAction(event2 -> {
                    changeChessBoard(false);
                    ChessBoard.turnCount = turnCount;
                    ChessBoard.isBotted = true;
                    chessBoard.setVisible(true);
                    gui1.setVisible(true);
                    setVisible(false);
                });

                setupOtherButtonHoverActions(webEngine);
            });

            button2.setOnAction(event -> {
                setVisible(false);
            });

            Scene scene = new Scene(pane);
            jfxPanel.setScene(scene);
        });
    }

    /**
     * Sets the icons of the cells in the ChessBoard based on the given user flag.
     *
     * @param  user  a boolean flag indicating whether to set the icons for the user or the AI
    */
    private void changeChessBoard(boolean user) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessBoard.cells[i][j].setIcon((user)? chessBoardUser[i][j] : chessBoardAI[i][j]);
            }
        }
    }

    /**
     * Copies the icons of the cells in the ChessBoard to either the user or AI chess board, depending on the value of the user parameter.
     *
     * @param  user  a boolean flag indicating whether to copy the icons for the user or the AI
    */
    protected static void copyChessBoard(boolean user) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (user) {
                    chessBoardUser[i][j] = (ImageIcon) (ChessBoard.cells[i][j].getIcon());
                } else {
                    chessBoardAI[i][j] = (ImageIcon) (ChessBoard.cells[i][j].getIcon());
                }
            }
        }
    }

    /**
     * Sets up the hover actions for the buttons using the provided WebEngine.
     *
     * @param  webEngine  the WebEngine used to execute JavaScript commands
    */
    private void setupButtonHoverActions(WebEngine webEngine) {
        button.setOnMouseEntered(event -> {
            webEngine.executeScript("document.getElementById('fake-play-button').classList.add('hover');");
        });

        button.setOnMouseExited(event -> {
            webEngine.executeScript("document.getElementById('fake-play-button').classList.remove('hover');");
        });

        button2.setOnMouseEntered(event -> {
            webEngine.executeScript("document.getElementById('fake-exit-button').classList.add('hover');");
        });

        button2.setOnMouseExited(event -> {
            webEngine.executeScript("document.getElementById('fake-exit-button').classList.remove('hover');");
        });
    }

    /**
     * Sets up the hover actions for the other buttons.
     *
     * @param  webEngine  the WebEngine object to execute the script on
    */
    private void setupOtherButtonHoverActions(WebEngine webEngine) {
        buttonOnline.setOnMouseEntered(event -> {
            System.out.println("Entered buttonOnline");
            webEngine.executeScript("document.getElementById('fake-online-button').style.backgroundColor = 'plum';");
            webEngine.executeScript("document.getElementById('fake-online-button').style.webkitTextFillColor = 'whitesmoke';");
        });

        buttonOnline.setOnMouseExited(event -> {
            System.out.println("Exited buttonOnline");
            webEngine.executeScript("document.getElementById('fake-online-button').style.backgroundColor = 'whitesmoke';");
            webEngine.executeScript("document.getElementById('fake-online-button').style.webkitTextFillColor = 'black';");
        });

        buttonMultiPlayer.setOnMouseEntered(event -> {
            System.out.println("Entered buttonMultiPlayer");
            webEngine.executeScript("document.getElementById('fake-multi-player-button').style.backgroundColor = 'black';");
            webEngine.executeScript("document.getElementById('fake-multi-player-button').style.webkitTextFillColor = 'whitesmoke';");
        });

        buttonMultiPlayer.setOnMouseExited(event -> {
            System.out.println("Exited buttonMultiPlayer");
            webEngine.executeScript("document.getElementById('fake-multi-player-button').style.backgroundColor = 'whitesmoke';");
            webEngine.executeScript("document.getElementById('fake-multi-player-button').style.webkitTextFillColor = 'black';");
        });

        buttonHardAi.setOnMouseEntered(event -> {
            System.out.println("Entered buttonHardAi");
            webEngine.executeScript("document.getElementById('fake-hard-ai-button').style.backgroundColor = 'plum';");
            webEngine.executeScript("document.getElementById('fake-hard-ai-button').style.webkitTextFillColor = 'whitesmoke';");
        });

        buttonHardAi.setOnMouseExited(event -> {
            System.out.println("Exited buttonHardAi");
            webEngine.executeScript("document.getElementById('fake-hard-ai-button').style.backgroundColor = 'whitesmoke';");
            webEngine.executeScript("document.getElementById('fake-hard-ai-button').style.webkitTextFillColor = 'black';");
        });

        buttonEasyAi.setOnMouseEntered(event -> {
            System.out.println("Entered buttonEasyAi");
            webEngine.executeScript("document.getElementById('fake-easy-ai-button').style.backgroundColor = 'black';");
            webEngine.executeScript("document.getElementById('fake-easy-ai-button').style.webkitTextFillColor = 'whitesmoke';");
        });

        buttonEasyAi.setOnMouseExited(event -> {
            System.out.println("Exited buttonEasyAi");
            webEngine.executeScript("document.getElementById('fake-easy-ai-button').style.backgroundColor = 'whitesmoke';");
            webEngine.executeScript("document.getElementById('fake-easy-ai-button').style.webkitTextFillColor = 'black';");
        });
    }

    /**
     * Adds the buttons to the pane.
    */
    private void addToPane() {
        pane.getChildren().addAll(webView, button, button2);
    }

    /**
     * Initializes the JavaFX toolkit.
    */
    private void initFX() {
        // Inizializza JavaFX Toolkit
        new JFXPanel(); // Questo avvia JavaFX e previene un possibile IllegalStateException
    }

    /**
     * Starts the JavaFX application.
    */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            webPageViewer = new WebPageViewer();
            webPageViewer.setVisible(true);
        });
    }
}
