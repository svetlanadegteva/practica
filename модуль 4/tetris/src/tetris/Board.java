package tetris;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import tetris.Shape.Tetrominoe;

public class Board extends JPanel {

    private final int BOARD_WIDTH = 30;
    private final int BOARD_HEIGHT = 30;
    private final int PERIOD_INTERVAL = 300;

    private Timer timer;
    private boolean isFallingFinished = false;
    private boolean isPaused = false;
    private int numLinesRemoved = 0;
    private int curX = 0;
    private int curY = 0;
    private Shape curPiece;
    private Tetrominoe[] board;
    JLabel lb;
    JButton btn1, btn2;

    public Board(Tetris parent) {

        initBoard(parent);
        setBackground(Color.BLACK);
        this.setLayout(null);
        btn1 = new JButton("Новая игра");
        btn1.setForeground(Color.RED);
        btn1.setFont(new Font("serif", 0, 20));
        btn1.setBounds(620, 30, 150, 50);
        btn1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	parent.dispose();
            	Tetris tetris = new Tetris();
            	tetris.setVisible(true);
            }
        });
        this.add(btn1);
        (btn2 = new JButton()).setText("Выход");
        btn2.setForeground(Color.RED);
        btn2.setFont(new Font("serif", 0, 20));
        btn2.setBounds(620, 100, 150, 50);
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                System.exit(0);
            }
        });
        this.add(btn2);
        (lb = new JLabel("Score: 0")).setForeground(Color.WHITE);
        lb.setFont(new Font("serif", 0, 30));
        lb.setBounds(630, 200, 150, 50);
        this.add(lb);
    }

    private void initBoard(Tetris parent) {

        setFocusable(true);
        addKeyListener(new TAdapter());
    }

    private int squareWidth() {

        return 20;
    }

    private int squareHeight() {

        return 20;
    }

    private Tetrominoe shapeAt(int x, int y) {

        return board[(y * BOARD_WIDTH) + x];
    }

    void start() {

        curPiece = new Shape();
        board = new Tetrominoe[BOARD_WIDTH * BOARD_HEIGHT];

        clearBoard();
        newPiece();

        timer = new Timer(PERIOD_INTERVAL, new GameCycle());
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.setColor(Color.WHITE);
      //рисование сетки
		  
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        Dimension size = getSize();
        int boardTop = (int) size.getHeight() - BOARD_HEIGHT * squareHeight();

        for (int i = 0; i < BOARD_HEIGHT; i++) {

            for (int j = 0; j < BOARD_WIDTH; j++) {

                Tetrominoe shape = shapeAt(j, BOARD_HEIGHT - i - 1);

                if (shape != Tetrominoe.NoShape) {

                    drawSquare(g, j * squareWidth(),
                            boardTop + i * squareHeight(), shape);
                }
            }
        }

        if (curPiece.getShape() != Tetrominoe.NoShape) {

            for (int i = 0; i < 6; i++) {

                int x = curX + curPiece.x(i);
                int y = curY - curPiece.y(i);

                drawSquare(g, x * squareWidth(),
                        boardTop + (BOARD_HEIGHT - y - 1) * squareHeight(),
                        curPiece.getShape());
            }
        }
    }

    private void dropDown() {

        int newY = curY;

        while (newY > 0) {

            if (!tryMove(curPiece, curX, newY - 1)) {

                break;
            }

            newY--;
        }

        pieceDropped();
    }

    private void oneLineDown() {

        if (!tryMove(curPiece, curX, curY - 1)) {

            pieceDropped();
        }
    }

    private void clearBoard() {

        for (int i = 0; i < BOARD_HEIGHT * BOARD_WIDTH; i++) {

            board[i] = Tetrominoe.NoShape;
        }
    }

    private void pieceDropped() {

        for (int i = 0; i < 6; i++) {

            int x = curX + curPiece.x(i);
            int y = curY - curPiece.y(i);
            board[(y * BOARD_WIDTH) + x] = curPiece.getShape();
        }

        removeFullLines();

        if (!isFallingFinished) {

            newPiece();
        }
    }

    private void newPiece() {

        curPiece.setRandomShape();
        curX = BOARD_WIDTH / 2 + 1;
        curY = BOARD_HEIGHT - 1 + curPiece.minY();

        if (!tryMove(curPiece, curX, curY)) {

            curPiece.setShape(Tetrominoe.NoShape);
            timer.stop();

            lb.setText("Score: " +numLinesRemoved);
            JOptionPane.showMessageDialog(null,"Game over. Score: " + numLinesRemoved);
        }
    }

    private boolean tryMove(Shape newPiece, int newX, int newY) {

        for (int i = 0; i < 6; i++) {

            int x = newX + newPiece.x(i);
            int y = newY - newPiece.y(i);

            if (x < 0 || x >= BOARD_WIDTH || y < 0 || y >= BOARD_HEIGHT) {

                return false;
            }

            if (shapeAt(x, y) != Tetrominoe.NoShape) {

                return false;
            }
        }

        curPiece = newPiece;
        curX = newX;
        curY = newY;

        repaint();

        return true;
    }

    private void removeFullLines() {

        int numFullLines = 0;

        for (int i = BOARD_HEIGHT - 1; i >= 0; i--) {

            boolean lineIsFull = true;

            for (int j = 0; j < BOARD_WIDTH; j++) {

                if (shapeAt(j, i) == Tetrominoe.NoShape) {

                    lineIsFull = false;
                    break;
                }
            }

            if (lineIsFull) {

                numFullLines++;

                for (int k = i; k < BOARD_HEIGHT - 1; k++) {
                    for (int j = 0; j < BOARD_WIDTH; j++) {
                        board[(k * BOARD_WIDTH) + j] = shapeAt(j, k + 1);
                    }
                }
            }
        }

        if (numFullLines > 0) {

            numLinesRemoved += numFullLines;

            lb.setText("Score: " +numLinesRemoved);
            isFallingFinished = true;
            curPiece.setShape(Tetrominoe.NoShape);
        }
    }

    private void drawSquare(Graphics g, int x, int y, Tetrominoe shape) {

        Color colors[] = {Color.ORANGE, Color.ORANGE,
        		Color.ORANGE, Color.ORANGE,
        		Color.ORANGE, Color.ORANGE,
        		Color.ORANGE, Color.ORANGE
        };

        Color color = colors[shape.ordinal()];

        g.setColor(color);
        g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);

        g.setColor(color.brighter());
        g.drawLine(x, y + squareHeight() - 1, x, y);
        g.drawLine(x, y, x + squareWidth() - 1, y);

        g.setColor(color.darker());
        g.drawLine(x + 1, y + squareHeight() - 1,
                x + squareWidth() - 1, y + squareHeight() - 1);
        g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1,
                x + squareWidth() - 1, y + 1);
    }

    private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            doGameCycle();
        }
    }

    private void doGameCycle() {

        update();
        repaint();
    }

    private void update() {

        if (isPaused) {

            return;
        }

        if (isFallingFinished) {

            isFallingFinished = false;
            newPiece();
        } else {

            oneLineDown();
        }
    }

    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            if (curPiece.getShape() == Tetrominoe.NoShape) {

                return;
            }

            int keycode = e.getKeyCode();

            switch (keycode) {

                case (37) : tryMove(curPiece, curX - 1, curY);break;
                case (39) : tryMove(curPiece, curX + 1, curY);break;
                case (38) : tryMove(curPiece.rotateLeft(), curX, curY);break;
                case (40) : dropDown();break;
            }
        }
    }
}
