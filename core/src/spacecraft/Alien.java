package spacecraft;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import helpers.Collision;
import weapon.ammo.Ammo;

import java.util.Random;

public class Alien extends Spacecraft {

    private int xSpeed = 3;
    private int ySpeed = 2;
    private long xAlea;
    private long yAlea;
    protected int points;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public int getySpeed() {
        return ySpeed;
    }

    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    public long getxAlea() {
        return xAlea;
    }

    public void setxAlea(long xAlea) {
        this.xAlea = xAlea;
    }

    public long getyAlea() {
        return yAlea;
    }

    public void setyAlea(long yAlea) {
        this.yAlea = yAlea;
    }

    public Alien(String name, int maxPuissance, SpriteBatch batch, Texture pictureAlien){
        super(name, batch);
       setMaxPuissance(maxPuissance);
       setPuissance(maxPuissance);
       setPicture(pictureAlien);

        int xPos = 0;

        xPos = (new Random().nextInt(2) == 0 )? xPos : Gdx.graphics.getWidth() - getPicture().getWidth();
        int yPos = new Random().nextInt(Gdx.graphics.getHeight() - getPicture().getHeight()) + Gdx.graphics.getHeight() /2;

        //Application des positions initiales des monstres
        setPosX(xPos);
        setPosY(yPos);

        //pour avoir un deplacement aleatoire
        xAlea = new Random().nextInt(5)  ;
        yAlea = new Random().nextInt(4)  ;
    }

    @Override
    public void move(Spacecraft spacecraft) {

        //dessin et positionnement des monstres
        getBatch().begin();
        getBatch().draw(getPicture(), getPosX(), getPosY());
        getBatch().end();

        //changement des positions
        setPosX(getPosX() + xSpeed);
        setPosY(getPosY() + ySpeed);

        //pour la descente
        xAlea -= 1;
        yAlea--;


        if(getPosX()<=0 || getPosX() >= Gdx.graphics.getWidth() - getPicture().getWidth())
            xSpeed = - xSpeed;

        if(getPosY()<= (float) Gdx.graphics.getHeight() /2 || getPosY() >= Gdx.graphics.getHeight() - getPicture().getHeight())
            ySpeed = - ySpeed;

        if(xAlea == 0) {
            xAlea =  new Random().nextInt(2) ;
            xSpeed = - xSpeed;
        }

        if(yAlea == 0){
            yAlea =  new Random().nextInt(2) ;
            ySpeed = - ySpeed;
        }

        if(getPosY() < 0)
            setPosY(0);
        if(getPosX() < 0)
            setPosX(0);

        if(getPosY() > Gdx.graphics.getHeight() - getPicture().getHeight())
            setPosY(Gdx.graphics.getHeight() - getPicture().getHeight());

        if(getPosX() > Gdx.graphics.getWidth() - getPicture().getWidth())
            setPosX(Gdx.graphics.getWidth() - getPicture().getWidth());


        if(!(this instanceof BossChaosbaneDestructor)){//les petits aliens
            if(new Collision().checkCollision(getPosX(),getPosY(),getPicture().getWidth(),getPicture().getHeight(),
                                            spacecraft.getPosX(),spacecraft.posY,spacecraft.getPicture().getWidth(),spacecraft.getPicture().getHeight())){
                ySpeed = - ySpeed;
                xSpeed = - xSpeed;
            }

        }

    }

    public void shotBy(Ammo ammo) {// quand il a ete tire
        setPuissance(getPuissance() - ammo.getDegats());
    }


}

