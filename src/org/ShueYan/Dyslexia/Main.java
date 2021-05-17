package org.ShueYan.Dyslexia;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

//    static int widthPerWord = 727;
//    static int heightPerWord = 920;
    static final int BORDER_X = 2;

    static final String inputFolderPath = "/Users/wayne/Documents/CollectedSample/Cropped/";
    static final String outputFolderPath = "/Users/wayne/Documents/CollectedSample/Cropped_Character/";

    public static void main(String[] args) {

        createOutputFolderIfNotExist(outputFolderPath);
        cropDataFolderToSingleWord();
    }

    private static void createOutputFolderIfNotExist(String outputFolderPath) {
        File file = new File(outputFolderPath);
        file.mkdirs();
    }

    private static void cropDataFolderToSingleWord() {
        File dir = new File(inputFolderPath);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File file : directoryListing) {
                try {
                    cropWords(file);
                    log("Finished cropping file : " + file.getAbsolutePath());
                } catch (IOException io) {
                    log("Not an image : " + file.getAbsolutePath());
                }
            }
        } else {
            log("Invalid input folder path : " + inputFolderPath);
        }
    }

    private static void cropWords(File imageFile) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(imageFile);
        String [] tokens = splitFileExtensions(imageFile.getName());

        if(bufferedImage == null){
            log("Skip non image file :" + imageFile.getName());
            return;
        }

        String filename = tokens[0];
        String extension = tokens[1];

        int startX = BORDER_X;
        int startY = 1;
        int index = 1;

        int nWidth = bufferedImage.getWidth()/3-1;
        int nHeight = bufferedImage.getHeight()/3-1;
        for (; index <= 3; index++) {
            BufferedImage croppedWord = cropImage(bufferedImage, startX, startY, nWidth, nHeight);
            saveImage(croppedWord, filename, extension, index);
            startX += nWidth;
        }


        startX = 2;
        startY += nHeight;
        for (; index <= 6; index++) {
            BufferedImage croppedWord = cropImage(bufferedImage, startX, startY, nWidth, nHeight);
            saveImage(croppedWord, filename, extension, index);
            startX += nWidth;
        }

        startX = 1;
        startY += nHeight;
        for (; index <= 9; index++) {
            BufferedImage croppedWord = cropImage(bufferedImage, startX, startY, nWidth, nHeight);
            saveImage(croppedWord, filename, extension, index);
            startX += nWidth;
        }
    }

    private static BufferedImage cropImage(BufferedImage bufferedImage, int x, int y, int width, int height) {
        BufferedImage croppedImage = bufferedImage.getSubimage(x, y, width, height);
        return croppedImage;
    }

    private static void saveImage(BufferedImage croppedWord, String filename, String extension, int index) throws IOException {
        File pathFile = new File(outputFolderPath + filename + "-C" + index + "." + extension);
        ImageIO.write(croppedWord, "png", pathFile);
    }

    /**
     *
     * @param fileName
     * @return [0]=filename, [1]=extension
     */
    private static String[] splitFileExtensions(String fileName){
        return fileName.split("\\.(?=[^\\.]+$)");
    }

    //TODO
    private static void log(String msg) {
        System.out.println(msg);
    }
}

