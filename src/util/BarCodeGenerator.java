package util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import jbarcodebean.JBarcodeBean;
import net.sourceforge.jbarcodebean.model.Interleaved25;

public class BarCodeGenerator {
    JBarcodeBean barcode;
    File file;
    BufferedImage bufferedImage;
    
    public BarCodeGenerator(String id) {
        barcode = new JBarcodeBean();
        barcode.setCodeType(new Interleaved25());

        barcode.setCode(id);
        barcode.setCheckDigit(true);

        bufferedImage = barcode.draw(new BufferedImage(80, 60, BufferedImage.TYPE_INT_RGB));
    }
    
    public void exportarPNG(){
        try {
            file = new File("codebar.png");
            ImageIO.write(bufferedImage, "png", file);
        } catch (IOException ex) {
        }
    }
    
    public void anadirAImageView(ImageView imageView){
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        imageView.setImage(image);
    }
}
