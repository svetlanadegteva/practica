package tetris;


import java.util.Random;

public class Shape {

    protected enum Tetrominoe { NoShape, LShape, ZShape, LineShape,
        TShape, RectShape, CShape, RShape }

    private Tetrominoe pieceShape; 
    private int coords[][];
    private int[][][] coordsTable;


    public Shape() {

        initShape();
    }

    private void initShape() {

        coords = new int[6][2];

        coordsTable = new int[][][] {
                { { 0, 0 },   { 0, 0 },   { 0, 0 },   { 0, 0 } ,   { 0, 0 },   { 0, 0 }},
                { { 0, 0  },  { 0, 1 },   { 1, 0 },   { 1, 1 },    { 0, 0 },   { 0, 0 }},
                { { 0, 0  },  { 0, 1 },   { 1, 0 },   { 1, 1 },    { 0, 0 },   { 0, 0 }},
                { { 0, 0  },  { 0, 1 },   { 1, 0 },   { 1, 1 },    { 0, 0 },   { 0, 0 }},
                { { 0, 0  },  { 0, 1 },   { 1, 0 },   { 1, 1 },    { 0, 0 },   { 0, 0 }},
                { { 0, 0  },  { 0, 1 },   { 1, 0 },   { 1, 1 },    { 0, 0 },   { 0, 0 }},
                { { 0, 0  },  { 0, 1 },   { 1, 0 },   { 1, 1 },    { 0, 0 },   { 0, 0 }},
                { { 0, 0  },  { 0, 1 },   { 1, 0 },   { 1, 1 },    { 0, 0 },   { 0, 0 }},
        };

        setShape(Tetrominoe.NoShape);
    }

    protected void setShape(Tetrominoe shape) {

        for (int i = 0; i < 6 ; i++) {

            for (int j = 0; j < 2; ++j) {

                coords[i][j] = coordsTable[shape.ordinal()][i][j];
            }
        }

        pieceShape = shape;
    }

    private void setX(int index, int x) { coords[index][0] = x; }
    private void setY(int index, int y) { coords[index][1] = y; }
    public int x(int index) { return coords[index][0]; }
    public int y(int index) { return coords[index][1]; }
    public Tetrominoe getShape()  { return pieceShape; }

    public void setRandomShape() {

        Random r = new Random();
        int x = Math.abs(r.nextInt()) % 7 + 1;

        Tetrominoe[] values = Tetrominoe.values();
        setShape(values[x]);
    }

    public int minX() {

        int m = coords[0][0];

        for (int i=0; i < 6; i++) {

            m = Math.min(m, coords[i][0]);
        }

        return m;
    }


    public int minY() {

        int m = coords[0][1];

        for (int i=0; i < 6; i++) {

            m = Math.min(m, coords[i][1]);
        }

        return m;
    }

    public Shape rotateLeft() {

        Shape result = new Shape();
        result.pieceShape = pieceShape;

        for (int i = 0; i < 6; ++i) {

            result.setX(i, y(i));
            result.setY(i, -x(i));
        }

        return result;
    }

    public Shape rotateRight() {

        Shape result = new Shape();
        result.pieceShape = pieceShape;

        for (int i = 0; i < 6; ++i) {

            result.setX(i, -y(i));
            result.setY(i, x(i));
        }

        return result;
    }
}

