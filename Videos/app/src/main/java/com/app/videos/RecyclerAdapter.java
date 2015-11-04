package com.app.videos;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

class RecyclerAdapter extends RecyclerView.Adapter  {

    private final List<MediaStoreData> data;
    private final int screenWidth;

    private int[] actualDimensions;

    RecyclerAdapter(Context context, List<MediaStoreData> data) {
        this.data = data;

        setHasStableIds(true);

        screenWidth = getWidth(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item, viewGroup, false);
        view.getLayoutParams().width = screenWidth;

        if (actualDimensions == null) {
            view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    if (actualDimensions == null) {
                        actualDimensions = new int[]{view.getWidth(), view.getHeight()};
                    }
                    view.getViewTreeObserver().removeOnPreDrawListener(this);
                    return true;
                }
            });
        }

        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        final MediaStoreData current = data.get(position);

        final ListViewHolder vh = (ListViewHolder) viewHolder;
        vh.title.setText(current.toString());

        ImageView image = vh.image;
        Uri uri = MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Video.Thumbnails.VIDEO_ID, MediaStore.Video.Thumbnails.DATA};
        String selection = projection[0] + "=?";
        String[] args = {current.rowId + ""};
        Cursor c = image.getContext().getContentResolver().query(uri, projection, selection, args, null);
        if (c != null && c.moveToFirst()) {
            image.setImageURI(Uri.parse(c.getString(1)));
        }

        final String path = current.uri.toString();
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, VideoPlayerActivity.class);
                intent.putExtra("uri", path);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).rowId;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    // Display#getSize(Point)
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    @SuppressWarnings("deprecation")
    private static int getWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final int result;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point size = new Point();
            display.getSize(size);
            result = size.x;
        } else {
            result = display.getWidth();
        }
        return result;
    }

    public static final class ListViewHolder extends RecyclerView.ViewHolder {

        private final ImageView image;
        private final TextView title;

        public ListViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
