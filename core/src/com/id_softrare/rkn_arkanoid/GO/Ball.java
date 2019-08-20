package com.id_softrare.rkn_arkanoid.GO;

import com.id_softrare.rkn_arkanoid.Obj;

/**
 * Created by nic on 4/20/18.
 */

public class Ball extends Obj {

    int mode;

    public Ball() {
        //x = 300;
        y = 220;
        x_scale=100;
        y_scale=100;
        radius=50;
        speed_x=0;
        speed_y=0;
        rotation_speed=0;
        type=TYPE_BALL;
    }

    public void process() {
        super.process();
        if(y<-150) {
            delete();
        }
        if(x<0){x=1; speed_x=speed_x*(-1); wallhit++;}
        if(x+x_scale>1080){x=1079-x_scale; speed_x=speed_x*(-1); wallhit++;}
        if(y+y_scale>1790){y=1789-y_scale; speed_y=speed_y*(-1);}

        if (wallhit>=6){

            speed_y-=0.06;
        }

        if(speed_y>0) rotation_speed=3;
        if(speed_y<0) rotation_speed = -3;
    }



}
