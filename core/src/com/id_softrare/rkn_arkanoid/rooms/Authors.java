package com.id_softrare.rkn_arkanoid.rooms;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.id_softrare.rkn_arkanoid.CoreClass;
import com.id_softrare.rkn_arkanoid.Room;

/**
 * Created by nic on 4/21/18.
 */

public class Authors extends Room {
    Texture background;
    float opacity;
    int counter;
    ShapeRenderer shapeRenderer;

    public void init(){
        background = new Texture("bg/Logo.png");
        opacity=1;
        counter = 300;
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
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
        next_room();
        return false;
    }

    private void next_room(){
        CoreClass.change_room(new Opening());
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public void render(SpriteBatch batch) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor((float)0x05/0xff , (float)0x14/0xff, (float)0x38/0xff, 1);


        batch.draw(background, 0,0);
        counter--;
        if(counter<0)
            opacity-=0.01;
        batch.setColor(1,1,1, opacity);

        if (opacity<0) {
            batch.setColor(1, 1, 1, 1);
            next_room();
        }
    }

}
