package image;

public class ImageEditor {
    /**
     *
     * @param image
     */
    public static void imagePadding(Image image){
        //Calculate the correct dimensions of the image
        int newWidth = nearestPowerOf2(image.getWidth());
        int newHeight = nearestPowerOf2(image.getHeight());

    }
    public static void parse(Image image){

    }

    private static int nearestPowerOf2(int value) {
        return (int) Math.pow(2, Math.ceil(Math.log(value) / Math.log(2)));
    }
}
