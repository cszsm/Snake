package control;

/**
 * Created by Zsolt on 2015.04.21..
 */
public class SingleGameManager extends GameManager {

    public SingleGameManager(SnakeManager snakeManager, FoodManager foodManager) {
        super(snakeManager, foodManager);
    }

    public void step() {

        snakeManager.step();
        if (snakeManager.eat(foodManager.getFood())) {
            foodManager.createFood(snakeManager.getSnake());
        }
        else
            snakeManager.removeTail();
    }
}
