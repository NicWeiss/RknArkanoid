package com.id_softrare.rkn_arkanoid.GO;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.id_softrare.rkn_arkanoid.Obj;

/**
 * Created by nic on 5/18/18.
 */

public class Bonuces extends Obj {
    private int sleep=0, id=0;

    public  Bonuces(){
        x= (int) (Math.random() * 950);
        y= 1620-(int) (Math.random() * 700);
        x_scale=150;
        y_scale=150;
        radius=75;
        speed_x=0;
        speed_y=-1;
        opacity=0;
        type = TYPE_BONUCES;
        rotation_speed_anim=9;
        anim_delay = 1;
    }

    @Override
    public void draw(Batch batch) {
        //super.draw(batch);

        if(y<-150) {
            delete();
        }
        if(!deleted) {
            batch.setColor(1, 1, 1, 1);
            if (img == null) return;
            batch.draw(img,                         //основной item
                    x, y,
                    x_scale / 2, y_scale / 2,
                    x_scale, y_scale,
                    (float) 0.5, 0.5f,
                    rotation,
                    0, 0,
                    img.getWidth(), img.getHeight(),
                    false, false);
            if (animated) {                          //Анимация
                opacity = (float) 0.5;
                batch.setColor(1, 1, 1, opacity);
                batch.draw(anim.get_frame(current_frame),
                        x, y,
                        x_scale / 2, y_scale / 2,
                        x_scale, y_scale,
                        1, 1,
                        rotation_anim,
                        0, 0,
                        anim.get_frame(current_frame).getWidth(), anim.get_frame(current_frame).getHeight(),
                        false, false);
            }
        }

    }

    public void process() {
        super.process();
        speed_y-=0.1;
        if(opacity<1)opacity+=0.1;

    }



}
