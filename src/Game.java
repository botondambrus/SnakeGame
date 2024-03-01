import sounds.Music;

import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {
    private final CardLayout layout;
    private final JPanel cardPanel;
    private final Music music;
    private boolean playMusic = true;


    public Game() {

        music = new Music();
        music.soundStart();
        layout = new CardLayout();
        cardPanel = new JPanel();
        cardPanel.setLayout(layout);
        add(cardPanel);
        GamePanel game = new GamePanel();

        //settings panel
        SettingsPanel settings = new SettingsPanel();
        JButton bMuteMusic = new JButton("Mute music");
        JButton bPlayMusic = new JButton("Play music");
        JButton bMain = new JButton("Main Menu");

        bPlayMusic.setLayout(null);
        bPlayMusic.setBounds(350, 140, 100, 50);
        settings.setLayout(null);
        settings.add(bPlayMusic);

        bMuteMusic.setLayout(null);
        bMuteMusic.setBounds(350, 265, 100, 50);
        settings.setLayout(null);
        settings.add(bMuteMusic);

        bMain.setLayout(null);
        bMain.setBounds(350, 390, 100, 50);
        settings.add(bMain);


        //menu panel
        GameMenu menu = new GameMenu();
        JButton b = new JButton("Start game");
        JButton bExit = new JButton("Exit");
        JButton bSettings = new JButton("Settings");

        b.setLayout(null);
        b.setBounds(350, 150, 100, 50);
        menu.setLayout(null);
        menu.add(b);

        bSettings.setLayout(null);
        bSettings.setBounds(350, 275, 100, 50);
        menu.setLayout(null);
        menu.add(bSettings);

        bExit.setLayout(null);
        bExit.setBounds(350, 400, 100, 50);
        menu.setLayout(null);
        menu.add(bExit);

        cardPanel.add(menu, "menu");
        cardPanel.add(game, "game");
        cardPanel.add(settings, "settings");

        //menu panel buttons
        b.addActionListener(e -> {
            layout.show(cardPanel, "game");
            game.requestFocusInWindow();
            game.setFocusable(true);
            game.startGame();
        });

        bExit.addActionListener(e -> dispose());

        bSettings.addActionListener(e -> {
            layout.show(cardPanel, "settings");
            settings.requestFocusInWindow();
            settings.setFocusable(true);
        });

        //settings panel buttons

        bMain.addActionListener(e -> {
            layout.show(cardPanel, "menu");
            menu.requestFocusInWindow();
            menu.setFocusable(true);
        });

        bMuteMusic.addActionListener(e -> {
            if (playMusic){
                music.soundStop();
                playMusic = false;
            }

        });

        bPlayMusic.addActionListener(e -> {
            if(!playMusic) {
                music.soundStart();
                playMusic = true;
            }
        });

        setTitle("Snake game");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(50, 50, 812, 612);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
    }
}
