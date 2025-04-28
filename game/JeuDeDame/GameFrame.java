
import javax.swing.*;

public class GameFrame extends JFrame {
    public GameFrame() {
        setTitle("Jeu de Dames");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setJMenuBar(new GameMenuBar(this));
        add(new BoardPanel());
        pack();
        setLocationRelativeTo(null);
    }
}
