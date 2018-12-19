package lab.humanz.memo;

import android.media.Image;

import java.util.ArrayList;
import java.util.List;

//Types of memo content
enum MemoContentType
{
    text,
    image,
    audio
}

//Model for memo
//Every insertion must be used by its own functions
public class Memo
{
    //Defines order and type of content of memo
    public List<MemoContentType> contentTypes;

    //Content holders for text and image
    public List<String> textContents;
    public List<Image> imageContents;

    public Memo()
    {
        this.contentTypes = new ArrayList<>();
    }

    public void insertTextContent(String text)
    {
        if (this.textContents == null)
            this.textContents = new ArrayList<>();

        this.contentTypes.add(MemoContentType.text);
        this.textContents.add(text);
    }

    public void insertImageContent(Image image)
    {
        if (this.imageContents == null)
            this.imageContents = new ArrayList<>();

        this.contentTypes.add(MemoContentType.image);
        this.imageContents.add(image);
    }
}
