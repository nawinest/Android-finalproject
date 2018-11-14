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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nawinc27.mac.findbuffet.Model.Customer;
import com.nawinc27.mac.findbuffet.R;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class EditProfileFragment extends Fragment {

    private Button mChoose;
    private Button mUploader;
    private FirebaseFirestore db;
    private ImageView mProfile;
    private static final int CAMERA_REQUEST_CODE = 1;
    private StorageReference mStorage;
    private Uri uri;
    private String uid, downloadImageUrl;
    private String imgUrlFromProfile;
    private boolean isUserPickImage = false;
    private Button back_btn_edit_profile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        db = FirebaseFirestore.getInstance();
        Bundle bundle = getArguments();
        if(bundle != null){
            uid = bundle.getString("uid");
            imgUrlFromProfile = bundle.getString("downloadUrl");
            downloadImageUrl = imgUrlFromProfile;
            getEditInfo(uid);
        }

        mChoose = getActivity().findViewById(R.id.capture_img_editprofile);
        mUploader = getActivity().findViewById(R.id.edit_btn);
        mStorage = FirebaseStorage.getInstance().getReference();
        mProfile = getActivity().findViewById(R.id.profile_img_editprofile);
        back_btn_edit_profile = getActivity().findViewById(R.id.back_btn_edit_profile);
        initBackBtn();

        //change from choose button
        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent.createChooser(intent,"Choose App"), CAMERA_REQUEST_CODE);
            }
        });
        mUploader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });



    }

    public void getEditInfo(String uid){
        final EditText edit_name = ((EditText)getView().findViewById(R.id.edit_name));
        final EditText edit_tel = ((EditText)getView().findViewById(R.id.edit_tel));
        db.collection("customer")
                .document(" Member " + uid)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(DocumentSnapshot snapshot, FirebaseFirestoreException e) {
                        if(snapshot.exists()){
                            String mName = snapshot.getString("name");
                            String mPhone = snapshot.getString("phone");
                            edit_name.setText(mName);
                            edit_tel.setText(mPhone);
                        }
                    }
                });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Edit profile","Result code : " + resultCode +"\n requestCode : " + requestCode);

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK){
            uri = data.getData();
            Picasso.with(getActivity()).load(uri).fit().centerCrop().into(mProfile);
            isUserPickImage = true;
        }else{
            isUserPickImage = false;
            downloadImageUrl = imgUrlFromProfile;
            Toast.makeText(getActivity(),"Please Choose image", Toast.LENGTH_LONG).show();
        }
    }


    public void upload(){
        if(!isUserPickImage) {
            downloadImageUrl = imgUrlFromProfile;
            EditText edit_name = ((EditText)getActivity().findViewById(R.id.edit_name));
            EditText edit_tel = ((EditText)getActivity().findViewById(R.id.edit_tel));
            Customer cus = new Customer(edit_name.getText().toString(), edit_tel.getText().toString()
                    ,downloadImageUrl);

            db.collection("customer").document((" Member " + uid))
                    .set(cus).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getActivity(),"Edit your profile success", Toast.LENGTH_LONG).show();
                }
            });
        }else {
            final StorageReference filePath = mStorage.child("Photo_profile").child("Profile_" + uid);
            final UploadTask uploadTask = filePath.putFile(uri);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if(!task.isSuccessful()){
                                throw task.getException();
                            }
                            downloadImageUrl = filePath.getDownloadUrl().toString();
                            return filePath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            downloadImageUrl = task.getResult().toString();
                            Log.d("EditProfile  :", "onComplete: " + downloadImageUrl);
                            EditText edit_name = ((EditText)getActivity().findViewById(R.id.edit_name));
                            EditText edit_tel = ((EditText)getActivity().findViewById(R.id.edit_tel));
                            Customer cus = new Customer(edit_name.getText().toString(), edit_tel.getText().toString()
                                    ,downloadImageUrl);
                            db.collection("customer").document((" Member " + uid))
                                    .set(cus).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getActivity(),"Edit your profile success", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(),"Uploading Fail, Sorry u must have logged in. ", Toast.LENGTH_LONG).show();
                    Log.d("Edit Profile", "Fail because user don't signed in to system");
                }
            });
        }
    }

    public void initBackBtn(){
        back_btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_view, new ProfileFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }



}
