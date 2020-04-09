import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.awt.Graphics;

public class Lada {

        private int[] pixels;
        private Rectangle boundingBox;
        private int width = 60;
        private int height = 10;

        public Lada(int x, int y, int col){
            boundingBox = new Rectangle(x, y, width, height);
            pixels = new int[width*height];
            for (int i = 0 ; i < pixels.length ; i++) {
                pixels[i] = col;
            }
        }

        public Rectangle getBoundingBox() {
            return boundingBox;
        }



        public void draw(int[] Screen, int screenWidth){
            for (int i = 0 ; i < height ; i++) {
                for (int j = 0 ; j < width ; j++) {
                    Screen[(boundingBox.y+i)*screenWidth + boundingBox.x+j] = pixels[i*width+j];



                }
            }
        }
    }