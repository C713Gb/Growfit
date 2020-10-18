package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Models.User;
import com.example.myapplication.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class Profile extends AppCompatActivity {

    TextView name, email;
    FirebaseAuth auth;
    DatabaseReference dref;
    Button edit;
    ImageButton back, addPic;
    ImageView profilePic;
    StorageTask uploadtask;
    StorageReference mstorageRef;
    private static final int PICK_IMAGE_REQUEST=1;
    private Uri mimageUri;
    String myUrl="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.username_txt);
        email = findViewById(R.id.email_txt_3);
        edit = findViewById(R.id.complete_profile_btn);
        back = findViewById(R.id.back_2);
        addPic = findViewById(R.id.add_picture);
        profilePic = findViewById(R.id.profile_pic);

        auth = FirebaseAuth.getInstance();

        try {

            dref = FirebaseDatabase.getInstance().getReference("Users").child(auth.getCurrentUser().getUid());
            mstorageRef= FirebaseStorage.getInstance().getReference("profilephoto");
            dref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);
                    String s_name = user.getUsername();
                    String s_email = user.getEmail();
                    name.setText(s_name);
                    email.setText(s_email);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Profile.this, EditProfile.class);
                    startActivity(intent);
                }
            });

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

            addPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            addPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  openfilechooser();
                  addtodata();
                }
            });
        } catch (Exception e) {
            Log.d("PROFILE", e.getMessage());
        }


    }
    public void addtodata(){
        final StorageReference fileReference=mstorageRef.child(System.currentTimeMillis()+"."+getFileExtension(mimageUri));
        uploadtask=fileReference.putFile(mimageUri);
        Task<Uri> urlTask = uploadtask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return fileReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    myUrl=downloadUri.toString();
//                    mRef.child("doccertificates").setValue(myUrl);
                    //String code = name.getText().toString().substring(0,2)+regnum.getText().toString().substring(0,2);
                    //String code=mAuth.getCurrentUser().getUid().toString();


//                    if(citys==null){
//                        citys="not set";
//                    }
//                    if(names==null){
//                        names="not set";
//                    }
//                    if(adds==null){
//                        adds="not set";
//                    }
//                    if(otherins==null){
//                        otherins="not set";
//                    }
//                    if(ages==null){
//                        ages="not set";
//                    }
//                    if(myUrl==null){
//                        myUrl="https://firebasestorage.googleapis.com/v0/b/otpmanager-23316.appspot.com/o/profilephoto%2F1595693066402.png?alt=media&token=9df3566d-d110-4721-874e-892033784ba6";
//                    }
                    dref.child("profileimage").setValue(myUrl);
                    Toast.makeText(Profile.this, "uploaded succesfully", Toast.LENGTH_SHORT).show();
                    //startActivity(intent);
                } else {
                    // Handle failures
                    // ...
                    Toast.makeText(Profile.this, "Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Profile.this, "Uploading failed", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void openfilechooser(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData()!=null){

            mimageUri=data.getData();
            Toast.makeText(this, ""+mimageUri, Toast.LENGTH_SHORT).show();
//            if(mimageUri==null){
//                mimageUri= Uri.parse("https://firebasestorage.googleapis.com/v0/b/otpmanager-23316.appspot.com/o/profilephoto%2F1595693066402.png?alt=media&token=9df3566d-d110-4721-874e-892033784ba6");
//            }
            Picasso.with(this).load(mimageUri).resize(120,120).transform(new CropCircleTransformation()).into(profilePic);
        }

    }
    private String getFileExtension(Uri uri){
        ContentResolver cR= getContentResolver();
        MimeTypeMap mime= MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}