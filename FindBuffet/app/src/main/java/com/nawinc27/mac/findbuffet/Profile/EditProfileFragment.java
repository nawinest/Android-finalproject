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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
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
    private Uri uri;
    private ProgressBar mProgress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mProgress = getActivity().findViewById(R.id.progress_upload);
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
            uri = data.getData();
            Picasso.with(getActivity()).load(uri).fit().centerCrop().into(mProfile);
            upload();
        }else{
            Toast.makeText(getActivity(),"Please Choose image", Toast.LENGTH_LONG).show();
        }
    }

    public void upload(){
        final StorageReference filePath = mStorage.child("Photo_profile").child("Profile");
        filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mProgress.setProgress(0);
                String urmn = filePath.getDownloadUrl().toString();
                Log.d("url", urmn);
                Toast.makeText(getActivity(),"Uploading finished... ", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),"Uploading Fail, Sorry u must have logged in. ", Toast.LENGTH_LONG).show();
                Log.d("Edit Profile", "Fail because user don't signed in to system");

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                mProgress.setProgress((int) progress);
            }
        });
    }
}
