package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTING
    final int originalTileSize = 16; // 16pixel x 16 pixel tile size
    final int scale = 3; // scaling factor
    public final int tileSize = originalTileSize * scale; // 48 x 48 pixels
    // 4: 3 Screen Ratio
    public final int maxScreenCol = 16; // 16 tiles horizontally
    public final int maxScreenRow = 12; // 12 tiles vertically
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    //FPS
    int FPS = 60;

    TileManager tileManager = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Sound music = new Sound();
    Sound soundEffects = new Sound();
    Thread gameThread; // For repeating a process like updating screen 60 times per second
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public CollisionChecker collChecker = new CollisionChecker(this);
    // ENTITY & OBJECT
    public Player player = new Player(this, keyH);
    public SuperObject[] obj = new SuperObject[10];
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // initialize window
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // for better rendering performance
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();
        playMusic(0);
    }
    public void startGameThread() {
        gameThread = new Thread(this); // Passing GamePanel class to the Thread
        gameThread.start(); // this will call the run method
     }

    @Override
    public void run() {
        double drawInterval = 10e8/FPS;
        double delta = 0;
        double lastTime = System.nanoTime();
        double currentTime;
        while(gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; // typecast to Graphics2D for more function access


        tileManager.draw(g2);

        for (SuperObject superObject : obj) {
            if (superObject != null) {
                superObject.draw(g2, this);
            }
        }

        player.draw(g2);
        ui.draw(g2);
        g2.dispose(); // to save memory
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSoundEffect(int i) {
        soundEffects.setFile(i);
        soundEffects.play();
    }
}
