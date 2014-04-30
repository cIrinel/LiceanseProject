package com.irinel.craiu.liceanse.halftone.impl;

import com.irinel.craiu.liceanse.halftone.ImageDitherer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;


public class FloydSteinbergDitherer implements ImageDitherer {

    private static final Logger LOG = LoggerFactory.getLogger(FloydSteinbergDitherer.class);


    public BufferedImage getDitheredImage(BufferedImage image, int colorChannel) {
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                int pixel = image.getRGB(i, j);

            }
        }
        LOG.info("applying dither to a image in ");
        return null;
    }

    public int getRoundedColorValue(int pixel, int channel) {
        int redValue = (pixel >> ColorDecomposer.RED_CHANNEL) & 0xff;
        int greenValue = (pixel >> ColorDecomposer.GREEN_CHANNEL) & 0xff;
        int blueValue = (pixel >> ColorDecomposer.BLUE_CHANNEL) & 0xff;


        return 0;
    }


}
