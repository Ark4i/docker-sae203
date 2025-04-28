
public class Piece {
    private boolean playerOne;
    private boolean king;

    public Piece(boolean playerOne, boolean king) {
        this.playerOne = playerOne;
        this.king = king;
    }

    public boolean isPlayerOne() {
        return playerOne;
    }

    public boolean isKing() {
        return king;
    }

    public void setKing(boolean king) {
        this.king = king;
    }
}
