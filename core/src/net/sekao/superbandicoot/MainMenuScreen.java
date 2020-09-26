package net.sekao.superbandicoot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import net.sekao.superbandicoot.assets.Textures;


public class MainMenuScreen implements Screen {
    final SuperBandicoot game;
    OrthographicCamera camera;    

    public MainMenuScreen(SuperBandicoot gam) {
        this.game = gam;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 800, 480);        
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2F, 1);
        Gdx.gl.glClear(16384);
        camera.update();
        game.batch.setProjectionMatrix(this.camera.combined);
        game.batch.begin();
        game.batch.draw(Textures.FONDO_MENU, 0, 0);
        game.font.draw(game.batch, "Toque cualquier punto para comenzar!", 300, 100);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new MainScreen(game));
            dispose();
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
        Textures.FONDO_MENU.dispose();
    }
}
