package net.sekao.superbandicoot;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sekao.superbandicoot.assets.Sounds;

public class Bandicoot extends Image {
    TextureRegion stand, angel;
    Animation walk, jump;
    boolean isDead = false;
    final SuperBandicoot game;
    int cont_wumpas;
    int cont_crates;
    int vidas;
    int checkPoint;
    float chkY;
    float chkX;
    private Rectangle player;
    private Rectangle cristal;

    float time = 0;
    float xVelocity = 0;
    float yVelocity = 0;
    boolean canJump = false;
    boolean isFacingRight = true;
    TiledMapTileLayer layer;
    TiledMapTileLayer layerCheckPoint;
    TiledMapTileLayer layerCrates;
    TiledMapTileLayer layerBox;

    final float GRAVITY = -2.4f;
    final float MAX_VELOCITY = 10f;
    final float DAMPING = 0.87f;

    public Bandicoot(SuperBandicoot gam) {
        this.player = new Rectangle();
        cont_crates = 5;
        cont_wumpas = 0;
        vidas = 2;
        checkPoint = 0;
        
        final float width = 19;
        final float height = 27;
        this.setSize(1, height / width);
        this.game= gam;
        this.player.x = width;
        this.player.y = height;

        // carga los cuadros de koala, divídelos y asignalos a animaciones
        Texture crashTexture = new Texture("crash.png");
        Texture jumpTexture = new Texture("crash_jump.png");
        TextureRegion[][] grid = TextureRegion.split(crashTexture, (int) width, (int) height);
        TextureRegion[][] grid_jump = TextureRegion.split(jumpTexture, (int) width, (int) height);

        stand = grid[0][0];
        angel = grid[0][5];
        
        jump = new Animation(0.07f, grid_jump[0][0]);
        jump.setPlayMode(Animation.PlayMode.NORMAL);
        
        walk = new Animation(0.15f, grid[0][2], grid[0][3], grid[0][4]);
        walk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
               
    }

    @Override
    public void act(float delta) {
        time = time + delta;

        boolean upTouched = Gdx.input.isTouched() && Gdx.input.getY() < Gdx.graphics.getHeight() / 2;
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || upTouched || Gdx.input.isKeyPressed(Input.Keys.W)) {
            if (canJump) {
                Sounds.jump.play();
                yVelocity = yVelocity + MAX_VELOCITY * 4;
            }
            canJump = false;
        }

        boolean leftTouched = Gdx.input.isTouched() && Gdx.input.getX() < Gdx.graphics.getWidth() / 3;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || leftTouched || Gdx.input.isKeyPressed(Input.Keys.A)) {
            xVelocity = -1 * MAX_VELOCITY;
            isFacingRight = false;
        }

        boolean rightTouched = Gdx.input.isTouched() && Gdx.input.getX() > Gdx.graphics.getWidth() * 2 / 3;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || rightTouched || Gdx.input.isKeyPressed(Input.Keys.D)) {
            xVelocity = MAX_VELOCITY;
            isFacingRight = true;
        }

        yVelocity = yVelocity + GRAVITY;

        float x = this.getX();
        float y = this.getY();
        float xChange = xVelocity * delta;
        float yChange = yVelocity * delta;

        if (canMoveTo(x + xChange, y, false) == false) {
            xVelocity = xChange = 0;
        }

        if (canMoveTo(x, y + yChange, yVelocity > 0) == false) {
            canJump = yVelocity < 0;
            yVelocity = yChange = 0;
        }
        
        this.setPosition(x + xChange, y + yChange);

        xVelocity = xVelocity * DAMPING;
        if (Math.abs(xVelocity) < 0.5f) {
            xVelocity = 0;
        }
        
        sumWumpasForLife();
        try {
            estaMuerto();
        } catch (InterruptedException ex) {
            Logger.getLogger(Bandicoot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion frame;
        

        if (yVelocity != 0) {
            frame = jump.getKeyFrame(time);
        } else if (xVelocity != 0) {
            frame = walk.getKeyFrame(time);
        } else {
            frame = stand;
        }
        
        if(this.player.y <= 0){
            this.isDead = true; 
            Sounds.die.play();
            frame = angel;           
        }

        if (isFacingRight) {
            batch.draw(frame, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        } else {
            batch.draw(frame, this.getX() + this.getWidth(), this.getY(), -1 * this.getWidth(), this.getHeight());
        }
    }

    private boolean canMoveTo(float startX, float startY, boolean shouldDestroy) {
        float endX = startX + this.getWidth();
        float endY = startY + this.getHeight();

        int x = (int) startX;
        while (x < endX) {

            int y = (int) startY;
            while (y < endY) {
                if (layer.getCell(x, y) != null) {
                    return false;
                }
                             
                if (layerCheckPoint.getCell(x, y) != null) {
                    if (shouldDestroy) {
                        layerCheckPoint.setCell(x, y, null);
                        Sounds.checkpoint.play();
                        this.checkPoint += 1;
                        chkX = x;
                        chkY = y;
                    }
                    return false;
                }
                
                if (layerCrates.getCell(x, y) != null) {
                    if (shouldDestroy) {
                        layerCrates.setCell(x, y, null);
                        Sounds.crate.play();
                        Sounds.wumpa.play();
                        cont_wumpas += cont_crates;
                    }
                    return false;
                }
                
                if (layerBox.getCell(x, y) != null) {
                    if (shouldDestroy) {
                        layerBox.setCell(x, y, null);
                        Sounds.crate.play();
                        Sounds.wumpa.play();
                        this.cont_wumpas += 1;
                    }
                    return false;
                }
                
                y = y + 1;
            }
            x = x + 1;
        }

        return true;
    }
    
    
    private void sumWumpasForLife(){
        if(cont_wumpas > 99){
            this.vidas += 1;
            Sounds.vida.play();
            this.cont_wumpas = 0;
        }
    }
    
    
    private void changeToFinalScreen(){
        this.game.setScreen(new FinalScreen(game));
    }    

    
    //si llego al final del mapa crear cristal y reproducir su sonido y despeus acabar el juego al cojerlo
    
    private void estaMuerto() throws InterruptedException{   
            
        if(this.getY() <= 0){
            isDead = true;
            this.vidas -= 1;
            if(isDead){
                Sounds.die.play();          
                if(vidas > 0){
                    
                    if(this.checkPoint == 1){
                        this.setPosition(chkX, chkY);
                    }else{
                        this.setPosition(5, 10);
                    }
                }else{
                    this.checkPoint = 0;
                    Sounds.pause();
                    Thread.sleep(600);
                    changeToFinalScreen();
                }                    
            }
        }
    }
    
}
