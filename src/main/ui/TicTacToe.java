package ui;

import model.Leaderboard;
import model.Player;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

// the GUI for the tic-tac-toe game
public class TicTacToe extends JPanel {
    JButton[] buttons = new JButton[9];
    int turn = 0;

    String input1;
    String input2;
    Player player1;
    Player player2;
    Leaderboard leaderboard = new Leaderboard();
    JLabel leaderboardDisplay = new JLabel("", JLabel.CENTER);
    JPanel leaderboardControls = new JPanel();
    JButton clearLeaderboardButton;
    JTextField playerToAdd;
    JLabel playerToAddLabel;
    ImageIcon xoIcon = new ImageIcon("XO pic small.png");

    private static final String JSON_STORE = "./data/leaderboard.json";
    private JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private JsonReader jsonReader = new JsonReader(JSON_STORE);

    // EFFECTS: sets the GUI and runs the game
    public TicTacToe() {
        int optionPicked = JOptionPane.showConfirmDialog(null, "Load saved leaderboard?");
        if (optionPicked == 0) {
            loadLeaderboard();
        }
        setPlayers();
        leaderboard.sortLeaderboard();
        setLeaderboardDisplay();

        leaderboardControls.setLayout(new BoxLayout(leaderboardControls, BoxLayout.Y_AXIS));
        clearLeaderboardButton = new JButton("<html>Clear leaderboard of<br/> all other players</html>");
        clearLeaderboardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        clearLeaderboardButton.addActionListener(new ClearLeaderboardButtonListener());
        leaderboardControls.add(clearLeaderboardButton);

        playerToAddLabel = new JLabel("<html>To add a new player,<br/> enter their name below.</html>");
        playerToAddLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerToAdd = new JTextField(20);
        playerToAdd.addActionListener(new PlayerToAddTextFieldListener());
        leaderboardControls.add(playerToAddLabel);
        leaderboardControls.add(playerToAdd);

        add(leaderboardDisplay);
        add(new JLabel("<html>Player 1 is X, player 2 is O.<br/> X goes first.</html>", JLabel.CENTER));
        add(leaderboardControls);

        setLayout(new GridLayout(4, 3));
        setButtons();
    }

    // MODIFIES: this
    // EFFECTS: sets players to those playing
    public void setPlayers() {
        input1 = JOptionPane.showInputDialog(
                null, "Player 1, enter your player name: ");
        player1 = new Player(input1);
        player1 = leaderboard.findOrAddPlayer(player1);

        input2 = JOptionPane.showInputDialog(
                null, "Player 2, enter your player name: ");
        player2 = new Player(input2);
        player2 = leaderboard.findOrAddPlayer(player2);
    }

    // MODIFIES: this
    // EFFECTS: updates the leaderboard display panel
    public void setLeaderboardDisplay() {
        String leaderboardString = "<html>LEADERBOARD:<br/>NAME - WINS - LOSSES";
        for (Player p : leaderboard.getLeaderboard()) {
            leaderboardString += "<br/>" + p.getName() + " " + p.getWins() + " " + p.getLosses();
        }
        leaderboardString += "</html>";
        leaderboardDisplay.setText(leaderboardString);
    }

    // MODIFIES: this
    // EFFECTS: initializes the buttons
    public void setButtons() {
        for (int i = 0; i <= 8; i++) {
            buttons[i] = new JButton();
            buttons[i].setText("");
            buttons[i].addActionListener(new TicTacToeButtonListener());
            buttons[i].setIcon(xoIcon);

            add(buttons[i]);
        }
    }

    // MODIFIES: this
    // EFFECTS: resets the buttons
    public void resetButtons() {
        for (int i = 0; i <= 8; i++) {
            buttons[i].setText("");
            buttons[i].setIcon(xoIcon);
        }
    }

    // action listener for the clear leaderboard button
    public class ClearLeaderboardButtonListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: removes all players not playing from leaderboard and updates leaderboard
        public void actionPerformed(ActionEvent e) {
            leaderboard.getLeaderboard().removeIf(p -> !p.equals(player1) && !p.equals(player2));
            leaderboard.sortLeaderboard();
            setLeaderboardDisplay();
        }
    }

    // action listener for player adding text field
    public class PlayerToAddTextFieldListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: adds new player to leaderboard, if the name is new, and updates leaderboard
        public void actionPerformed(ActionEvent e) {
            String playerName = playerToAdd.getText();
            Player player3 = new Player(playerName);
            leaderboard.findOrAddPlayer(player3);
            leaderboard.sortLeaderboard();
            setLeaderboardDisplay();
            playerToAdd.selectAll();
        }
    }

    // action listener for the tic-tac-toe buttons
    public class TicTacToeButtonListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: sets the Xs and Os during the game and handles the game over operations if game is draw or won
        public void actionPerformed(ActionEvent e) {

            JButton buttonClicked = (JButton) e.getSource();
            buttonClicked.setIcon(null);

            if (turn % 2 == 0) {
                buttonClicked.setText("X");

            } else {
                buttonClicked.setText("O");
            }

            if (isGameWon()) {
                displayWinner();
                updateWinsLosses();

                leaderboard.sortLeaderboard();
                setLeaderboardDisplay();

                gameOver();
                return;
            }

            if (isGameDraw()) {
                JOptionPane.showMessageDialog(null, "Draw");
                gameOver();
                return;
            }

            turn++;

        }

        // REQUIRES: isGameWon()
        // EFFECTS: makes a message saying who won
        public void displayWinner() {
            if (turn % 2 == 0) {
                JOptionPane.showMessageDialog(null, "Player 1 wins");
            } else {
                JOptionPane.showMessageDialog(null, "Player 2 wins");
            }
        }

        // REQUIRES: isGameWon() or isGameDraw()
        // MODIFIES: this
        // EFFECTS: resets game, asks if user wants to save leaderboard, and restarts the game
        public void gameOver() {
            resetButtons();
            turn = 0;

            int optionPicked = JOptionPane.showConfirmDialog(null, "Game Over. Save the leaderboard?");
            if (optionPicked == 0) {
                saveLeaderboard();
            }
            setPlayers();
            leaderboard.sortLeaderboard();
            setLeaderboardDisplay();
        }

        // REQUIRES: isGameWon()
        // MODIFIES: this
        // EFFECTS: based on who won, adds wins and losses
        public void updateWinsLosses() {
            if (turn % 2 == 0) {
                player1.addWin();
                player2.addLoss();
            } else {
                player1.addLoss();
                player2.addWin();
            }
        }

        // REQUIRES: !isGameWon()
        // EFFECTS: true if the game is a draw
        public boolean isGameDraw() {
            for (JButton b: buttons) {
                if (b.getText().equals("")) {
                    return false;
                }
            }
            return true;
        }

        // EFFECTS: true if game is won by some player
        public boolean isGameWon() {
            if (checkAdjacent(0, 1) && checkAdjacent(1, 2)) { //horizontal win checks
                return true;
            } else if (checkAdjacent(3, 4) && checkAdjacent(4, 5)) {
                return true;
            } else if (checkAdjacent(6, 7) && checkAdjacent(7, 8)) {
                return true;
            } else if (checkAdjacent(0, 3) && checkAdjacent(3, 6)) { //vertical win checks
                return true;
            } else if (checkAdjacent(1, 4) && checkAdjacent(4, 7)) {
                return true;
            } else if (checkAdjacent(2, 5) && checkAdjacent(5, 8)) {
                return true;
            } else if (checkAdjacent(0, 4) && checkAdjacent(4, 8)) { //diagonal win checks
                return true;
            } else {
                return checkAdjacent(2, 4) && checkAdjacent(4, 6);
            }
        }

        // EFFECTS: true if the buttons at given indexes have equal text that isn't ""
        public boolean checkAdjacent(int a, int b) {
            return buttons[a].getText().equals(buttons[b].getText()) && !buttons[a].getText().equals("");
        }

    }

    // MODIFIES: this
    // EFFECTS: loads leaderboard from file
    private void loadLeaderboard() {
        try {
            leaderboard = jsonReader.read();
            JOptionPane.showMessageDialog(null, "Loaded leaderboard from " + JSON_STORE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: saves the leaderboard to file
    private void saveLeaderboard() {
        try {
            jsonWriter.open();
            jsonWriter.write(leaderboard);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null, "Saved leaderboard to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Unable to write to file: " + JSON_STORE);
        }
    }
}
