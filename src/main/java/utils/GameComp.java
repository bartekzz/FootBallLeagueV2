package utils;

import common.Game;
import java.util.Comparator;

/**
 * A class which implements a comparator used to order dates (Game-object)
 */
public class GameComp implements Comparator<Game> {

    public int compare(Game ob1, Game ob2) {
        if (ob1.getDate().after(ob2.getDate())) {
            return 1;
        }
        else {
            return -1;
        }
    }
}
