import com.sun.corba.se.spi.presentation.rmi.PresentationManager;

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

        b = new Ball(200,260);
        paddle = new Paddle(200,299,0xFFFF0000);
        lador=new ArrayList<>();
        lador.add(new Lada(10, 30, 0xFFFFFFFF));
        lador.add(new Lada(10, 70, 0xFFFFFFFF));
        lador.add(new Lada(100, 30, 0xFFFFFFFF));
        lador.add(new Lada(190, 30, 0xFFFFFFFF));
        lador.add(new Lada(280, 30, 0xFFFFFFFF));
        lador.add(new Lada(100, 70, 0xFFFFFFFF));
        lador.add(new Lada(190, 70, 0xFFFFFFFF));
        lador.add(new Lada(280, 70, 0xFFFFFFFF));
        lador.add(new Lada(100, 110, 0xFFFFFFFF));
        lador.add(new Lada(190, 110, 0xFFFFFFFF));
        lador.add(new Lada(280, 110, 0xFFFFFFFF));
        lador.add(new Lada(10, 110, 0xFFFFFFFF));
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

        b.update(paddle.getBoundingBox(),lador);
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