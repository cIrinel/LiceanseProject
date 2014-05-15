package com.irinel.craiu.liceanse.shareGeneration;

import com.irinel.craiu.liceanse.imageutils.ExpandedPixel;
import com.irinel.craiu.liceanse.imageutils.PixelConverter;
import com.irinel.craiu.liceanse.imageutils.RgbColor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class HonSchemeEncryptionTest {

    private HonSchemeEncryption honSchemeEncryption;
    private static final String MANGENTA_HALFTONED_IMAGE = "halftonedImages/mangentaHalftoned.jpg";
    private static final String CYAN_HALFTONED_IMAGE = "halftonedImages/mangentaHalftoned.jpg";
    private static final String YELLOW_HALFTONED_IMAGE = "halftonedImages/mangentaHalftoned.jpg";
    private int pixel;


    @Before
    public void setUp() throws Exception {
        honSchemeEncryption = new HonSchemeEncryption();
    }


    @Test
    public void testPixelExpands() {
        pixel = PixelConverter.getIntColorFromRGB(0, 0, 255);
        ExpandedPixel expandedPixel = honSchemeEncryption.getExpandedPixelByChannel(pixel, PixelConverter.BLUE_CHANNEL);
        Assert.assertEquals(expandedPixel != null, true);
    }

    @Test
    public void testsecondSharePixelExpandsCorrectly() {
        int pixel = PixelConverter.getIntColorFromRGB(255, 255, 0);
        ExpandedPixel expandedPixel = honSchemeEncryption.getExpandedPixelByChannel(pixel, PixelConverter.BLUE_CHANNEL);
        int[][] firstExpandedPixel = expandedPixel.getFirstShareExpandedPixel();

        Assert.assertEquals(firstExpandedPixel[0][0], firstExpandedPixel[1][1]);
        Assert.assertEquals(firstExpandedPixel[0][1], firstExpandedPixel[1][0]);
        Assert.assertNotSame(firstExpandedPixel[0][0], firstExpandedPixel[0][1]);
        Assert.assertNotSame(firstExpandedPixel[0][1], firstExpandedPixel[1][1]);
    }

    @Test
    public void testSecondSharePixelExpandsCorrectly() {
        int pixel = PixelConverter.getIntColorFromRGB(255, 255, 0);
        ExpandedPixel expandedPixel = honSchemeEncryption.getExpandedPixelByChannel(pixel, PixelConverter.BLUE_CHANNEL);
        int[][] secondExpandedPixel = expandedPixel.getSecondShareExpandedPixel();

        Assert.assertEquals(secondExpandedPixel[0][0], secondExpandedPixel[1][1]);
        Assert.assertEquals(secondExpandedPixel[0][1], secondExpandedPixel[1][0]);
        Assert.assertNotSame(secondExpandedPixel[0][0], secondExpandedPixel[0][1]);
        Assert.assertNotSame(secondExpandedPixel[0][1], secondExpandedPixel[1][1]);
    }

    @Test
    public void testWhitePixelExpandsCorrectly() {
        int pixel = PixelConverter.getIntColorFromRGB(255, 255, 255);
        ExpandedPixel expandedPixel = honSchemeEncryption.getExpandedPixelByChannel(pixel, PixelConverter.BLUE_CHANNEL);
        Assert.assertTrue(expandedPixel.getFirstShareExpandedPixel() == expandedPixel.getSecondShareExpandedPixel());
    }

    @Test
    public void testColorPixelExpandsCorrectly() {
        int pixel = PixelConverter.getIntColorFromRGB(255, 255, 0);
        ExpandedPixel expandedPixel = honSchemeEncryption.getExpandedPixelByChannel(pixel, PixelConverter.BLUE_CHANNEL);
        Assert.assertTrue(expandedPixel.getFirstShareExpandedPixel() != expandedPixel.getSecondShareExpandedPixel());
    }

    @Test
    public void testGetExpandedPixel() {
        int pixel = PixelConverter.getIntColorFromRGB(0, 0, 255);
        ExpandedPixel expandedPixel = honSchemeEncryption.getExpandedPixel(pixel);
        int[][] recomposedPixelBlock = new int[2][2];
        RgbColor rgbColor1;
        RgbColor rgbColor2;

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                rgbColor1 = PixelConverter.getRGBColorFromInt(expandedPixel.getFirstShareExpandedPixel()[i][j]);
                rgbColor2 = PixelConverter.getRGBColorFromInt(expandedPixel.getSecondShareExpandedPixel()[i][j]);

                recomposedPixelBlock[i][j] = PixelConverter.getIntColorFromRGB(
                        Math.max(rgbColor1.getRed(), rgbColor2.getRed()),
                        Math.max(rgbColor1.getGreen(), rgbColor2.getGreen()),
                        Math.max(rgbColor1.getBlue(), rgbColor2.getBlue())
                );
            }
        }

        Assert.assertEquals(recomposedPixelBlock[0][1], recomposedPixelBlock[1][0]);
        Assert.assertEquals(recomposedPixelBlock[0][0], recomposedPixelBlock[1][1]);
        Assert.assertEquals(PixelConverter.getRGBColorFromInt(recomposedPixelBlock[0][0]), new RgbColor(255, 255, 0));
        Assert.assertEquals(PixelConverter.getRGBColorFromInt(recomposedPixelBlock[1][1]), new RgbColor(255, 255, 0));
        Assert.assertEquals(PixelConverter.getRGBColorFromInt(recomposedPixelBlock[0][1]), new RgbColor(255, 255, 255));
        Assert.assertEquals(PixelConverter.getRGBColorFromInt(recomposedPixelBlock[1][0]), new RgbColor(255, 255, 255));

    }

}
