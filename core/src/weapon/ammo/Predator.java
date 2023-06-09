package weapon.ammo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import helpers.AllAssets;
import spacecraft.Spacecraft;

public class Predator extends Ammo{
    // Static
    public final static String DEFAULT_NAME = "Predator";
    public final static float DEFAULT_DEGATS = 55;
    private final static int DEFAULT_SPEED = 5;
    public Music soundShoot = Gdx.audio.newMusic(Gdx.files.internal("song/rocketflyby-32158.mp3"));


    // Constructor
    public Predator(float xPosition, float yPosition, SpriteBatch batch, AllAssets assets){
        super(DEFAULT_NAME,DEFAULT_DEGATS,DEFAULT_SPEED, xPosition, yPosition, batch, assets, assets.getPredatorPicture());
    }

    // Methodes
    public void move(Spacecraft target) {
         this.soundShoot.play();
        if(target.getPosX() + (float) target.getPicture().getWidth() /2 < getxPosition())
            setxPosition(getxPosition() - getSpeed());
        else
            setxPosition(getxPosition() + getSpeed());

        if (target.getPosY() + (float) target.getPicture().getHeight() /2> getyPosition())
            setyPosition(getyPosition() + getSpeed());
        else
            setyPosition(getyPosition() - getSpeed());

        // aim sur le target
        Texture cible = getAssets().getAim();

        getBatch().begin();
        getBatch().draw(cible, target.getPosX() + (float) (target.getPicture().getWidth() /2) - (float) cible.getWidth() /2, target.getPosY() - (float) cible.getHeight() /2);
        getBatch().draw(getImage(), getxPosition(), getyPosition());
        getBatch().end();
    }
}



