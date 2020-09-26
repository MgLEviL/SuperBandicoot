package net.sekao.superbandicoot;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.*;
import com.badlogic.gdx.scenes.scene2d.*;
import net.sekao.superbandicoot.assets.Sounds;

public class MainScreen implements Screen {
    Stage stage;
    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    OrthographicCamera camera;
    Bandicoot player;
    //Crystal cristal;
    Enemy enemy;
    SuperBandicoot game;
    private ShapeRenderer debugRenderer;
    
    
    public MainScreen(SuperBandicoot gam){
        this.game = gam;
        debugRenderer = new ShapeRenderer();
    }

    
    @Override
    public void show() {
        map = new TmxMapLoader().load("level1.tmx");
        final float pixelsPerTile = 16;
        renderer = new OrthogonalTiledMapRenderer(map, 1 / pixelsPerTile);
        camera = new OrthographicCamera();
        Sounds.fondo.play();

        stage = new Stage();
        stage.getViewport().setCamera(camera);

        player = new Bandicoot(game);
        enemy = new Enemy(game);
    
        player.layer = (TiledMapTileLayer) map.getLayers().get("walls");
        player.layerCrates = (TiledMapTileLayer) map.getLayers().get("Crates");
        player.layerCheckPoint = (TiledMapTileLayer) map.getLayers().get("checkpoint");
        player.layerBox = (TiledMapTileLayer) map.getLayers().get("Box");
        player.setPosition(5, 10);
        enemy.setPosition(10, 10);
        stage.addActor(player);
        stage.addActor(enemy);
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);        
    
        camera.position.x = player.getX();
        camera.update();

        renderer.setView(camera);
        renderer.render();
        
        boolean debug = false;
        if (debug) renderDebug();
        
        stage.act(delta);
        stage.draw();
        showInfo();   
    }

    @Override
    public void dispose() {
        Sounds.pause();
    }
    @Override
    public void hide() {
    }
    @Override
    public void pause() {
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, 20 * width / height, 20);
    }
    @Override
    public void resume() {
    }
    
    private void showInfo(){
        game.batch.begin();
        game.font.draw(game.batch, "Vidas: ", 650, 450);
        game.font.draw(game.batch, Integer.toString(player.vidas), 750, 450);
        game.font.draw(game.batch, "Wumpas: ", 620, 430);
        game.font.draw(game.batch, Integer.toString(player.cont_wumpas), 750, 430);
        game.batch.end();
    }
    
    private void renderDebug () {
        debugRenderer.setProjectionMatrix(camera.combined);
        debugRenderer.begin(ShapeType.Line);

        debugRenderer.setColor(Color.RED);
        debugRenderer.rect(this.player.getX(), player.getY(), player.getWidth(), player.getHeight());

        debugRenderer.setColor(Color.YELLOW);
        TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get("walls");
        for (int y = 0; y <= layer.getHeight(); y++) {
                for (int x = 0; x <= layer.getWidth(); x++) {
                        Cell cell = layer.getCell(x, y);
                        if (cell != null) {
                                if (camera.frustum.boundsInFrustum(x + 0.5f, y + 0.5f, 0, 1, 1, 0))
                                        debugRenderer.rect(x, y, 1, 1);
                        }
                }
        }
        debugRenderer.end();
    }
}
