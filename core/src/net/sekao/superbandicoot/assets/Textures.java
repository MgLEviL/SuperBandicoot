/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sekao.superbandicoot.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 *
 * @author mglevil
 */
public class Textures {   
        
    public static Texture GAME_OVER = new Texture(Gdx.files.internal("gameover.png"));
    public static Texture FONDO_MENU = new Texture(Gdx.files.internal("fondo_menu.png"));


    public static void pause() {
        GAME_OVER.dispose();
        FONDO_MENU.dispose();

    }

    public static void resume() {

        GAME_OVER = new Texture(Gdx.files.internal("gameover.png"));
        FONDO_MENU = new Texture(Gdx.files.internal("fondo_menu.png"));

    }


}
