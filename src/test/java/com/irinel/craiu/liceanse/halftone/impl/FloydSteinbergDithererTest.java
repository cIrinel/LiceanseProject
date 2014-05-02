package com.irinel.craiu.liceanse.halftone.impl;

import com.google.common.io.Resources;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;


public class FloydSteinbergDithererTest {

    FloydSteinbergDitherer ditherer;
    private static final String CYAN_SCALE_IMAGE = "colorScaleImages/cyanScale.jpg";
    private static final String MANGENTA_SCALE_IMAGE = "colorScaleImages/mangentaScale.jpg";
    private static final String YELLOW_SCALE_IMAGE = "colorScaleImages/yellowScale.jpg";
    private static final int EXPECTED_ROUND_UP_COLOR_VALUE = 255;
    private static final int EXPECTED_ROUND_DOWN_COLOR_VALUE = 0;
    BufferedImage binaryImage;
    ColorDecomposer colorDecomposer;


    @Before
    public void setUp() {
        ditherer = new FloydSteinbergDitherer();
        colorDecomposer = new ColorDecomposer();
    }


    @Test
    public void testGetCyanDitheredImage() throws Exception {
        binaryImage = ditherer.getDitheredImage(ImageIO.read(Resources.getResource(CYAN_SCALE_IMAGE)),
                ColorDecomposer.RED_CHANNEL);
        boolean isImageBinary = true;

        for (int i = 0; i < binaryImage.getWidth(); i++) {
            for (int j = 0; j < binaryImage.getHeight(); j++) {
                int pixel = binaryImage.getRGB(i, j);
                if (((pixel >> ColorDecomposer.GREEN_CHANNEL) & 0xff) != 255 &&
                        ((pixel >> ColorDecomposer.BLUE_CHANNEL) & 0xff) != 255 &&
                        (((pixel >> ColorDecomposer.RED_CHANNEL) & 0xff) != 255 ||
                                ((pixel >> ColorDecomposer.RED_CHANNEL) & 0xff) != 0)) {
                    isImageBinary = false;
                }
            }
        }


        assertEquals(isImageBinary, true);
    }

    @Test
    public void testGetMangentaDitheredImage() throws Exception {
        binaryImage = ditherer.getDitheredImage(ImageIO.read(Resources.getResource(MANGENTA_SCALE_IMAGE)),
                ColorDecomposer.GREEN_CHANNEL);
        boolean isImageBinary = true;

        for (int i = 0; i < binaryImage.getWidth(); i++) {
            for (int j = 0; j < binaryImage.getHeight(); j++) {
                int pixel = binaryImage.getRGB(i, j);
                if (((pixel >> ColorDecomposer.RED_CHANNEL) & 0xff) != 255 &&
                        ((pixel >> ColorDecomposer.BLUE_CHANNEL) & 0xff) != 255 &&
                        (((pixel >> ColorDecomposer.GREEN_CHANNEL) & 0xff) != 255 ||
                                ((pixel >> ColorDecomposer.GREEN_CHANNEL) & 0xff) != 0)) {
                    isImageBinary = false;
                }
            }
        }

        assertEquals(isImageBinary, true);
    }


    @Test
    public void testGetYellowDitheredImage() throws Exception {

        binaryImage = ditherer.getDitheredImage(ImageIO.read(Resources.getResource(YELLOW_SCALE_IMAGE)),
                ColorDecomposer.BLUE_CHANNEL);
        boolean isImageBinary = true;

        for (int i = 0; i < binaryImage.getWidth(); i++) {
            for (int j = 0; j < binaryImage.getHeight(); j++) {
                int pixel = binaryImage.getRGB(i, j);
                if (((pixel >> ColorDecomposer.RED_CHANNEL) & 0xff) != 255 &&
                        ((pixel >> ColorDecomposer.GREEN_CHANNEL) & 0xff) != 255 &&
                        (((pixel >> ColorDecomposer.BLUE_CHANNEL) & 0xff) != 255 ||
                                ((pixel >> ColorDecomposer.BLUE_CHANNEL) & 0xff) != 0)) {
                    isImageBinary = false;
                }
            }
        }


        assertEquals(isImageBinary, true);
    }

    @Test
    public void testGetRountColorValue() throws Exception {
        int pixel = new Color(129, 122, 128).getRGB();
        int red = ditherer.getRoundedColorValue(pixel, ColorDecomposer.RED_CHANNEL);
        int blue = ditherer.getRoundedColorValue(pixel, ColorDecomposer.BLUE_CHANNEL);
        int green = ditherer.getRoundedColorValue(pixel, ColorDecomposer.GREEN_CHANNEL);

        Assert.assertEquals(red, EXPECTED_ROUND_UP_COLOR_VALUE);
        Assert.assertEquals(green, EXPECTED_ROUND_DOWN_COLOR_VALUE);
        Assert.assertEquals(blue, EXPECTED_ROUND_UP_COLOR_VALUE);
    }

    @Test
    public void testGetColorOfChannelFromInt() {
        int pixel = new Color(129, 122, 128).getRGB();
        int red = ditherer.getChanellColorFromInt(pixel, ColorDecomposer.RED_CHANNEL);
        int blue = ditherer.getChanellColorFromInt(pixel, ColorDecomposer.BLUE_CHANNEL);
        int green = ditherer.getChanellColorFromInt(pixel, ColorDecomposer.GREEN_CHANNEL);

        Assert.assertEquals(red, 129);
        Assert.assertEquals(green, 122);
        Assert.assertEquals(blue, 128);

    }
}
