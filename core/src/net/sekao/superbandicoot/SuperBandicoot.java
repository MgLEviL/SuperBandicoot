package net.sekao.superbandicoot;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SuperBandicoot extends Game {
    public SpriteBatch batch;
    BitmapFont font;
    public static int WIDTH = 480, HEIGHT = 800;
    
    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.setScreen(new MainMenuScreen(this));
    }
    
    @Override
    public void render() {
        super.render();
    }
    @Override
    public void dispose() {
        this.batch.dispose();
        this.font.dispose();
    }
    
}
