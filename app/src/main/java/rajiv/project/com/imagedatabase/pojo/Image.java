package rajiv.project.com.imagedatabase.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SUJAN on 31-Oct-17.
 */

public class Image implements Parcelable {

    private String id;
    private String name;
    private String path;
    private String height;
    private String width;
    private String size;
    private String caption;

    public Image() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", height='" + height + '\'' +
                ", width='" + width + '\'' +
                ", size='" + size + '\'' +
                ", caption='" + caption + '\'' +
                '}';
    }

    protected Image(Parcel in) {
        id = in.readString();
        name = in.readString();
        path = in.readString();
        height = in.readString();
        width = in.readString();
        size = in.readString();
        caption = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(path);
        dest.writeString(height);
        dest.writeString(width);
        dest.writeString(size);
        dest.writeString(caption);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}