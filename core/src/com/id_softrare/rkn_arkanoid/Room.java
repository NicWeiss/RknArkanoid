package com.id_softrare.rkn_arkanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by nic on 3/11/18.////////
 */

public abstract class Room implements InputProcessor {
    public void render(SpriteBatch batch){

    }
    public void init(){

    }
    public  void destruct(){

    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        Gdx.app.log("SB","Room finalize");
    }

}


