package gift;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import spacecraft.Skyblade;

import java.util.Random;

public class BonusPower extends Gift {
    private static final String DEFAULT_NAME = " BONUS DU PUISSANCE " ;
    private static final String DEFAULT_IMAGE = "" ;
    private static final int BONUS = 250 ;

    public BonusPower( Skyblade skyblade, float x ,float y, SpriteBatch batch) {
        super(DEFAULT_NAME,DEFAULT_IMAGE,skyblade,x,y,batch);
    }

    public void collect() {
        this.skyblade.setPuissance( this.skyblade.getPuissance() + BONUS);
    }
}
