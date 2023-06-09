package bonus;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import helpers.AllAssets;
import spacecraft.Skyblade;

public class BonusPower extends Bonus {
    private static final String DEFAULT_NAME = " BONUS DU PUISSANCE " ;
    private static final float BONUS = 250f ;

    public BonusPower(Skyblade skyblade, float x , float y, SpriteBatch batch, AllAssets assets) {
        super(DEFAULT_NAME,skyblade,x,y,batch, assets);
        setBonus(BONUS);
        setPicture(assets.getBonusPowerPicture());
    }

    public void collect() {
        getAssets().getBonuCollect().play();
        this.skyblade.setPuissance((this.skyblade.getPuissance() +  BONUS > this.skyblade.getMaxPuissance()) ? this.skyblade.getMaxPuissance() : this.skyblade.getPuissance() + BONUS);
    }
}
