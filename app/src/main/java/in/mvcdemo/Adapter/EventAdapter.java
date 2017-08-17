package in.mvcdemo.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

import in.mvcdemo.Activity.ActivityLanding_;
import in.mvcdemo.Custom.CustomTextView;
import in.mvcdemo.Model.GetAllEventGuest;
import in.mvcdemo.R;
import in.mvcdemo.Utills.Constant;
import in.mvcdemo.Utills.VPreferences;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    private final Context mContext;
    private final List<GetAllEventGuest> mGuestList;

    public EventAdapter(Context context, List<GetAllEventGuest> photographerDatas) {
        this.mContext = context;
        this.mGuestList = photographerDatas;




    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).
                inflate(R.layout.row_item_event, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.txtEventName.setText(mGuestList.get(position).getSport());
        holder.txtEventDate.setText("Date:" +mGuestList.get(position).getDate());
        holder.txtEventVenue.setText("Venue:" +mGuestList.get(position).getVanue_name());

        final String url = Constant.PENDING_IMG_URL + mGuestList.get(position).getSport_image();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext)
                .build();
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.app_bg) // resource or drawable
                .showImageForEmptyUri(R.drawable.ic_empty) // resource or drawable
                .showImageOnFail(R.drawable.ic_error) // resource or drawable
                .delayBeforeLoading(3000)
                .resetViewBeforeLoading(true)  // default
                .cacheInMemory(true) // default => false
                .cacheOnDisk(true) // default => false
                .build();

        imageLoader.displayImage(url, holder.imgEvent, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                holder.progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                holder.progressBar.setVisibility(View.GONE);
            }
        });
//        holder.txtName.setText(mGuestList.get(position).getFirst_name() + mGuestList.get(position).getLast_name());

//        Glide.with(mContext)
//                .load(Constant.PENDING_IMG_URL + mGuestList.get(position).getSport_image())
//                .asGif()
//                .placeholder(R.raw.ring)
//                .into(holder.imgEvent);



//        Glide.with(mContext)
//                .load(url)
//                .placeholder(R.drawable.piwo_48)
////                .transform(new CircleTransform(mContext))
//                .into(holder.imgEvent);


//        ImageLoader imageLoader = ImageLoader.getInstance();
//        DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .showImageForEmptyUri(R.drawable.ic_empty)
//                .showImageOnFail(R.drawable.ic_error)
//                .resetViewBeforeLoading(true)
//                .cacheOnDisk(true)
//                .imageScaleType(ImageScaleType.EXACTLY)
//                .bitmapConfig(Bitmap.Config.RGB_565)
//                .considerExifParams(true)
//                .displayer(new FadeInBitmapDisplayer(300))
//                .build();
//
//        imageLoader.getInstance()
//                .displayImage(url, holder.imgEvent, options, new SimpleImageLoadingListener() {
//                    @Override
//                    public void onLoadingStarted(String imageUri, View view) {
//                        holder.progressBar.setProgress(0);
//                        holder.progressBar.setVisibility(View.VISIBLE);
//                    }
//
//                    @Override
//                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//                        holder.progressBar.setVisibility(View.GONE);
//                    }
//
//                    @Override
//                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                        holder.progressBar.setVisibility(View.GONE);
//                    }
//                });

        holder.imgEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(mContext, ActivityLanding_.class);
                    intent.putExtra("EVENT_TYPE",mGuestList.get(position).getLive());
                    VPreferences.setPreferanceEventID(mContext,mGuestList.get(position).getEid());
                    VPreferences.setPreferanceEventName(mContext,mGuestList.get(position).getSport());
//                    VPreferences.setPreferanceTeamName(mContext,mGuestList.get(position).getTeam_name());
                    VPreferences.setPreferanceLattitude(mContext,mGuestList.get(position).getLatitude());
                    VPreferences.setPreferanceLongitude(mContext,mGuestList.get(position).getLongitude());
                    mContext.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return mGuestList.size();
    }

    public void clearData() {
        int size = this.mGuestList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.mGuestList.remove(0);
            }
            this.notifyItemRangeRemoved(0, size);
        }
    }

    /**
     * View Holder
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        //Restaurant
        private CustomTextView txtEventName = null,txtEventDate = null,txtEventVenue = null;
        private ImageView imgEvent= null;
        private RelativeLayout rlImage =null;
        private ProgressBar progressBar;

        private ViewHolder(View itemView) {
            super(itemView);
            this.progressBar = (ProgressBar) itemView.findViewById(R.id.progress);
            this.txtEventName = (CustomTextView) itemView.findViewById(R.id.txtEventName);
            this.txtEventDate = (CustomTextView) itemView.findViewById(R.id.txtEventDate);
            this.txtEventVenue = (CustomTextView) itemView.findViewById(R.id.txtEventVenue);
            this.imgEvent = (ImageView) itemView.findViewById(R.id.imgEvent);
            this.rlImage = (RelativeLayout) itemView.findViewById(R.id.rlImage);
        }
    }
}