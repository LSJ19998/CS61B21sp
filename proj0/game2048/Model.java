package game2048;

import java.util.Formatter;
import java.util.Observable;
import java.util.*;



/** The state of a game of 2048.
 *  @author TODO: YOUR NAME HERE
 */

// only use Side.North etc
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;



    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        boolean changed;
        changed = false;

        // TODO: Modify this.board (and perhaps this.score) to account
        // for the tilt to the Side SIDE. If the board changed, set the
        // changed local variable to true.
        board.setViewingPerspective(side);

        for(int i = 0; i < size(); i += 1) {
            int[] val = col_tile(board, i);
            // 迭代的超出, 直接退出
            for (int j = size() - 2; j >= 0; j -= 1) {
                if (val[j] == 0) {
                    continue;
                }
                Tile t = board.tile(i, j);
                // 选择是有空格
                int zero = zero_row(val, j);
                // 1. 有空格
                if (zero != j) {
                    board.move(i, zero, t);
                    val[j] = 0;
                    val[zero] = board.tile(i, zero).value();
                    changed = true;
                    // 移动到空格处了, 看是否可以合并.   如果可以合并. 如果不可以合并, 不需要操作
                    if (up_equal(val, zero, zero)) {
                        val[zero] = 0;
                        val[zero + 1] = -1;
                        t = board.tile(i, zero);
                        board.move(i, zero + 1, t);
                        score += board.tile(i, zero + 1).value();
                        changed = true;
                    }
                    // 2. 没有空格
                } else {
                    if (up_equal(val, j, j)) {
                        val[j] = 0;
                        val[zero + 1] = -1;
                        t = board.tile(i, j);
                        board.move(i, j + 1, t);
                        score += board.tile(i, j + 1).value();
                        changed = true;
                    }
                }

            }
        }

        board.setViewingPerspective(Side.NORTH);
        checkGameOver();
        if (changed) {
            setChanged();
        }

        return changed;
    }

    /**  将Board实例中的第i列元素收集到一个列表中 */
    public static int[] col_tile(Board b, int i) {
        int[] test = new int[b.size()];
        for (int j = 0; j < b.size(); j += 1) {
            if (b.tile(i,j) == null) {
                test[j] = 0;
            } else {
                test[j] = b.tile(i,j).value();
            }
        }
        return test;
    }

    /** 返回数组lst第i个元素之后最后一个0的位置 */
    public static int zero_row(int[] lst, int i) {
        int res = i + 1;
        for (res = i + 1; res < lst.length; res += 1) {
            if (lst[res] != 0) {
                return res - 1;
            }
        }
        return  res - 1;
    }

    // 这个出现了问题, 实际想要解决的问题  和  这个函数不一致
    /** 判断数组当前值和zero + 1 值是否相等 */
    public static boolean up_equal(int[] lst, int zero, int j) {
        if (zero + 1 >= lst.length) {
            return false;
        }
        int test1 = lst[j];
        int test2 = lst[zero + 1];
        return test1 == test2;
    }







    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     *  should use the tile(int col, int row) and size()
     * */
    public static boolean emptySpaceExists(Board b) {
        // TODO: Fill in this function. check Board have empty tile.
        for (int i = 0; i < b.size(); i += 1) {
            for (int j = 0; j < b.size(); j += 1) {
                if (b.tile(i, j) == null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     * we have private variable MAX_PIECE == 2048
     */

    public static boolean maxTileExists(Board b) {
        // TODO: Fill in this function.

        /*
        * this is problem is using null compare with int, but they can't compare
        * so that it need to add condition that t is null type.
        * */

        /*
        * null can only compare with object rather than other primitive type
        * */
        for (int i = 0; i < b.size(); i += 1) {
            for (int j = 0; j < b.size(); j += 1) {
                if (b.tile(i, j) != null && b.tile(i, j).value() == MAX_PIECE) {
                    return true;
                }
            }
        }
        return false;
    }



    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
//    可以再思考一下
    public static boolean atLeastOneMoveExists(Board b) {
        // TODO: Fill in this function.
        if (emptySpaceExists(b) || maxTileExists(b)) {
            return true;
        }
        for (int i = 0; i < b.size() - 1; i += 1) {
            for (int j = 0; j < b.size() - 1; j += 1) {
                if (b.tile(i, j) != null && b.tile(i + 1, j) != null) {
                    int left = b.tile(i, j).value();
                    int right = b.tile(i + 1, j).value();
                    if (left == right) {
                        return true;
                    }
                }
                if (b.tile(i, j) != null && b.tile(i, j + 1) != null) {
                    int up =  b.tile(i, j).value();
                    int down = b.tile(i, j + 1).value();
                    if (up == down) {
                        return true;
                    }
                }
                if (b.tile(i + 1, j + 1) != null && b.tile(i + 1, j) != null)  {
                    if (b.tile(i + 1, j + 1).value() == b.tile(i + 1, j).value()) {
                        return true;
                    }
                }
                if (b.tile(i + 1, j + 1) != null && b.tile(i, j + 1) != null) {
                    if (b.tile(i + 1, j + 1).value() == b.tile(i, j + 1).value()) {
                        return true;
                    }
                }

            }
        }
        return false;
    }

    private static boolean mergeMoveExists(Board b) {
        // set position left right bottom top
        int[][] neighbors = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int len = b.size();
        for (Tile t : b) {
            int c = t.col();    // x
            int r = t.row();    // y
            // compare they are equal
            for (int i = 0; i < 4; i++) {
                int nc = c + neighbors[i][0];
                int nr = r + neighbors[i][1];
                if ((0 <= nc && nc < len) && (0 <= nr && nr < len)
                        && (b.tile(nr, nc).value() == t.value())) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
