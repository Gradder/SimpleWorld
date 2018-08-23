package com.gradder.simpleworld;

import com.badlogic.gdx.graphics.Texture;
import com.gradder.simpleworld.Units.AbstractTrooper;

class Reptile extends AbstractTrooper {

    Reptile() {
        width = 64;
        height = 64;
        texture = new Texture("penguin.png");
        position.x = 0;
        position.y = 0;
        position.z = 0;
    }

    void tempWalking() {
        if (this.position.x < destinationX) {
            this.position.x++;
        }

        if (this.position.y < destinationY) {
            this.position.y++;
        }
    }
}
