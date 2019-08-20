package com.id_softrare.rkn_arkanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * Created by nic on 5/19/18.
 */

public class Animation {
    private int el_count;
    private Texture[] images;
    private String type;

    public Animation(){
        Gdx.app.log("SB","Animation Start");
    }

    public  Texture get_frame( int id){
        if(id>el_count-1)id=el_count;
        if (id<0)id=0;
        return images[id];
    }

    public int get_el_count(){return el_count;}
    public String get_type(){return type;}

    public void load_package(String name) {
        type=name;
        String output, lines[], filename = "animations/" + name + "/info.idx";
        Gdx.app.log("SB", "Opening animation package '" + filename + "'");
        FileHandle file = Gdx.files.internal(filename);
        try {
            output = file.readString();
        } catch (GdxRuntimeException e) {
            Gdx.app.log("SB", "Error: " + e.getMessage());
            return;
        }

        lines = output.split("\n");

        String temp[], line;
        for (int i = 0; i < lines.length; i++) {
            line = lines[i].replace(" ", "");
            if (line.startsWith("count:")) {
                temp = line.split(":");
                el_count = Integer.parseInt(temp[1]);

                Gdx.app.log("SB", "Element count:'" + el_count + "'");
                continue;
            }
            if (line.startsWith("an:")) {
                temp = line.split(":");
                String temp_type = temp[2];

                Gdx.app.log("SB", "Elements:'" + temp[1] + "'");

                temp = temp[1].split(",");
                for (int k = 0; k < temp.length; k++) {
                    int index = Integer.parseInt(temp[k]);
                    if (index < 0 || index > el_count) {
                        Gdx.app.log("SB", "Значение элемента вне диапазона :'" + index + "'");
                        continue;
                    }
                    Gdx.app.log("SB", "Type is '" + temp_type + "'");
                }
                continue;
            }
            Gdx.app.log("SB", "Unrecognized!!! '" + line + "'");
        }

        images = new Texture[el_count];
        for (int i = 0; i < el_count; i++) {
            filename = "animations/" + name + "/" + i + ".png";
            try {
                images[i] = new Texture(filename);
            } catch (GdxRuntimeException e) {
                Gdx.app.log("SB", "Error loading file '" + filename + "' " + e.getMessage());
            }
        }
    }


}
