import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeFrame extends JFrame {
    private TicTacToeButton[][] buttons = new TicTacToeButton[3][3];  // 3x3 grid of buttons
    private char currentPlayer = 'X';  // 'X' always starts
    private int moveCount = 0;

    public TicTacToeFrame() {
        setTitle("Tic Tac Toe");
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3));

        // Create and add buttons to the board
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                TicTacToeButton button = new TicTacToeButton(row, col);
                button.setFont(new Font("Arial", Font.PLAIN, 60));
                button.addActionListener(new ButtonListener());  // Use a single listener for all buttons
                buttons[row][col] = button;
                boardPanel.add(button);
            }
        }

        // Quit button
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> System.exit(0));

        add(boardPanel, BorderLayout.CENTER);
        add(quitButton, BorderLayout.SOUTH);

        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TicTacToeButton button = (TicTacToeButton) e.getSource();

            // Ignore if the button is already filled
            if (!button.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Invalid move! Try again.");
                return;
            }

            // Make the move
            button.setState(currentPlayer);
            moveCount++;

            // Check for a win or tie
            if (checkWin(currentPlayer)) {
                JOptionPane.showMessageDialog(null, currentPlayer + " wins!");
                promptPlayAgain();
            } else if (moveCount == 9) {
                JOptionPane.showMessageDialog(null, "It's a full-board tie!");
                promptPlayAgain();
            } else if (!areWinningMovesPossible()) {
                JOptionPane.showMessageDialog(null, "It's a tie! No winning moves left.");
                promptPlayAgain();
            } else {
                // Switch player
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            }
        }
    }

    // Check for a win
    private boolean checkWin(char player) {
        // Check rows, columns, and diagonals
        for (int i = 0; i < 3; i++) {
            if ((buttons[i][0].getState() == player && buttons[i][1].getState() == player && buttons[i][2].getState() == player) ||
                    (buttons[0][i].getState() == player && buttons[1][i].getState() == player && buttons[2][i].getState() == player)) {
                return true;
            }
        }
        return (buttons[0][0].getState() == player && buttons[1][1].getState() == player && buttons[2][2].getState() == player) ||
                (buttons[0][2].getState() == player && buttons[1][1].getState() == player && buttons[2][0].getState() == player);
    }

    // Check if there are possible winning moves left
    private boolean areWinningMovesPossible() {
        // Check all rows, columns, and diagonals to see if there's still a chance for someone to win
        for (int i = 0; i < 3; i++) {
            if (isLineOpenForWin(buttons[i][0], buttons[i][1], buttons[i][2]) ||  // Check rows
                    isLineOpenForWin(buttons[0][i], buttons[1][i], buttons[2][i])) {  // Check columns
                return true;
            }
        }
        // Check diagonals
        return isLineOpenForWin(buttons[0][0], buttons[1][1], buttons[2][2]) ||
                isLineOpenForWin(buttons[0][2], buttons[1][1], buttons[2][0]);// No possible winning moves left
    }

    // Helper method to check if a line (row, column, or diagonal) is still open for a win
    private boolean isLineOpenForWin(TicTacToeButton b1, TicTacToeButton b2, TicTacToeButton b3) {
        char[] states = {b1.getState(), b2.getState(), b3.getState()};
        int xCount = 0, oCount = 0, emptyCount = 0;

        // Count Xs, Os, and empty spaces in the line
        for (char state : states) {
            if (state == 'X') xCount++;
            else if (state == 'O') oCount++;
            else emptyCount++;
        }

        // If both X and O are present, this line is no longer open for a win
        if (xCount > 0 && oCount > 0) return false;

        // If only one player has moves and there are still empty spots, the line is open for a win
        return emptyCount > 0;
    }

    // Prompt to play again or quit
    private void promptPlayAgain() {
        int response = JOptionPane.showConfirmDialog(null, "Play again?", "Tic Tac Toe", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            resetBoard();
        } else {
            System.exit(0);
        }
    }

    // Reset the board for a new game
    private void resetBoard() {
        currentPlayer = 'X';
        moveCount = 0;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setState(' ');
            }
        }
    }
}
