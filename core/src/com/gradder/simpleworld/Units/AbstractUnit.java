package com.gradder.simpleworld.Units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractUnit {
    protected int speed;
    protected float width;
    protected float height;

    protected float destinationX;
    protected float destinationY;

    enum State {
        STAND, WALK, FIRE
    }
    protected Texture texture;

    protected final Vector3 position;

    protected State state;

    public AbstractUnit(){
        speed = 5;
        width = 32;
        height = 32;
        destinationX = 0;
        destinationY = 0;
        state = State.STAND;
        position = new Vector3();
    }

    public void setDestinationCoordinates(float x, float y){
        this.destinationX = x;
        this.destinationY = y;
    }

    public void setPosition(float x, float y){
        position.x = x;
        position.y = y;
    }

    public void render(SpriteBatch batch){
        batch.begin();
        batch.draw(texture, position.x, position.y, width, height);
        batch.end();
    }

    public float getPositionX(){
        return position.x;
    }
    public float getPositionY(){
        return position.y;
    }

}
