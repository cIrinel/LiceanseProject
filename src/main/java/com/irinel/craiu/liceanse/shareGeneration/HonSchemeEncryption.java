package com.irinel.craiu.liceanse.shareGeneration;


import com.irinel.craiu.liceanse.imageutils.DecomposedCMYImage;
import com.irinel.craiu.liceanse.imageutils.EncryptedImage;
import com.irinel.craiu.liceanse.imageutils.ExpandedPixel;
import com.irinel.craiu.liceanse.imageutils.PixelConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class HonSchemeEncryption {

    Logger LOG = LoggerFactory.getLogger(HonSchemeEncryption.class);

    public EncryptedImage getEncryptedImage(DecomposedCMYImage decomposedImage) {
        int width = decomposedImage.getCyanImage().getWidth();
        int height = decomposedImage.getCyanImage().getHeight();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

            }
        }

        return new EncryptedImage();
    }

    public ExpandedPixel getExpandedPixel(int pixel) {
        ExpandedPixel redChannelExpandedPixel, blueChannelExpandedPixel, greenChannelExpandedPixel;
        ExpandedPixel expandedPixel = new ExpandedPixel();
        redChannelExpandedPixel = getExpandedPixelByChannel(pixel, PixelConverter.RED_CHANNEL);
        blueChannelExpandedPixel = getExpandedPixelByChannel(pixel, PixelConverter.BLUE_CHANNEL);
        greenChannelExpandedPixel = getExpandedPixelByChannel(pixel, PixelConverter.GREEN_CHANNEL);

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

    public ExpandedPixel getExpandedPixelByChannel(int pixel, int colorChannel) {
        ExpandedPixel expandedPixel = new ExpandedPixel();
        int color = PixelConverter.getChanellColorFromInt(pixel, colorChannel);
        int[][] expandedPixelAux = new int[2][2];

        expandedPixelAux[0][0] = 0;
        expandedPixelAux[0][1] = 255;
        expandedPixelAux[1][0] = 255;
        expandedPixelAux[1][1] = 0;

        if (color == 0) {
            expandedPixel.setFirstShareExpandedPixel(expandedPixelAux);
            expandedPixel.setSecondShareExpandedPixel(getReversedPixel());
        } else if (color == 255) {
            expandedPixel.setFirstShareExpandedPixel(expandedPixelAux);
            expandedPixel.setSecondShareExpandedPixel(expandedPixelAux);
        } else {
            LOG.error(String.format("Something went wrong, found pixel with color Channel value = %s", colorChannel));
        }

        if (getRandomDecision()) {
            expandedPixelAux = expandedPixel.getFirstShareExpandedPixel();
            expandedPixel.setFirstShareExpandedPixel(expandedPixel.getSecondShareExpandedPixel());
            expandedPixel.setSecondShareExpandedPixel(expandedPixelAux);
        }

        return expandedPixel;
    }


    public boolean getRandomDecision() {
        return (Math.random() < 0.5) ? false : true;
    }

    public int[][] getReversedPixel() {
        int[][] reversedPixel = {{255, 0}, {0, 255}};
        return reversedPixel;
    }


}
