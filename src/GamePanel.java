import sounds.EatSound;
import sounds.GameOverSound;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class GamePanel extends JPanel {
    private static final int width = 800;
    private static final int height = 600;
    private static final int size = 25;
    private static final int units = (width * height) / size;
    private int delay = 200;
    private final int[] snakeX = new int[units];
    private final int[] snakeY = new int[units];
    private int length;
    private int appleX;
    private int appleY;
    private boolean running;
    private final Random random = new Random();
    private char direction;
    private int score;
    private Timer timer;
    private final GameOverSound sound;

    public GamePanel() {
        sound = new GameOverSound();
        new EatSound();
        setLayout(new FlowLayout());
        setBackground(new Color(399679));
        setOpaque(true);
        addKeyListener(new Keys());
        timer = new Timer(delay, e -> {
            if (running) {
                move();
                apple();
                collision();
            }

            repaint();
        });
        setFocusable(true);
    }

    //start game
    public void startGame() {
        score = 0;
        length = 1;
        direction = 'd';
        newApple();
        running = true;
        snakeX[0] = 400;
        snakeY[0] = 300;
        timer.start();
    }

    public void paintComponent(Graphics g) {
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            gameRunning(g);
        } else {
            gameOver(g);
        }
    }

    //draw snake and apple, write score
    public void gameRunning(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(getForeground());
        g.setColor(Color.red);
        g.fillOval(appleX, appleY, size, size);
        g.setColor(new Color(5812758));
        g.fillRect(snakeX[0], snakeY[0], size, size);

        IntStream.range(1, length).forEach(i -> {
            g.setColor(new Color(18441));
            g.fillRect(snakeX[i], snakeY[i], 25, 25);
        });

        g.setColor(new Color(8, 152, 255));
        g.setFont(new Font("Serif", Font.BOLD, 20));
        g.drawString("Score: " + score, 0, g.getFont().getSize());
    }

    //game over text and buttons
    public void gameOver(Graphics g) {
        JButton b = new JButton("Save score");
        JButton bNew = new JButton("New game");
        JButton bExit = new JButton("Exit");
        b.addActionListener((e) -> {
            try {
                saveFile();
                b.setEnabled(false);

            } catch (IOException var3) {
                throw new RuntimeException(var3);
            }
        });

        bNew.addActionListener(e -> {
            removeAll();
            repaint();
            startGame();
        });

        bExit.addActionListener(e -> System.exit(0));

        b.setBounds(300, 225, 200, 50);
        bNew.setBounds(300, 325, 200, 50);
        bExit.setBounds(300, 425, 200, 50);

        List<JButton> buttons = new ArrayList<>(Arrays.asList(b, bNew, bExit));

        buttons.stream()
                .peek(button -> button.setLayout(null))
                .forEach(this::add);



        //game over text
        ImageIcon background = new ImageIcon("src/background/blue.gif");
        Graphics2D g2d = (Graphics2D) g;
        background.paintIcon(this, g2d, -150, -200);
        g.setColor(new Color(8, 152, 255));
        g.setFont(new Font("Serif", Font.BOLD, 80));
        FontMetrics m2 = this.getFontMetrics(g.getFont());
        g.drawString("Game Over", (width - m2.stringWidth("Game over")) / 2, 150);
    }

    //check apple eat
    public void apple() {
        if (snakeX[0] == appleX && snakeY[0] == appleY) {
            length++;
            score++;
            new EatSound();
            newApple();
            if (delay > 100 && score % 3 == 0) {
                delay -= 50;
                timer.stop();
                timer = new Timer(delay, e -> {
                    if (running) {
                        move();
                        apple();
                        collision();
                    }
                    repaint();
                });
                timer.start();
            }
        }
    }

    //set new apple position
    public void newApple() {
        appleX = random.nextInt(1, width / size - 1) * size;
        appleY = random.nextInt(1, height / size - 1) * size;
    }

    //check collision
    public void collision() {
        for (int i = 1; i < snakeX.length; ++i) {
            if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                running = false;
                timer.stop();
                sound.soundStart();
                break;
            }
        }

        if (snakeX[0] < 0) {
            running = false;
            timer.stop();
            sound.soundStart();
        }

        if (snakeY[0] < 0) {
            running = false;
            timer.stop();
            sound.soundStart();
        }

        if (snakeX[0] >= width) {
            running = false;
            timer.stop();
            sound.soundStart();
        }

        if (snakeY[0] >= height - size) {
            running = false;
            timer.stop();
            sound.soundStart();
        }
    }

    //snake move
    public void move() {
        for (int i = length - 1; i >= 0; --i) {
            snakeX[i + 1] = snakeX[i];
            snakeY[i + 1] = snakeY[i];
        }

        switch (direction) {
            case 'd' -> snakeY[0] += size;
            case 'l' -> snakeX[0] -= size;
            case 'r' -> snakeX[0] += size;
            case 'u' -> snakeY[0] -= size;
        }
    }

    //save score
    private void saveFile() throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        FileWriter fw = new FileWriter("Score.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        String var10001 = dtf.format(now);
        bw.write("Time: " + var10001 + ", score: " + score);
        bw.newLine();
        bw.close();
    }

    //key adapter
    public class Keys extends KeyAdapter {
        public Keys() {
        }

        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            switch (e.getKeyChar()) {
                case 'a' -> {
                    if (direction != 'r') {
                        direction = 'l';
                    }
                }
                case 'd' -> {
                    if (direction != 'l') {
                        direction = 'r';
                    }
                }
                case 's' -> {
                    if (direction != 'u') {
                        direction = 'd';
                    }
                }
                case 'w' -> {
                    if (direction != 'd') {
                        direction = 'u';
                    }
                }
            }
        }
    }
}