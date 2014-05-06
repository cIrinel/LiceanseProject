package com.irinel.craiu.liceanse.halftone.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;

import static com.google.common.base.Preconditions.checkNotNull;


public class FloydSteinbergDitherer{

    private static final Logger LOG = LoggerFactory.getLogger(FloydSteinbergDitherer.class);


    public BufferedImage getDitheredImage(BufferedImage image, int colorChannel) {
        checkNotNull(image, "Image to be dithered is null!");
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage ditheredImage = new BufferedImage(width, height, ColorSpace.TYPE_RGB);

        for (int i = 0; i   < width; i++) {
            for (int j = 0; j < height; j++) {
                int oldPixel = image.getRGB(i, j);
                int newPixel = getRgbIntFromColor(getRoundedColorValue(oldPixel, colorChannel), colorChannel);

                int quantizedError = getQuantizationError(oldPixel, newPixel, colorChannel);

                if (i + 1 < width - 1)
                    image.setRGB(i + 1, j,
                            getDitheredPixel(image.getRGB(i + 1, j), quantizedError, (double) 7 / 16, colorChannel));
                if (i - 1 >= 0 && j + 1 < height - 1)
                    image.setRGB(i - 1, j + 1,
                            getDitheredPixel(image.getRGB(i - 1, j + 1), quantizedError, (double) 3 / 16, colorChannel));
                if (j + 1 < height - 1)
                    image.setRGB(i, j + 1,
                            getDitheredPixel(image.getRGB(i, j + 1), quantizedError, (double) 5 / 16, colorChannel));
                if (i + 1 < width - 1 && j + 1 < height - 1)
                    image.setRGB(i + 1, j + 1,
                            getDitheredPixel(image.getRGB(i + 1, j + 1), quantizedError, (double) 1 / 16, colorChannel));

                ditheredImage.setRGB(i, j, newPixel);
            }
        }

        return ditheredImage;
    }

    public int getRoundedColorValue(int pixel, int colorChannel) {
        int color = getChanellColorFromInt(pixel, colorChannel);
        if (color < 128) {
            return 0;
        }
        return 255;
    }


    public int getChanellColorFromInt(int pixel, int colorChannel) {
        return pixel >> colorChannel & 0xff;
    }

    public int getRgbIntFromColor(int pixel, int colorChannel) {
        if (colorChannel == ColorDecomposer.RED_CHANNEL)
            return new ColorDecomposer().getIntColorFromRGB(pixel, 255, 255);
        if (colorChannel == ColorDecomposer.GREEN_CHANNEL)
            return new ColorDecomposer().getIntColorFromRGB(255, pixel, 255);
        if (colorChannel == ColorDecomposer.BLUE_CHANNEL)
            return new ColorDecomposer().getIntColorFromRGB(255, 255, pixel);
        LOG.error(String.format("%s is not a valid color channel!", colorChannel));
        return 0;
    }

    public int getQuantizationError(int oldPixel, int newPixel, int colorChannel) {
        return getChanellColorFromInt(oldPixel, colorChannel) - getChanellColorFromInt(newPixel, colorChannel);
    }

    public int getDitheredPixel(int pixel, int error, double modifier, int colorChannel) {
        int ditheredColor = getChanellColorFromInt(pixel, colorChannel) + (int) (error * modifier);
        if (ditheredColor > 255) ditheredColor = 255;
        if (ditheredColor < 0) ditheredColor = 0;

        return getRgbIntFromColor(ditheredColor, colorChannel);
    }


}
