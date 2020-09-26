/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sekao.superbandicoot.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 *
 * @author mglevil
 */
public class Sounds {  
    // Musica
    public static Music fondo = Gdx.audio.newMusic(Gdx.files.internal("sounds/medieval_music.mp3"));
    // Efectos de sonido
    public static Sound jump = Gdx.audio.newSound(Gdx.files.internal("sounds/crash_jump.wav"));
    // MÃºsica
    public static Music gameover = Gdx.audio.newMusic(Gdx.files.internal("sounds/gameover.mp3"));
    // Efectos de sonido
    public static Sound wumpa = Gdx.audio.newSound(Gdx.files.internal("sounds/wumpa_eat.mp3"));
    // MÃºsica
    public static Sound nitro = Gdx.audio.newSound(Gdx.files.internal("sounds/nitro_explosion.wav")); 
    // Efectos de sonido
    public static Sound vida = Gdx.audio.newSound(Gdx.files.internal("sounds/life_up.mp3"));

    public static Sound die = Gdx.audio.newSound(Gdx.files.internal("sounds/crash_uau.wav"));
    
    public static Sound crate = Gdx.audio.newSound(Gdx.files.internal("sounds/crate_break.wav"));
    
    public static Sound checkpoint = Gdx.audio.newSound(Gdx.files.internal("sounds/checkpoint.wav"));


    public static void pause() {
        fondo.stop();
        nitro.stop();
        wumpa.stop();
        vida.stop();
        jump.stop();
        gameover.stop();
        crate.stop();
        die.stop();
        checkpoint.stop();
    }

    public static void resume() {
        fondo = Gdx.audio.newMusic(Gdx.files.internal("sounds/medieval_music.mp3"));
        
        jump = Gdx.audio.newSound(Gdx.files.internal("sounds/crash_jump.wav"));

        gameover = Gdx.audio.newMusic(Gdx.files.internal("sounds/gameover.mp3"));

        wumpa = Gdx.audio.newSound(Gdx.files.internal("sounds/wumpa_eat.mp3"));

        nitro = Gdx.audio.newSound(Gdx.files.internal("sounds/nitro_explosion.wav")); 

        vida = Gdx.audio.newSound(Gdx.files.internal("sounds/life_up.mp3"));
        
        die = Gdx.audio.newSound(Gdx.files.internal("sounds/crash_uau.wav"));
        
        crate = Gdx.audio.newSound(Gdx.files.internal("sounds/crate_break.wav"));
        
        checkpoint = Gdx.audio.newSound(Gdx.files.internal("sounds/checkpoint.wav"));
    }  
}
