package com.irinel.craiu.liceanse;


import com.irinel.craiu.liceanse.halftone.impl.ColorDecomposer;
import com.irinel.craiu.liceanse.halftone.impl.FloydSteinbergDitherer;
import com.irinel.craiu.liceanse.imageutils.DecomposedCMYImage;
import com.irinel.craiu.liceanse.imageutils.EncryptedImage;
import com.irinel.craiu.liceanse.imageutils.PixelConverter;
import com.irinel.craiu.liceanse.shareGeneration.HonSchemeEncryption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VCrypt {

    private static final Logger LOG = LoggerFactory.getLogger(VCrypt.class);
    private ColorDecomposer colorDecomposer;
    private FloydSteinbergDitherer floydSteinbergDitherer;
    private HonSchemeEncryption honSchemeEncryption;

    public VCrypt() {
        colorDecomposer = new ColorDecomposer();
        floydSteinbergDitherer = new FloydSteinbergDitherer();
        honSchemeEncryption = new HonSchemeEncryption();
    }

    public void encryptImage(BufferedImage bufferedImage) throws IOException {

        LOG.info("Starting encyption process", VCrypt.class);
        LOG.info("Decomposing image to cmy components", VCrypt.class);
        DecomposedCMYImage decomposedCMYImage = colorDecomposer.getDecomposedCMYImages(bufferedImage);

        LOG.info("Starting dithering process with decomposed images", VCrypt.class);
        DecomposedCMYImage ditheredCMYImage = new DecomposedCMYImage();
        ditheredCMYImage.setCyanImage(floydSteinbergDitherer.getDitheredImage(decomposedCMYImage.getCyanImage(),
                PixelConverter.RED_CHANNEL));
        LOG.info("Dithered cyan image", VCrypt.class);
        ditheredCMYImage.setMangentaImage(floydSteinbergDitherer.getDitheredImage(decomposedCMYImage.getMangentaImage(),
                PixelConverter.GREEN_CHANNEL));
        LOG.info("Dithered mangenta image", VCrypt.class);
        ditheredCMYImage.setYellowImage(floydSteinbergDitherer.getDitheredImage(decomposedCMYImage.getYellowImage(),
                PixelConverter.BLUE_CHANNEL));
        LOG.info("Dithered yellow image", VCrypt.class);

        LOG.info("Starting encryption", VCrypt.class);
        EncryptedImage encryptedImage = honSchemeEncryption.getEncryptedImage(ditheredCMYImage);
        LOG.info("Generated shares for image", VCrypt.class);

        ImageIO.write(encryptedImage.getFirstShare(), "gif", new File("firstShare.gif"));
        ImageIO.write(encryptedImage.getSecondShare(), "gif", new File("secondShare.gif"));

    }
}
