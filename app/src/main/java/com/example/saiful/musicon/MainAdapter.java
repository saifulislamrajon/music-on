package com.example.saiful.musicon;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    Communicator communicator;
    private ArrayList<String> mDataset;
    Context context;
    int[] images = {R.drawable.james, R.drawable.ayub_bachu, R.drawable.sumon, R.drawable.imran, R.drawable.nancy, R.drawable.habib};
//    int[] images = {R.drawable.email};

    /*public MainAdapter(ArrayList<String> mDataset, Context context) {
        this.mDataset = mDataset;
        this.context = context;
    }*/

    public MainAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(MainAdapter.ViewHolder holder, int position) {
//        holder.mTitle.setText(mDataset.get(position));
        holder.mImage.setImageResource(images[position]);

    }

    @Override
    public int getItemCount() {
//        return mDataset.size();
        return images.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        //        private TextView mTitle;
        RecyclerView recyclerView;
        private ImageView mImage;

        public ViewHolder(final View itemView) {
            super(itemView);
//            mTitle = itemView.findViewById(R.id.title);
            mImage = itemView.findViewById(R.id.Ititle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = getPosition();

                    switch (id) {
                        case 0:
                            /*communicator = (Communicator)
                            communicator.respond2("james");*/
                            context.startActivity(new Intent(context, MainworkFragmentPlatform.class).putExtra("common", "james"));
                            break;
                        case 1:
                            context.startActivity(new Intent(context, MainworkFragmentPlatform.class).putExtra("common", "ayub bachchu"));
                            break;
                        case 2:
                            context.startActivity(new Intent(context, MainworkFragmentPlatform.class).putExtra("common", "sumon"));
                            break;
                        case 3:
                            context.startActivity(new Intent(context, MainworkFragmentPlatform.class).putExtra("common", "imran"));
                            break;
                        case 4:
                            context.startActivity(new Intent(context, MainworkFragmentPlatform.class).putExtra("common", "nancy"));
                            break;
                        case 5:
                            context.startActivity(new Intent(context, MainworkFragmentPlatform.class).putExtra("common", "habib"));
                            break;
                    }
                }
            });
        }
    }
}
