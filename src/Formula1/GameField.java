package Formula1;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {

    private Image trace = new ImageIcon("trace.jpg").getImage();
    private Random random = new Random();
    private Timer timer = new Timer(1, this);
    private MyCar myCar = new MyCar(320, 540, 40);
    private OtherCar otherCar1 = new OtherCar(650, 2000, -10);
    private OtherCar otherCar2 = new OtherCar(600, -1300, 21);
    private OtherCar otherCar3 = new OtherCar(900, -1500, 26);
    private OtherCar otherCar4 = new OtherCar(200, 3500, -11);
    private OtherCar otherCar5 = new OtherCar(50, -1000, 8);
    private GameComponents petrol = new GameComponents(random.nextInt(800), -100);
    private GameComponents flag = new GameComponents(random.nextInt(800), -500);

    private int traceX1 = 0;
    private int traceY1 = 0;
    private int traceWidth = 940;
    private int traceHeight = 800;
    private int traceX2 = 0;
    private int traceY2 = -800;
    private double petrolCount = 100;
    private int score = 0;

    private boolean inGame = true;
    private BufferedImage myCarBf;
    private BufferedImage otherCar1Bf;
    private BufferedImage otherCar2Bf;
    private BufferedImage otherCar3Bf;
    private BufferedImage otherCar4Bf;
    private BufferedImage otherCar5Bf;
    private BufferedImage light;
    private BufferedImage turbo;
    private BufferedImage warning;
    private BufferedImage petrolCanister;
    private BufferedImage flags;
    private BufferedImage crashImage;

    GameField() {
        timer.start();
        setFocusable(true);
        addKeyListener(new KeyControl());
    }

    public void paint(Graphics g) {
        super.paint(g);
        try {
            myCarBf = ImageIO.read(new File("myCar.png"));
            warning = ImageIO.read(new File("warning.png"));
            otherCar1Bf = ImageIO.read(new File("car1.png"));
            otherCar2Bf = ImageIO.read(new File("car2.png"));
            otherCar3Bf = ImageIO.read(new File("car3.png"));
            otherCar4Bf = ImageIO.read(new File("car4.png"));
            otherCar5Bf = ImageIO.read(new File("car5.png"));
            light = ImageIO.read(new File("light.png"));
            turbo = ImageIO.read(new File("turbo.png"));
            petrolCanister = ImageIO.read(new File("petrol.png"));
            flags = ImageIO.read(new File("flag.png"));
            crashImage = ImageIO.read(new File("1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        g.setColor(Color.darkGray);
        g.fillRect(1024, 0, 160, 800);
        g.setColor(Color.yellow);
        g.setFont(new Font("serif", Font.BOLD, 13));
        g.drawString("Petrol = " + Math.round(petrolCount) + " Litre", 1030, 50);
        g.setColor(Color.green);
        g.drawString("Score = " + score + ":", 1030, 102);
        g.drawString("Speed = " + myCar.directSpeed * 3 + " km/h", 1030, 122);
        g.drawString("Control:", 1030, 150);
        g.drawString("right VK : right", 1030, 172);
        g.drawString("left VK : left", 1030, 194);
        g.drawString("up VK : turbo", 1030, 216);
        g.drawString("down VK : break", 1030, 238);
        if (petrolCount < 30) {
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Low Petrol", 1030, 70);
            g.drawString("Check Canister", 1030, 87);
        }
        g.drawImage(trace, traceX1, traceY1, this);
        g.drawImage(trace, traceX2, traceY2, this);
        g.drawImage(myCarBf, myCar.x, myCar.y, this);
        g.drawImage(otherCar1Bf, otherCar1.x, otherCar1.y, this);
        g.drawImage(otherCar2Bf, otherCar2.x, otherCar2.y, this);
        g.drawImage(otherCar3Bf, otherCar3.x, otherCar3.y, this);
//        g.drawImage(otherCar4Bf, otherCar4.x, otherCar4.y, this);
//        g.drawImage(otherCar5Bf, otherCar5.x, otherCar5.y, this);
        g.drawImage(flags, flag.xP, flag.yP, null);
        drawWarning(otherCar1);
        drawWarning(otherCar4);
        if (!inGame) {
            g.drawImage(crashImage, 312, 100, 500, 600, this);

        }
    }

    private void moveBack(double speed) {
        if (petrolCount > 0) {
            traceY1 += speed;
            traceY2 += speed;
            petrolCount -= 0.03;
            if (traceY1 > 800) {
                traceY1 = 0;
                traceY2 = -800;
            }
        }
    }

    private void drawWarning(OtherCar car) {
        if (inGame) {
            if (car.y - 250 > myCar.y && (car.x + 40 | car.x) >= myCar.x && (car.x + 40 | car.x) <= myCar.x + 100) {
                getGraphics().drawImage(warning, car.x + 10, 700, null);
            }
        }
    }

    private void checkPetrol() {
        if (inGame) {
            if (petrolCount <= 30) {
                getGraphics().drawImage(petrolCanister, petrol.xP, petrol.yP, null);
                petrol.movePoint(5);
                if (myCar.x + 40 >= petrol.xP && myCar.x <= petrol.xP + 50 && myCar.y <= petrol.yP + 67) {
                    petrolCount += 50;
                    petrol.yP = -100;
                    int [] ints = new int[5];
                    int max = Arrays.stream(ints).max().getAsInt();
                }
            }
        }
    }

    private void checkFlag() {
        if (inGame) {
            flag.movePoint(10);
            if (myCar.x + 40 >= flag.xP && myCar.x <= flag.xP + 100 && myCar.y <= flag.yP + 54) {
                score++;
                flag.xP = random.nextInt(850);
                flag.yP = -500;
            }
            if (flag.yP > 800) {
                flag.xP = random.nextInt(850);
                flag.yP = -500;
            }
        }
    }

    private void otherCarMove(OtherCar car, double speed, int interaction) {
        if (inGame) {
            int x = random.nextInt(traceWidth);
            car.move(0, speed);
            if (car.y > interaction * traceHeight) {
                car.y = -interaction * traceHeight;
                if (x < 25 && x >= otherCar1.x && x <= otherCar1.x + 100
                        && x >= otherCar2.x && x <= otherCar2.x + 100
                        && x >= otherCar3.x && x <= otherCar3.x + 100
                        && x >= otherCar4.x && x <= otherCar4.x + 100
                        && x >= otherCar5.x && x <= otherCar5.x + 135) {
                    car.x = 730;
                } else {
                    car.x = x;
                }
            }
            if (car.y < -interaction * traceHeight) {
                car.y = interaction * traceHeight;
                if (x < 25) {
                    car.x = 720;
                } else {
                    car.x = x;
                }
            }
        }
    }

    private void carCrash() {
        if (inGame) {
            if (isCarCrashed(40)) {

                inGame = false;

            }
            if (isCarCrashed(90)) {

                inGame = false;

            }
            if (isCarCrashed(10)) {

                inGame = false;

            }

        }

    }

    private boolean isCarCrashed(int crashPoint){
        return (myCar.x + crashPoint >= otherCar1.x && myCar.x + crashPoint <= otherCar1.x + 100 && otherCar1.y <= myCar.y + 208 && myCar.y <= otherCar1.y + 195 ||
                myCar.x + crashPoint >= otherCar2.x && myCar.x + crashPoint <= otherCar2.x + 100 && otherCar2.y <= myCar.y + 208 && myCar.y <= otherCar2.y + 208 ||
                myCar.x + crashPoint >= otherCar3.x && myCar.x + crashPoint <= otherCar3.x + 80 && otherCar3.y <= myCar.y + 208 && myCar.y <= otherCar3.y + 202 ||
                myCar.x + crashPoint >= otherCar4.x && myCar.x + crashPoint <= otherCar4.x + 100 && otherCar4.y <= myCar.y + 208 && myCar.y <= otherCar4.y + 189 ||
                myCar.x + crashPoint >= otherCar5.x && myCar.x + crashPoint <= otherCar5.x + 135 && otherCar5.y <= myCar.y + 208 && myCar.y <= otherCar5.y + 376);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            moveBack(myCar.directSpeed);
            otherCarMove(otherCar1, otherCar1.directSpeed, 2);
            otherCarMove(otherCar2, otherCar2.directSpeed, 2);
            otherCarMove(otherCar3, otherCar3.directSpeed, 4);
//            otherCarMove(otherCar4, otherCar4.directSpeed, 6);
//            otherCarMove(otherCar5, otherCar5.directSpeed, 7);
            checkPetrol();
            checkFlag();
            carCrash();
        }else {
            int result = JOptionPane.showConfirmDialog(this,"Exit ?");
            if (result == JOptionPane.YES_OPTION){
                System.exit(1);
            }else {
                restart();

            }
        }
        repaint();
    }

    private void restart(){
        inGame = true;
        score = 0;
        petrolCount = 100;
        myCar.x = 320;
        myCar.y = 540;
        myCar.directSpeed = 40;
        otherCar1.x = 650;
        otherCar1.y = 2000;
        otherCar1.directSpeed = -10;
        otherCar2.x = 600;
        otherCar2.y = -1300;
        otherCar2.directSpeed = 21;
        otherCar3.x = 900;
        otherCar3.y = -1500;
        otherCar3.directSpeed = 26;
        otherCar4.x = 200;
        otherCar4.y = 3500;
        otherCar4.directSpeed = -11;
        otherCar5.x = 50;
        otherCar5.y = 1000;
        otherCar5.directSpeed = 8;
    }

    public class KeyControl extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (inGame) {
                switch (key) {
                    case KeyEvent.VK_RIGHT: {
                        if (myCar.x + 120 <= 1024) {
                            myCar.move(25, 0);
                        }
                    }
                    break;
                    case KeyEvent.VK_LEFT: {
                        if (myCar.x >= 0)
                            myCar.move(-25, 0);
                    }
                    break;
                    case KeyEvent.VK_UP: {
                        getGraphics().drawImage(turbo, myCar.x + 20, myCar.y + 200, null);
                        if (myCar.directSpeed <= 60) {
                            myCar.directSpeed++;

                            otherCar1.directSpeed++;
                            otherCar4.directSpeed++;
                            otherCar2.directSpeed += 0.3;
                            otherCar3.directSpeed += 0.3;
                            petrolCount--;
                        }
                    }
                    break;
                    case KeyEvent.VK_DOWN: {
                        getGraphics().drawImage(light, myCar.x - 10, myCar.y + 123, null);
                        if (myCar.directSpeed > 0) {
                            myCar.directSpeed--;


                            otherCar1.directSpeed -= 0.5;
                            otherCar4.directSpeed -= 0.5;
                            otherCar2.directSpeed -= 0.3;
                            otherCar3.directSpeed -= 0.3;
                        }
                    }
                    break;
                }

            }
        }
    }
}
