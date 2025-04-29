import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Morpion implements MouseListener , ActionListener
{
    private static final String[] ENS_THEME = {"basique","espace","nourriture","planete"};
    private int        cptTheme; 

    private JFrame     frame;

    private JPanel     panelMorpion;
    private JLabel[][] tabLabel;
    private char  [][] plateau;

    private JTextField txtChat;
    private JTextArea  logChat;
    private JButton    btnChat;

    private boolean    monTour;
    private boolean    aJoue;
    private int        dernierMouvement;
    private char       monSigne;
    private char       signeOpposant;

    private JButton    btnTheme;
    private JLabel     lblTheme;


    public Morpion()
    {
        /* ----------------------------- */
        /* ----------------------------- */
		/*            METIER             */
		/* ----------------------------- */
        /* ----------------------------- */
        this.cptTheme         = 0;
        this.aJoue            = false;
        this.dernierMouvement = -1;
        this.monTour          = false;
        this.monSigne         = ' ';

        this.plateau          = new char [3][3];
        for (int cptLig = 0; cptLig < this.plateau.length; cptLig++)
            for (int cptCol = 0; cptCol < this.plateau[cptLig].length; cptCol++)
                this.plateau[cptLig][cptCol] = ' ';
        
        /* ----------------------------- */
        /* ----------------------------- */
		/*              IHM              */
		/* ----------------------------- */
        /* ----------------------------- */

        JPanel      panelSend, panelChat, panelTheme;
        JScrollPane scrollPane;
        
        /* ----------------------------- */
		/* Création des composants       */
		/* ----------------------------- */
        this.frame        = new JFrame("Morpion Multijoueur");

        this.panelMorpion = new JPanel(new GridLayout(3, 3, 10, 10));
        this.panelMorpion.setBackground(Color.BLACK);

        this.tabLabel = new JLabel[this.plateau.length][this.plateau[0].length];
        for (int cptLig = 0; cptLig < this.tabLabel.length; cptLig++)
            for (int cptCol = 0; cptCol < this.tabLabel[cptLig].length; cptCol++)
                this.tabLabel[cptLig][cptCol] = new JLabel(new ImageIcon("./images/vide.png"));


        panelChat = new JPanel( new BorderLayout() );

        this.logChat = new JTextArea();
        this.logChat.setEditable(false);

        scrollPane = new JScrollPane(this.logChat);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        panelSend = new JPanel();

        this.txtChat = new JTextField();
        this.txtChat.setPreferredSize(new Dimension(300, 30));

        this.btnChat = new JButton("Envoyer");


        panelTheme = new JPanel();

        this.btnTheme = new JButton("Thème");

        this.lblTheme = new JLabel();

		/* ----------------------------- */
		/* Positionnement des composants */
		/* ----------------------------- */

        for (int cptLig = 0; cptLig < this.tabLabel.length; cptLig++)
            for (int cptCol = 0; cptCol < this.tabLabel[cptLig].length; cptCol++)
                this.panelMorpion.add(this.tabLabel[cptLig][cptCol]);


        panelChat.add(  panelSend, BorderLayout.SOUTH  );
        panelChat.add( scrollPane, BorderLayout.CENTER );


        panelSend.add( this.txtChat );
        panelSend.add( this.btnChat );


        panelTheme.add( this.btnTheme );
        panelTheme.add( this.lblTheme );

        panelChat.add( panelTheme, BorderLayout.NORTH );

        this.frame.add(      panelChat   , BorderLayout.EAST   );
        this.frame.add( this.panelMorpion, BorderLayout.CENTER );

        this.frame.pack();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
		/* ------------------------------ */
		/* Activation des composants      */
		/* ------------------------------ */

        for (JLabel[] ligne : this.tabLabel)
            for (JLabel c : ligne)
                c.addMouseListener(this);
        
        this.btnChat.addActionListener(this);

        this.btnTheme.addActionListener(this);
        
    }

    public void setMonSigne(char symbol)
    {
        this.monSigne      = symbol;
        this.signeOpposant = (symbol == 'X') ? 'O' : 'X';

        this.lblTheme.setIcon( new ImageIcon( "./images/" + Morpion.ENS_THEME[this.cptTheme] + "/" + this.monSigne + ".png" ) );
    }

    public void    setTour             (boolean turn) {        this.monTour = turn        ; }
    public boolean getAJoue            ()             { return this.aJoue                 ; }
    public void    setAJoue            (boolean b)    {        this.aJoue   = b           ; }
    public int     getDernierMouvement ()             { return this.dernierMouvement      ; }
    public void    majIHM              ()             {        this.panelMorpion.repaint(); }

    public void changerTheme() 
    {
        if ( this.cptTheme >= Morpion.ENS_THEME.length -1 ) this.cptTheme = 0;
        else                                                this.cptTheme++;

        for (int cptLig = 0; cptLig < this.plateau.length; cptLig++)
            for (int cptCol = 0; cptCol < this.plateau[cptLig].length; cptCol++)
            {
                char symbole =      this.plateau [cptLig][cptCol];
                if (symbole == ' ') this.tabLabel[cptLig][cptCol].setIcon(new ImageIcon("./images/vide.png"));
                else                this.tabLabel[cptLig][cptCol].setIcon(new ImageIcon("./images/" + Morpion.ENS_THEME[this.cptTheme] + "/" + symbole + ".png"));
            }

        this.lblTheme.setIcon( new ImageIcon( "./images/" + Morpion.ENS_THEME[this.cptTheme] + "/" + this.monSigne + ".png" ) );
        
        this.majIHM();
    }

    public void placerJeton(int index, char symbole)
    {
        int lig = index / 3;
        int col = index % 3;

        if (plateau[lig][col] == ' ')
        {
            this.plateau [lig][col] = symbole;
            this.tabLabel[lig][col].setIcon(new ImageIcon("./images/" + Morpion.ENS_THEME[this.cptTheme] + "/" + symbole + ".png"));
        }
    }

    public boolean aGagner(char symbole)
    {
        for (int lig = 0; lig < this.plateau.length; lig++)
            if (this.plateau[lig][0] == symbole && this.plateau[lig][1] == symbole && this.plateau[lig][2] == symbole) return true;
        
        for (int col = 0; col < this.plateau[0].length; col++)
            if (this.plateau[0][col] == symbole && this.plateau[1][col] == symbole && this.plateau[2][col] == symbole) return true;
            
        if (this.plateau[0][0] == symbole && this.plateau[1][1] == symbole && this.plateau[2][2] == symbole) return true;
        if (this.plateau[0][2] == symbole && this.plateau[1][1] == symbole && this.plateau[2][0] == symbole) return true;
        return false;
    }

    private boolean plateauEstPlein()
    {
        for (char[] ligne : this.plateau)
            for (char c : ligne )
                if (c == ' ') return false;
        return true;
    }

    private void viderPlateau()
    {
        for (int cptLig = 0; cptLig < this.plateau.length; cptLig++)
            for (int cptCol = 0; cptCol < this.plateau[cptLig].length; cptCol++)
            {
                this.plateau [cptLig][cptCol] = ' ';
                this.tabLabel[cptLig][cptCol].setIcon(new ImageIcon("./images/vide.png"));
            }
    }

    public void mouseClicked(MouseEvent e)
    {
        if (!this.monTour) return;

        for (int cptLig = 0; cptLig < this.plateau.length; cptLig++)
            for (int cptCol = 0; cptCol < this.plateau[cptLig].length; cptCol++)
            {
                if (e.getSource() == this.tabLabel[cptLig][cptCol] && this.plateau[cptLig][cptCol] == ' ')
                {
                    this.placerJeton(cptLig * 3 + cptCol, this.monSigne);
                    this.dernierMouvement = cptLig * 3 + cptCol;
                    this.aJoue            = true;
                    this.monTour          = false;
                    this.finJeu(this.monSigne);
                    return;
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

        if ( e.getSource() == this.btnTheme ) this.changerTheme();
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

    public static void main(String[] args) {
        new Morpion();
    }
}