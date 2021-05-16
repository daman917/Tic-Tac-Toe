package ui;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("Tic-Tac-Toe");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().add(new TicTacToe());
        window.setSize(666, 888);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
