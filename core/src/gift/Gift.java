package gift;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import spacecraft.Skyblade;

abstract public class Gift {
    protected String name;
    protected Texture picture;
    protected Skyblade skyblade;

    protected SpriteBatch batch;

    private float posX,posY;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Skyblade getSkyblade() {
        return skyblade;
    }
    public void setSkyblade(Skyblade skyblade) {
        this.skyblade = skyblade;
    }

    public Texture getPicture() {
        return picture;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public Gift(String name) {
       setName(name);
    }
    public Gift(String name, String picture,Skyblade skyblade,float x,float y, SpriteBatch batch) {
        setName(name);
        setSkyblade(skyblade);
        setPosX(x);
        setPosY(y);
        this.picture = new Texture(picture);
        this.batch = batch;


    }
    //methodes abstraites
    abstract public void collect();

    public void draw(){
        this.batch.begin();
        this.batch.draw(getPicture(),getPosX(),getPosY());
        this.batch.end();
    }

}
