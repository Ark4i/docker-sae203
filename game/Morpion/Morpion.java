
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Morpion implements MouseListener
{
    private JFrame     frame;
    private JPanel     panel;
    private JLabel[][] grid;
    private char[][]   board;
    private boolean    myTurn;
    private boolean    hasPlayed;
    private int        lastMoveIndex;
    private char       mySymbol;
    private char       opponentSymbol;

    public Morpion()
    {
        board         = new char[3][3];
        grid          = new JLabel[3][3];
        hasPlayed     = false;
        lastMoveIndex = -1;
        myTurn        = false;

        frame = new JFrame("Morpion Multijoueur");
        panel = new JPanel(new GridLayout(3, 3, 10, 10));
        panel.setBackground(Color.BLACK);

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                board[i][j] = ' ';
                grid [i][j] = new JLabel(new ImageIcon("./images/vide.png"));
                grid [i][j].addMouseListener(this);
                panel.add(grid[i][j]);
            }
        }

        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void setMySymbol(char symbol)
    {
        mySymbol = symbol;
        opponentSymbol = (symbol == 'X') ? 'O' : 'X';
    }

    public void    setTurn (boolean turn) { myTurn = turn       ; }
    public boolean getAJoue()             { return hasPlayed    ; }
    public void    setAJoue(boolean b)    { hasPlayed = b       ; }
    public int     getInt  ()             { return lastMoveIndex; }
    public void    majIHM  ()             { panel.repaint()     ; }

    public void    placerJeton(int index, char symbole)
    {
        int row = index / 3;
        int col = index % 3;

        if (board[row][col] == ' ')
        {
            board[row][col] = symbole;
            grid[row][col].setIcon(new ImageIcon("./images/" + symbole + ".png"));
        }
    }

    public boolean aGagner(char symbole)
    {
        for (int i = 0; i < 3; i++)
        {
            if (board[i][0] == symbole && board[i][1] == symbole && board[i][2] == symbole) return true;
            if (board[0][i] == symbole && board[1][i] == symbole && board[2][i] == symbole) return true;
        }
        if (board[0][0] == symbole && board[1][1] == symbole && board[2][2] == symbole) return true;
        if (board[0][2] == symbole && board[1][1] == symbole && board[2][0] == symbole) return true;
        return false;
    }

    private boolean isBoardFull()
    {
        for (char[] row : board)
            for (char c : row)
                if (c == ' ') return false;
        return true;
    }

    public void mouseClicked(MouseEvent e)
    {
        if (!myTurn) return;

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (e.getSource() == grid[i][j] && board[i][j] == ' ')
                {
                    placerJeton(i * 3 + j, mySymbol);
                    lastMoveIndex = i * 3 + j;
                    hasPlayed = true;
                    myTurn = false;
                    checkGameEnd(mySymbol);
                    return;
                }
            }
        }
    }

    private void checkGameEnd(char symbole)
    {
        if (aGagner(symbole))
        {
            JOptionPane.showMessageDialog(frame, "Le joueur '" + symbole + "' a gagné !");
            frame.dispose();
        }
        else if (isBoardFull())
        {
            JOptionPane.showMessageDialog(frame, "Match nul !");
            frame.dispose();
        }
    }

    public void receiveMove(int index)
    {
        placerJeton(index, opponentSymbol);
        checkGameEnd(opponentSymbol);
    }

    public void mouseEntered (MouseEvent e) {}
    public void mouseExited  (MouseEvent e) {}
    public void mousePressed (MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
}
