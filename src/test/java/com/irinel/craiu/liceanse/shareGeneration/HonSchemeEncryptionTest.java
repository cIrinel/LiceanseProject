package com.irinel.craiu.liceanse.shareGeneration;

import com.irinel.craiu.liceanse.imageutils.ExpandedPixel;
import com.irinel.craiu.liceanse.imageutils.PixelConverter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;


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
        ExpandedPixel expandedPixel = honSchemeEncryption.getExpandedPixel(pixel, PixelConverter.BLUE_CHANNEL);
        Assert.assertEquals(expandedPixel != null, true);
    }

    @Test
    public void testsecondSharePixelExpandsCorrectly() {
        int pixel = PixelConverter.getIntColorFromRGB(255, 255, 0);
        ExpandedPixel expandedPixel = honSchemeEncryption.getExpandedPixel(pixel, PixelConverter.BLUE_CHANNEL);
        int[][] firstExpandedPixel = expandedPixel.getFirstShareExpandedPixel();

        Assert.assertEquals(firstExpandedPixel[0][0], firstExpandedPixel[1][1]);
        Assert.assertEquals(firstExpandedPixel[0][1], firstExpandedPixel[1][0]);
        Assert.assertNotSame(firstExpandedPixel[0][0], firstExpandedPixel[0][1]);
        Assert.assertNotSame(firstExpandedPixel[0][1], firstExpandedPixel[1][1]);
    }

    @Test
    public void testSecondSharePixelExpandsCorrectly() {
        int pixel = PixelConverter.getIntColorFromRGB(255, 255, 0);
        ExpandedPixel expandedPixel = honSchemeEncryption.getExpandedPixel(pixel, PixelConverter.BLUE_CHANNEL);
        int[][] secondExpandedPixel = expandedPixel.getSecondShareExpandedPixel();

        Assert.assertEquals(secondExpandedPixel[0][0], secondExpandedPixel[1][1]);
        Assert.assertEquals(secondExpandedPixel[0][1], secondExpandedPixel[1][0]);
        Assert.assertNotSame(secondExpandedPixel[0][0], secondExpandedPixel[0][1]);
        Assert.assertNotSame(secondExpandedPixel[0][1], secondExpandedPixel[1][1]);
    }

    @Test
    public void testWhitePixelExpandsCorrectly() {
        int pixel = PixelConverter.getIntColorFromRGB(255, 255, 255);
        ExpandedPixel expandedPixel = honSchemeEncryption.getExpandedPixel(pixel, PixelConverter.BLUE_CHANNEL);
        Assert.assertTrue(expandedPixel.getFirstShareExpandedPixel() == expandedPixel.getSecondShareExpandedPixel());
    }

    @Test
    public void testColorPixelExpandsCorrectly() {
        int pixel = PixelConverter.getIntColorFromRGB(255, 255, 0);
        ExpandedPixel expandedPixel = honSchemeEncryption.getExpandedPixel(pixel, PixelConverter.BLUE_CHANNEL);
        Assert.assertTrue(expandedPixel.getFirstShareExpandedPixel() != expandedPixel.getSecondShareExpandedPixel());
    }

}
