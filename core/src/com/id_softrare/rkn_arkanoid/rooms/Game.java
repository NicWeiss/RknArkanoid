package com.id_softrare.rkn_arkanoid.rooms;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.id_softrare.rkn_arkanoid.CoreClass;
import com.id_softrare.rkn_arkanoid.GO.Player;
import com.id_softrare.rkn_arkanoid.GO.Services;
import com.id_softrare.rkn_arkanoid.GO_Controller;
import com.id_softrare.rkn_arkanoid.GraphicLoader;
import com.id_softrare.rkn_arkanoid.Obj;
import com.id_softrare.rkn_arkanoid.Physic;
import com.id_softrare.rkn_arkanoid.Room;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by nic on 4/20/18.
 */

public class Game extends Room {

    Texture background, bar, life_image, higscore_image, pause_screen, black, death;
    BitmapFont font;
    public static float finger_x,finger_y, tmp_x, tmp_y;
    static Timer timer;
    boolean game = false;
    boolean gen_bonuce=false;
    boolean black_screen=false;
    public static float base_speed, mod_speed=0;
    private int gen_ball=0, to_exit=0;
    float black_opacity=0;
    private  GraphicLoader numbers;
    public static ArrayList<Obj> objects;
    private float score_scale_y, score_scale_x;
    public static int global_counter, objects_count=0;
    public static boolean  is_ball=true, pause=false, hold ;
    private int speed_heigh_limit=100;
    public static ShapeRenderer Radius;

    int player_id, life_count;

    public int gen_telega=0;

    @Override
    public void init() {
        Radius = new ShapeRenderer();
        GO_Controller.init(this);
        numbers = new GraphicLoader();
        numbers.load_grapfSet("numbers");

        hold=false;

        life_count=2;
        global_counter=0;
        mod_speed=0;
        score_scale_y=120;
        score_scale_x=90;
        finger_x=1080/2;
        font = new BitmapFont();
        font.setColor(Color.WHITE);//1f, 0f, 0f, 1f);
        font.getData().setScale(5f,5f);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        objects = new ArrayList<Obj>();
        base_speed=22;

        GO_Controller.create_player();
        GO_Controller.create_ball(1,0);

        background = new Texture("bg/background.png");
        pause_screen = new Texture("bg/pause_screen.png");
        bar = new Texture("system/bar.png");
        life_image = new Texture("system/ball.png");
        higscore_image = new Texture("system/blocked.png");
        black = new Texture("bg/black.png");
        death = new Texture("system/death.png");

        timer = new Timer();
        TimerTask task = new Physic(this);
        timer.scheduleAtFixedRate(task,0,33);


        Gdx.app.log("SB","game_init");
        Obj s;
        for (int i=0; i<50; i++){
            GO_Controller.create_service();
        }
        for (int i=0; i<20; i++){
            GO_Controller.create_telegram();
        }

    }

    public void hittest(Obj get_ball){
        float hyp;
        float kat1, kat2;
        Obj ball = get_ball;//objects.get(1);
        Obj service_item;
        for(int i=1;i<objects.size();i++){
            service_item=objects.get(i);

            if(service_item.get_type()!=Obj.TYPE_SERVICE)
                continue;
            kat1= Math.abs(ball.get_radius_x()-service_item.get_radius_x());
            kat2 = Math.abs(ball.get_radius_y()-service_item.get_radius_y());
            hyp=(float)Math.sqrt(kat1*kat1+kat2*kat2);
            if(hyp<=(ball.get_radius()+objects.get(i).get_radius())){
                ((Services)service_item).do_fall(ball.get_speed_x(),ball.get_speed_y());

                //ball.do_hit();
                global_counter++;
                ball.do_combo();
                ball.clear_wallhit();
                reaction(ball,service_item);
                base_speed=base_speed+0.05f;
                if(base_speed>60)base_speed=speed_heigh_limit;
                service_item.set_opacity(0.99f);
                score_scale_y+=50;
                score_scale_x+=30;
                Gdx.input.vibrate(50);
                //Gdx.app.log("SB","item type is "+service_item.get_services_type());


            }
        }
    }

    public void dupllicate(){
        Obj s;
        int count = (int) (Math.random() * 2.5);
        if(count>2) count=2;
        for (int i=0; i<count; i++){
            GO_Controller.create_telegram();
        }
    }

    public void reaction(Obj b1, Obj b2){

        // задаем переменные массы шаров
        float mass1 = b1.get_mass();
        float mass2 = b2.get_mass();
        // задаем переменные скорости
        float xVel1 = b1.get_speed_x();
        float xVel2 = b2.get_speed_x();
        float yVel1 = b1.get_speed_y();
        float yVel2 = b2.get_speed_y();
        float run = (b1.get_radius_x()-b2.get_radius_x());
        float rise = (b1.get_radius_y()-b2.get_radius_y());
        //Угол между осью х и линией действия
        float Alfa = (float)Math.atan2(rise, run);
        float cosAlfa = (float)Math.cos(Alfa);
        float sinAlfa = (float)Math.sin(Alfa);
        // находим скорости вдоль линии действия
        float xVel1prime = xVel1*cosAlfa+yVel1*sinAlfa;
        float xVel2prime = xVel2*cosAlfa+yVel2*sinAlfa;
        // находим скорости перпендикулярные линии действия
        float yVel1prime = yVel1*cosAlfa-xVel1*sinAlfa;
        float yVel2prime = yVel2*cosAlfa-xVel2*sinAlfa;
        // применяем законы сохранения
        float P = (mass1*xVel1prime+mass2*xVel2prime);
        float V = (xVel1prime-xVel2prime);
        float v2f = (P+mass1*V)/(mass1+mass2);
        float v1f = v2f-xVel1prime+xVel2prime;
        xVel1prime = v1f;
        xVel2prime = v2f;

        if(xVel1prime<0) xVel1prime = base_speed*(-1);
        if(xVel1prime>0) xVel1prime = base_speed;
        if(yVel1prime<0) yVel1prime = base_speed*(-1);
        if(yVel1prime>0) yVel1prime = base_speed;

        xVel1 = xVel1prime*cosAlfa-yVel1prime*sinAlfa;
        xVel2 = xVel2prime*cosAlfa-yVel2prime*sinAlfa;
        yVel1 = yVel1prime*cosAlfa+xVel1prime*sinAlfa;
        yVel2 = yVel2prime*cosAlfa+xVel2prime*sinAlfa;

        int ver = (int) (Math.random() * 7.5);
        Gdx.app.log("SB","вероятность бонуса  "+ver );
        if (ver==1){
            // координаты для генерации бонусов
            tmp_x=b2.get_x();
            tmp_y=b2.get_y();
            gen_bonuce=true;
        }

        b1.set_speed_x(xVel1);
        b2.set_speed_x(xVel2);
        b1.set_speed_y(yVel1);
        b2.set_speed_y(yVel2);
    }

    public void hitboard(Obj get_ball){
        Obj board = objects.get(0);//player
        Obj ball = get_ball; //objects.get(1);//ball


        if(ball.get_y()>board.get_y()+board.get_y_scale() || ball.get_y() < -10)
            return;

        boolean hit=false;

        if((board.get_x()>ball.get_x()) && (board.get_x()<ball.get_x()+ball.get_x_scale()) ){
            hit=true;
        }
        if((ball.get_x()>board.get_x()) && (ball.get_x()<board.get_x()+board.get_x_scale()) ){
            hit=true;
        }

        if(!hit)
            return;

        ball.clear_wallhit();
        ball.clear_combo();

        float run = (board.get_radius_x()-ball.get_radius_x());
        float rise = (board.get_radius_y()-ball.get_radius_y());
        //Угол между осью х и линией действия
        float Alfa = (float)Math.atan2(rise, run);
        float cosAlfa = (float)Math.cos(Alfa);
        float sinAlfa = (float)Math.sin(Alfa);

        if(ball.get_type()==1) {
            ball.set_speed_y(sinAlfa * base_speed * (-1));
            ball.set_speed_x(cosAlfa * base_speed * (-1));
        }
        if(ball.get_type()==4) {
            if(life_count>0){
                ball.delete();
            }else{
                if(!ball.get_services_type().equals("onedeath")){
                    ball.delete();
                }
            }
            bonus_apply(ball.get_services_type());
        }
        Gdx.input.vibrate(250);

    }

    public  void bonus_apply(String name){
        Gdx.app.log("SB",""+name );

        if(name.equals("fast") || name.equals("slow")){
            if(name.equals("fast"))base_speed+=2;
            if(name.equals("slow"))base_speed-=2;
            if (base_speed<4)base_speed=10;
            if(base_speed>60)base_speed=speed_heigh_limit;
            for(int i=0; i<objects.size();i++) {
                if(objects.get(i).get_type()==1) {
                    Obj item=objects.get(i);
                    if(item.get_speed_x()==0 || item.get_speed_y()==0)break;
                    float c =(float)Math.sqrt(item.get_speed_x()*item.get_speed_x()+item.get_speed_y()*item.get_speed_y());
                    float a = item.get_speed_x();
                    float b = item.get_speed_y();
                    float sin =b/c;
                    float Alfa = (float)Math.asin(sin);
                    float cosAlfa = (float)Math.cos(Alfa);
                    float sinAlfa = (float)Math.sin(Alfa);
                    if(item.get_speed_x()>0)item.set_speed_x(cosAlfa*base_speed);
                    if(item.get_speed_x()<0)item.set_speed_x(cosAlfa*base_speed*-1);
                    item.set_speed_y(sinAlfa*base_speed);

                }
            }
        }
        if(name.equals("multiball"))gen_ball=3;
        if(name.equals("plussize"))objects.get(0).change_scale_x(70);
        if(name.equals("minussize"))objects.get(0).change_scale_x(-70);
        if(name.equals("onedeath"))life_count--;
        if(name.equals("oneup"))life_count++;


    }



    public void render(SpriteBatch batch) { ///////////////                                         RENDER!
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0,0,0, 1);
        batch.draw(background, 0,0);
        if(black_screen){
            batch.setColor(1, 1, 1, black_opacity);
            batch.draw(black, 0,0);
            batch.draw(death, 1080/2-death.getHeight()/2,1920/2-death.getWidth());
            batch.setColor(1, 1, 1, 1);
        }
        if (objects.size()>0) {
            Player temp;
            temp = (Player) objects.get(player_id);
            temp.process((int)finger_x);
            for (int i = 0; i < objects.size(); i++) {
                objects.get(i).process();
                objects.get(i).draw(batch);
            }
        objects.get(1).draw(batch);
        }

        //Dynamic generate new items
        if(gen_telega>0){
            gen_telega--;
            dupllicate();
        }

        if(gen_bonuce){
            gen_bonuce=false;
            GO_Controller.create_bonuce(tmp_x,tmp_y);
        }
        if(gen_ball!=0){
            GO_Controller.create_ball(gen_ball,base_speed);
            gen_ball=0;
        }

        batch.draw(bar,Gdx.graphics.getWidth()*(-2),1920-130,Gdx.graphics.getWidth()*18,1920);
        batch.draw(life_image,50,1800,100,100);
        batch.draw(higscore_image,1080-150,1800,100,100);
        //counter's;
        draw_score(batch);

        if(pause)batch.draw(pause_screen, 0,0);

        exit();

    }


    private void exit() {
        if (life_count<0){
            if(is_ball==true){
                black_screen=true;
                hold=true;
                black_opacity+=0.02;
                if (black_opacity>1){
                    black_opacity=1;
                    to_exit++;
                    if(to_exit>100){
                        timer.cancel();
                        CoreClass.change_room(new BadEnd());
                    }
                }
            }else {
                timer.cancel();
                CoreClass.change_room(new BadEnd());
            }
        }
        if(objects.size()>0){
            if (objects_count==0) {
                timer.cancel();
                CoreClass.change_room(new GoodEnd());
            }
        }
        if(objects.size()>0){
            if (is_ball==false)if(life_count>0){
                life_count--;
                is_ball=true;
                GO_Controller.create_ball(1,0);
            }
            if (is_ball==false) {
                timer.cancel();
                CoreClass.change_room(new BadEnd());
            }
         }
    }

    private void draw_score(SpriteBatch batch){
        if(objects.size()>0){
            int correction=0;
            if (score_scale_y>120){score_scale_y-=score_scale_y/10;}
            if (score_scale_x>90){score_scale_x-=score_scale_x/10;}
            //Life Count
            if(life_count>0) {
                if(life_count>9)correction=0; else correction=-100;
                if (life_count / 10 % 10 != 0) {
                    batch.draw(numbers.get_texture(life_count / 10 % 10), 150+correction, 1800, 90, 120);
                }
                batch.draw(numbers.get_texture(life_count % 10), 250+correction, 1800, 90, 120);
            }else batch.draw(numbers.get_texture(0), 150+correction, 1800, 90, 120);


            //score
            if(global_counter>9999)batch.draw(numbers.get_texture(global_counter/10000%10),1080-700-((score_scale_x-90)/2),1800-((score_scale_y-120)/2),score_scale_x,score_scale_y);
            if(global_counter>999)batch.draw(numbers.get_texture(global_counter/1000%10),1080-600-((score_scale_x-90)/2),1800-((score_scale_y-120)/2),score_scale_x,score_scale_y);
            if(global_counter>99)batch.draw(numbers.get_texture(global_counter/100%10),1080-500-((score_scale_x-90)/2),1800-((score_scale_y-120)/2),score_scale_x,score_scale_y);
            if(global_counter>9)batch.draw(numbers.get_texture(global_counter/10%10),1080-400-((score_scale_x-90)/2),1800-((score_scale_y-120)/2),score_scale_x,score_scale_y);
            batch.draw(numbers.get_texture(global_counter%10),1080-300-((score_scale_x-90)/2),1800-((score_scale_y-120)/2),score_scale_x,score_scale_y);

        }
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(pause) { pause=false; hold=false;}       //если пауза то отменяем
        if(hold) {return false;}                    //если ожидание то прекращаем
        finger_x=(screenX*CoreClass.camera.viewportWidth)/Gdx.graphics.getWidth();
        finger_x=(finger_x-CoreClass.camera.viewportWidth/2+1080/2);

        finger_y=(screenY*CoreClass.camera.viewportHeight)/Gdx.graphics.getHeight();
        finger_y=1920-(finger_y-CoreClass.camera.viewportHeight/2+1920/2);

        Obj board = objects.get(0);

        for(int i=0; i<objects.size();i++) {
            if(objects.get(i).get_services_type()=="ball") {
                Obj ball = objects.get(i);
                if(ball.get_speed_x()==0){
                    ball.set_x(board.get_radius_x()-ball.get_x_scale()/2);
                    game=true;
                }
            }
        }


        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(pause) { return false;}      //если пауза то выход
        for(int i=0; i<objects.size();i++) {
            if (objects.get(i).get_services_type() == "ball") {
                Obj ball = objects.get(i);
                if (ball.get_speed_x() == 0 && game == true) {
                    ball.set_speed_x(base_speed);
                    ball.set_speed_y(base_speed);
                }
            }
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(hold) { return false;}      //если ожидание то прекращаем
        finger_x=(screenX* CoreClass.camera.viewportWidth)/Gdx.graphics.getWidth();
        finger_x=(finger_x-CoreClass.camera.viewportWidth/2+1080/2);

        finger_y=(screenY*CoreClass.camera.viewportHeight)/Gdx.graphics.getHeight();
        finger_y=1920-(finger_y-CoreClass.camera.viewportHeight/2+1920/2);
        if(objects.size()>0) {
            Obj board = objects.get(0);

            for (int i = 0; i < objects.size(); i++) {
                if (objects.get(i).get_services_type() == "ball") {
                    Obj ball = objects.get(i);
                    if (ball.get_speed_x() == 0) {
                        ball.set_x(board.get_radius_x() - ball.get_x_scale() / 2);
                    }
                }
            }
        }
        return false;
    }


    @Override
    public void destruct(){
        timer.purge();
        String tmp = String.valueOf(objects.size());
        Gdx.app.log("SB",tmp);
        objects.clear();
        tmp = String.valueOf(objects.size());
        Gdx.app.log("SB",tmp);
        System.gc();

    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK){
            // Do your optional back button handling (show pause menu?)
            if (hold){
                if (pause){System.exit(0);}else {return false;}
            }
            if (!pause){
                pause=true;
                hold=true;
            }
        }
        return false;
    }
}

