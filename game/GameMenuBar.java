
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMenuBar extends JMenuBar {
    public GameMenuBar(JFrame frame) {
        JMenu menu = new JMenu("Menu");

        JMenuItem newGame = new JMenuItem("Nouvelle Partie");
        newGame.addActionListener(e -> {
            frame.dispose();
            Main.main(null);
        });

        JMenuItem quit = new JMenuItem("Quitter");
        quit.addActionListener(e -> System.exit(0));

        menu.add(newGame);
        menu.add(quit);
        add(menu);
    }
}
