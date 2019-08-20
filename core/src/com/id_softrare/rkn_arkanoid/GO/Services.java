package com.id_softrare.rkn_arkanoid.GO;

import com.id_softrare.rkn_arkanoid.Obj;

import static com.id_softrare.rkn_arkanoid.rooms.Game.hold;

/**
 * Created by nic on 4/20/18.
 */

public class Services extends Obj {

    public  Services(){
        x= (int) (Math.random() * 950);
        y= 1620-(int) (Math.random() * 700);
        x_scale=150;
        y_scale=150;
        radius=75;
        speed_x=0;
        type = TYPE_SERVICE;
    }

    public void process() {
        if(hold) { return;}      //если пауза то выход
        super.process();
        if(type==TYPE_SERVICE)
            return;

        if(speed_x>0)
            speed_x-=0.3;
        if(speed_x<0)
            speed_x+=0.3;

        speed_y-=1;


        if(y<-150) {
            delete();
        }

        if(x<0){x=1; speed_x=speed_x*(-1);}
        if(x+x_scale>1080){x=1079-x_scale; speed_x=speed_x*(-1);}
        if(y+y_scale>1820){y=1819-y_scale; speed_y=speed_y*(-1);}

        if (opacity<1){
            if (opacity>0.3f){opacity=opacity-0.03f;}
            if (opacity<0.3f){opacity=0.3f;}


        }
    }

    public void do_fall(float x_speed, float y_speed){
        type = TYPE_EFFECT;
        //speed_y = y_speed;
        //speed_x = x_speed;
    }
}
