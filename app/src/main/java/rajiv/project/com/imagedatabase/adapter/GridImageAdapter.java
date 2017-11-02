package rajiv.project.com.imagedatabase.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rajiv.project.com.imagedatabase.R;
import rajiv.project.com.imagedatabase.activity.ImageViewActivity;
import rajiv.project.com.imagedatabase.fragment.DialogFragment;
import rajiv.project.com.imagedatabase.pojo.Image;

/**
 * Created by SUJAN on 31-Oct-17.
 */

public class GridImageAdapter extends RecyclerView.Adapter<GridImageAdapter.GridViewHolder> implements ImageViewActivity.ActivityInterface {

    private DBAdapter dbAdapter;
    private Context mContext;
    private List<Image> imagesList = Collections.EMPTY_LIST;
    private LayoutInflater inflater;
    private List<GridViewHolder> holderList = new ArrayList<>();


    public GridImageAdapter(Context mContext, List<Image> imagesList) {
        this.mContext = mContext;
        dbAdapter = DBAdapter.getAdapter(mContext);
        this.imagesList = imagesList;
        this.inflater = LayoutInflater.from(mContext);
    }

    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_grid, parent, false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, final int position) {
        final Image finalImage = imagesList.get(position);

        holderList.add(holder);
        if (finalImage.getCaption() == null) {

            holder.nameTextView.setText("No Caption");
        } else {

            holder.nameTextView.setText(finalImage.getCaption());
        }

        holder.sizeTextView.setText(finalImage.getSize());

        File file = new File(finalImage.getPath());
        Picasso.with(mContext).load(file).into(holder.imageView);

        holder.editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putInt("position", position);

                DialogFragment dialogFragment = new DialogFragment();
                dialogFragment.setArguments(bundle);
                dialogFragment.setCancelable(false);
                dialogFragment.show(((ImageViewActivity) mContext).getSupportFragmentManager(), "FTAG");
            }
        });

    }


    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    @Override
    public void activityToAdapter(String caption, int position) {
        holderList.get(position).nameTextView.setText(caption);
        dbAdapter.addCaption(imagesList.get(position).getId(), caption);
        imagesList.get(position).setCaption(caption);
    }


    public class GridViewHolder extends RecyclerView.ViewHolder {

        View view;
        ImageView imageView, editImageView;
        TextView nameTextView, sizeTextView;

        public GridViewHolder(View itemView) {
            super(itemView);

            view = itemView;

            imageView = (ImageView) view.findViewById(R.id.adapter_imageView);
            editImageView = (ImageView) view.findViewById(R.id.adapter_edit_imageView);
            nameTextView = (TextView) view.findViewById(R.id.adapter_image_name);
            sizeTextView = (TextView) view.findViewById(R.id.adapter_image_size);
        }

    }


}
