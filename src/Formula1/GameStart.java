package Formula1;



import javax.swing.*;
import java.awt.*;


public class GameStart extends JFrame {

    GameField field = new GameField();
    private final int WIDTH = 1184;
    private final int HEIGHT = 800;

    GameStart(){
        setSize(WIDTH,HEIGHT);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Շամշյանի մոտ");
        setLocation(200,50);
        add(field);
    }
    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null,"CONTROL"+"\nright VK : right" +
                "\n left VK : left" +
                "\n up VK : turbo" +
                "\n down VK : break");
        EventQueue.invokeLater(GameStart::new);

    }
}
