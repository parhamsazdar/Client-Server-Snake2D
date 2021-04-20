package Server;

import java.io.Serializable;

public class Snake implements Serializable {
    static int[] Snake_X_Length = new int[750];
    static int[] Snake_Y_Length = new int[750];

    int Score = 0;
    int SnakeBodyLength = 25;
    int LengthOfSnake = 3;


    public void MoveRight() {
        for (int r = LengthOfSnake - 1; r >= 0; r--) {
            Snake_Y_Length[r + 1] = Snake_Y_Length[r];
        }
        for (int r = LengthOfSnake; r >= 0; r--) {
            if (r == 0) {
                Snake_X_Length[r] += SnakeBodyLength;
            } else {
                Snake_X_Length[r] = Snake_X_Length[r - 1];
            }
            if (Snake_X_Length[r] > GamePlay.MAX_WIDTH_LOC) {
                Snake_X_Length[r] = GamePlay.MIN_WIDTH_LOC;
            }
        }
    }


    public void MoveLeft() {
        for (int r = LengthOfSnake - 1; r >= 0; r--) {
            Snake_Y_Length[r + 1] = Snake_Y_Length[r];
        }
        for (int r = LengthOfSnake; r >= 0; r--) {
            if (r == 0) {
                Snake_X_Length[r] -= SnakeBodyLength;
            } else {
                Snake_X_Length[r] = Snake_X_Length[r - 1];
            }
            if (Snake_X_Length[r] < GamePlay.MIN_WIDTH_LOC) {
                Snake_X_Length[r] = GamePlay.MAX_WIDTH_LOC;
            }
        }
    }

    public void MoveUp() {
        for (int r = LengthOfSnake - 1; r >= 0; r--) {
            Snake_X_Length[r + 1] = Snake_X_Length[r];
        }
        for (int r = LengthOfSnake; r >= 0; r--) {
            if (r == 0) {
                Snake_Y_Length[r] -= SnakeBodyLength;
            } else {
                Snake_Y_Length[r] = Snake_Y_Length[r - 1];
            }
            if (Snake_Y_Length[r] < GamePlay.MAX_HEIGHT_LOC) {
                Snake_Y_Length[r] = GamePlay.MIN_HEIGHT_LOC;
            }
        }
    }

    public void MoveDown() {
        for (int r = LengthOfSnake - 1; r >= 0; r--) {
            Snake_X_Length[r + 1] = Snake_X_Length[r];
        }
        for (int r = LengthOfSnake; r >= 0; r--) {
            if (r == 0) {
                Snake_Y_Length[r] += SnakeBodyLength;
            } else {
                Snake_Y_Length[r] = Snake_Y_Length[r - 1];
            }
            if (Snake_Y_Length[r] > GamePlay.MIN_HEIGHT_LOC) {
                Snake_Y_Length[r] = GamePlay.MAX_HEIGHT_LOC;
            }
        }
    }


}
