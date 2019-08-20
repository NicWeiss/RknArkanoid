package com.id_softrare.rkn_arkanoid.GO;

import com.id_softrare.rkn_arkanoid.rooms.Game;

/**
 * Created by nic on 4/20/18.
 */

public class Telega extends Services {

    Game game;

    public Telega(Game _game){
        game = _game;
    }
    @Override
    public void do_fall(float x_speed, float y_speed) {
        super.do_fall(x_speed, y_speed);
        game.gen_telega++;
    }
}
