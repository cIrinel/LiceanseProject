package com.irinel.craiu.liceanse.authenticationSupport;


import com.irinel.craiu.liceanse.imageutils.DitheredImageDifference;
import com.irinel.craiu.liceanse.imageutils.PixelConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        int[][] differences = new int[imageWidth][imageHeight];

        for (int i = 0; i < imageWidth; i++) {
            for (int j = 0; j < imageHeight; j++) {
                int originalImagePixel = PixelConverter.getChanellColorFromInt(originalImage.getRGB(i, j), colorChannel);
                int ditheredImagePixel = PixelConverter.getChanellColorFromInt(ditheredImage.getRGB(i, j), colorChannel);
                differences[i][j] = originalImagePixel - ditheredImagePixel;
            }
        }

        return differences;
    }

    public DitheredImageDifference getDitheredImageDifferences(BufferedImage originalImage, BufferedImage ditheredImage) {
        return new DitheredImageDifference(
                getDifferencesByColorChannel(originalImage, ditheredImage, PixelConverter.RED_CHANNEL),
                getDifferencesByColorChannel(originalImage, ditheredImage, PixelConverter.GREEN_CHANNEL),
                getDifferencesByColorChannel(originalImage, ditheredImage, PixelConverter.BLUE_CHANNEL)
        );
    }

    public BufferedImage getRecomposedDitheredImage(BufferedImage ditheredImage, DitheredImageDifference difference) {

        checkNotNull(ditheredImage, "dithered image is null");
        checkNotNull(difference, "differences matrices are null");
        LOGGER.info("Starting to recompose dithered image using all color channels");

        int imageHeight = ditheredImage.getHeight();
        int imageWidth = ditheredImage.getWidth();
        int[][] redScaleDifference = difference.getRedScaleDifferences();
        int[][] blueScaleDifference = difference.getBlueScaleDifferences();
        int[][] greenScaleDifference = difference.getGreenScaleDifferences();

        BufferedImage recomposedImage = new BufferedImage(imageWidth, imageHeight, ColorSpace.TYPE_RGB);

        for (int i = 0; i < imageWidth; i++) {
            for (int j = 0; j < imageHeight; j++) {


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
                        PixelConverter.getIntColorFromRGB(recomposedRedPixel, recomposedGreenPixel, recomposedBluePixel);
                recomposedImage.setRGB(i, j, recomposedPixel);
            }
        }
        return recomposedImage;
    }

    public BufferedImage getRecomposedDitheredImageByColorChannel(
            BufferedImage ditheredImage, int[][] colorChDifference, int colorChannel) {
        int imageHeight = ditheredImage.getHeight();
        int imageWidth = ditheredImage.getWidth();

        BufferedImage recomposedImage = new BufferedImage(imageWidth, imageHeight, ColorSpace.TYPE_RGB);

        for (int i = 0; i < imageWidth; i++) {
            for (int j = 0; j < imageHeight; j++) {
                int recomposedPixel =
                        PixelConverter.getChanellColorFromInt(ditheredImage.getRGB(i, j), colorChannel) +
                                colorChDifference[i][j];
                recomposedPixel = PixelConverter.getRgbIntFromColor(recomposedPixel, colorChannel);
                recomposedImage.setRGB(i, j, recomposedPixel);
            }
        }
        return recomposedImage;
    }
}
