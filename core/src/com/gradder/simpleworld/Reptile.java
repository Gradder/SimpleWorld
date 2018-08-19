package com.gradder.simpleworld;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

class Reptile {
    static float WIDTH;
    static float HEIGHT;

    float realX;
    float realY;

    float destinationX;
    float destinationY;

    float epsilon = (float)0.0001;
    int offset = 20;

    enum State {
        Standing, Walking, Jumping
    }
    private Texture reptileTexture;

    final Vector2 position = new Vector2();
    final Vector2 velocity = new Vector2();
    State state = State.Walking;

    Reptile(){
        Reptile.WIDTH = 32;
        Reptile.HEIGHT = 32;
        reptileTexture = new Texture("penguin.png");
        position.x = 20;
        position.y = 30;
        realX = 0;
        realY = 0;
    }

void goToCoordinates(float x, float y){
        destinationX = x;
        destinationY = y;
}
    void updateReptile (float deltaTime, float x, float y) {
        //realX = x;
        //realY = y;
    /*Vector3 vector = worldToIso(new Vector3(new Vector2(50, position.y++), 1), 2, 2);
        System.out.println(vector);
    position.x = vector.x;
        position.y = vector.y;*/

       // Vector2 vector2 = convertToScreen(position.x, position.y, offset, offset);
        if(!(Math.abs(realX - destinationX) < epsilon)){
            realX++;
        }

            if(!((Math.abs(realY - destinationY) < epsilon))) {
                realY++;
            }
            Vector2 vector2 = convert2DtoISO(realX, realY);
            position.x = vector2.x;
            position.y = vector2.y;
        }



    void renderReptile (float deltaTime, Batch renderer) {
        // based on the koala state, get the animation frame
       // TextureRegion frame = null;

        // draw the koala, depending on the current velocity
        // on the x-axis, draw the koala facing either right
        // or left

       // batch.begin();

            renderer.draw(reptileTexture, this.position.x, this.position.y, Reptile.WIDTH, Reptile.HEIGHT);
           // renderer.
       // System.out.println("                            "+this.position.x + " " + this.position.y+ " kek");
       // batch.end();
    }
    private Vector3 worldToIso(Vector3 point, int tileWidth, int tileHeight) {
        //gameCamera.unproject(point);
        point.x /= tileWidth;
        point.y = (point.y - tileHeight / 2) / tileHeight + point.x;
        point.x -= point.y - point.x;
        return point;
    }
    public Vector2 convertToScreen(float x, float y, int offsetX, int offsetY)
    {
        Vector2 screen;
        float TILE_WIDTH = 1;
        float TILE_DEPTH = 0.5f;
        //calculate the screen coordinates
        float _x =  offsetX - (y * TILE_WIDTH/2) + (x * TILE_WIDTH/2) - (TILE_WIDTH/2);
        float _y = offsetY + (y * TILE_DEPTH/2) + (x * TILE_DEPTH/2);

        screen = new Vector2(_x, _y);
        System.out.println(screen);
        return screen;
    }

    Vector2 convert2DtoISO(float cartX, float cartY){
        return new Vector2(cartX - cartY,(cartX + cartY) / 2);
    }
    Vector2 convertISOto2D(float isoX, float isoY){

        return new Vector2((2 * isoY + isoX) / 2,(2 * isoY - isoX) / 2);
    }
}
