package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity.BUNDLE_EXTRA_NEIGHBOUR;

public class SecondActivity extends AppCompatActivity {

    @BindView(R.id.toggle_fav_button)
    public android.support.design.widget.FloatingActionButton mToggle_fav_btn;
    @BindDrawable(R.drawable.ic_star_border_white_24dp)
    public Drawable mStar;
    @BindDrawable(R.drawable.ic_star_white_24dp)
    public Drawable mStarWhite;

    @BindView(R.id.avatar_img)
    ImageView mAvatarImg;

    Neighbour mNeighbour;

    NeighbourApiService mNeighbourApiService;


    /**
     * a>b ? a : b;
     */
    private void setFavoriteButton() {

        mToggle_fav_btn.setImageDrawable(mNeighbour.getFavorite() ? mStarWhite : mStar);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extended);
        ButterKnife.bind(this);
        mNeighbourApiService = DI.getNeighbourApiService();
        mNeighbour = (Neighbour) getIntent().getSerializableExtra(BUNDLE_EXTRA_NEIGHBOUR);
        Glide.with(this).load(mNeighbour.getAvatarUrl()).into(mAvatarImg);
        final TextView neighbourNameTextView = (TextView) findViewById(R.id.avatar_name);
        neighbourNameTextView.setText(mNeighbour.getName());
        setFavoriteButton();


        mToggle_fav_btn.setOnClickListener(view -> {
            mNeighbourApiService.changeFavStatus(mNeighbour);
            mNeighbour.toggleFavoriteButton();
            setFavoriteButton();

        });


    }


}
