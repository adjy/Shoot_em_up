package weapon.SkyBladeWeapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import exceptions.NoWeaponExeption;
import helpers.Collision;
import helpers.AllAssets;
import spacecraft.Alien;
import spacecraft.Spacecraft;
import weapon.Weapon;
import weapon.ammo.Predator;

import java.util.HashSet;
import java.util.Iterator;

public class PredatorFury extends Weapon {
    // Static
    private static final String DEFAULT_NAME = "Predator Fury";
    protected HashSet<Predator> predators; // Hashset de munitions
    private boolean fire;
    private final int maxAmmos = 5;
    private int nbAmmo;
    private  float times, duration;
    private  float times2, duration2;

    public int getMaxAmmos() {
        return maxAmmos;
    }

    public int getNbAmmo() {
        return nbAmmo;
    }

    public void setNbAmmo(int nbAmmo) {
        this.nbAmmo = nbAmmo;
    }

    public boolean isFire() {
        return fire;
    }

    public void setFire(boolean fire) {
        this.fire = fire;
    }

    // Methode
    public PredatorFury(SpriteBatch batch, Spacecraft spacecraft, AllAssets assets) {
        super(batch, spacecraft, assets);
        setName(DEFAULT_NAME);
        this.predators = new HashSet<>();
        this.fire = true;
        setNbAmmo(maxAmmos);
        times = 0f;
        duration = 10f;

        times2 = 0f;
        duration2 = 1f;

    }

    // Methodes
    public void createAmmo(){//creer un seul munition

        float xPosition = getSpacecraft().getPosX() + ((float) getSpacecraft().getPicture().getWidth() /2 ) ;
        float yPosition = getSpacecraft().getPosY() + getSpacecraft().getPicture().getHeight();
        Predator predator = new Predator(xPosition,yPosition, getBatch(), getAssets());
        munitions.add(predator);

        // Explosions a la creation du munition
        Texture boom = getAssets().getExplosion5();
        getBatch().begin();
        getBatch().draw(boom,predator.getxPosition()-predator.getImage().getWidth()-8,predator.getyPosition()-predator.getImage().getHeight());
        getBatch().end();

        predators.add(predator);
        lastAmmoTime = TimeUtils.nanoTime(); // dernier ammo creer

    }
    public void spawnAllAmmo(Alien target) throws NoWeaponExeption {
        if(target == null){
            spawnAllAmmo();
        }
        else {
            /* Methode qui permet de creer les munitions, de supprimer les munitions hors de l'ecran et d'afficher les munitions */
            create(); // Create les ammos
            Iterator<Predator> iterator = predators.iterator();

            while (iterator.hasNext()) {
                Predator ammo = iterator.next();
                ammo.move(target); // gerer les deplacement des ammos

                if ((ammo.getyPosition() > Gdx.graphics.getHeight() + 2) || (ammo.getyPosition() < 0)
                        || (ammo.getxPosition() < 0) || (ammo.getxPosition() > Gdx.graphics.getWidth())) // Supprimer les ammos
                    iterator.remove();
            }
        }
    }

    public void shoot(Spacecraft opponent) {
        /* Methode qui permet de verifier s'il y a des collisions entre l'ennemi et une munition */
        Iterator<Predator> iterator = predators.iterator();

        while (iterator.hasNext()) {
            Predator ammo = iterator.next();
            if (new Collision().checkCollision(ammo.getxPosition(), ammo.getyPosition(), ammo.getImage().getWidth(), ammo.getImage().getHeight(), opponent.getPosX(),
                    opponent.getPosY(), opponent.getPicture().getWidth(), opponent.getPicture().getHeight())) {//si les tirs ont touche les ennemis !!
                Texture boom = getAssets().getBoom6();
                getBatch().begin();
                getBatch().draw(boom,ammo.getxPosition() - (float) boom.getWidth() /2,ammo.getyPosition()- (float) boom.getHeight() /2);
                getBatch().end();
                ammo.soundShoot.dispose();
                Music sound = Gdx.audio.newMusic(Gdx.files.internal("song/boom_c_06-102838.mp3"));
                sound.play();

                Music soundShoot;
                soundShoot = Gdx.audio.newMusic(Gdx.files.internal("song/mixkit-multiple-fireworks-explosions-1689.wav")); // song explosion quand un alien est mort
                soundShoot.play();

                sound.dispose();
                opponent.shotBy(ammo);//il a ete tire !!
                iterator.remove();

            }
        }
    }

    public boolean isFull(){
        return getNbAmmo() >= getMaxAmmos();
    }
    public boolean isEmpty(){
        return getNbAmmo() <= 0 ;
    }

    @Override
    public void create() {
        times += Gdx.graphics.getDeltaTime();
        times2 += Gdx.graphics.getDeltaTime();

        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT) && !isEmpty() && times2 >= duration2 ){ // permet de tirer la tete chercheuse avec le click droit
           createAmmo();
           setNbAmmo(getNbAmmo() - 1);
           times2 = 0;
        }

        if (times >= duration && !isFull()) { // Generer les tetes chercheuses
            setNbAmmo(getNbAmmo() + 1);
            times = 0;
        }
    }
}
