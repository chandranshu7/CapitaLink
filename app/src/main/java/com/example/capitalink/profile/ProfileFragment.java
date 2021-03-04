package com.example.capitalink.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.capitalink.R;
import com.example.capitalink.loginSignup.login;
import com.facebook.AccessToken;
import com.facebook.login.Login;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    TextView fullName;
    private FirebaseAuth.AuthStateListener authStateListener;
    CircleImageView profileImage;
    FirebaseFirestore fStore;
    FirebaseUser user;
    StorageReference storageReference;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_profile, container, false);

        MaterialButton logout=(MaterialButton)rootView.findViewById(R.id.logout_btn);
         fullName=(TextView)rootView.findViewById(R.id.fullname_field);
        firebaseAuth=FirebaseAuth.getInstance(FirebaseApp.getInstance());
        profileImage=(CircleImageView) rootView.findViewById(R.id.profile_image) ;
        ImageButton editProfile=(ImageButton)rootView.findViewById(R.id.edit_profile);


        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),EditProfile.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(),login.class));


            }
        });

        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if (AccessToken.getCurrentAccessToken()!=null){
                    Toast.makeText(getActivity(),AccessToken.getCurrentAccessToken().getExpires().toString(),Toast.LENGTH_SHORT);
                }

                if (user!=null){

                    String email=user.getEmail();
                    String name=user.getDisplayName();
                    Uri personPhoto = user.getPhotoUrl();
                    fullName.setText(name);
                    Picasso.with(getActivity()).load(personPhoto).into(profileImage);





                }

                else {
                    fullName.setText("");
                    profileImage.setImageBitmap(null);
                }

            }
        };





        // Create a list of profiles
        final ArrayList<profile> profiles = new ArrayList<profile>();
        profiles.add(new profile("Saved",R.drawable.ic_baseline_favorite_border_24));
        profiles.add(new profile("My Interests",R.drawable.ic_outline_folder_24));
        profiles.add(new profile("Invite Friends",R.drawable.ic_outline_share_24));
        profiles.add(new profile("Settings",R.drawable.ic_baseline_settings_24));
        profiles.add(new profile("Provide Services",R.drawable.ic_outline_storefront_24));
        profiles.add(new profile("Support",R.drawable.ic_outline_support_24));






        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        profileAdapter adapter = new profileAdapter(getActivity(),0, profiles);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.
        ListView listView = (ListView) rootView.findViewById(R.id.list_profile);

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.
        listView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener!=null){
        firebaseAuth.removeAuthStateListener(authStateListener);
    }}



}
