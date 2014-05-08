package com.irinel.craiu.liceanse.halftone.impl;

import com.google.common.io.Resources;
import com.irinel.craiu.liceanse.imageutils.PixelConverter;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;


public class FloydSteinbergDithererTest {

    private static final Logger logger = LoggerFactory.getLogger(FloydSteinbergDithererTest.class);
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
        int red = PixelConverter.getChanellColorFromInt(pixel, ColorDecomposer.RED_CHANNEL);
        int blue = PixelConverter.getChanellColorFromInt(pixel, ColorDecomposer.BLUE_CHANNEL);
        int green = PixelConverter.getChanellColorFromInt(pixel, ColorDecomposer.GREEN_CHANNEL);

        Assert.assertEquals(red, 129);
        Assert.assertEquals(green, 122);
        Assert.assertEquals(blue, 128);

    }

    @Test
    public void testRemakeImage() throws IOException {

        logger.info("Recomposing a dithered image is a visual test only");
        BufferedImage cyanHalftone = ImageIO.read(Resources.getResource("halftonedImages/cyanHalftoned.jpg"));
        BufferedImage yellowHalftone = ImageIO.read(Resources.getResource("halftonedImages/yellowHalftoned.jpg"));
        BufferedImage mangentaHalftone = ImageIO.read(Resources.getResource("halftonedImages/mangentaHalftoned.jpg"));

        int width = cyanHalftone.getWidth();
        int height = cyanHalftone.getHeight();
        BufferedImage resultingImage = new BufferedImage(width, height, ColorSpace.TYPE_RGB);


        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                resultingImage.setRGB(i, j, PixelConverter.getIntColorFromRGB(
                        255 - PixelConverter.getChanellColorFromInt(cyanHalftone.getRGB(i, j), ColorDecomposer.RED_CHANNEL),
                        255 - PixelConverter.getChanellColorFromInt(mangentaHalftone.getRGB(i, j), ColorDecomposer.GREEN_CHANNEL),
                        255 - PixelConverter.getChanellColorFromInt(yellowHalftone.getRGB(i, j), ColorDecomposer.BLUE_CHANNEL)
                ));
            }
        }
        //  ImageIO.write(resultingImage, "jpg", new File("recomposedDitheredImage.jpg"));

    }
}
