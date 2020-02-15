package com.example.collegemate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Constants;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static java.security.AccessController.getContext;

public class Books extends AppCompatActivity {

    StorageReference mStorageRef;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        Toolbar toolbar = findViewById(R.id.signup_toolbar);
        setSupportActionBar(toolbar);
        Button fetch_button=findViewById(R.id.fetch_button);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(Global.documentData.userInfo.uid);
        //Toast.makeText(this, Global.documentData.userinfo.uid, Toast.LENGTH_SHORT).show();

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPDFFile();

            }
        });
        fetch_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewAllFiles();
            }
        });
    }

    private void getPDFFile() {
        Intent intent=new Intent();
        intent.setType("application/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select PDF FILE"),1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK
                && data!=null && data.getData()!=null){
            uploadPDFFile(data.getData());
        }
    }

    private void uploadPDFFile(Uri data) {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();
        String location_file=data.getPath();
        File upload_file=new File(data);
        //String filext=location_file.substring(location_file.lastIndexOf("."));
        //Toast.makeText(this, ""+data, Toast.LENGTH_LONG).show();
        //System.out.println(data);
        StorageReference reference=mStorageRef.child(Global.documentData.userInfo.uid+"/"+upload_file.id+".pdf");
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uri=taskSnapshot.getStorage().getDownloadUrl();
                        while(!uri.isComplete());
                        Uri url=uri.getResult();

                        uploadPDF uploadPDF=new uploadPDF(url.toString());
                        databaseReference.child(databaseReference.push().getKey()).setValue(uploadPDF);
                        Toast.makeText(Books.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                double progress=(100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                progressDialog.setMessage("Uploaded: "+(int)progress+"%");
            }
        });
        upload_file.add_file();
    }
    private void viewAllFiles(){
        StorageReference listRef = mStorageRef.child(Global.documentData.userInfo.uid+"/");

        listRef.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        for (StorageReference item : listResult.getItems()) {
                            int flag=0;
                            //System.out.println(File.arr_file.toString());
                            for(int i=0;i< File.arr_file.size();i++){
                                if(item.getName().substring(0,item.getName().lastIndexOf('.')-1).equals(((Long)File.arr_file.get(i).id).toString())){
                                    flag++;
                                }
                            }
                            if(flag==0){
                                getFileUrl(item);
                                File down_file=new File();
                                down_file.id=Long.valueOf(item.getName().substring(0,item.getName().lastIndexOf('.')-1));
                                System.out.println(down_file.id);
                                down_file.path=DIRECTORY_DOWNLOADS;
                                down_file.add_file();
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Uh-oh, an error occurred!
                    }
                });
    }
    private void downloadFile(Context context, String fileName, String fileExtension, String destinationDirectory, String url) {

        DownloadManager downloadmanager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtension);

        downloadmanager.enqueue(request);

    }
    private void getFileUrl(StorageReference ref){
        final String name=ref.getName();
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url =uri.toString();
                downloadFile(Books.this,name.substring(0,name.lastIndexOf('.')-1),name.substring(name.lastIndexOf('.')+1),DIRECTORY_DOWNLOADS,url);
            }
        });
    }
}
