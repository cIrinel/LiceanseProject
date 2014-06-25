package com.irinel.craiu.liceanse.authenticationSupport;

import com.google.common.io.Resources;
import com.irinel.craiu.liceanse.imageutils.DitheredImageDifference;
import com.irinel.craiu.liceanse.imageutils.PixelConverter;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class ImageRecomposerSupportTest {
    private static final String COLOR_SCALE_IMAGE = "colorScaleImages/cyanScale.jpg";
    private static final String DITHERED_IMAGE = "halftonedImages/cyanHalftoned.jpg";
    private static final String FULL_COLOR_SCALE_IMAGE = "fullDitheredImage.jpg";
    private ImageRecomposerSupport imageRecomposerSupport;
    private BufferedImage originalImage;
    private BufferedImage ditheredImage;

    @Before
    public void setUp() throws Exception {
        imageRecomposerSupport = new ImageRecomposerSupport();
        originalImage = ImageIO.read(Resources.getResource(COLOR_SCALE_IMAGE));
        ditheredImage = ImageIO.read(Resources.getResource(DITHERED_IMAGE));
    }

    @Test
    public void testGetDifferencesByColorChannelTest() throws Exception {
        int[][] redDifferences = imageRecomposerSupport
                .getDifferencesByColorChannel(originalImage, ditheredImage, PixelConverter.RED_CHANNEL);
        int[][] greenDifferences = imageRecomposerSupport
                .getDifferencesByColorChannel(originalImage, ditheredImage, PixelConverter.GREEN_CHANNEL);
        int[][] blueDifferences = imageRecomposerSupport
                .getDifferencesByColorChannel(originalImage, ditheredImage, PixelConverter.BLUE_CHANNEL);
        int[][] emptyArray = new int[originalImage.getHeight()][originalImage.getWidth()];

        Assert.assertNotSame(redDifferences, emptyArray);
        Assert.assertNotSame(greenDifferences, emptyArray);
        Assert.assertNotSame(blueDifferences, emptyArray);
    }

    @Test
    public void getDitheredImageDifferencesTest() {
        DitheredImageDifference ditheredImageDifference = imageRecomposerSupport
                .getDitheredImageDifferences(originalImage, ditheredImage);
        Assert.assertNotNull(ditheredImageDifference.getBlueScaleDifferences());
        Assert.assertNotNull(ditheredImageDifference.getRedScaleDifferences());
        Assert.assertNotNull(ditheredImageDifference.getGreenScaleDifferences());
    }

    @Test
    public void getRecomposedDitheredImageByColorChannelTest() {
        DitheredImageDifference ditheredImageDifference = imageRecomposerSupport
                .getDitheredImageDifferences(originalImage, ditheredImage);


        BufferedImage recomposedImage = imageRecomposerSupport.getRecomposedDitheredImageByColorChannel(
                ditheredImage,
                ditheredImageDifference.getRedScaleDifferences(),
                PixelConverter.RED_CHANNEL);
        for (int i = 0; i < originalImage.getWidth(); i++) {
            for (int j = 0; j < originalImage.getHeight(); j++) {
                Assert.assertEquals(
                        PixelConverter.getChanellColorFromInt(recomposedImage.getRGB(i, j), PixelConverter.RED_CHANNEL),
                        PixelConverter.getChanellColorFromInt(originalImage.getRGB(i, j), PixelConverter.RED_CHANNEL));
            }
        }

    }

    @Test
    public void getRecomposedDitheredImageTest() throws IOException {
        BufferedImage ditheredImage = ImageIO.read(Resources.getResource(FULL_COLOR_SCALE_IMAGE));
        DitheredImageDifference difference = imageRecomposerSupport.
                getDitheredImageDifferences(originalImage, ditheredImage);
        BufferedImage recomposedImage = imageRecomposerSupport.getRecomposedDitheredImage(ditheredImage, difference);

        for (int i = 0; i < originalImage.getWidth(); i++) {
            for (int j = 0; j < originalImage.getHeight(); j++) {
                Assert.assertEquals(
                        PixelConverter.getChanellColorFromInt(recomposedImage.getRGB(i, j), PixelConverter.RED_CHANNEL),
                        PixelConverter.getChanellColorFromInt(originalImage.getRGB(i, j), PixelConverter.RED_CHANNEL));
                Assert.assertEquals(
                        PixelConverter.getChanellColorFromInt(recomposedImage.getRGB(i,  j), PixelConverter.BLUE_CHANNEL),
                        PixelConverter.getChanellColorFromInt(originalImage.getRGB(i, j), PixelConverter.BLUE_CHANNEL));
                Assert.assertEquals(
                        PixelConverter.getChanellColorFromInt(recomposedImage.getRGB(i, j), PixelConverter.GREEN_CHANNEL),
                        PixelConverter.getChanellColorFromInt(originalImage.getRGB(i, j), PixelConverter.GREEN_CHANNEL));
            }
        }

    }


}
