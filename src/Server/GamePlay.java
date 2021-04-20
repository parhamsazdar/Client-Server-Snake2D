package Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class GamePlay extends JPanel implements KeyListener, ActionListener, Serializable {

    static int MIN_HEIGHT_LOC = 625;
    static int MAX_HEIGHT_LOC = 75;

    static int MIN_WIDTH_LOC = 25;
    static int MAX_WIDTH_LOC = 850;

    static boolean isFinish=false;
    static boolean isWinner = false;
    static int[] FOOD_X = {25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500, 525, 550, 575, 600, 625, 650,
            675, 700, 725, 750, 775, 800, 825, 850};
    static int[] FOOD_Y = {75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500, 525, 550, 575, 600, 625};

    static ArrayList<Integer> Initialize_X_Index = new ArrayList<Integer>(Arrays.asList(1, 2, 3));


    static int[] BARRIER_X = FOOD_X.clone();
    static int[] BARRIER_Y = FOOD_Y.clone();


    private Random random = new Random();
    private int X_POS_FOOD = random.nextInt(FOOD_X.length);
    private int Y_POS_FOOD = random.nextInt(FOOD_Y.length);

    private int move = 0;

    private boolean up = false;
    private boolean down = false;
    private boolean right = false;
    private boolean left = false;
    Barrier barrier;
    Snake snake;
    private ImageIcon RightMouth;
    private ImageIcon LeftMouth;
    private ImageIcon DownMouth;
    private ImageIcon upMouth;
    private ImageIcon SnakeImage;
    private ImageIcon FoodIcon = new ImageIcon(getClass().getResource("..\\enemy.png"));
    private ImageIcon Title = new ImageIcon(getClass().getResource("..\\snaketitle.jpg"));


    private Timer timer;
    private int Delay = 100;


    public GamePlay(Barrier barrier, Snake snake) {
        addKeyListener(this);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(Delay, this);
        timer.start();
        this.barrier = barrier;
        this.snake = snake;

    }

    public void setDefaultDirection(Graphics g) {
        RightMouth = new ImageIcon(getClass().getResource("..\\rightmouth.png"));
        RightMouth.paintIcon(this, g, this.snake.Snake_X_Length[0], this.snake.Snake_Y_Length[0]);
    }

    public void InitializeSnakeDefaultLength(Graphics g) {
        this.snake.Snake_X_Length[2] = FOOD_X[Initialize_X_Index.get(0)];
        this.snake.Snake_X_Length[1] = FOOD_X[Initialize_X_Index.get(1)];
        this.snake.Snake_X_Length[0] = FOOD_X[Initialize_X_Index.get(2)];


        this.snake.Snake_Y_Length[2] = 100;
        this.snake.Snake_Y_Length[1] = 100;
        this.snake.Snake_Y_Length[0] = 100;

    }


    public void FinalizedGame() {
        right = false;
        left = false;
        up = false;
        down = false;
        timer.setDelay(Delay);
    }

    public void isEatFood() {

        if (this.snake.Snake_X_Length[0] == FOOD_X[X_POS_FOOD] && this.snake.Snake_Y_Length[0] == FOOD_Y[Y_POS_FOOD]) {
            this.snake.LengthOfSnake++;
            X_POS_FOOD = random.nextInt(FOOD_X.length);
            Y_POS_FOOD = random.nextInt(FOOD_Y.length);
            timer.setDelay(timer.getDelay() - 5);
            this.snake.Score++;
        }
    }

    public int getScore() {
        return this.snake.Score;
    }

    public void isCollisionToBarrier(Graphics g) {
        java.util.List<Integer> MapRandom_X = barrier.RandomXIndexBarrier.stream().map(number -> BARRIER_X[number]).collect(Collectors.toList());
        java.util.List<Integer> MapRandom_Y = barrier.RandomYIndexBarrier.stream().map(number -> BARRIER_Y[number]).collect(Collectors.toList());
        for (int i = 0; i < MapRandom_X.size(); i++) {
            if (MapRandom_X.get(i).equals(this.snake.Snake_X_Length[0]) && MapRandom_Y.get(i).equals(this.snake.Snake_Y_Length[0])) {
                FinalizedGame();
                g.setColor(Color.WHITE);
                g.setFont(new Font("arial", Font.BOLD, 50));
                g.drawString("Game Over", 300, 300);

                g.setFont(new Font("arial", Font.BOLD, 20));
                g.drawString("Press Enter To Restart", 350, 350);
            }
        }
    }

    public void isCollision(Graphics g) {
        for (int b = 1; b < this.snake.LengthOfSnake; b++) {
            if (this.snake.Snake_X_Length[0] == this.snake.Snake_X_Length[b] && this.snake.Snake_Y_Length[0] == this.snake.Snake_Y_Length[b]) {

                FinalizedGame();
                g.setColor(Color.WHITE);
                g.setFont(new Font("arial", Font.BOLD, 50));
                g.drawString("Game Over", 300, 300);

                g.setFont(new Font("arial", Font.BOLD, 20));
                g.drawString("Press Enter To Restart", 350, 350);
            }
        }
    }

    public void RestartGame() {
        FinalizedGame();
        this.snake.Score = 0;
        isFinish = false;
        isWinner =false;
        move = 0;
        this.snake.LengthOfSnake = 3;
        this.barrier.ResetBarrierLocation();
        repaint();
    }

    /**
     * check  the food location dosent have equal location with barriers
     */
    public boolean isValidLocationForFood(int x, int y) {
        int Index_x = barrier.RandomXIndexBarrier.indexOf(x);
        int Index_y = barrier.RandomYIndexBarrier.indexOf(y);
        if (Index_x == Index_y && Index_x !=-1){
            return false;
        }
        else {
            return true;
        }
    }

    public void IntroduceWiner(Graphics g){
        FinalizedGame();
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.BOLD, 50));
        g.drawString("Game Over", 300, 300);

        g.setFont(new Font("arial", Font.BOLD, 20));
        g.drawString("Press Enter To Restart", 350, 350);
    }

    public void isWinnerOfGame(Graphics g){
        FinalizedGame();
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.BOLD, 50));
        g.drawString("You are winner", 300, 300);

        g.setFont(new Font("arial", Font.BOLD, 20));
        g.drawString("Press Enter To Restart", 350, 350);
    }


    public void paint(Graphics g) {
        requestFocus(true);
        // Draw a title image border
        g.setColor(Color.WHITE);
        g.drawRect(24, 10, 851, 55);

        // Fill the image icon of title
        Title.paintIcon(this, g, 25, 11);


        // draw play area
        g.setColor(Color.WHITE);
        g.drawRect(24, 74, 851, 577);

        // draw background for the gameplay
        g.setColor(Color.black);
        g.fillRect(25, 75, 850, 575);

        // set default direction of snake

        setDefaultDirection(g);

        for (int a = 0; a < this.snake.LengthOfSnake; a++) {
            if (move == 0) {
                // Check Food location is located correctly
                if (isValidLocationForFood(X_POS_FOOD, Y_POS_FOOD) && !Initialize_X_Index.contains(X_POS_FOOD)
                        && Y_POS_FOOD != 100) {
                } else {
                    X_POS_FOOD = this.random.nextInt(FOOD_X.length);
                    Y_POS_FOOD = this.random.nextInt(FOOD_Y.length);
                }
                InitializeSnakeDefaultLength(g);
            }

            if (a == 0 && right) {
                RightMouth = new ImageIcon(getClass().getResource("..\\rightmouth.png"));
                RightMouth.paintIcon(this, g, this.snake.Snake_X_Length[a], this.snake.Snake_Y_Length[a]);
            } else if (a == 0 && left) {
                LeftMouth = new ImageIcon(getClass().getResource("..\\leftmouth.png"));
                LeftMouth.paintIcon(this, g, this.snake.Snake_X_Length[a], this.snake.Snake_Y_Length[a]);
            } else if (a == 0 && up) {
                upMouth = new ImageIcon(getClass().getResource("..\\upmouth.png"));
                upMouth.paintIcon(this, g, this.snake.Snake_X_Length[a], this.snake.Snake_Y_Length[a]);
            } else if (a == 0 && down) {
                DownMouth = new ImageIcon(getClass().getResource("..\\downmouth.png"));
                DownMouth.paintIcon(this, g, this.snake.Snake_X_Length[a], this.snake.Snake_Y_Length[a]);
            } else if (a != 0) {
                SnakeImage = new ImageIcon(getClass().getResource("..\\snakeimage.png"));
                SnakeImage.paintIcon(this, g, this.snake.Snake_X_Length[a], this.snake.Snake_Y_Length[a]);
            }
        }

        // This method is for Game over
        isEatFood();
        // Check if food location is valid

        FoodIcon.paintIcon(this, g, FOOD_X[X_POS_FOOD], FOOD_Y[Y_POS_FOOD]);


        // paint barrier
        for (int i = 0; i < barrier.RandomXIndexBarrier.size(); i++) {
            ImageIcon Test = new ImageIcon(getClass().getResource("..\\barrier_2.jpg"));
            Test.paintIcon(this, g, BARRIER_X[barrier.RandomXIndexBarrier.get(i)],
                    BARRIER_Y[barrier.RandomYIndexBarrier.get(i)]);
        }
        // check for collision to it self
        isCollision(g);
        // check for collision to barrier
        isCollisionToBarrier(g);
        //
        if (isWinner){
            isWinnerOfGame(g);
        }
        if (isFinish && !isWinner){
            IntroduceWiner(g);
        }
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (right) {
            this.snake.MoveRight();
            repaint();
        }
        if (left) {
            this.snake.MoveLeft();
            repaint();
        }
        if (up) {
            this.snake.MoveUp();
            repaint();
        }
        if (down) {
            this.snake.MoveDown();
            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            RestartGame();
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            move++;
            right = (!left) ? true : false;
            up = false;
            down = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            move++;
            left = (!right) ? true : false;
            up = false;
            down = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            move++;
            up = (!down) ? true : false;
            right = false;
            left = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            move++;
            down = (!up) ? true : false;
            right = false;
            left = false;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
