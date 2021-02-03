package tetris;


import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Tetris extends JFrame {

    private JLabel statusbar;

    public Tetris() {

        initUI();
    }

    private void initUI() {

        Board board = new Board(this);
        add(board);
        board.start();
        setTitle("Tetris");
        setSize(800, 640);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    JLabel getStatusBar() {

        return statusbar;
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            Tetris game = new Tetris();
            game.setVisible(true);
        });
    }
}
