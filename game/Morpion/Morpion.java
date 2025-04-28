import javax.swing.*;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.*;

public class Morpion implements MouseListener
{
    private JFrame     framePrc;
    private JPanel     panelPrc;
    private JLabel[][] tabLabel;
    
    private char[][] plateau;
    private char     joueur;

    public Morpion()
    {
        this.plateau = new char[3][3];
        for ( int cptLig = 0; cptLig< this.plateau.length; cptLig++ )
            for ( int cptCol = 0; cptCol < this.plateau[cptLig].length; cptCol++ )
                this.plateau[cptLig][cptCol] = ' ';

        this.joueur  = 'X';
        
        this.framePrc = new JFrame();
        this.panelPrc = new JPanel();
        this.panelPrc.setBackground(Color.BLACK);

        this.tabLabel = new JLabel[3][3];
        for ( int cptLig = 0; cptLig< this.tabLabel.length; cptLig++ )
            for ( int cptCol = 0; cptCol < this.tabLabel[cptLig].length; cptCol++ )
            {
                this.tabLabel[cptLig][cptCol] = new JLabel( new ImageIcon( "./images/vide.png" ) );
                this.panelPrc.add( this.tabLabel[cptLig][cptCol] );
                this.tabLabel[cptLig][cptCol].addMouseListener( this );
            }

        this.framePrc.add( this.panelPrc );

        this.panelPrc.setLayout( new GridLayout(3,3, 10, 10) );
        
        this.framePrc.setVisible(true);
        this.framePrc.pack();

    }

    public void mouseClicked(MouseEvent e)
	{
		for ( int cptLig = 0; cptLig< this.tabLabel.length; cptLig++ )
            for ( int cptCol = 0; cptCol < this.tabLabel[cptLig].length; cptCol++ )
                if ( e.getSource() == this.tabLabel[cptLig][cptCol] && this.plateau[cptLig][cptCol] == ' ' )
                {
                    this.tabLabel[cptLig][cptCol].setIcon( new ImageIcon( "./images/" + this.joueur + ".png" ) );
                    this.plateau[cptLig][cptCol] = this.joueur;
                    this.changerJoueur();
                    System.out.println("aaa");
                }
        
        if ( this.gagner() | this.finDujeu() )
        {
            this.framePrc.setVisible(false);
            this.framePrc = new JFrame();
            this.framePrc.add( new JLabel("FIN DU JEUX") );
            this.framePrc.setSize(100, 100);
            this.framePrc.setVisible(true);
        }
    }

	public void mouseEntered (MouseEvent e){}
	public void mouseExited  (MouseEvent e){}
    public void mousePressed (MouseEvent e){}
	public void mouseReleased(MouseEvent e){}

    private boolean finDujeu()
    {   
        for ( int cptLig = 0; cptLig< this.plateau.length; cptLig++ )
            for ( int cptCol = 0; cptCol < this.plateau[cptLig].length; cptCol++ )
                if ( this.plateau[cptLig][cptCol] == ' ' ) return false;
                
        return true;
    }

    private boolean gagner()
    {
        /*Horizontal */
        for ( int cpt = 0; cpt < this.plateau.length; cpt++ )
            if ( this.plateau[cpt][0] == this.joueur && this.plateau[cpt][1] == this.joueur && this.plateau[cpt][2] == this.joueur )
                return true;


        /*Vertical  */
        for ( int cpt = 0; cpt < this.plateau.length; cpt++ )
        if ( this.plateau[0][cpt] == this.joueur && this.plateau[1][cpt] == this.joueur && this.plateau[2][cpt] == this.joueur )
            return true;


        /*Diagonal Gauche */
        if ( this.plateau[0][0] == this.joueur && this.plateau[1][1] == this.joueur && this.plateau[2][2] == this.joueur )
            return true;

        /*Diagonal Droite */
        if ( this.plateau[2][0] == this.joueur && this.plateau[1][1] == this.joueur && this.plateau[0][2] == this.joueur )
            return true;

        return false;
    }

    private void changerJoueur()
    {
        if ( this.joueur == 'X' ) this.joueur = 'O';
        else                      this.joueur = 'X'; 
    }


	

    public static void main(String[] args)
    {
        new Morpion();    
    }
}
