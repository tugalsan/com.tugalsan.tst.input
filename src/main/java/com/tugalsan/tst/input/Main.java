package com.tugalsan.tst.input;

import com.tugalsan.api.input.server.TS_InputCommonUtils;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import static java.lang.System.out;
import java.nio.file.Path;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class Main {

//    private static final TS_Log d = TS_Log.of(Main.class);
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

    //cd C:\me\codes\com.tugalsan\tst\com.tugalsan.tst.input
    //java --enable-preview --add-modules jdk.incubator.vector -jar target/com.tugalsan.tst.input-1.0-SNAPSHOT-jar-with-dependencies.jar
    //java -jar target/com.tugalsan.tst.input-1.0-SNAPSHOT-jar-with-dependencies.jar
    public static void main(String... s) {
        var size = size();
        log.ci("main", "size", size);
        var img = shotPicture(size);
        var jpg = Path.of("D:\\git\\tst\\com.tugalsan.tst.input\\out.jpg");
        toJpg(img, jpg);
    }

    static void toJpg(BufferedImage input, Path output) {
        try {
            ImageIO.write(input, "jpg", output.toFile());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    static Rectangle size() {
        var localGrapicsEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        var screensDevices = localGrapicsEnv.getScreenDevices();
        var allScreenBounds = new java.awt.Rectangle();
        for (var screen : screensDevices) {
            var screenBounds = screen.getDefaultConfiguration().getBounds();
            allScreenBounds.width += screenBounds.width;
            allScreenBounds.height = Math.max(allScreenBounds.height, screenBounds.height);
        }
        return allScreenBounds;
    }

    static BufferedImage shotPicture(Rectangle size) {
        return TS_InputCommonUtils.robot().createScreenCapture(size);
    }

}
