package com.irinel.craiu.liceanse.halftone.impl;

import com.irinel.craiu.liceanse.imageutils.DecomposedImage;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


public class ColorDecomposerTest {
    //TODO: add guava to this project to manage resources better---manu
    private static final String IMAGE_FILE_NAME = "src/main/resources/hdImage.jpg";
    private ColorDecomposer colorDecomposer;
    private BufferedImage bufferedImage;
    private DecomposedImage decomposedImage;

    @Before
    public void setUp() throws IOException {
        colorDecomposer = new ColorDecomposer();
        bufferedImage = ImageIO.read(new File(IMAGE_FILE_NAME));
        decomposedImage = colorDecomposer.decomposeImageToPrimaryColors(bufferedImage);
    }


    @Test
    public void imageDecomposesToFirstPrimaryColor() throws Exception {
        assertEquals(decomposedImage.getImageInPrimaryColor1() != null, true);
    }

    @Test
    public void imageDecomposesToSecondPrimaryColor() throws Exception {
        assertEquals(decomposedImage.getImageInPrimaryColor2() != null, true);
    }

    @Test
    public void imageDecomposesToThirdPrimaryColor() throws Exception {
        assertEquals(decomposedImage.getImageInPrimaryColor3() != null, true);
    }
}
