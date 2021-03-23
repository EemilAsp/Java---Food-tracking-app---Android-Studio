package com.hotsoup;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;


public class LoadProfile {
    public static LoadProfile instance = new LoadProfile();;
    private String filePath = "";
    private static ArrayList<UserProfile> userList = new ArrayList<UserProfile>();
    private Context context;
    public LoadProfile() {
        //Load all profiles
        String path = Environment.getExternalStorageDirectory().toString()+"/Users";
        File[] userFiles = (new File(path)).listFiles();
        try{
        for (File userFile : userFiles) {
            try {
                FileInputStream fis = context.openFileInput(userFile.getAbsolutePath());
                ObjectInputStream ois = new ObjectInputStream(fis);
                userList.add((UserProfile) ois.readObject());
                ois.close();
                fis.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }}
        catch (NullPointerException e){
            System.out.println("#########Empty list#######");
        }

    }
    public static LoadProfile getInstance(){
        return instance;
    }

    public UserProfile identifyUser(String username, String password){
        //Find user with right username and after that hash password and check if its same
        UserProfile user = null;
        for(int i = 0; i < userList.size() ; i++){
            if(userList.get(i).getUserName().equals(username)){
                if(makeHash(password, userList.get(i).getSalt()) == userList.get(i).getPassword())
                user = userList.get(i);
            }
        }
        //returs object or null
    return user;}


    public void createNewProfile(String username, String password){
        byte[] salt = getSalt();//creates salt
        byte[] hashedPassword = makeHash(password, salt);//Hash+ salt to password

        UserProfile user = new UserProfile(username, hashedPassword, salt);
        updateUserData(user);//writes itself to file
    }
    private byte[] makeHash(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");//uses Sha-512
            if((salt = getSalt()) == null){
                throw new NoSuchAlgorithmException();//No salt got
            }
            md.update(salt);
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            md.reset();
            return hash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;

        }
    }
    //Create salt for hashing
    private byte[] getSalt(){
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            byte[] salt = new byte[16];
            sr.nextBytes(salt);
            return salt;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateUserData(UserProfile user){
        //Gets object(UserProfile) and writes it to file inside Users
        try {
            FileOutputStream fos = context.openFileOutput("/Users/"+user.getUserName(), Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(user);
            os.close();
            fos.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public boolean isNameFree(String name){
        //if name is not in use returns true
        boolean nametaken = true;
        if(!userList.isEmpty()){
        for (UserProfile user : userList){
            if (name.equals(user.getUserName())) {
                nametaken = false;
                break;
            }
        }}
        return nametaken;
    }

    public UserProfile findloggedIn(){
        if(!userList.isEmpty()){
            for (UserProfile user : userList){
                if (user.isRememberMe()) {
                    return user;
                }
            }}
        return null;
    }

}
