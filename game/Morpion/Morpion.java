import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Morpion implements MouseListener , ActionListener
{
    private static final String[] ENS_THEME = {"basique","espace","nourriture","planete"};

    private String themeActuel = "basique"; 

    private JFrame     frame;
    private JPanel     panelMorpion;
    private JLabel[][] tabLabel;
    private char  [][] plateau;
    private JPanel     panelChat;
    private JPanel     panelSend;
    private JTextField txtChat;
    private JTextArea  logChat;
    private JButton    btnChat;
    private boolean    monTour;
    private boolean    aJoue;
    private int        dernierMouvement;
    private char       monSigne;
    private char       signeOpposant;


    public Morpion()
    {
        this.plateau          = new char  [3][3];
        this.tabLabel         = new JLabel[3][3];
        this.aJoue            = false;
        this.dernierMouvement = -1;
        this.monTour          = false;

        this.frame = new JFrame("Morpion Multijoueur");
        this.panelMorpion = new JPanel(new GridLayout(3, 3, 10, 10));
        this.panelMorpion.setBackground(Color.BLACK);

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                this.plateau [i][j] = ' ';
                this.tabLabel[i][j] = new JLabel(new ImageIcon("./images/vide.png"));
                this.tabLabel[i][j].addMouseListener(this);
                this.panelMorpion.add(this.tabLabel[i][j]);
            }
        }

        this.panelChat = new JPanel( new BorderLayout() );
        this.frame.add(this.panelChat, BorderLayout.EAST);

        this.panelSend = new JPanel();
        this.panelChat.add(this.panelSend, BorderLayout.SOUTH);

        this.logChat = new JTextArea();
        this.logChat.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(this.logChat);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.panelChat.add(scrollPane, BorderLayout.CENTER);

        this.txtChat = new JTextField();
        this.txtChat.setPreferredSize(new Dimension(300, 30));
        this.panelSend.add(this.txtChat);
        this.btnChat = new JButton("Envoyer");
        this.panelSend.add(this.btnChat);
        this.btnChat.addActionListener(this);

        this.frame.add(this.panelMorpion);
        this.frame.pack();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
    }

    public void setMonSigne(char symbol)
    {
        this.monSigne      = symbol;
        this.signeOpposant = (symbol == 'X') ? 'O' : 'X';
    }

    public void    setTour             (boolean turn) {        this.monTour = turn  ; }
    public boolean getAJoue            ()             { return this.aJoue           ; }
    public void    setAJoue            (boolean b)    {        this.aJoue   = b     ; }
    public int     getDernierMouvement ()             { return this.dernierMouvement; }
    public void    majIHM              ()             {        this.panelMorpion.repaint() ; }

    public void setTheme(String theme) 
    {
        this.themeActuel = theme;
    }

    public void    placerJeton(int index, char symbole)
    {
        int row = index / 3;
        int col = index % 3;

        if (plateau[row][col] == ' ')
        {
            this.plateau [row][col] = symbole;
            this.tabLabel[row][col].setIcon(new ImageIcon("./images/" + this.themeActuel + "/" + symbole + ".png"));
        }
    }

    public boolean aGagner(char symbole)
    {
        for (int i = 0; i < 3; i++)
        {
            if (this.plateau[i][0] == symbole && this.plateau[i][1] == symbole && this.plateau[i][2] == symbole) return true;
            if (this.plateau[0][i] == symbole && this.plateau[1][i] == symbole && this.plateau[2][i] == symbole) return true;
        }
        if (this.plateau[0][0] == symbole && this.plateau[1][1] == symbole && this.plateau[2][2] == symbole) return true;
        if (this.plateau[0][2] == symbole && this.plateau[1][1] == symbole && this.plateau[2][0] == symbole) return true;
        return false;
    }

    private boolean plateauEstPlein()
    {
        for (char[] row : this.plateau)
            for (char c : row)
                if (c == ' ') return false;
        return true;
    }


    private void viderPlateau() {
        for (int i = 0; i < this.plateau.length; i++) {
            for (int j = 0; j < this.plateau[i].length; j++) {
                this.plateau[i][j] = ' ';  // Vide les données du plateau
                this.tabLabel[i][j].setIcon(new ImageIcon("./images/vide.png")); // Vide l'affichage
            }
        }
    }

    public void mouseClicked(MouseEvent e)
    {
        if (!this.monTour) return;

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (e.getSource() == this.tabLabel[i][j] && this.plateau[i][j] == ' ')
                {
                    this.placerJeton(i * 3 + j, this.monSigne);
                    this.dernierMouvement = i * 3 + j;
                    this.aJoue            = true;
                    this.monTour          = false;
                    this.finJeu(this.monSigne);
                    return;
                }
            }
        }
    }

    public void actionPerformed(ActionEvent e)
    {
        if ( e.getSource() == this.btnChat )
        {
            String message = this.txtChat.getText().trim();
            if ( message.isEmpty() ) return;
            
            this.logChat.append("Joueur " + this.monSigne + " : " + message + "\n");

            this.txtChat.setText("");
        }
    }

    private void finJeu(char symbole)
    {
        if (this.aGagner(symbole)||this.plateauEstPlein())
        {
            if(JOptionPane.showConfirmDialog(this.frame, "Voulez vous rejouer ?", "Repondez svp", JOptionPane.YES_NO_OPTION) == 0)
            {
                viderPlateau();
                this.majIHM();
                
            }
            
            else {this.frame.dispose();}
        }

    }

    public void receiveMove(int index)
    {
        this.placerJeton (index, this.signeOpposant);
        this.finJeu      (       this.signeOpposant);
    }

    public void mouseEntered (MouseEvent e) {}
    public void mouseExited  (MouseEvent e) {}
    public void mousePressed (MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
}