package com.irinel.craiu.liceanse.shareGeneration;

import com.google.common.io.Resources;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;


public class HonSchemeDecryptionTest {

    HonSchemeDecryption honSchemeDecryption;
    private static final String SHARE_1_FILE_NAME = "encryptedImages/firstShare.gif";
    private static final String SHARE_2_FILE_NAME = "encryptedImages/secondShare.gif";

    @Before
    public void setUp() throws Exception {
        honSchemeDecryption = new HonSchemeDecryption();
    }

    @Test
    public void testDecryptImages() throws Exception {
        BufferedImage share1 = ImageIO.read(Resources.getResource(SHARE_1_FILE_NAME));
        BufferedImage share2 = ImageIO.read(Resources.getResource(SHARE_2_FILE_NAME));
        BufferedImage bufferedImage = honSchemeDecryption.decryptImages(share1, share2);
        ImageIO.write(bufferedImage, "gif", new File("decryptedTextImage.gif"));
    }
}
