package com.id_softrare.rkn_arkanoid;

import com.badlogic.gdx.Gdx;
import com.id_softrare.rkn_arkanoid.rooms.Game;

import java.util.TimerTask;

import static com.id_softrare.rkn_arkanoid.rooms.Game.hold;
import static com.id_softrare.rkn_arkanoid.rooms.Game.is_ball;
import static com.id_softrare.rkn_arkanoid.rooms.Game.objects;
import static com.id_softrare.rkn_arkanoid.rooms.Game.objects_count;

/**
 * Created by nic on 4/20/18.
 */



public class Physic extends TimerTask {

    private static Game game;

    private static int id = 0;

    public Physic(Game _game){
        game = _game;
        id++;
    }



    @Override
    public void run() {
        if(hold) { return;}      //если ожидание то выход
            int local_objects_count = 0, count_ball = 0;

            for (int i = 0; i < objects.size(); i++) {
                if (objects.get(i).get_type() == 2 || objects.get(i).get_type() == 3) {
                    if (!objects.get(i).statatus()) {
                        local_objects_count++; // Проверка, остались ли ещё сервисы и эффекты
                    }
                }

                if (objects.get(i).get_type() == 4) {
                    if (!objects.get(i).statatus()) {
                        local_objects_count++; // Проверка, остались ли ещё бонусы
                        game.hitboard(objects.get(i));

                    }
                }

                if (objects.get(i).get_services_type() == "ball") {
                    if (!objects.get(i).statatus()) {
                        count_ball++;
                    } // проверка есть ли ещё шары
                    game.hittest(objects.get(i));
                    game.hitboard(objects.get(i));
                }
            }

            objects_count = local_objects_count;
            if (count_ball > 0) {
                is_ball = true;
            } else {
                is_ball = false;
            }
    }


    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        Gdx.app.log("SB","physic finalize "+id);
        id --;
        game = null;
    }
}
