package com.irinel.craiu.liceanse.halftone.impl;

import com.irinel.craiu.liceanse.imageutils.PixelConverter;
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
                int newPixel = PixelConverter.getRgbIntFromColor(getRoundedColorValue(oldPixel, colorChannel), colorChannel);

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
        int color = PixelConverter.getChanellColorFromInt(pixel, colorChannel);
        if (color < 128) {
            return 0;
        }
        return 255;
    }






    public int getQuantizationError(int oldPixel, int newPixel, int colorChannel) {
        return PixelConverter.getChanellColorFromInt(oldPixel, colorChannel) -
                PixelConverter.getChanellColorFromInt(newPixel, colorChannel);
    }

    public int getDitheredPixel(int pixel, int error, double modifier, int colorChannel) {
        int ditheredColor = PixelConverter.getChanellColorFromInt(pixel, colorChannel) + (int) (error * modifier);
        if (ditheredColor > 255) ditheredColor = 255;
        if (ditheredColor < 0) ditheredColor = 0;

        return PixelConverter.getRgbIntFromColor(ditheredColor, colorChannel);
    }


}
