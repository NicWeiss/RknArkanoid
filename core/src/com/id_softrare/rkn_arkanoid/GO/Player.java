package com.id_softrare.rkn_arkanoid.GO;

import com.id_softrare.rkn_arkanoid.Obj;

/**
 * Created by nic on 4/20/18.
 */

public class Player extends Obj {

    public  Player(){
        x= 500;
        y= 150;
        x_scale=300;
        y_scale=60;
        type=TYPE_PLAYER;
    }

    public void process(int touch_x) {
        x=touch_x-get_x_scale()/2;
        if(x<0)x=0;
        if(x+x_scale>1080)x=1080-x_scale;
    }
}
