package com.example.mobilemoneyguard;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;

public class Init {

    static public String id;
    static public String email;

    public Init(){
        System.out.println("Initialization");
        FirebaseIstance();
    }


    FileWriter info;
    private void FirebaseIstance(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        String[] dirs = CheckDirectories();
        try{info = new FileWriter(dirs[2] + File.separator+"info.txt", false);
        } catch(Exception e){System.out.println("Error creating info.txt");}
        DownloadFile("DataV1.txt", Environment.getExternalStorageDirectory()+File.separator + "Cyan"+File.separator + "MoneyGuard" + File.separator +id + File.separator+"DataV1.txt", storage, true);
        //FirebaseStorage storage2 = FirebaseStorage.getInstance("gs://moneyguard-1565995930582.appspot.com");
        UploadFile(id + "/DataV1.txt", Environment.getExternalStorageDirectory()+File.separator + "Cyan"+File.separator + "MoneyGuard" + File.separator +id + File.separator+"DataV1.txt", storage, "text/plain");


        //String stringa2 ="";
        //try{StringCipher.decodeAndDecrypt(stringa, stringa2) ;}
        //catch(Exception e) {System.out.println("Decoding error");}


    }

    String path;
    StorageMetadata Metadata;
    StorageReference FileRef;
    boolean isdata = false;
    private void DownloadFile(String path_storage, String path_local, FirebaseStorage storage, boolean isdata_){
        try{
            this.isdata = isdata_;
            StorageReference storageRef = storage.getReference();
            FileRef = storageRef.child(path_storage);
            File localFile = new File(path_local);
            path = localFile.getAbsolutePath();
            FileRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    // Local temp file has been created
                    System.out.println("CREATED File: " + path);
                    FileRef.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                        @Override
                        public void onSuccess(StorageMetadata metadata) {
                            // Local temp file has been created
                            try{
                                if (isdata){
                                    PrintWriter print = new PrintWriter(info);
                                    print.print(metadata.getCreationTimeMillis());
                                    print.close();
                                }
                            } catch(Exception e){}
                            System.out.println("CREATED File: " + path + "__last modified: "+metadata.getCreationTimeMillis());
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    System.out.println("FAILED TO CREATE FILE: " + exception.getMessage());
                    // Handle any errors
                }
            });
        } catch(Exception e){
            System.out.println("FAILED TO CREATE FILE: " + path + " - Error: "+ e.getMessage());}

    }

    private void UploadFile(String path_storage, String path_local, FirebaseStorage storage, String contenttype) {
        try {
            InputStream stream = new FileInputStream(new File(path_local));
            StorageReference storageRef = storage.getReference();
            StorageReference FinalRef = storageRef.child(path_storage);

            StorageMetadata metadata = new StorageMetadata.Builder().setContentType(contenttype).build();

            UploadTask uploadTask = FinalRef.putStream(stream, metadata);
            path = path_local;
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    System.out.println("Error uploading file: " + path + " - " + exception);
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    System.out.println("CREATED File: " + path);
                }
            });
        } catch(Exception e){System.out.println("Error uploading file: " + path + " - " + e);}
    }


    private String[] CheckDirectories(){
        String[] dirs = new String[6];
        File CyanPath = new File(Environment.getExternalStorageDirectory(), "Cyan");
        if(!CyanPath.exists()) { CyanPath.mkdirs();}
        File MoneyPath = new File(Environment.getExternalStorageDirectory()+File.separator + "Cyan", "MoneyGuard");
        if(!MoneyPath.exists()) { MoneyPath.mkdirs();}
        File IDPath = new File(Environment.getExternalStorageDirectory()+File.separator + "Cyan"+File.separator + "MoneyGuard", id);
        if(!IDPath.exists()) { IDPath.mkdirs();}
        File IconsPath = new File(Environment.getExternalStorageDirectory()+File.separator + "Cyan"+File.separator + "MoneyGuard" + File.separator +id, "Icons");
        if(!IconsPath.exists()) { IconsPath.mkdirs();}
        File TipoPath = new File(Environment.getExternalStorageDirectory()+File.separator + "Cyan"+File.separator + "MoneyGuard" + File.separator +id+ File.separator +"Icons", "Tipo");
        if(!TipoPath.exists()) { TipoPath.mkdirs();}
        File MetodoPath = new File(Environment.getExternalStorageDirectory()+File.separator + "Cyan"+File.separator + "MoneyGuard" + File.separator +id+ File.separator +"Icons", "Metodi");
        if(!MetodoPath.exists()) { MetodoPath.mkdirs();}
        dirs[0] = CyanPath.getAbsolutePath();
        dirs[1] = MoneyPath.getAbsolutePath();
        dirs[2] = IDPath.getAbsolutePath();
        dirs[3] = IconsPath.getAbsolutePath();
        dirs[4] = TipoPath.getAbsolutePath();
        dirs[5] = MetodoPath.getAbsolutePath();
        return dirs;
    }

}
