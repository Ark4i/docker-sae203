
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class BoardPanel extends JPanel {
    private final int TILE_SIZE = 80;
    private Piece[][] board;
    private boolean playerOneTurn = true;
    private int selectedRow = -1, selectedCol = -1;
    private int capturesPlayerOne = 0, capturesPlayerTwo = 0;
    private Image crownImage;

    public BoardPanel() {
        setPreferredSize(new Dimension(8 * TILE_SIZE, 8 * TILE_SIZE + 50));
        board = new Piece[8][8];
        initializeBoard();
        try {
            crownImage = ImageIO.read(new File("../res/crown.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = e.getX() / TILE_SIZE;
                int row = (e.getY() - 50) / TILE_SIZE;

                if (row < 0 || row >= 8 || col < 0 || col >= 8) return;

                if (selectedRow == -1 && board[row][col] != null && board[row][col].isPlayerOne() == playerOneTurn) {
                    selectedRow = row;
                    selectedCol = col;
                } else if (selectedRow != -1) {
                    boolean captured = movePiece(selectedRow, selectedCol, row, col);
                    if (!captured) {
                        playerOneTurn = !playerOneTurn;
                    }
                    selectedRow = -1;
                    selectedCol = -1;
                }
                repaint();
            }
        });
    }

    private void initializeBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = (row + 1) % 2; col < 8; col += 2) {
                board[row][col] = new Piece(true, false);
            }
        }
        for (int row = 5; row < 8; row++) {
            for (int col = (row + 1) % 2; col < 8; col += 2) {
                board[row][col] = new Piece(false, false);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setFont(new Font("Arial", Font.BOLD, 16));
        for (int col = 0; col < 8; col++) {
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf((char) ('A' + col)), col * TILE_SIZE + TILE_SIZE / 2 - 5, 30);
        }

        g.drawString("J1: " + capturesPlayerOne + " captures", 10, getHeight() - 10);
        g.drawString("J2: " + capturesPlayerTwo + " captures", getWidth() - 150, getHeight() - 10);

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 == 0) {
                    g.setColor(Color.LIGHT_GRAY);
                } else {
                    g.setColor(new Color(200, 200, 200));
                }
                g.fillRect(col * TILE_SIZE, row * TILE_SIZE + 50, TILE_SIZE, TILE_SIZE);

                if (row == selectedRow && col == selectedCol) {
                    g.setColor(Color.YELLOW);
                    g.drawRect(col * TILE_SIZE, row * TILE_SIZE + 50, TILE_SIZE, TILE_SIZE);
                    g.drawRect(col * TILE_SIZE + 1, row * TILE_SIZE + 51, TILE_SIZE - 2, TILE_SIZE - 2);
                }

                Piece piece = board[row][col];
                if (piece != null) {
                    if (piece.isPlayerOne()) {
                        g.setColor(Color.RED);
                    } else {
                        g.setColor(Color.BLACK);
                    }
                    g.fillOval(col * TILE_SIZE + 10, row * TILE_SIZE + 60, TILE_SIZE - 20, TILE_SIZE - 20);
                    if (piece.isKing() && crownImage != null) {
                        g.drawImage(crownImage, col * TILE_SIZE + 20, row * TILE_SIZE + 70, 40, 40, null);
                    }
                }
            }
        }
    }

    private boolean movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        if (!isValidPosition(fromRow, fromCol) || !isValidPosition(toRow, toCol)) return false;

        Piece piece = board[fromRow][fromCol];
        if (piece == null || board[toRow][toCol] != null) return false;

        int rowDiff = toRow - fromRow;
        int colDiff = toCol - fromCol;

        if ((piece.isKing() && Math.abs(rowDiff) == 1 && Math.abs(colDiff) == 1) ||
            (!piece.isKing() && rowDiff == (piece.isPlayerOne() ? 1 : -1) && Math.abs(colDiff) == 1)) {
            board[toRow][toCol] = piece;
            board[fromRow][fromCol] = null;
            promoteIfNeeded(toRow, toCol);
            return false;
        }

        if ((piece.isKing() && Math.abs(rowDiff) == 2 && Math.abs(colDiff) == 2) ||
            (!piece.isKing() && rowDiff == (piece.isPlayerOne() ? 2 : -2) && Math.abs(colDiff) == 2)) {
            int midRow = (fromRow + toRow) / 2;
            int midCol = (fromCol + toCol) / 2;
            Piece captured = board[midRow][midCol];
            if (captured != null && captured.isPlayerOne() != piece.isPlayerOne()) {
                board[toRow][toCol] = piece;
                board[fromRow][fromCol] = null;
                board[midRow][midCol] = null;
                if (piece.isPlayerOne()) {
                    capturesPlayerOne++;
                } else {
                    capturesPlayerTwo++;
                }
                promoteIfNeeded(toRow, toCol);
                return true;
            }
        }

        return false;
    }

    private void promoteIfNeeded(int row, int col) {
        Piece piece = board[row][col];
        if (piece != null) {
            if ((piece.isPlayerOne() && row == 7) || (!piece.isPlayerOne() && row == 0)) {
                piece.setKing(true);
            }
        }
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
}
