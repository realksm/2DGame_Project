package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Obj_Boots extends SuperObject {
    public Obj_Boots() {
        name = "Boots";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/boots.png")));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
