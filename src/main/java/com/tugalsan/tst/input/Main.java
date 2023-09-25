package com.tugalsan.tst.input;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import static java.lang.System.out;
import java.nio.file.Path;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class Main {

//    private static final TS_Log d = TS_Log.of(Main.class);
    //cd C:\me\codes\com.tugalsan\tst\com.tugalsan.tst.input
    //java --enable-preview --add-modules jdk.incubator.vector -jar target/com.tugalsan.tst.input-1.0-SNAPSHOT-jar-with-dependencies.jar
    //java -jar target/com.tugalsan.tst.input-1.0-SNAPSHOT-jar-with-dependencies.jar
    public static void main(String... s) {

        var screenRectangle = screenRectangle();
        log.ci("main", "screenRectangle", screenRectangle);
        var scaleFactor = scaleFactor();
        log.ci("main", "scaleFactor", scaleFactor);
        screenRectangle.setRect(
                scaleFactor * screenRectangle.x,
                scaleFactor * screenRectangle.y,
                scaleFactor * screenRectangle.width,
                scaleFactor * screenRectangle.height
        );

        var img = shotPicture(screenRectangle);
        if (img == null) {
            return;
        }
        var jpg = Path.of("D:\\git\\tst\\com.tugalsan.tst.input\\out.jpg");
        toJpg(img, jpg);
    }

    static float scaleFactor() {
        return Toolkit.getDefaultToolkit().getScreenResolution() / 96f;
    }

    static void toJpg(BufferedImage input, Path output) {
        try {
            ImageIO.write(input, "jpg", output.toFile());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    static Rectangle screenRectangle() {
        var rectangle = new Rectangle(0, 0, 0, 0);
        for (var graphicsDevice : GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()) {
            rectangle = rectangle.union(graphicsDevice.getDefaultConfiguration().getBounds());
        }
        return rectangle;
    }

    static BufferedImage shotPicture(Rectangle size) {
        try {
            return new Robot().createScreenCapture(size);
        } catch (AWTException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    static class log {

        public static void ci(String funcName, Object... oAll) {
            out.print(funcName);
            Arrays.stream(oAll).forEachOrdered(oi -> {
                out.print(" ");
                out.print(oi);
            });
            out.println();
        }
    }

}
