package rajiv.project.com.imagedatabase.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rajiv.project.com.imagedatabase.util.Constant;
import rajiv.project.com.imagedatabase.adapter.DBAdapter;
import rajiv.project.com.imagedatabase.R;

public class MainActivity extends AppCompatActivity {


    private static final int CODE_PICKER = 2000;
    private Button saveButton, viewButton;
    private DBAdapter dbAdapter;
    private ArrayList<Image> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void init() {

        saveButton = (Button) findViewById(R.id.saveImage_button);
        viewButton = (Button) findViewById(R.id.viewImage_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveImage();
            }
        });
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ImageViewActivity.class));
            }
        });

        dbAdapter = DBAdapter.getAdapter(this);
    }


    private void saveImage() {

        ImagePicker imagePicker = ImagePicker.create(this)
                .theme(R.style.ImagePickerTheme)
                .returnAfterFirst(false)
                .folderMode(true)
                .folderTitle("Folder")
                .imageTitle("Tap to select");

        imagePicker.multi()
                .limit(10)
                .showCamera(false)
                .imageDirectory("Camera")
                .imageFullDirectory(Environment.getExternalStorageDirectory().getPath()) // can be full path
                .origin(images)
                .start(CODE_PICKER);

    }


    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (requestCode == CODE_PICKER && resultCode == RESULT_OK && data != null) {
            images = (ArrayList<Image>) ImagePicker.getImages(data);
            printImages(images);
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void printImages(List<Image> images) {
        if (images == null) return;
        boolean isAdded = false;
        for (int i = 0, l = images.size(); i < l; i++) {

            File oldPhoto = new File(images.get(i).getPath());
            String oldPhotoName = oldPhoto.getName();
            String oldPhotoPath = oldPhoto.getPath().substring(0, oldPhoto.getPath().lastIndexOf("/"));

            String newPhotoPath = oldPhotoPath + "/" + Constant.HIDDEN_NAME + oldPhotoName;
            File newPhoto = new File(newPhotoPath);
            oldPhoto.renameTo(newPhoto);


            Bitmap bitmap = BitmapFactory.decodeFile(newPhoto.getPath());
            isAdded = dbAdapter.addImage(newPhoto.getName(), newPhoto.getPath(), String.valueOf(bitmap.getHeight()), String.valueOf(bitmap.getWidth()), String.format("%d X %d", bitmap.getHeight(), bitmap.getWidth()));
        }
        if (isAdded) {
            Toast.makeText(this, "Images Saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Unable to save image", Toast.LENGTH_SHORT).show();

        }
    }

}
