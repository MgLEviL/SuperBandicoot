package net.sekao.superbandicoot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import net.sekao.superbandicoot.assets.Sounds;
import net.sekao.superbandicoot.assets.Textures;


public class FinalScreen implements Screen {
    final SuperBandicoot game;
    OrthographicCamera camera;

    public FinalScreen(SuperBandicoot gam) {
        this.game = gam;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 800.0F, 480.0F);
        
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.0F, 0.0F, 0.2F, 1.0F);
        Gdx.gl.glClear(16384);
        
        Sounds.gameover.play();
        this.camera.update();       
        this.game.batch.setProjectionMatrix(this.camera.combined);
        this.game.batch.begin();
        this.game.batch.draw(Textures.GAME_OVER, 0, 0);
        this.game.batch.end();
        
        if (Gdx.input.isTouched()) {
            this.game.setScreen(new MainMenuScreen(this.game));
            Sounds.gameover.pause();
            //this.dispose();
        }

    }

    public void resize(int width, int height) {
    }

    public void show() {
    }

    public void hide() {
    }

    public void pause() {
    }

    public void resume() {
    }

    public void dispose() {
        
        //Textures.GAME_OVER.dispose();
    }
}
