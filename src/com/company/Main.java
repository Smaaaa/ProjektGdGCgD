package com.company;


import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Random;

public class Main extends Application {
    // Ganzes Bild im Fenster, unten rechts nicht abgeschnitten
    public static final int WIDTH_OFFSET = 14;
    public static final int HEIGHT_OFFSET = 37;

    public static void main(String[] args) {
        Application.launch(args);
    }

    Canvas canvas;
    Group root;
    Scene scene;

    File file;
    Image image;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Ebenen erstellen, Canvas ist Leinwand auf der gezeichnet wird
        canvas = new Canvas();
        root = new Group(canvas);
        scene = new Scene(root);

        // ESCAPE   -> Close program
        // O        -> Open file
        // S        -> Scaling image
        // D        -> Drawing image
        // L        -> Lettering
        // K        -> Kreis
        //B         -> Balken
        //J         -> Säulen
        //5         -> Kreis und Viereck

        // wird ausgeführt wenn Taste geklickt wird
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                System.out.println("Escaping program ...");
                primaryStage.hide();
            } else if (event.getCode() == KeyCode.O) {
                System.out.println("Opening file ...");
                // öffnet Dialog zum Datei auswählen
                FileChooser chooser = new FileChooser();
                file = chooser.showOpenDialog(scene.getWindow());
                if (file != null) {
                    image = new Image(file.toURI().toString());
                    System.out.printf("  original size: %.2f/%.2f%n", image.getWidth(), image.getHeight());
                } else {
                    System.out.println("  cancelled");
                }
                //skalieren
            } else if (event.getCode() == KeyCode.S) {
                System.out.println("Scaling image ...");
                double width = image.getWidth();
                double height = image.getHeight();
                double maxWidth = scene.getWindow().getWidth() - WIDTH_OFFSET;
                double maxHeight = scene.getWindow().getHeight() - HEIGHT_OFFSET;
                System.out.printf("  width : %.2f - %.2f%n", width, maxWidth);
                System.out.printf("  height: %.2f - %.2f%n", height, maxHeight);
                if (width > maxWidth || height > maxHeight) {
                    double scale = Math.min(maxWidth / width, maxHeight / height);
                    width = Math.floor(width * scale);
                    height = Math.floor(height * scale);
                    image = new Image(file.toURI().toString(), (int) width, (int) height, false, false);
                    System.out.printf("  adjusting size: %.2f/%.2f%n", width, height);
                }
                //Draw
            } else if (event.getCode() == KeyCode.D) {
                System.out.println("Drawing image ...");
                // altes Canvas löschen
                root.getChildren().remove(canvas);
                // größe vom Fenster ändern
                scene.getWindow().setWidth(image.getWidth() + WIDTH_OFFSET);
                scene.getWindow().setHeight(image.getHeight() + HEIGHT_OFFSET);
                // neues Canvas für neues Bild erstellen
                canvas = new Canvas(image.getWidth() + WIDTH_OFFSET, image.getHeight() + HEIGHT_OFFSET);
                root.getChildren().add(canvas);
                canvas.getGraphicsContext2D().drawImage(image, 0, 0);
                //Lettering
            } else if (event.getCode() == KeyCode.L) {
                System.out.println("Lettering ...");
                // Zufallsgenerator für pixel und buchstaben
                Random random = new Random();
                String letters = "|.oO/\\-_";
                // zeichne 100.000 buchstaben
                for (int i = 0; i < 100000; i++) {
                    // zufälligen pixel auswählen
                    int x = random.nextInt((int) image.getWidth());
                    int y = random.nextInt((int) image.getHeight());
                    // farbe auslesen
                    Color color = image.getPixelReader().getColor(x, y);
                    canvas.getGraphicsContext2D().setFill(color);
                    // zufälligen buchstaben wählen
                    char randomLetter = letters.charAt(random.nextInt(letters.length()));
                    canvas.getGraphicsContext2D().fillText(String.valueOf(randomLetter), x, y);
                }
                //Kreise
            } else if (event.getCode() == KeyCode.K) {
                System.out.println("Kreise");
                Random random = new Random();
                for (int i = 0; i < 1000 * 100; i++) {
                    int x = random.nextInt(((int) image.getWidth()));
                    int y = random.nextInt(((int) image.getHeight()));
                    Color color = image.getPixelReader().getColor(x, y);
                    color = Color.color(color.getRed(), color.getGreen(), color.getBlue(), random.nextDouble());
                    canvas.getGraphicsContext2D().setFill(color);
                    canvas.getGraphicsContext2D().fillOval(x, y, 10, 10);
                }
                //Balken
            } else if (event.getCode() == KeyCode.B) {
                System.out.println("Balken");
                Random random = new Random();
                for (int i = 0; i < 1000 * 100; i++) {
                    int x = random.nextInt(((int) image.getWidth()));
                    int y = random.nextInt(((int) image.getHeight()));
                    Color color = image.getPixelReader().getColor(x, y);
                    color = Color.color(color.getRed(), color.getGreen(), color.getBlue(), random.nextDouble());
                    canvas.getGraphicsContext2D().setFill(color);
                    canvas.getGraphicsContext2D().fillRect(x, y, 20, 5);
                }
                //Säulen
            } else if (event.getCode() == KeyCode.J) {
                System.out.println("Säulen");
                Random random = new Random();
                for (int i = 0; i < 1000 * 100; i++) {
                    int x = random.nextInt(((int) image.getWidth()));
                    int y = random.nextInt(((int) image.getHeight()));
                    Color color = image.getPixelReader().getColor(x, y);
                    color = Color.color(color.getRed(), color.getGreen(), color.getBlue(), random.nextDouble());
                    canvas.getGraphicsContext2D().setFill(color);
                    canvas.getGraphicsContext2D().fillRect(x, y, 5, 20);
                }
                //Kreise und Vierecke
            } else if (event.getCode() == KeyCode.DIGIT5) {
                System.out.println("Magic.");
                Random random = new Random();
                for (int i = 0; i < 1000 * 100; i++) {
                    int x = random.nextInt(((int) image.getWidth()));
                    int y = random.nextInt(((int) image.getHeight()));
                    Color color = image.getPixelReader().getColor(x, y);
                    color = Color.color(color.getRed(), color.getGreen(), color.getBlue(), random.nextDouble());
                    canvas.getGraphicsContext2D().setFill(color);
                    if (random.nextBoolean()) {
                        canvas.getGraphicsContext2D().fillRect(x, y, 10, 10);
                    } else {
                        canvas.getGraphicsContext2D().fillOval(x, y, 10, 10);
                    }
                }
            }
        });

        // szene setzen, titel ändern, anzeigen
        primaryStage.setScene(scene);
        primaryStage.setTitle("Projekt GdG CgD");
        primaryStage.show();
    }
}
