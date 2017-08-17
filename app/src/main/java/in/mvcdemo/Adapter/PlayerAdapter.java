package in.mvcdemo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.List;

import in.mvcdemo.Activity.ActivityPlayerProfile_;
import in.mvcdemo.Custom.CircleImageView;
import in.mvcdemo.Custom.CustomTextView;
import in.mvcdemo.Model.GetPlayerEventGuest;
import in.mvcdemo.R;
import in.mvcdemo.Utills.Constant;
import in.mvcdemo.Utills.ImageUtils;


public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {
    private final Context mContext;
    private final List<GetPlayerEventGuest> mGuestList;

    public PlayerAdapter(Context context, List<GetPlayerEventGuest> photographerDatas) {
        this.mContext = context;
        this.mGuestList = photographerDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).
                inflate(R.layout.row_item_player, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.txtPlayername.setText(mGuestList.get(position).getName());
        holder.txtTeamName.setText(mGuestList.get(position).getTeamname());
        holder.txtJersyNo.setText(mGuestList.get(position).getShirtno());
        ImageUtils.getImageLoader(mContext).
                displayImage(Constant.PENDING_IMG_URL + mGuestList.get(position).getImage(),
                        holder.img_user);

        holder.txtPlayerInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ActivityPlayerProfile_.class);
                intent.putExtra("PLA_ID",mGuestList.get(position).getPid());
                intent.putExtra("PLA_NAME",mGuestList.get(position).getName());
                intent.putExtra("PLA_GENDER",mGuestList.get(position).getGender());
                intent.putExtra("PLA_IMAGE",mGuestList.get(position).getImage());
                intent.putExtra("PLA_EVENT",mGuestList.get(position).getEventname());
                intent.putExtra("PLA_COACH",mGuestList.get(position).getCoachname());
                intent.putExtra("PLA_TEAM",mGuestList.get(position).getTeamname());
                intent.putExtra("PLA_OTHER_ATTR",mGuestList.get(position).getOther_attributes());
                intent.putExtra("PLA_JNO",mGuestList.get(position).getShirtno());

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
        private CustomTextView txtPlayername = null,txtTeamName = null,txtPlayerInfo=null,txtJersyNo =null;
        private CircleImageView img_user= null;
        private RelativeLayout rlPlayer =null;

        private ViewHolder(View itemView) {
            super(itemView);
            this.txtPlayername = (CustomTextView) itemView.findViewById(R.id.txtPlayername);
            this.txtTeamName = (CustomTextView) itemView.findViewById(R.id.txtTeamName);
            this.img_user = (CircleImageView) itemView.findViewById(R.id.img_user);
            this.txtJersyNo = (CustomTextView) itemView.findViewById(R.id.txtJersyNo);
            this.rlPlayer = (RelativeLayout) itemView.findViewById(R.id.rlPlayer);
            this.txtPlayerInfo = (CustomTextView) itemView.findViewById(R.id.txtPlayerInfo);
        }
    }
}