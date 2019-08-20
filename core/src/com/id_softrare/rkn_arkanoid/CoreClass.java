package com.id_softrare.rkn_arkanoid;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.id_softrare.rkn_arkanoid.rooms.Authors;

import static com.badlogic.gdx.Application.LOG_INFO;

public class CoreClass extends ApplicationAdapter {
	SpriteBatch batch;
	ExtendViewport viewport;
	public static OrthographicCamera camera;


	public static Room room;

	@Override
	public void create () {
		Gdx.app.setLogLevel(LOG_INFO);
		batch = new SpriteBatch();
		room = null;
		camera = new OrthographicCamera();
		viewport = new ExtendViewport(1080,1920,camera);
		viewport.apply();
		camera.position.set(1080/2,1920/2,0);
		change_room(new Authors()); //THIS IS ROOM CHANGER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!HERE!
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		viewport.update(width, height);
	}

	public static void change_room(Room new_room){
		if(room!=null) {
			room.destruct();
			room = null;
		}
        //Texture.clearAllTextures(Gdx.app);
		System.gc();
		room = new_room;
		room.init();
		Gdx.input.setInputProcessor(room);
		Gdx.input.setCatchBackKey(true);
	}

	@Override
	public void render () {
		//viewport.apply();
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		room.render(batch);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
