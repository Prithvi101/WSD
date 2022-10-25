package com.spider.wsd;



import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.spider.wsd.Utils.Constants;
import com.spider.wsd.Utils.Model.StoryModel;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;



public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {

    private static final int MENU_ITEM_VIEW_TYPE = 0;

    private BlankFragment context;
    private ArrayList<Object> filesList;

    public StoryAdapter(BlankFragment context, ArrayList<Object> filesList) {
        this.context = context;
        this.filesList = filesList;
    }

    @Override
    public StoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case MENU_ITEM_VIEW_TYPE:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row,null,false);
                return new ViewHolder(view);

            default:

                return null;
        }
    }

    @Override
    public void onBindViewHolder(StoryAdapter.ViewHolder holder, final int position) {



        int viewType = getItemViewType(position);
        switch (viewType){
            case MENU_ITEM_VIEW_TYPE:
                final StoryModel files = (StoryModel) filesList.get(position);
                final Uri uri = Uri.parse(files.getUri().toString());
if(files.getUri().toString().endsWith(".jpg"))  {

        holder.playIcon.setVisibility(View.INVISIBLE);

    Glide.with(context)
            .load(files.getUri())
            .into(holder.savedImage);
}

else if(files.getUri().toString().endsWith(".mp4")){


        holder.playIcon.setVisibility(View.VISIBLE);


    Glide.with(context)
            .load(files.getUri())
            .into(holder.savedImage);





}








                holder.Share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkFolder();
                        final String path = ((StoryModel) filesList.get(position)).getPath();
                        final File file = new File(path);
                        String destPath = Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.SAVE_FOLDER_NAME;
                        File destFile = new File(destPath);
                        try {
                            FileUtils.copyFileToDirectory(file,destFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        MediaScannerConnection.scanFile(
                                context.getActivity(),
                                new String[]{ destPath + files.getFilename()},
                                new String[]{ "*/*"},
                                new MediaScannerConnection.MediaScannerConnectionClient()
                                {
                                    public void onMediaScannerConnected()
                                    {
                                    }
                                    public void onScanCompleted(String path, Uri uri)
                                    {
                                        Log.d("path: ",path);
                                    }
                                });
                        Toast.makeText(context.getActivity(), "Saved to: "+ destPath + files.getFilename(), Toast.LENGTH_LONG).show();


                        final String share_path = destPath + files.getFilename();


                        File  sharingfile =  new File(share_path);
                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);


                        sharingIntent.setType("image/jpeg");
                        sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                        context.startActivity(Intent.createChooser(sharingIntent, "Share image using"));





                    }
                });





                holder.playIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkFolder();
                        final String path = ((StoryModel) filesList.get(position)).getPath();
                        final File file = new File(path);
                        String destPath = Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.SAVE_FOLDER_NAME;
                        File destFile = new File(destPath);
                        try {
                            FileUtils.copyFileToDirectory(file,destFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        MediaScannerConnection.scanFile(
                                context.getActivity(),
                                new String[]{ destPath + files.getFilename()},
                                new String[]{ "*/*"},
                                new MediaScannerConnection.MediaScannerConnectionClient()
                                {
                                    public void onMediaScannerConnected()
                                    {
                                    }
                                    public void onScanCompleted(String path, Uri uri)
                                    {
                                        Log.d("path: ",path);
                                    }
                                });
                        Toast.makeText(context.getActivity(), "Saved to: "+ destPath + files.getFilename(), Toast.LENGTH_LONG).show();


                        final String videoPath = destPath + files.getFilename();



                        File  videoplay =  new File(videoPath);

                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromFile(videoplay));
                        intent.setDataAndType(Uri.parse(videoPath), "video/mp4");
                        context.startActivity(intent);

                    }
                });



                holder.downloadID.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkFolder();
                        final String path = ((StoryModel) filesList.get(position)).getPath();
                        final File file = new File(path);
                        String destPath = Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.SAVE_FOLDER_NAME;
                        File destFile = new File(destPath);
                        try {
                            FileUtils.copyFileToDirectory(file,destFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        MediaScannerConnection.scanFile(
                                context.getActivity(),
                                new String[]{ destPath + files.getFilename()},
                                new String[]{ "*/*"},
                                new MediaScannerConnection.MediaScannerConnectionClient()
                                {
                                    public void onMediaScannerConnected()
                                    {
                                    }
                                    public void onScanCompleted(String path, Uri uri)
                                    {
                                        Log.d("path: ",path);
                                    }
                                });
                        Toast.makeText(context.getActivity(), "Saved to: "+ destPath + files.getFilename(), Toast.LENGTH_LONG).show();
                    }
                });
                break;

            default:

        }

    }


    public void checkFolder() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.SAVE_FOLDER_NAME ;
        File dir = new File(path);
        boolean isDirectoryCreated = dir.exists();
        if (!isDirectoryCreated) {
            isDirectoryCreated = dir.mkdir();
        }
        if (isDirectoryCreated) {
            Log.d("Folder", "Already Created");
        }
    }

    @Override
    public int getItemCount() {
        return filesList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        ImageView savedImage;
        ImageView playIcon;
        TextView downloadID;
        TextView Share;
        public ViewHolder(View itemView) {
            super(itemView);

            Share = (TextView) itemView.findViewById(R.id.share);
            savedImage = (ImageView) itemView.findViewById(R.id.mainImageView);
            playIcon = (ImageView) itemView.findViewById(R.id.playButtonImage);
            downloadID = (TextView) itemView.findViewById(R.id.downloadID);
        }
    }
}
