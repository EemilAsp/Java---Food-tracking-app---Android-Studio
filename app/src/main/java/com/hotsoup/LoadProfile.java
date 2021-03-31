package com.hotsoup;

import android.app.Application;
import android.content.Context;

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
import java.util.Arrays;


public class LoadProfile extends Application {
    static LoadProfile instance;
    private final String directory = "UserProfileDir";
    private ArrayList<UserProfile> userList = new ArrayList<>();
    public LoadProfile() {
        instance = this;
    }
    public UserProfile user = null;
    public static LoadProfile getInstance(){
        return instance;
    }

    public void reload(){
        //Load all profiles from the directory, if some is checked remember me returns that profile

                    try{
                    Context context = getApplicationContext();
                    String folder = context.getFilesDir().getAbsolutePath() + File.separator +  directory;
                    File directory = new File(folder);
                    File[] userFiles = directory.listFiles();
                    if(userFiles != null){
                    for (File userFile : userFiles) {
                            System.out.println(userFile.getAbsolutePath());
                            FileInputStream fis = new FileInputStream(userFile);
                            ObjectInputStream ois = new ObjectInputStream(fis);
                                try {
                                    userList.add((UserProfile) ois.readObject());
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }
                            ois.close();
                            fis.close();

                    }}}
                    catch (IOException | NullPointerException e){
                        e.printStackTrace();

        }

    }

    public UserProfile getUser() { return user; }

    public UserProfile identifyUser(String username, String password){
        //Find user with right username and after that hash password and check if its same

        for(int i = 0; i < userList.size() ; i++){
            if(userList.get(i).getUserName().equals(username)){
                if(Arrays.equals(makeHash(password, userList.get(i).getSalt()), userList.get(i).getPassword())) {
                    user = userList.get(i);
                    return userList.get(i);
                }
            }
        }
        //returs object or null
        System.out.println("Returns null");
    return null;}


    public void createNewProfile(String username, String password){
        byte[] salt = getSalt();//creates salt
        byte[] hashedPassword = makeHash(password, salt);//Hash+ salt to password

        UserProfile user = new UserProfile(username, hashedPassword, salt);
        updateUserData(user);//writes itself to file
    }
    private byte[] makeHash(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");//uses Sha-512
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
        //Gets object(UserProfile) and writes it to file inside Profile Directory
        //If there is no directory yet, it will create one
        String filename = user.getUserName();
        try {
            Context context = getApplicationContext();
            File folder = new File(context.getFilesDir().getAbsolutePath() + File.separator + directory);
            if (!folder.exists()) {
                folder.mkdirs();
            }


            FileOutputStream fos = new FileOutputStream(new File(folder, filename), false);
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
        System.out.println("Finding logged in people @LoadProfile");
        System.out.println(userList);
        if(!userList.isEmpty()){
            System.out.println(userList);
            System.out.println("was the whole list @LoadProfile");
            for (UserProfile user : userList){

                System.out.print(user.userName);
                System.out.println("in @LoadProfile");
                if (user.isRememberMe()) {
                    System.out.println("User logged in found");
                    return user;
                }
            }}
        System.out.println("Not people found @LoadProfile");
        return null;
    }

}
