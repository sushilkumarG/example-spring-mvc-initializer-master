package com.example.image;

import java.awt.image.BufferedImage;

public class AllImagesHolder {

    private VerticalImagesHolder[] verticalImagesHolders = new VerticalImagesHolder[Config.NUMBER_OF_HORIZONTAL_MOTIONs];
    private BufferedImage          mergedImage;

    public AllImagesHolder(VerticalImagesHolder[] verticalImagesHolders) {
        this.verticalImagesHolders = verticalImagesHolders;
        initializeAndStitch();
    }

    private void initializeAndStitch() {
        BufferedImage tempOutput = null;
        if (verticalImagesHolders.length == 1) {
            this.mergedImage = verticalImagesHolders[0].getMergedImage();
            return;
        }
        for (int indx = 1; indx < verticalImagesHolders.length; indx++) {
            if (indx == 1) {
                if (verticalImagesHolders.length == 1) {
                    tempOutput = verticalImagesHolders[0].getMergedImage();
                }
                else {
                    tempOutput = StitchingProcessor.stitch(
                            verticalImagesHolders[0].getMergedImage(),
                            verticalImagesHolders[1].getMergedImage());
                }

            }
            else {
                tempOutput = StitchingProcessor.stitch(tempOutput, verticalImagesHolders[indx].getMergedImage());
            }
        }
        this.mergedImage = tempOutput;
    }

    public BufferedImage getMergedImage() {
        return mergedImage;
    }
}
