package com.ariyanjakaria.spacetour;

public class packageModel {

    String image;
    String name;
    String uid;
    String dview;
    String gif;

    public packageModel(String image, String name,String uid,String dview,String gif) {
        this.image = image;
        this.name = name;
        this.uid=uid;
        this.dview=dview;
        this.gif=gif;
    }

    public String getGif() {
        return gif;
    }

    public void setGif(String gif) {
        this.gif = gif;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
   public  String getDview()
   {
       return  dview;
   }
   public void setDview(String dview)
   {
       this.dview=dview;
   }


}
