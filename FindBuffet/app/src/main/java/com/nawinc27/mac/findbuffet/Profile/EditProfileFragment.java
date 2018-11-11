package com.nawinc27.mac.findbuffet.Profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nawinc27.mac.findbuffet.R;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class EditProfileFragment extends Fragment {

    private Button mUpload;
    private ImageView mProfile;
    private static final int CAMERA_REQUEST_CODE = 1;
    private StorageReference mStorage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mUpload = getActivity().findViewById(R.id.capture_img_editprofile);
        mStorage = FirebaseStorage.getInstance().getReference();
        mProfile = getActivity().findViewById(R.id.profile_img_editprofile);
        mUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent.createChooser(intent,"Choose App"), CAMERA_REQUEST_CODE);
            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK){
            final Uri uri = data.getData();

            StorageReference filePath = mStorage.child("Photo_profile").child("Profile");
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    Toast.makeText(getActivity(),"Uploading finished... ", Toast.LENGTH_LONG).show();
                    try{
//                        Bitmap bitmap = BitmapFactory.decodeStream(getActivity()
//                                .getContentResolver()
//                                .openInputStream(uri));
//                        int ivWidth = mProfile.getWidth();
//                       e int ivHeight = mProfile.getHeight();
//                        Log.d("edit image profile", mProfile.getWidth()+" and " +mProfile.getHeight() );
//                        Bitmap bitmap1  = Bitmap.createScaledBitmap(bitmap, ivWidth, ivHeight, true);
//                        mProfile.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                        mProfile.setImageBitmap(bitmap1);

                        Picasso.with(getActivity()).load(uri).fit().centerCrop().into(mProfile);

                    }catch (Exception e){

                    }


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(),"Uploading Fail, Sorry u must have logged in. ", Toast.LENGTH_LONG).show();
                    Log.d("Edit Profile", "Fail because user don't signed in to system");

                }
            });
        }else{
            Toast.makeText(getActivity(),"Please Choose image", Toast.LENGTH_LONG).show();
        }
    }
}
