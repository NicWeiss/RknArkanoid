package com.id_softrare.rkn_arkanoid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import static com.id_softrare.rkn_arkanoid.rooms.Game.hold;

/**
 * Created by nic on 4/20/18.
 */

public class Obj {
    protected float x;
    protected float y;
    protected int x_scale;
    protected int y_scale;
    protected Texture img;
    protected float speed_x;
    protected float speed_y;
    protected float rotation=0;
    protected float rotation_anim=0;
    protected float rotation_speed_anim;
    protected float rotation_speed;
    protected float radius;
    protected float mass = 1;
    protected float opacity = 1;
    protected float gravity = 0;
    protected float counter = 0;
    protected float wallhit = 0;
    protected float combo = 0;
    protected String services_type="UNKNOWN";
    protected Animation anim;
    protected boolean animated = false;
    protected boolean deleted = false;
    protected int current_frame = 0;
    protected int anim_delay = 0;
    protected int anim_counter = 0;
    //private Texture[] animation;

    public int type;


    public static int TYPE_PLAYER = 0;
    public static int TYPE_BALL = 1;
    public static int TYPE_SERVICE = 2;
    public static int TYPE_EFFECT = 3;
    public static int TYPE_BONUCES = 4;



    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        img.dispose();
        //Gdx.app.log("SB","Obj finalize!");
    }

    public void draw(Batch batch){
        //batch.draw(img,x,y,x_scale,y_scale);

        batch.setColor(1, 1, 1, opacity);
        if(img==null)return;
        if(deleted)return;

        if(animated)
            img = anim.get_frame(current_frame);

        batch.draw(img,
                x, y,
                x_scale/2,y_scale/2,
                x_scale,y_scale,
                1,1,
                rotation,
                0,0,
                img.getWidth(),img.getHeight(),
                false,false);
        batch.setColor(1, 1, 1, 1);

        //Debug for radius hit test
        /*batch.end();
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.setProjectionMatrix(CoreClass.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.circle(x+x_scale/2, y+y_scale/2, radius);
        shapeRenderer.end();
        batch.begin();*/
    }

    public void set_texture(Texture tx){
        img = tx;
        animated = false;
    }

    public float get_x(){return x;}
    public float get_y(){return y;}
    public float get_x_scale(){return x_scale;}
    public float get_y_scale(){return y_scale;}
    public float get_radius(){return radius;}

    public void process(){
        if(hold) { return;}      //если пауза то выход
        if(animated){
            anim_counter++;
            if(anim_counter>anim_delay){
                current_frame++;
                if(current_frame>=anim.get_el_count()){current_frame=0;}
                anim_counter=0;
            }
        }
            x = x + speed_x;
            y = y + speed_y;

        rotation+=rotation_speed;
        rotation_anim+=rotation_speed_anim;
    }

    public float get_radius_x() {
        return x+x_scale/2;
    }
    public float get_radius_y() {
        return y+y_scale/2;
    }

    public int get_type() {
        return type;
    }
    public  void set_animation(Animation get){
        animated = true;
        anim = get;
    }
    public void set_type(int type) {this.type=type; }
    public  void set_services_type(String type){services_type=type;}
    public  String get_services_type(){return services_type;}
    public  float get_speed_x() {
        return speed_x;
    }
    public  float get_speed_y() {
        return speed_y;
    }
    public  float get_mass() {
        return mass;
    }
    public  void  set_speed_x(float new_speed) {
        speed_x=new_speed;
    }
    public  void  set_speed_y(float new_speed) {
        speed_y=new_speed;
    }
    public  void  set_opacity(float new_opacity){opacity=new_opacity;}
    public  void  set_x(float new_x){x=new_x; }
    public  void  set_y(float new_y){y=new_y; }
    public  void  set_gravity(float new_gravity){gravity=new_gravity; }
    public  void  clear_wallhit(){wallhit=0; }
    public  void  clear_combo(){combo=0; }
    public  float get_counter(){return counter; }
    public  float get_combo(){return combo; }
    public  float get_wallhit(){return wallhit; }
    public  void  do_hit(){counter++;}
    public  void  do_combo(){combo++;}
    public  void  delete(){deleted=true;}
    public  boolean  statatus(){return deleted;}
    public  void change_scale_x(int scale){
        x_scale+=scale;
        if (x_scale>500)x_scale=500;
        if (x_scale<150)x_scale=150;
    }
}

