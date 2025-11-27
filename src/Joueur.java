import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

public class Joueur {

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    private  double width =80, height =20;
    private Group token;

    public int vitesse = 3;


    public Joueur(){
        this.token =createTopDownLink(1);
    }

    public Group getToken(){
        return token;
    }
    public double getX(){
        return this.token.getLayoutX();
    }
    public double getY(){
        return this.token.getLayoutY();
    }

    public void setX(double x){
        this.token.setLayoutX(x);
    }
    public void setY(double y){
        this.token.setLayoutY(y);
    }

    public void setRotation(int r){
        this.token.setRotate(r);
    }
    private Group createTopDownLink(double scale) {

        Ellipse corp= new Ellipse(0,0, width/2, height/2);
        Circle tete = new Circle(0,0,15);
        Circle main = new Circle(32,-10,6);
        Rectangle sword = new Rectangle(25,-13 ,15,3);
        sword.setFill(Color.WHITE);
        corp.setFill(Color.DARKOLIVEGREEN);


        Group g = new Group(corp, tete, main, sword);

        return g;
    }

    public void deplacer(double dx, double dy, int rot){
        this.setX( this.getX() + dx );
        this.setY( this.getY() + dy );
        this.setRotation( rot );
    }

    public boolean collideLeft(Rectangle mur){
        double xDroiteMur = mur.getX()+mur.getWidth();
        double xGauchePerso;

        if (token.getRotate() == 0 || token.getRotate() == 180)
            xGauchePerso = token.getLayoutX() - this.width/2;
        else
            xGauchePerso = token.getLayoutX() - this.height/2;

        return token.getLayoutY()>mur.getY() && token.getLayoutY() < mur.getY()+mur.getHeight() && xGauchePerso < xDroiteMur && xGauchePerso > mur.getX();

    }
    public boolean collideRight(Rectangle mur){
        double xGaucheMur = mur.getX();
        double xdroitePerso;

        if (token.getRotate() == 0 || token.getRotate() == 180)
            xdroitePerso = token.getLayoutX() + this.width/2;
        else
            xdroitePerso = token.getLayoutX() + this.height/2;

        return token.getLayoutY()>mur.getY() && token.getLayoutY() < mur.getY()+mur.getHeight() && xdroitePerso > xGaucheMur && xdroitePerso < mur.getX()+mur.getWidth();

    }

    public boolean collideTop(Rectangle mur){
        double yBasMur = mur.getY()+mur.getHeight();
        double yHautPerso;

        if (token.getRotate() == 0 || token.getRotate() == 180)
            yHautPerso = token.getLayoutY() - this.height/2;
        else
            yHautPerso = token.getLayoutY() - this.width/2;

        return token.getLayoutX()>mur.getX()                        //Le centre du token est a droite du coté gauche du mur
                && token.getLayoutX() < mur.getX()+mur.getWidth()   //Le centre du token est a gauche du coté droit du mur
                && yHautPerso < yBasMur                             //Le haut du perso touche le bas d'un mur
                && yHautPerso > mur.getY();                         //Le haut du perso est sous la partie supérieure du mur

    }
     public boolean collideBottom(Rectangle mur){
        double yHautMur = mur.getY();
        double yBasPerso;

        if (token.getRotate() == 0 || token.getRotate() == 180)
            yBasPerso = token.getLayoutY() + this.height/2;
        else
            yBasPerso = token.getLayoutY() + this.width/2;

        return token.getLayoutX()>mur.getX()                        //Le centre du token est a droite du coté gauche du mur
                && token.getLayoutX() < mur.getX()+mur.getWidth()   //Le centre du token est a gauche du coté droit du mur
                && yBasPerso > yHautMur                             //Le haut du perso touche le bas d'un mur
                && yBasPerso < mur.getY()+mur.getHeight();                         //Le haut du perso est sous la partie supérieure du mur

    }


}
