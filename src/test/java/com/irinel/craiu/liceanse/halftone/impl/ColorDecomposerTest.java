package com.irinel.craiu.liceanse.halftone.impl;

import com.google.common.io.Resources;
import com.irinel.craiu.liceanse.imageutils.DecomposedCMYImage;
import com.irinel.craiu.liceanse.imageutils.RgbColor;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;

import static org.junit.Assert.*;

import java.awt.*;
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
        decomposedImage = colorDecomposer.getDecomposedCMYImages(bufferedImage);
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

    @Test
    public void imageIsYellowColorScale() {
        BufferedImage yellowScaleImage = decomposedImage.getYellowScaleImage();
        boolean isImageYellowScale = true;
        for (int i = 0; i < yellowScaleImage.getWidth(); i++) {
            for (int j = 0; j < yellowScaleImage.getHeight(); j++) {
                int pixel = yellowScaleImage.getRGB(i, j);
                if (((pixel >> ColorDecomposer.RED_CHANNEL) & 0xff) != 255 ||
                        ((pixel >> ColorDecomposer.GREEN_CHANNEL) & 0xff) != 255) {
                    isImageYellowScale = false;
                }
            }
        }
        assertEquals(isImageYellowScale, true);
    }

    @Test
    public void imageIsMangentaColorScale() {
        BufferedImage mangentaScaleImage = decomposedImage.getMangentaScaleImage();
        boolean isImagemangentaScale = true;
        for (int i = 0; i < mangentaScaleImage.getWidth(); i++) {
            for (int j = 0; j < mangentaScaleImage.getHeight(); j++) {
                int pixel = mangentaScaleImage.getRGB(i, j);
                if (((pixel >> ColorDecomposer.RED_CHANNEL) & 0xff) != 255 ||
                        ((pixel >> ColorDecomposer.BLUE_CHANNEL) & 0xff) != 255) {
                    isImagemangentaScale = false;
                }
            }
        }
        assertEquals(isImagemangentaScale, true);
    }

    @Test
    public void imageIsCyanColorScale() {
        BufferedImage cyanScaleImage = decomposedImage.getCyanScaleImage();
        boolean isImagecyanScale = true;
        for (int i = 0; i < cyanScaleImage.getWidth(); i++) {
            for (int j = 0; j < cyanScaleImage.getHeight(); j++) {
                int pixel = cyanScaleImage.getRGB(i, j);
                if (((pixel >> ColorDecomposer.GREEN_CHANNEL) & 0xff) != 255 ||
                        ((pixel >> ColorDecomposer.BLUE_CHANNEL) & 0xff) != 255) {
                    isImagecyanScale = false;
                }
            }
        }
        assertEquals(isImagecyanScale, true);
    }

    @Test
    public void testGetRgbColorFromInt() {
        Color color = new Color(100, 121, 232);
        RgbColor rgbColor = colorDecomposer.getRGBColorFromInt(color.getRGB());
        assertEquals(rgbColor.getBlue(), 232);
        assertEquals(rgbColor.getRed(), 100);
        assertEquals(rgbColor.getGreen(), 121);
    }

    @Test
    public void testGetWhiteIntColorFromInt() {
        int whiteColor = colorDecomposer.getIntColorFromRGB(120, 150, 25);
        assertEquals(25, colorDecomposer.getRGBColorFromInt(whiteColor).getBlue());
        assertEquals(120, colorDecomposer.getRGBColorFromInt(whiteColor).getRed());
        assertEquals(150, colorDecomposer.getRGBColorFromInt(whiteColor).getGreen());
    }

    public void testImageReasembles(){
        //TODO: write me
    }
}
