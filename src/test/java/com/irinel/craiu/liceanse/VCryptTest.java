package com.irinel.craiu.liceanse;

import com.google.common.io.Resources;
import com.irinel.craiu.liceanse.imageutils.EncryptedImage;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class VCryptTest {

    VCrypt vCrypt;
    private static final String HD_IMAGE_JPG = "text.png";
    BufferedImage bufferedImage;

    @Before
    public void setUp() throws Exception {
        vCrypt = new VCrypt();
        bufferedImage = ImageIO.read(Resources.getResource(HD_IMAGE_JPG));
    }

    @Test
    public void testEncryptImage() throws Exception {
        EncryptedImage encryptedImage = vCrypt.encryptImage(bufferedImage);
        ImageIO.write(encryptedImage.getFirstShare(), "gif", new File("src/main/resources/encryptedImages/firstShare.gif"));
        ImageIO.write(encryptedImage.getSecondShare(), "gif", new File("src/main/resources/encryptedImages/secondShare.gif"));
    }
}
