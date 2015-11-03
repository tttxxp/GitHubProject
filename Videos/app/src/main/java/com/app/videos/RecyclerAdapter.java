package com.app.videos;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;

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
        final View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_item, viewGroup, false);
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
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        MediaStoreData current = data.get(position);

        final ListViewHolder vh = (ListViewHolder) viewHolder;
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

        public ListViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
