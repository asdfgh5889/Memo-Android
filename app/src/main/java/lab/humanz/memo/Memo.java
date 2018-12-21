package lab.humanz.memo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Model for memo
//Every insertion must be used by its own functions
public class Memo implements Serializable
{
    public static final int AUDIO = 0;
    public static final int TEXT = 1;
    public static final int IMAGE = 2;

    public static Integer imageId = 0;
    public static File imageDir;
    private static int compressedImageSize = 1000;

    /**
     * Defines order and type of content of memo
     */
    public List<Integer> contentTypes;

    /**
     * Content holders for text and image
     */
    public List<String> textContents;

    /**
     * Image content ids, ids also are image file names
     */
    public List<Integer> imageContents;

    /**
     * Bitmap images are cached here
     */
    public List<Bitmap> imageBitmapCache;

    public Memo()
    {
        this.contentTypes = new ArrayList<>();
    }

    public void insertTextContent(String text)
    {
        if (this.textContents == null)
            this.textContents = new ArrayList<>();
        this.contentTypes.add(TEXT);
        this.textContents.add(text);
    }

    public void removeContentAt(int position) {
        switch (contentTypes.get(position)) {
            case TEXT: {
                this.textContents.remove(this.getPositionFor(position));
            } break;
            case IMAGE: {
                this.imageContents.remove(this.getPositionFor(position));
                this.imageBitmapCache.remove(this.getPositionFor(position));
            } break;
        }
        contentTypes.remove(position);
    }

    public void removeLastContent() {
        switch (contentTypes.get(contentTypes.size() - 1)) {
            case TEXT: this.textContents.remove(this.textContents.size() - 1); break;
            case IMAGE: {
                this.imageContents.remove(this.imageContents.size() - 1);
                this.imageBitmapCache.remove(this.imageBitmapCache.size() - 1);
            } break;
        }
        this.contentTypes.remove(this.contentTypes.size() - 1);
    }

    /**
     * Inserts image to model
     * @param image
     */
    public void insertImageContent(Bitmap image)
    {
        if (this.imageContents == null)
            this.imageContents = new ArrayList<>();
        if(this.imageBitmapCache == null)
            this.imageBitmapCache = new ArrayList<>();

        Bitmap compressed = this.compressImage(image);
        this.saveImage(compressed, imageId.toString());
        this.contentTypes.add(IMAGE);
        this.imageContents.add(imageId++);
        this.imageBitmapCache.add(compressed);
    }

    /**
     * Return relative position for given item position.
     * @param itemPosition
     * @return relative position for given type
     */
    public int getPositionFor(Integer itemPosition){
        int contentPosition = 0;
        for (int i = 0; i < itemPosition; i++)
            if (contentTypes.get(itemPosition) == contentTypes.get(i))
                contentPosition++;
        return contentPosition;
    }

    /**
     * Saves given image to a file with given file name.
     * Here file name must be id of image file.
     * @param image
     * @param imageName image id.
     */
    private void saveImage(Bitmap image, String imageName) {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 80, byteArray);
        File imageFile = new File(imageDir, this.hashBitmap(byteArray.toByteArray()));
        if (imageFile.exists()) {
            try (FileOutputStream out = new FileOutputStream(imageFile)) {
                byteArray.writeTo(out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Compresses selected image to max of compressedImageSize x compressedImageSize
     * @param imageBitmap image to compress
     * @return Compressed image
     */
    private Bitmap compressImage(Bitmap imageBitmap) {
        Bitmap compressed;
        if (imageBitmap.getWidth() > imageBitmap.getHeight()) {
            int height = compressedImageSize * imageBitmap.getHeight() / imageBitmap.getWidth();

            compressed = Bitmap.createScaledBitmap(imageBitmap, compressedImageSize, height, true);
        } else {
            int width = compressedImageSize * imageBitmap.getWidth() / imageBitmap.getHeight();
            compressed = Bitmap.createScaledBitmap(imageBitmap, width, compressedImageSize, true);
        }
        return  compressed;
    }

    /**
     * Creates md5 hash of bitmap image
     * @param imageByte
     * @return md5 hash string
     */
    private String hashBitmap(byte[] imageByte) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] messageDigest = digest.digest(imageByte);
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();
        } catch (Exception e) {
            return "";
        }
    }
}
