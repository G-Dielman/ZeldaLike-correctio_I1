import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Random;

public class ZeldaLike extends Application {

    private static final int LARGEUR = 800;
    private static final int HAUTEUR = 600;


    private Pane monde;
    private Joueur joueur;

    private Rectangle[] murs;

    @Override
    public void start(Stage primaryStage) {
        monde = new Pane();
        monde.setPrefSize(LARGEUR, HAUTEUR);
        monde.setStyle("-fx-background-color: #8B7355;"); // sol terre battue

        // Création du joueur
        joueur = new Joueur();
        joueur.getToken().setLayoutX(100);
        joueur.getToken().setLayoutY(100);

        // Création de quelques murs
        Rectangle murHaut = new Rectangle(0, 0, LARGEUR, 40);
        Rectangle murBas = new Rectangle(0, HAUTEUR - 40, LARGEUR, 40);
        Rectangle murGauche = new Rectangle(0, 0, 40, HAUTEUR);
        Rectangle murDroite = new Rectangle(LARGEUR - 40, 0, 40, HAUTEUR);
        Rectangle blocCentre = new Rectangle(300, 200, 200, 50);

        murs = new Rectangle[]{murHaut, murBas, murGauche, murDroite, blocCentre};

        for (Rectangle r : murs) {
            r.setFill(Color.DARKOLIVEGREEN);
            r.setStroke(Color.BLACK);

        }

        monde.getChildren().addAll(murHaut, murBas, murGauche, murDroite, blocCentre, joueur.getToken());

        Scene scene = new Scene(monde, LARGEUR, HAUTEUR);

        // Gestion des touches
        scene.setOnKeyPressed(e -> {
            double dx = 0, dy = 0;
            int rotation=0;

            if (e.getCode() == KeyCode.Z || e.getCode() == KeyCode.UP) {
                dy -= joueur.vitesse;
                rotation = 0;
            }
            if (e.getCode() == KeyCode.S || e.getCode() == KeyCode.DOWN) {
                dy += joueur.vitesse;
                rotation = 180;
            }
            if (e.getCode() == KeyCode.Q || e.getCode() == KeyCode.LEFT) {
                dx -= joueur.vitesse;
                rotation = 270;
            }
            if (e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT) {
                dx += joueur.vitesse;
                rotation = 90;
            }

            deplacerJoueur(dx, dy, rotation);

        });

        primaryStage.setTitle("🗡️ Zelda-like - Vue du dessus");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Déplace le joueur et vérifie les collisions avec les murs.
     */
    private void deplacerJoueur(double dx, double dy, int rotation) {

        joueur.deplacer(dx,dy,rotation);

        if (dx == 0 && dy == 0) return;


        // Vérifie collision
        for (var m : murs) {

            double actualWidth;

            if (joueur.getToken().getRotate() == 0 || joueur.getToken().getRotate() == 180)
                actualWidth = joueur.getWidth()/2;
            else
                actualWidth= joueur.getHeight()/2;

            double actualHeight;

            if (joueur.getToken().getRotate() == 0 || joueur.getToken().getRotate() == 180)
                actualHeight = joueur.getHeight()/2;
            else
                actualHeight= joueur.getWidth()/2;

            if( joueur.collideLeft(m) ){
                double newX = m.getX()+m.getWidth() + actualWidth;

                joueur.setX(newX);
                System.out.println("Collision à gauche");
            }

            else if( joueur.collideRight(m) ){
                double newX = m.getX() - actualWidth;

                joueur.setX(newX);
                System.out.println("Collision à droite");
            }

            else if( joueur.collideTop(m) ){
                double newY = m.getY() + m.getHeight() + actualHeight;

                joueur.setY(newY);
                System.out.println("Collision au dessus");
            }

            else if( joueur.collideBottom(m) ){
                double newY = m.getY() - actualHeight;

                joueur.setY(newY);
                System.out.println("Collision en bas");
            }


        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
