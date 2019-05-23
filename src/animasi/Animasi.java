/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animasi;

/**
 *
 * @author dex
 */
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class Animasi extends Frame implements Runnable {

    Thread animation;
    int frameDelay = 35;
    Image frames[];
    Image frames2[];
    Image frames3[];
    Image sun;
    Image aw;
    Image play;
    int numFrames;
    int numFrames2;
    int numFrames3;
    int currentFrame = 0;
    int currentFrame2 = 0;
    int currentFrame3 = 0;
    long lastDisplay = 0;
    int screenWidth = 279;
    int screenHeight = 170;
    int clip = 0;
    boolean gerak = true;
    String state = "menu";
    int sound = 0;
    String o = System.getProperty("user.dir") + "\\src\\sprite\\";
    
    public static void main(String[] args) {
        Animasi app = new Animasi();
    }

    private void backsound() {
        String url = new File("src/sound/Lagu Anak - Helly (Aku Punya Anjing Kecil).mp3").toURI().toString();
        new javafx.scene.media.AudioClip(url).play();
    }

    public Animasi() {

        super("Animasi dikejar anjing");
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getX() >= 102 && e.getX() <= 148) {
                    if (e.getY() >= 86 && e.getY() <= 118) {
                        state = "play";

                    }

                }
            }
        });
        setup();
        setSize(screenWidth, screenHeight);
        addWindowListener(new WindowEventHandler());
        show();
        animation = new Thread(this);
        animation.start();
    }

    void setup() {
        setupMenuBar();
        setFont(new Font("default", Font.BOLD, 18));
        Toolkit toolkit = getToolkit();
        frames = new Image[8];
        frames2 = new Image[4];
        frames3 = new Image[3];
        sun = toolkit.getImage(o + "sun.png");
        aw = toolkit.getImage(o + "aw.png");
        play = toolkit.getImage(o + "play.png");
        // objek yang akan ditampilkan
        frames[0] = toolkit.getImage(o + "l1.png");
        frames[1] = toolkit.getImage(o + "l2.png");
        frames[2] = toolkit.getImage(o + "l3.png");
        frames[3] = toolkit.getImage(o + "l4.png");
        frames[4] = toolkit.getImage(o + "l5.png");
        frames[5] = toolkit.getImage(o + "l6.png");
        frames[6] = toolkit.getImage(o + "l7.png");
        frames[7] = toolkit.getImage(o + "l8.png");
        frames2[0] = toolkit.getImage(o + "r1.png");
        frames2[1] = toolkit.getImage(o + "r2.png");
        frames2[2] = toolkit.getImage(o + "r3.png");
        frames2[3] = toolkit.getImage(o + "r4.png");
        frames3[0] = toolkit.getImage(o + "d1.png");
        frames3[1] = toolkit.getImage(o + "d2.png");
        frames3[2] = toolkit.getImage(o + "d3.png");

        numFrames = frames.length;
        numFrames2 = frames2.length;
        numFrames3 = frames3.length;
    }

    void setupMenuBar() {

        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem fileExit = new MenuItem("Exit");
        fileExit.addActionListener(new MenuItemHandler());
        fileMenu.add(fileExit);
        menuBar.add(fileMenu);
        setMenuBar(menuBar);
    }

    public void paint(Graphics g) {

        if (state.equalsIgnoreCase("play")) {
            if (clip <= 80) {
                gerak = true;
            } else if (clip >= 120) {
                gerak = false;
            }

            if (gerak == true) {
                clip += 1;
            } else if (gerak == false) {
                clip -= 1;
            }
            g.drawImage(sun, 220, 50, this);
            g.drawImage(aw, 110, 70, this);
            g.drawImage(aw, 20, 60, this);
            g.drawImage(frames2[currentFrame2], 0, 149, this);
            g.drawImage(frames[currentFrame], clip, 100, this);
            g.drawImage(frames3[currentFrame3], clip - 60, 118, this);
            if (sound == 0) {
                sound = 1;
                backsound();
            }
        } else {
            g.drawImage(play, 100, 85, this);
        }

    }

    public void run() {
        // Perulangan animasi
        do {

            long time = System.currentTimeMillis();
            if (time - lastDisplay > frameDelay) {
                removeAll();
                repaint();
                try {
                    Thread.sleep(frameDelay);
                } catch (InterruptedException ex) {
                }
                ++currentFrame;
                ++currentFrame2;
                ++currentFrame3;
                currentFrame %= numFrames;
                currentFrame2 %= numFrames2;
                currentFrame3 %= numFrames3;
                lastDisplay = time;
            }
        } while (true);
    }

    class MenuItemHandler implements ActionListener, ItemListener {

        public void actionPerformed(ActionEvent ev) {
            String s = ev.getActionCommand();
            if (s == "Exit") {
                System.exit(0);
            }
        }

        public void itemStateChanged(ItemEvent e) {
        }
    }

    class WindowEventHandler extends WindowAdapter {

        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }
}
