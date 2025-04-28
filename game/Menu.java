import javax.swing.*;
import java.awt.event.*;

public class Menu extends JMenuBar implements ActionListener
{
    private JMenuItem     menuiMorpion;

    public Menu()
    {
        /*----------------------------*/
        /* Création des composants    */
        /*----------------------------*/

        // les JMenu
        JMenu menuJeu    = new JMenu("Menu Jeux"   );
        JMenu menuOption = new JMenu("Menu Option" );

        // les JItemMenu
        this.menuiMorpion = new JMenuItem ("Morpion" );



        /*-------------------------------*/
        /* positionnement des composants */
        /*-------------------------------*/

        // des JItemMenu dans les JMenu
        menuFichier.add( this.menuiFichierNouveau );
        menuFichier.add( this.menuiFichierOuvrir  );


        // Des JMenu dans la JMenuBar
        this.add( menuFichier   );


        /*-------------------------------*/
        /* Activation des composants     */
        /*-------------------------------*/
        this.menuiFichierNouveau        .addActionListener ( this );
        this.menuiFichierOuvrir         .addActionListener ( this );
        

    }

    public void actionPerformed ( ActionEvent e )
    {
        if ( e.getSource() instanceof JMenuItem )
            System.out.println ( ( (JMenuItem) e.getSource() ).getText() );

    }
}
