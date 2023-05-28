package com.example.eagletalk;
public class Upload {

    private String mDescription;
    private String mImageUrl;

    private String mUsername;

    public Upload(){}

    public Upload(String userName, String description, String imageUrl){
        if(description.trim().equals("")){
            description = "Look at my post!";
        }

        mUsername = userName;
        mDescription = description;
        mImageUrl = imageUrl;
    }

   public String getmUsername(){
        return mUsername;
    }

    public void setUserName(String username){
        this.mUsername = mUsername;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.mImageUrl = mImageUrl;
    }
    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String dEscription) {
        this.mDescription = mDescription;
    }





}