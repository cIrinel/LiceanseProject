package com.irinel.craiu.liceanse;

import com.google.common.io.Resources;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class VCryptTest {

    VCrypt vCrypt;
    private static final String HD_IMAGE_JPG = "hdImage.jpg";
    BufferedImage bufferedImage;

    @Before
    public void setUp() throws Exception {
        vCrypt = new VCrypt();
        bufferedImage = ImageIO.read(Resources.getResource(HD_IMAGE_JPG));
    }

    @Test
    public void testEncryptImage() throws Exception {
        vCrypt.encryptImage(bufferedImage);

    }
}
