package com.irinel.craiu.liceanse.shareGeneration;


import com.irinel.craiu.liceanse.imageutils.DecomposedCMYImage;
import com.irinel.craiu.liceanse.imageutils.EncryptedImage;
import com.irinel.craiu.liceanse.imageutils.ExpandedPixel;
import com.irinel.craiu.liceanse.imageutils.PixelConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.plaf.synth.ColorType;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class HonSchemeEncryption {

    Logger LOG = LoggerFactory.getLogger(HonSchemeEncryption.class);

    public EncryptedImage getEncryptedImage(DecomposedCMYImage decomposedImage) {
        int width = decomposedImage.getCyanImage().getWidth();
        int height = decomposedImage.getCyanImage().getHeight();
        BufferedImage firstShare = new BufferedImage(width * 2, height * 2, ColorSpace.TYPE_RGB);
        BufferedImage secondShare = new BufferedImage(width * 2, height * 2, ColorSpace.TYPE_RGB);

        BufferedImage cyanImage = decomposedImage.getCyanImage();
        BufferedImage mangentaImage = decomposedImage.getMangentaImage();
        BufferedImage yellowImage = decomposedImage.getYellowImage();

        ExpandedPixel expandedPixel;
        EncryptedImage encryptedImage = new EncryptedImage();

        for (int i = 0; i < width * 2; i = i + 2) {
            for (int j = 0; j < height * 2; j = j + 2) {

                expandedPixel = getExpandedPixel(
                        cyanImage.getRGB(i / 2, j / 2),
                        mangentaImage.getRGB(i / 2, j / 2),
                        yellowImage.getRGB(i / 2, j / 2));

                firstShare.setRGB(i, j, expandedPixel.getFirstShareExpandedPixel()[0][0]);
                firstShare.setRGB(i, j + 1, expandedPixel.getFirstShareExpandedPixel()[0][1]);
                firstShare.setRGB(i + 1, j, expandedPixel.getFirstShareExpandedPixel()[1][0]);
                firstShare.setRGB(i + 1, j + 1, expandedPixel.getFirstShareExpandedPixel()[1][1]);

                secondShare.setRGB(i, j, expandedPixel.getSecondShareExpandedPixel()[0][0]);
                secondShare.setRGB(i, j + 1, expandedPixel.getSecondShareExpandedPixel()[0][1]);
                secondShare.setRGB(i + 1, j, expandedPixel.getSecondShareExpandedPixel()[1][0]);
                secondShare.setRGB(i + 1, j + 1, expandedPixel.getSecondShareExpandedPixel()[1][1]);
            }
        }

        encryptedImage.setFirstShare(firstShare);
        encryptedImage.setSecondShare(secondShare);

        return encryptedImage;
    }

    public ExpandedPixel getExpandedPixel(int cyanPixel, int mangentaPixel, int yellowPixel) {
        ExpandedPixel redChannelExpandedPixel;
        ExpandedPixel blueChannelExpandedPixel;
        ExpandedPixel greenChannelExpandedPixel;
        ExpandedPixel expandedPixel = new ExpandedPixel();

        redChannelExpandedPixel = getExpandedPixelByChannel(cyanPixel, PixelConverter.RED_CHANNEL);
        greenChannelExpandedPixel = getExpandedPixelByChannel(mangentaPixel, PixelConverter.GREEN_CHANNEL);
        blueChannelExpandedPixel = getExpandedPixelByChannel(yellowPixel, PixelConverter.BLUE_CHANNEL);

        int[][] firstPixelShare = new int[2][2];
        int[][] secondPixelShare = new int[2][2];

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                firstPixelShare[i][j] = PixelConverter.getIntColorFromRGB(
                        redChannelExpandedPixel.getFirstShareExpandedPixel()[i][j],
                        greenChannelExpandedPixel.getFirstShareExpandedPixel()[i][j],
                        blueChannelExpandedPixel.getFirstShareExpandedPixel()[i][j]
                );
                secondPixelShare[i][j] = PixelConverter.getIntColorFromRGB(
                        redChannelExpandedPixel.getSecondShareExpandedPixel()[i][j],
                        greenChannelExpandedPixel.getSecondShareExpandedPixel()[i][j],
                        blueChannelExpandedPixel.getSecondShareExpandedPixel()[i][j]
                );
            }
        }

        expandedPixel.setFirstShareExpandedPixel(firstPixelShare);
        expandedPixel.setSecondShareExpandedPixel(secondPixelShare);

        return expandedPixel;
    }

    //TODO:ExpandedPixel may need refactored. all the program uses rgb values for the representation of pixel this class uses the channel color values...INCONSISTENCY!!!
    //TODO: method also randomises block arangement. rename or split
    public ExpandedPixel getExpandedPixelByChannel(int pixel, int colorChannel) {
        ExpandedPixel expandedPixel = new ExpandedPixel();
        int color = PixelConverter.getChanellColorFromInt(pixel, colorChannel);
        int[][] expandedPixelAux = new int[2][2];

        expandedPixelAux[0][0] = 255;
        expandedPixelAux[0][1] = 0;
        expandedPixelAux[1][0] = 0;
        expandedPixelAux[1][1] = 255;
        // System.out.println(color+"-"+colorChannel);
        if (color == 255) {
            if (getRandomDecision()) {
                expandedPixel.setFirstShareExpandedPixel(expandedPixelAux);
                expandedPixel.setSecondShareExpandedPixel(getReversedPixel());
            } else {
                expandedPixel.setFirstShareExpandedPixel(getReversedPixel());
                expandedPixel.setSecondShareExpandedPixel(expandedPixelAux);
            }

        } else if (color == 0) {
            if (getRandomDecision()) {
                expandedPixel.setFirstShareExpandedPixel(expandedPixelAux);
                expandedPixel.setSecondShareExpandedPixel(expandedPixelAux);
            } else {
                expandedPixel.setFirstShareExpandedPixel(getReversedPixel());
                expandedPixel.setSecondShareExpandedPixel(getReversedPixel());
            }

        } else {
            LOG.error(String.format("Something went wrong, found pixel with color Channel value = %s and color = %s", colorChannel, color));
        }


        return expandedPixel;
    }

    public boolean getRandomDecision() {
        return (Math.random() < 0.5) ? false : true;
    }

    public int[][] getReversedPixel() {
        int[][] reversedPixel = {{0, 255}, {255, 0}};
        return reversedPixel;
    }


}
