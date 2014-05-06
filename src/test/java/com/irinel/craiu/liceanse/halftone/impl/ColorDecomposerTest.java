package com.irinel.craiu.liceanse.halftone.impl;

import com.google.common.io.Resources;
import com.irinel.craiu.liceanse.imageutils.DecomposedCMYImage;
import com.irinel.craiu.liceanse.imageutils.RgbColor;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;

import static org.junit.Assert.*;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ColorDecomposerTest {

    private static final String IMAGE_FILE_NAME = "text.png";
    private ColorDecomposer colorDecomposer;
    private BufferedImage bufferedImage;
    private DecomposedCMYImage decomposedImage;
    private FloydSteinbergDitherer ditherer;

    @Before
    public void setUp() throws IOException {
        colorDecomposer = new ColorDecomposer();
        bufferedImage = ImageIO.read(Resources.getResource(IMAGE_FILE_NAME));
        decomposedImage = colorDecomposer.getDecomposedCMYImages(bufferedImage);
        ditherer = new FloydSteinbergDitherer();
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
    public void imageIsYellowColorScale() throws IOException {
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
    public void imageIsMangentaColorScale() throws IOException {
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
        ImageIO.write(mangentaScaleImage , "jpg" , new File("textMangentaScale.jpg"));
        assertEquals(isImagemangentaScale, true);
    }

    @Test
    public void imageIsCyanColorScale() throws IOException {
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

    @Test
    public void testRemakeImage() throws IOException {

        BufferedImage cyanScale = decomposedImage.getCyanScaleImage();
        BufferedImage yellowScale = decomposedImage.getYellowScaleImage();
        BufferedImage mangentaScale = decomposedImage.getMangentaScaleImage();


        int width = cyanScale.getWidth();
        int height = cyanScale.getHeight();
        BufferedImage resultingImage = new BufferedImage(width, height, ColorSpace.TYPE_RGB);

        boolean areImagesIdentical = true;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                resultingImage.setRGB(i, j, colorDecomposer.getIntColorFromRGB(
                        255 - ditherer.getChanellColorFromInt(cyanScale.getRGB(i, j), ColorDecomposer.RED_CHANNEL),
                        255 - ditherer.getChanellColorFromInt(mangentaScale.getRGB(i, j), ColorDecomposer.GREEN_CHANNEL),
                        255 - ditherer.getChanellColorFromInt(yellowScale.getRGB(i, j), ColorDecomposer.BLUE_CHANNEL)
                ));
            }
        }

        ImageIO.write(resultingImage, "jpg", new File("image.jpg"));

        /*for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (bufferedImage.getRGB(i, j) != resultingImage.getRGB(i, j)) {
                    areImagesIdentical = false;
                    System.out.println(i + "-" + j);
                }
            }
        }
        assertEquals(areImagesIdentical, true);*/
    }
}
