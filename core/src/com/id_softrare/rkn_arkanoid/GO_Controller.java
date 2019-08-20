package com.id_softrare.rkn_arkanoid;

import com.badlogic.gdx.graphics.Texture;
import com.id_softrare.rkn_arkanoid.GO.Ball;
import com.id_softrare.rkn_arkanoid.GO.Bonuces;
import com.id_softrare.rkn_arkanoid.GO.Player;
import com.id_softrare.rkn_arkanoid.GO.Services;
import com.id_softrare.rkn_arkanoid.GO.Telega;
import com.id_softrare.rkn_arkanoid.rooms.Game;
import static com.id_softrare.rkn_arkanoid.rooms.Game.objects_count;
import static com.id_softrare.rkn_arkanoid.rooms.Game.finger_x;

/**
 * Created by nic on 5/5/18.
 */

public class GO_Controller {

    private static GraphicLoader services_graphicLoader=null, bonuces_graphicLoader=null;
    private static Animation gem=null, b_fire=null;
    private static Game game;

    public static void init(Game _game){
        services_graphicLoader = new GraphicLoader();
        services_graphicLoader.load_grapfSet("services");

        bonuces_graphicLoader = new GraphicLoader();
        bonuces_graphicLoader.load_grapfSet("bonuces");

        //Загрузка анимации
        b_fire = new Animation();
        b_fire.load_package("sheild");


        game = _game;
    }



    public static void create_service(){
        Obj s;
        s = new Services();
        objects_count++;
        int id=+(int) (Math.random() * (services_graphicLoader.get_el_count()));
        s.set_texture(services_graphicLoader.get_texture(id));
        s.set_services_type(services_graphicLoader.get_type(id));
        game.objects.add(s);
    }

    public static void create_ball(int ball_count, float speed){
        float tmp=1;
        for(int i=1; i<= ball_count; i++) {
            Obj s;
            s = new Ball();
            s.set_texture(new Texture("system/ball.png"));
            s.set_services_type("ball");
            s.set_x(500);
            s.set_speed_x(speed*tmp);
            s.set_speed_y((float) Math.sin(Math.PI/2) * speed);
            s.set_x(finger_x-50);
            tmp*=-0.8;
            game.objects.add(s);
        }
    }

    public static void create_player(){
            Obj s;
            s = new Player();
            s.set_texture(new Texture("system/player.png"));
            s.set_services_type("player");
            game.objects.add(s);
    }

    public static void create_telegram(){
        Obj s;
        s = new Telega(game);
        int id = services_graphicLoader.get_id_by_type("telegram");
        if (id<0){return;}
        s.set_texture(services_graphicLoader.get_texture(id));
        s.set_services_type(services_graphicLoader.get_type(id));
        game.objects.add(s);
    }
    public static void create_bonuce(float x, float y){
        Obj s;
        s = new Bonuces();
        //s.set_texture(gem.get_frame());
        int id=+(int) (Math.random() * (bonuces_graphicLoader.get_el_count()));
        s.set_texture(bonuces_graphicLoader.get_texture(id));
        s.set_animation(b_fire);
        s.set_services_type(bonuces_graphicLoader.get_type(id));
        s.set_x(x);
        s.set_y(y);
        game.objects.add(s);
    }


}
