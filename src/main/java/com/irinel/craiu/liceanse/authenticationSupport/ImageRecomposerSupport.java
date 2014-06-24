package com.irinel.craiu.liceanse.authenticationSupport;


import com.irinel.craiu.liceanse.halftone.impl.ColorDecomposer;
import com.irinel.craiu.liceanse.imageutils.DitheredImageDifference;
import com.irinel.craiu.liceanse.imageutils.PixelConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.plaf.synth.ColorType;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;

import static com.google.common.base.Preconditions.checkNotNull;

public class ImageRecomposerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageRecomposerSupport.class);


    public int[][] getDifferencesByColorChannel(BufferedImage originalImage,
                                                BufferedImage ditheredImage, int colorChannel) {
        checkNotNull(originalImage, "Original image is null");
        checkNotNull(ditheredImage, "Dithered image is null");
        LOGGER.info("Starting to gather differences between original image and dithered image");

        int imageHeight = ditheredImage.getHeight();
        int imageWidth = ditheredImage.getWidth();
        int[][] differences = new int[imageHeight][imageWidth];

        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                int originalImagePixel = PixelConverter.getChanellColorFromInt(originalImage.getRGB(i, j), colorChannel);
                int ditheredImagePixel = PixelConverter.getChanellColorFromInt(ditheredImage.getRGB(i, j), colorChannel);
                differences[i][j] = originalImagePixel - ditheredImagePixel;
            }
        }

        return differences;
    }

    public BufferedImage getRecomposedDitheredImage(BufferedImage ditheredImage, DitheredImageDifference difference) {
        int imageHeight = ditheredImage.getHeight();
        int imageWidth = ditheredImage.getWidth();
        int[][] redScaleDifference = difference.getRedScaleDifferences();
        int[][] blueScaleDifference = difference.getBlueScaleDifferences();
        int[][] greenScaleDifference = difference.getGreenScaleDifferences();

        BufferedImage recomposedImage = new BufferedImage(imageWidth, imageHeight, ColorSpace.TYPE_RGB);

        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {


                int recomposedRedPixel =
                        PixelConverter.getChanellColorFromInt(ditheredImage.getRGB(i, j), PixelConverter.RED_CHANNEL) +
                                redScaleDifference[i][j];
                int recomposedBluePixel =
                        PixelConverter.getChanellColorFromInt(ditheredImage.getRGB(i, j), PixelConverter.BLUE_CHANNEL) +
                                blueScaleDifference[i][j];
                int recomposedGreenPixel =
                        PixelConverter.getChanellColorFromInt(ditheredImage.getRGB(i, j), PixelConverter.GREEN_CHANNEL) +
                                greenScaleDifference[i][j];

                int recomposedPixel =
                        PixelConverter.getIntColorFromRGB(recomposedRedPixel, recomposedBluePixel, recomposedGreenPixel);
                recomposedImage.setRGB(i, j, recomposedPixel);
            }
        }
        return ditheredImage;
    }
}
