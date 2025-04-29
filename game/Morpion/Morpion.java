
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Morpion implements MouseListener
{
    private JFrame     frame;
    private JPanel     panel;
    private JLabel[][] grid;
    private char  [][] board;
    private boolean    myTurn;
    private boolean    hasPlayed;
    private int        lastMoveIndex;
    private char       mySymbol;
    private char       opponentSymbol;

    public Morpion()
    {
        this.board         = new char  [3][3];
        this.grid          = new JLabel[3][3];
        this.hasPlayed     = false;
        this.lastMoveIndex = -1;
        this.myTurn        = false;

        this.frame = new JFrame("Morpion Multijoueur");
        this.panel = new JPanel(new GridLayout(3, 3, 10, 10));
        this.panel.setBackground(Color.BLACK);

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                this.board[i][j] = ' ';
                this.grid [i][j] = new JLabel(new ImageIcon("./images/vide.png"));
                this.grid [i][j].addMouseListener(this);
                this.panel.add(grid[i][j]);
            }
        }

        this.frame.add(panel);
        this.frame.pack();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
    }

    public void setMySymbol(char symbol)
    {
        this.mySymbol       = symbol;
        this.opponentSymbol = (symbol == 'X') ? 'O' : 'X';
    }

    public void    setTurn (boolean turn) { this.myTurn    = turn    ; }
    public boolean getAJoue()             { return this.hasPlayed    ; }
    public void    setAJoue(boolean b)    { this.hasPlayed = b       ; }
    public int     getInt  ()             { return this.lastMoveIndex; }
    public void    majIHM  ()             { this.panel.repaint()     ; }

    public void    placerJeton(int index, char symbole)
    {
        int row = index / 3;
        int col = index % 3;

        if (board[row][col] == ' ')
        {
            this.board[row][col] = symbole;
            this.grid [row][col].setIcon(new ImageIcon("./images/" + symbole + ".png"));
        }
    }

    public boolean aGagner(char symbole)
    {
        for (int i = 0; i < 3; i++)
        {
            if (this.board[i][0] == symbole && this.board[i][1] == symbole && this.board[i][2] == symbole) return true;
            if (this.board[0][i] == symbole && this.board[1][i] == symbole && this.board[2][i] == symbole) return true;
        }
        if (this.board[0][0] == symbole && this.board[1][1] == symbole && this.board[2][2] == symbole) return true;
        if (this.board[0][2] == symbole && this.board[1][1] == symbole && this.board[2][0] == symbole) return true;
        return false;
    }

    private boolean isBoardFull()
    {
        for (char[] row : this.board)
            for (char c : row)
                if (c == ' ') return false;
        return true;
    }

    public void mouseClicked(MouseEvent e)
    {
        if (!this.myTurn) return;

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (e.getSource() == this.grid[i][j] && this.board[i][j] == ' ')
                {
                    this.placerJeton(i * 3 + j, this.mySymbol);
                    this.lastMoveIndex = i * 3 + j;
                    this.hasPlayed = true;
                    this.myTurn = false;
                    this.checkGameEnd(this.mySymbol);
                    return;
                }
            }
        }
    }

    private void checkGameEnd(char symbole)
    {
        if (aGagner(symbole))
        {
            JOptionPane.showMessageDialog(this.frame, "Le joueur '" + symbole + "' a gagné !");
            this.frame.dispose();
        }
        else if (isBoardFull())
        {
            JOptionPane.showMessageDialog(this.frame, "Match nul !");
            this.frame.dispose();
        }
    }

    public void receiveMove(int index)
    {
        placerJeton (index, this.opponentSymbol);
        checkGameEnd(       this.opponentSymbol);
    }

    public void mouseEntered (MouseEvent e) {}
    public void mouseExited  (MouseEvent e) {}
    public void mousePressed (MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
}
