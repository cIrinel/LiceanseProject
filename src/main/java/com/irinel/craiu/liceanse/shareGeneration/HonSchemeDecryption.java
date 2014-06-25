package com.irinel.craiu.liceanse.shareGeneration;


import com.irinel.craiu.liceanse.imageutils.PixelConverter;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;

public class HonSchemeDecryption {

    public BufferedImage decryptImages(BufferedImage share1, BufferedImage share2) {
        int width = share1.getWidth();
        int height = share1.getHeight();
        BufferedImage bufferedImage = new BufferedImage(width, height, ColorSpace.TYPE_RGB);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int firstSharePixel = share1.getRGB(i, j);
                int secondSharePixel = share2.getRGB(i, j);

                int stackedPixel = PixelConverter.getIntColorFromRGB(
                        Math.min(PixelConverter.getChanellColorFromInt(firstSharePixel, PixelConverter.RED_CHANNEL),
                                PixelConverter.getChanellColorFromInt(secondSharePixel, PixelConverter.RED_CHANNEL)),
                        Math.min(PixelConverter.getChanellColorFromInt(firstSharePixel, PixelConverter.GREEN_CHANNEL),
                                PixelConverter.getChanellColorFromInt(secondSharePixel, PixelConverter.GREEN_CHANNEL)),
                        Math.min(PixelConverter.getChanellColorFromInt(firstSharePixel, PixelConverter.BLUE_CHANNEL),
                                PixelConverter.getChanellColorFromInt(secondSharePixel, PixelConverter.BLUE_CHANNEL)));
                bufferedImage.setRGB(i, j, stackedPixel);
            }
        }
        return bufferedImage;
    }
}
