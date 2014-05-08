package com.irinel.craiu.liceanse.shareGeneration;

import com.irinel.craiu.liceanse.imageutils.ExpandedPixel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class HonSchemeEncryptionTest {

    private HonSchemeEncryption honSchemeEncryption;
    public static final String MANGENTA_HALFTONED_IMAGE = "halftonedImages/mangentaHalftoned.jpg";
    public static final String CYAN_HALFTONED_IMAGE = "halftonedImages/mangentaHalftoned.jpg";
    public static final String YELLOW_HALFTONED_IMAGE = "halftonedImages/mangentaHalftoned.jpg";


    @Before
    public void setUp() throws Exception {
        honSchemeEncryption = new HonSchemeEncryption();
    }


    @Test
    public void testPixelExpands() {
        int pixel = 2;
        ExpandedPixel expandedPixel = honSchemeEncryption.getExpandedPixel(pixel, 0);
        Assert.assertEquals(expandedPixel != null, true);
    }

    @Test
    public void testsecondSharePixelExpandsCorrectly() {
        int pixel = 2;
        ExpandedPixel expandedPixel = honSchemeEncryption.getExpandedPixel(pixel, 0);
        int[][] firstExpandedPixel = expandedPixel.getFirstShareExpandedPixel();

        Assert.assertEquals(firstExpandedPixel[0][0], firstExpandedPixel[1][1]);
        Assert.assertEquals(firstExpandedPixel[0][1], firstExpandedPixel[1][0]);
        Assert.assertNotSame(firstExpandedPixel[0][0], firstExpandedPixel[0][1]);
        Assert.assertNotSame(firstExpandedPixel[0][1], firstExpandedPixel[1][1]);
    }

    @Test
    public void testSecondSharePixelExpandsCorrectly() {
        int pixel = 2;
        ExpandedPixel expandedPixel = honSchemeEncryption.getExpandedPixel(pixel, 0);
        int[][] secondExpandedPixel = expandedPixel.getSecondShareExpandedPixel();

        Assert.assertEquals(secondExpandedPixel[0][0], secondExpandedPixel[1][1]);
        Assert.assertEquals(secondExpandedPixel[0][1], secondExpandedPixel[1][0]);
        Assert.assertNotSame(secondExpandedPixel[0][0], secondExpandedPixel[0][1]);
        Assert.assertNotSame(secondExpandedPixel[0][1], secondExpandedPixel[1][1]);
    }
}
