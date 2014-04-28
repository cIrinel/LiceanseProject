package com.irinel.craiu.liceanse.halftone.impl;

import com.google.common.io.Resources;
import com.irinel.craiu.liceanse.imageutils.DecomposedCMYImage;
import org.junit.Before;
import org.junit.Test;
import javax.imageio.ImageIO;
import static org.junit.Assert.*;
import java.awt.image.BufferedImage;
import java.io.IOException;



public class ColorDecomposerTest {

    private static final String IMAGE_FILE_NAME = "hdImage.jpg";
    private ColorDecomposer colorDecomposer;
    private BufferedImage bufferedImage;
    private DecomposedCMYImage decomposedImage;

    @Before
    public void setUp() throws IOException {
        colorDecomposer = new ColorDecomposer();
        bufferedImage = ImageIO.read(Resources.getResource(IMAGE_FILE_NAME));
        decomposedImage = colorDecomposer.decomposeImageToCNYColors(bufferedImage);
    }


    @Test
    public void imageDecomposesToRedScale() throws Exception {
        assertEquals(decomposedImage.getCyanScaleImage() != null, true);
    }

    @Test
    public void imageDecomposesToGreenScale() throws Exception {
        assertEquals(decomposedImage.getMangentaScaleImage() != null, true);
    }

    @Test
    public void imageDecomposesToBlueScale() throws Exception {
        assertEquals(decomposedImage.getMangentaScaleImage() != null, true);

    }
}
