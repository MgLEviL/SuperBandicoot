package net.sekao.superbandicoot;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class Enemy extends Image {
    TextureRegion stand;
    Animation walk, jump;
    boolean isDead = false;
    final SuperBandicoot game;
    int vida;
    private final Rectangle enemy;

    float time = 0;
    float xVelocity = 0;
    float yVelocity = 0;
    boolean canJump = false;
    boolean isFacingRight = true;
    TiledMapTileLayer layer;

    final float GRAVITY = -2.4f;
    final float MAX_VELOCITY = 10f;
    final float DAMPING = 0.87f;

    public Enemy(SuperBandicoot gam) {
        this.enemy = new Rectangle();
        vida = 400;
        
        final float width = 19;
        final float height = 27;
        this.setSize(1, height / width);
        this.game= gam;
        this.enemy.x = width;
        this.enemy.y = height;

        // carga los cuadros de koala, divídelos y asignalos a animaciones
        Texture crashTexture = new Texture("crash.png");
        Texture jumpTexture = new Texture("crash_jump.png");
        TextureRegion[][] grid = TextureRegion.split(crashTexture, (int) width, (int) height);
        TextureRegion[][] grid_jump = TextureRegion.split(jumpTexture, (int) width, (int) height);

        stand = grid[0][0];
        
        jump = new Animation(0.07f, grid_jump[0][0]);
        jump.setPlayMode(Animation.PlayMode.NORMAL);
        
        walk = new Animation(0.15f, grid[0][2], grid[0][3], grid[0][4]);
        walk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
       
    }

    @Override
    public void act(float delta) {
        time = time + delta;

        yVelocity = yVelocity + GRAVITY;

        float x = this.getX();
        float y = this.getY();
        float xChange = xVelocity * delta;
        float yChange = yVelocity * delta;

        if (canMoveTo(x + xChange, y) == false) {
            xVelocity = xChange = 0;
        }

        if (canMoveTo(x, y + yChange) == false) {
            canJump = yVelocity < 0;
            yVelocity = yChange = 0;
        }
        
        this.setPosition(x + xChange, y + yChange);

        xVelocity = xVelocity * DAMPING;
        if (Math.abs(xVelocity) < 0.5f) {
            xVelocity = 0;
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

        if (isFacingRight) {
            batch.draw(frame, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        } else {
            batch.draw(frame, this.getX() + this.getWidth(), this.getY(), -1 * this.getWidth(), this.getHeight());
        }
    }

    private boolean canMoveTo(float startX, float startY) {
        float endX = startX + this.getWidth();
        float endY = startY + this.getHeight();

        int x = (int) startX;
        while (x < endX) {

            int y = (int) startY;

            x = x + 1;
        }

        return true;
    }


}
