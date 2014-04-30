package com.irinel.craiu.liceanse.halftone.impl;

import com.google.common.io.Resources;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;


public class FloydSteinbergDithererTest {

    FloydSteinbergDitherer ditherer;
    private static final String CYAN_SCALE_IMAGE = "cyanScale.jpg";
    private static final String MANGENTA_SCALE_IMAGE = "mangentaScale.jpg";
    private static final String YELLOW_SCALE_IMAGE = "yellowScale.jpg";
    BufferedImage binaryImage;


    @Before
    public void setUp() {
        ditherer = new FloydSteinbergDitherer();

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
        binaryImage = ImageIO.read(Resources.getResource(YELLOW_SCALE_IMAGE));
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
}
