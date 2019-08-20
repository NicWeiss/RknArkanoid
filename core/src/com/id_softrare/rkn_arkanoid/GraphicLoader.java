package com.id_softrare.rkn_arkanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * Created by nic on 4/14/18.
 */

public class GraphicLoader {
    private int el_count;
    private Texture[] images;
    private String[] types;


    public GraphicLoader(){
        el_count = 0;
        Gdx.app.log("SB","INIT");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        for(int i=0; i>images.length; i++){
            images[i].dispose();
        }
    }


    public Texture get_texture(int id){return images[id];}
    public String get_type(int id){return types[id];}
    public int get_el_count(){return el_count;}




    public void load_grapfSet(String name){
        String output,  lines[], filename = name+"/info.idx";
        Gdx.app.log("SB","Opening grafpack '"+filename+"'");
        FileHandle file = Gdx.files.internal(filename);
        try {
            output = file.readString();
        }catch (GdxRuntimeException e){
            Gdx.app.log("SB","Error: "+e.getMessage());
            return;
        }

        lines=output.split("\n");

        String temp[],line;
        for(int i=0;i<lines.length;i++){
            line = lines[i].replace(" ","");
            if(line.startsWith("count:")){
                temp = line.split(":");
                el_count = Integer.parseInt(temp[1]);
                types = new String[el_count];
                for (int k=0; k<el_count; k++ ){
                    types[k]="UNKNOWN";
                }

                Gdx.app.log("SB","Element count:'"+el_count+"'");
                continue;
            }
            if(line.startsWith("el:")){
                temp = line.split(":");
                String temp_type=temp[2];

                Gdx.app.log("SB","Elements:'"+temp[1]+"'");

                temp = temp[1].split(",");
                for(int k=0; k<temp.length;k++){
                    int index = Integer.parseInt(temp[k]);
                    if(index<0 || index>el_count){
                        Gdx.app.log("SB","Значение элемента вне диапазона :'"+index+"'");
                        continue;
                    }
                    types[index]=temp_type;
                    Gdx.app.log("SB","Type is '"+temp_type+"'");
                }
                continue;
            }
            Gdx.app.log("SB","Unrecognized!!! '"+line+"'");
        }

        images = new Texture[el_count];
        for (int i=0; i<el_count;i++){
            filename =  name + "/" + i + ".png";
            try {
                images[i] = new Texture(filename);
            }catch (GdxRuntimeException e){
                Gdx.app.log("SB","Error loading file '"+filename+"' "+e.getMessage());
            }
        }

    }

    public int get_id_by_type(String type) {
        for (int i=0; i<el_count; i++){
            //Gdx.app.log("SB","["+types[i]+"] : ["+type+"]");
            if(types[i].equals(type)){return i;}
        }
        return -1;
    }

    public void free(){

    }
}
