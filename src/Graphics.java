import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

/**
 * This is a class
 * Created 2020-03-25
 *
 * @author Magnus Silverdal
 */
public class Graphics extends Canvas implements Runnable {
    private String title = "Graphics";
    private int width;
    private int height;

    private JFrame frame;
    private BufferedImage image;
    private int[] pixels;
    private int scale;

    private Thread thread;
    private boolean running = false;
    private int fps = 60;
    private int ups = 60;

    private Ball b;
    private Paddle paddle;
    private ArrayList <Lada> lador;
    private Lada lada1;
    private Lada lada2;
    private Lada lada3;
    private Lada lada4;
    private Lada lada5;
    private Lada lada6;
    private Lada lada7;
    private Lada lada8;
    private Lada lada9;
    private Lada lada10;
    private Lada lada11;
    private Lada lada12;
    private Lada lada13;
    private Lada lada14;
    private Lada lada15;
    private Lada lada16;
    private Lada lada17;
    private Lada lada18;
    private Lada lada19;
    private Lada lada20;
    private Lada lada21;
    private Lada lada22;
    private Lada lada23;
    private Lada lada24;
    private Lada lada25;
    private Lada lada26;
    private Lada lada27;
    private Lada lada28;
    private Lada lada29;
    public Graphics(int w, int h, int scale) {
        this.width = w;
        this.height = h;
        this.scale = scale;
        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        Dimension size = new Dimension(scale*width, scale*height);
        setPreferredSize(size);
        frame = new JFrame();
        frame.setTitle(title);
        frame.add(this);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        this.addKeyListener(new MyKeyListener());
        this.addMouseListener(new MyMouseListener());
        this.requestFocus();

        b = new Ball(200,100);
        paddle = new Paddle(200,299,0xFFFF0000);
        lador.add(new Lada(10,10,0xFFFFFFFF));
        lada1 = new Lada(10,30,0xFFFFFFFF);
        lada2 = new Lada(10,50,0xFFFFFFFF);
        lada3 = new Lada(10,70,0xFFFFFFFF);
        lada4 = new Lada(10,90,0xFFFFFFFF);
        lada5 = new Lada(10,110,0xFFFFFFFF);
        lada6 = new Lada(80,110,0xFFFFFFFF);
        lada7 = new Lada(150,110,0xFFFFFFFF);
        lada8 = new Lada(220,110,0xFFFFFFFF);
        lada9 = new Lada(290,110,0xFFFFFFFF);
        lada10 = new Lada(290,90,0xFFFFFFFF);
        lada11 = new Lada(290,70,0xFFFFFFFF);
        lada12 = new Lada(290,50,0xFFFFFFFF);
        lada13 = new Lada(290,30,0xFFFFFFFF);
        lada14 = new Lada(290,10,0xFFFFFFFF);
        lada15 = new Lada(220,30,0xFFFFFFFF);
        lada16 = new Lada(150,30,0xFFFFFFFF);
        lada17 = new Lada(80,30,0xFFFFFFFF);
        lada18 = new Lada(220,10,0xFFFFFFFF);
        lada19 = new Lada(150,10,0xFFFFFFFF);
        lada20 = new Lada(80,10,0xFFFFFFFF);
        lada21 = new Lada(80,50,0xFFFFFFFF);
        lada22 = new Lada(150,50,0xFFFFFFFF);
        lada23 = new Lada(220,50,0xFFFFFFFF);
        lada24 = new Lada(80,70,0xFFFFFFFF);
        lada25 = new Lada(150,70,0xFFFFFFFF);
        lada26 = new Lada(220,70,0xFFFFFFFF);
        lada27 = new Lada(80,90,0xFFFFFFFF);
        lada28 = new Lada(150,90,0xFFFFFFFF);
        lada29 = new Lada(220,90,0xFFFFFFFF);
//finns det något bättre sätt att lägga till lådor :(
    }

    private void draw() {
        for (int i = 0 ; i < pixels.length ; i++) {
            pixels[i] = 0xFF000000;
        }
        b.draw(pixels,width);
        paddle.draw(pixels,width);
        for (int i=0; i<lador.size(); i++)
            lador.get(i).draw(pixels,width);


        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        java.awt.Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g.dispose();
        bs.show();
    }

    private void update() {

        b.update(paddle.getBoundingBox());
        paddle.update();

    }

    public synchronized void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        double frameUpdateinteval = 1000000000.0 / fps;
        double stateUpdateinteval = 1000000000.0 / ups;
        double deltaFrame = 0;
        double deltaUpdate = 0;
        long lastTime = System.nanoTime();

        while (running) {
            long now = System.nanoTime();
            deltaFrame += (now - lastTime) / frameUpdateinteval;
            deltaUpdate += (now - lastTime) / stateUpdateinteval;
            lastTime = now;

            while (deltaUpdate >= 1) {
                update();
                deltaUpdate--;
            }

            while (deltaFrame >= 1) {
                draw();
                deltaFrame--;
            }
        }
        stop();
    }

    private class MyKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent keyEvent) {

        }

        @Override
        public void keyPressed(KeyEvent keyEvent) {
            paddle.keyPressed(keyEvent);
        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {
            paddle.keyReleased(keyEvent);
        }
    }

    private class MyMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {
        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {
        }
    }
}