package com.irinel.craiu.liceanse.halftone.impl;


import com.irinel.craiu.liceanse.imageutils.DecomposedCMYImage;
import com.irinel.craiu.liceanse.imageutils.RgbColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.google.common.base.Preconditions.checkNotNull;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;

public class ColorDecomposer {
    private static final Logger LOG = LoggerFactory.getLogger(ColorDecomposer.class);

    public static final int RED_CHANNEL = 16;
    public static final int GREEN_CHANNEL = 8;
    public static final int BLUE_CHANNEL = 0;

    //TODO: rename and refactor this method. its too big
    public DecomposedCMYImage getDecomposedCMYImages(BufferedImage bufferedImage) {
        LOG.info("Attempting to decompose input image into \"colorscale\"");
        checkNotNull(bufferedImage, "Input image buffer is null");

        DecomposedCMYImage decomposedImage = new DecomposedCMYImage();
        int imageHeight = bufferedImage.getHeight();
        int imageWidth = bufferedImage.getWidth();

        BufferedImage cyanScaleImage = new BufferedImage(imageWidth, imageHeight, ColorSpace.TYPE_RGB);
        BufferedImage mangentaScaleImage = new BufferedImage(imageWidth, imageHeight, ColorSpace.TYPE_RGB);
        BufferedImage yellowScaleImage = new BufferedImage(imageWidth, imageHeight, ColorSpace.TYPE_RGB);


        for (int i = 0; i < bufferedImage.getWidth(); i++) {
            for (int j = 0; j < bufferedImage.getHeight(); j++) {
                int pixel = bufferedImage.getRGB(i, j);
                cyanScaleImage.setRGB(i, j, getIntColorFromRGB(255 - (pixel >> RED_CHANNEL) & 0xff, 255, 255));
                mangentaScaleImage.setRGB(i, j, getIntColorFromRGB(255, 255 - (pixel >> GREEN_CHANNEL) & 0xff, 255));
                yellowScaleImage.setRGB(i, j, getIntColorFromRGB(255, 255, 255 - (pixel >> BLUE_CHANNEL) & 0xff));

            }
        }

        decomposedImage.setCyanScaleImage(cyanScaleImage);
        decomposedImage.setMangentaScaleImage(mangentaScaleImage);
        decomposedImage.setYellowScaleImage(yellowScaleImage);

        return decomposedImage;
    }

    public int getIntColorFromRGB(int red, int green, int blue) {
        return new Color(red, green, blue).getRGB();
    }

    public RgbColor getRGBColorFromInt(int pixelColor) {
        RgbColor rgbColor = new RgbColor();
        rgbColor.setRed(pixelColor >> RED_CHANNEL & 0xff);
        rgbColor.setBlue((pixelColor >> BLUE_CHANNEL) & 0xff);
        rgbColor.setGreen((pixelColor >> GREEN_CHANNEL) & 0xff);

        return rgbColor;

    }


}
