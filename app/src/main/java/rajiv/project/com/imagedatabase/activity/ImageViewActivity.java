package rajiv.project.com.imagedatabase.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


import rajiv.project.com.imagedatabase.R;
import rajiv.project.com.imagedatabase.adapter.DBAdapter;
import rajiv.project.com.imagedatabase.adapter.GridImageAdapter;
import rajiv.project.com.imagedatabase.pojo.Image;
import rajiv.project.com.imagedatabase.util.Communicator;

public class ImageViewActivity extends AppCompatActivity implements Communicator {

    private DBAdapter dbAdapter;
    private List<Image> imageList = new ArrayList<>();
    private RecyclerView recyclerView;
    private GridImageAdapter gridImageAdapter;
    private LinearLayout noDataLayout;

    ActivityInterface activityInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        init();
        getImages();
    }

    private void init() {

        recyclerView = (RecyclerView) findViewById(R.id.activity_image_view_recyclerView);
        noDataLayout = (LinearLayout) findViewById(R.id.noDataLayout);
    }

    private void setRecyclerView() {

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(gridImageAdapter);
        gridImageAdapter.notifyDataSetChanged();

    }


    private void getImages() {

        dbAdapter = DBAdapter.getAdapter(this);
        imageList = dbAdapter.getAllImagesList();
        if (imageList.size() > 0) {

            gridImageAdapter = new GridImageAdapter(this, imageList);
            activityInterface = gridImageAdapter;
            setRecyclerView();

        } else {

            recyclerView.setVisibility(View.GONE);
            noDataLayout.setVisibility(View.VISIBLE);

        }

    }


    @Override
    public void fragmentToIntent(String caption, int position) {
        activityInterface.activityToAdapter(caption, position);
    }

    public interface ActivityInterface {

        public void activityToAdapter(String caption, int position);
    }
}
