import javax.swing.*;

public class TicTacToeButton extends JButton {
    private int row;
    private int col;
    private char state;  // 'X', 'O', or ' ' for empty

    public TicTacToeButton(int row, int col) {
        this.row = row;
        this.col = col;
        this.state = ' ';  // Initially empty
        setText("");  // Button label empty initially
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public char getState() {
        return state;
    }

    public void setState(char state) {
        this.state = state;
        setText(String.valueOf(state));  // Update the button display
    }

    public boolean isEmpty() {
        return state == ' ';
    }
}
