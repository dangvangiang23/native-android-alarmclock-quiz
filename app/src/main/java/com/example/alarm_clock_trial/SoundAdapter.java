package com.example.alarm_clock_trial;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URI;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;


public class SoundAdapter extends RecyclerView.Adapter<SoundAdapter.SoundViewHolder> {
    private Context context;
    private List<Sound> listSound;
    boolean check = false;
   private  Clickable clickable;

    public SoundAdapter(Context context, Clickable clickable) {
        this.context = context;
        this.clickable = clickable;
    }



    public void setData(List<Sound> list) {
        this.listSound = list;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public SoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_sound, parent, false);
        return new SoundViewHolder(view, clickable);
    }

    @Override
    public void onBindViewHolder(@NonNull SoundViewHolder holder, int position) {
        Sound sound = listSound.get(position);
        if (sound == null) {
            return;
        }
        holder.imgSound.setImageResource(sound.getSound_img());
        holder.soundName.setText(sound.getSound_name());
        MediaPlayer mediaPlayer = MediaPlayer.create(holder.itemView.getContext(), sound.getSound_path());
        holder.soundName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             if(mediaPlayer.isPlaying() && check==true){
                 Log.d("Musicgv", "pas: "+ mediaPlayer.toString());
                 mediaPlayer.pause();
                 holder.gifImageButton.setImageResource(0);
                 check = false;

             }else {
                 Log.d("Musicgv", "plas: "+ mediaPlayer.toString());

                 mediaPlayer.start();
                 holder.gifImageButton.setImageResource(R.drawable.sound_200);
                check = true;
             }

            }
        });

        holder.addSoundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
              clickable.getSound(sound);
            }
        })
        ;
    }

    @Override
    public int getItemCount() {
        if (listSound != null) {
            return listSound.size();
        } else {
            return 0;
        }
    }

    public class SoundViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgSound;
        private TextView soundName;
        private ImageView addSoundBtn;
        private GifImageView gifImageButton;
        Clickable clickable;

        public SoundViewHolder(@NonNull View itemView, Clickable clickable) {
            super(itemView);
            this.clickable = clickable;
            imgSound = itemView.findViewById(R.id.icon);
            soundName = itemView.findViewById(R.id.sound_name);
            addSoundBtn = itemView.findViewById(R.id.add_sound);
            gifImageButton = itemView.findViewById(R.id.gif);
        }
    }
    public interface Clickable{
        void getSound(Sound sound);
    }
}
