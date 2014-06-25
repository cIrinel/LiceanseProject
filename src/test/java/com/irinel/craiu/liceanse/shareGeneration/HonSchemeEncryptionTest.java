package com.irinel.craiu.liceanse.shareGeneration;

import com.google.common.io.Resources;
import com.irinel.craiu.liceanse.imageutils.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


public class HonSchemeEncryptionTest {

    private HonSchemeEncryption honSchemeEncryption;
    private static final String MANGENTA_HALFTONED_IMAGE = "halftonedImages/mangentaHalftoned.jpg";
    private static final String CYAN_HALFTONED_IMAGE = "halftonedImages/cyanHalftoned.jpg";
    private static final String YELLOW_HALFTONED_IMAGE = "halftonedImages/yellowHalftoned.jpg";
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

    //Not a test because getExpandedPixelByChannel outputs randomly
    public void testWhitePixelExpandsCorrectly() {
        int pixel = PixelConverter.getIntColorFromRGB(255, 255, 255);
        ExpandedPixel expandedPixel = honSchemeEncryption.getExpandedPixelByChannel(pixel, PixelConverter.BLUE_CHANNEL);
        Assert.assertTrue(expandedPixel.getFirstShareExpandedPixel() == expandedPixel.getSecondShareExpandedPixel());
    }

    //@Test
    //Cannot be tested because of random behaviour
    public void testColorPixelExpandsCorrectly() {
        int pixel = PixelConverter.getIntColorFromRGB(255, 255, 0);
        ExpandedPixel expandedPixel = honSchemeEncryption.getExpandedPixelByChannel(pixel, PixelConverter.BLUE_CHANNEL);
        Assert.assertTrue(expandedPixel.getFirstShareExpandedPixel() != expandedPixel.getSecondShareExpandedPixel());
    }

    @Test
    public void testGetExpandedPixel() {
        int cyanPixel = PixelConverter.getIntColorFromRGB(0, 255, 255);
        int mangentaPixel = PixelConverter.getIntColorFromRGB(255, 0, 255);
        int yellowPixel = PixelConverter.getIntColorFromRGB(255, 255, 255);

        ExpandedPixel expandedPixel = honSchemeEncryption.getExpandedPixel(cyanPixel, mangentaPixel, yellowPixel);
        Assert.assertEquals(expandedPixel.getFirstShareExpandedPixel()[0][0]
                , expandedPixel.getFirstShareExpandedPixel()[1][1]);
        Assert.assertEquals(expandedPixel.getFirstShareExpandedPixel()[0][1]
                , expandedPixel.getFirstShareExpandedPixel()[1][0]);
    }


    public void testGetEncryptedImage() throws IOException {
        DecomposedCMYImage decomposedCMYImage = new DecomposedCMYImage();
        decomposedCMYImage.setCyanImage(ImageIO.read(Resources.getResource(CYAN_HALFTONED_IMAGE)));
        decomposedCMYImage.setMangentaImage(ImageIO.read(Resources.getResource(MANGENTA_HALFTONED_IMAGE)));
        decomposedCMYImage.setYellowImage(ImageIO.read(Resources.getResource(YELLOW_HALFTONED_IMAGE)));

        //  EncryptedImage encryptedImage = honSchemeEncryption.getEncryptedImage(decomposedCMYImage);
        //   ImageIO.write(encryptedImage.getFirstShare(), "jpg", new File("firstShare.jpg"));
        //   ImageIO.write(encryptedImage.getSecondShare(), "jpg", new File("secondShare.jpg"));
    }


}
