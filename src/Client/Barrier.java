package Client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

enum Decision {
    VERTICAL,
    HORIZONTAL,
}

public class Barrier implements Serializable {
    /**
     * This class First set random  X & Y
     * Based of that X & Y Fill RandomXIndexBarrier & RandomYIndexBarrier
     * Use RandomYIndexBarrier & RandomXIndexBarrier for paint in my Game Board
     */
    static int MIN_SIZE_BARRIER = 5;
    static int MAX_SIZE_BARRIER = 15;
    static int NUMBER_BARRIER = 0;
    Random random = new Random();
    ArrayList<Integer> RandomXIndexBarrier = new ArrayList<>();
    ArrayList<Integer> RandomYIndexBarrier = new ArrayList<>();
    int FIRST_X;
    int FIRST_Y;

    public Barrier() {
        FIRST_X = random.nextInt(4 + GamePlay.BARRIER_X.length - 7);
        FIRST_Y = random.nextInt(4 + GamePlay.BARRIER_Y.length - 7);

        this.RandomXIndexBarrier.add(FIRST_X);
        this.RandomYIndexBarrier.add(FIRST_Y);

        this.SelectVerticalOrHorizontal(this.RandomXIndexBarrier, this.RandomYIndexBarrier);
    }

    public void ResetBarrierLocation() {
        FIRST_X = random.nextInt(GamePlay.BARRIER_X.length);
        FIRST_Y = random.nextInt(GamePlay.BARRIER_Y.length);

        RandomXIndexBarrier.clear();
        RandomYIndexBarrier.clear();

        RandomXIndexBarrier.add(FIRST_X);
        RandomYIndexBarrier.add(FIRST_Y);

        SelectVerticalOrHorizontal(this.RandomXIndexBarrier, this.RandomYIndexBarrier);
    }

    /**
     * Return random Decision
     */
     static Decision ReturnRandomDecision() {
        Random random = new Random();
        Decision[] DecisionArray = {Decision.VERTICAL, Decision.HORIZONTAL};
        int RandomInt = random.nextInt(2);
        return DecisionArray[RandomInt];
    }

    /**
     * Recognize the side element of given argument
     * Then randomly Fill the RandomXIndexBarrier
     */
    public void ReturnX_Pos(int x) {
        ArrayList<Integer> Test_X = new ArrayList<>();
        if (x < GamePlay.BARRIER_X.length - 1 && x > 0) {
            if (!this.RandomXIndexBarrier.contains(x + 1) && x + 1 < GamePlay.BARRIER_X.length - 1) {
                Test_X.add(x + 1);
            }
            if (!this.RandomXIndexBarrier.contains(x - 1) && x - 1 > 0) {
                Test_X.add(x - 1);
            }
            if (Test_X.size() > 0) {
                int MyLocalRandom = this.random.nextInt(Test_X.size());
                if (this.isValidLocation(Test_X.get(MyLocalRandom), RandomYIndexBarrier.get(RandomYIndexBarrier.size() - 1))) {
                    this.RandomXIndexBarrier.add(Test_X.get(MyLocalRandom));
                    this.RandomYIndexBarrier.add(RandomYIndexBarrier.get(RandomYIndexBarrier.size() - 1));
                    NUMBER_BARRIER++;
                }
            }
        }
    }

    public boolean isValidLocation(int x, int y) {
        if (GamePlay.Initialize_X_Index.contains(x) && y == 1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Recognize the side element of given argument
     * Then randomly Fill the RandomYIndexBarrier
     */
    public void ReturnY_Pos(int y) {
        ArrayList<Integer> Test_Y = new ArrayList<>();
        if (y < GamePlay.BARRIER_Y.length - 1 && y > 0) {
            if (!this.RandomYIndexBarrier.contains(y + 1) && y + 1 < GamePlay.BARRIER_Y.length - 1) {
                Test_Y.add(y + 1);
            }
            if (!this.RandomYIndexBarrier.contains(y - 1) && y - 1 > 0) {
                Test_Y.add(y - 1);
            }
            if (Test_Y.size() > 0) {
                int MyLocalRandom = this.random.nextInt(Test_Y.size());
                if (this.isValidLocation(RandomXIndexBarrier.get(RandomXIndexBarrier.size() - 1), Test_Y.get(MyLocalRandom))) {
                    this.RandomYIndexBarrier.add(Test_Y.get(MyLocalRandom));
                    this.RandomXIndexBarrier.add(RandomXIndexBarrier.get(RandomXIndexBarrier.size() - 1));
                    NUMBER_BARRIER++;
                }
            }
        }
    }

    /**
     * Get help from ReturnRandomDecision for random decision
     * Fill the RandomXIndexBarrier & RandomYIndexBarrier from random position
     */
    public void SelectVerticalOrHorizontal(ArrayList<Integer> RandomXIndexBarrier, ArrayList<Integer> RandomYIndexBarrier) {
        for (int i = 0; i < RandomYIndexBarrier.size(); i++) {

            Decision DecisionVar = this.ReturnRandomDecision();
            if (DecisionVar.equals(Decision.HORIZONTAL)) {
                this.ReturnX_Pos(RandomXIndexBarrier.get(i));

            } else {
                this.ReturnY_Pos(RandomYIndexBarrier.get(i));
            }
        }

    }


    public void AddBarrier() {
        int FIRST_X_New = random.nextInt(4 + GamePlay.BARRIER_X.length - 7);
        int FIRST_Y_New = random.nextInt(4 + GamePlay.BARRIER_Y.length - 7);

        ArrayList<Integer> LocalRandomX = new ArrayList<>(Arrays.asList(FIRST_X_New));
        ArrayList<Integer> LocalRandomY = new ArrayList<>(Arrays.asList(FIRST_Y_New));


        SelectVerticalOrHorizontal(LocalRandomX, LocalRandomY);
        this.RandomXIndexBarrier.addAll(LocalRandomX);
        this.RandomYIndexBarrier.addAll(LocalRandomY);
    }


    public static void main(String[] args) {
//        Barrier barrier = new Barrier();
////        barrier.SelectVerticalOrHorizontal();
//        System.out.println(barrier.RandomYIndexBarrier);
//        System.out.println(barrier.RandomXIndexBarrier);

        ArrayList<Integer> TestArray = new ArrayList<>(Arrays.asList(1, 2, 3));
        int x = TestArray.get(0);
        TestArray.set(0, 10);
        int y = 25;
        System.out.println(TestArray.indexOf(y));

    }
}
