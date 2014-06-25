package com.irinel.craiu.liceanse.imageutils;


import com.irinel.craiu.liceanse.halftone.impl.ColorDecomposer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class PixelConverter {

    private static final Logger LOG = LoggerFactory.getLogger(PixelConverter.class);

    public static final int RED_CHANNEL = 16;
    public static final int GREEN_CHANNEL = 8;
    public static final int BLUE_CHANNEL = 0;

    public static int getIntColorFromRGB(int red, int green, int blue) {
        return new Color(red, green, blue).getRGB();
    }

    public static RgbColor getRGBColorFromInt(int pixelColor) {
        RgbColor rgbColor = new RgbColor();
        rgbColor.setRed(pixelColor >> RED_CHANNEL & 0xff);
        rgbColor.setBlue((pixelColor >> BLUE_CHANNEL) & 0xff);
        rgbColor.setGreen((pixelColor >> GREEN_CHANNEL) & 0xff);

        return rgbColor;

    }

    public static int getChanellColorFromInt(int pixel, int colorChannel) {
        return pixel >> colorChannel & 0xff;
    }

    public static int getRgbIntFromColor(int pixel, int colorChannel) {
        if (colorChannel == RED_CHANNEL)
            return getIntColorFromRGB(pixel, 255, 255);
        if (colorChannel == GREEN_CHANNEL)
            return getIntColorFromRGB(255, pixel, 255);
        if (colorChannel == BLUE_CHANNEL)
            return getIntColorFromRGB(255, 255, pixel);
        LOG.error(String.format("%s is not a valid color channel!", colorChannel));
        return 0;
    }
}
