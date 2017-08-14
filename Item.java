package com.crash.boozl.boozl.code;

import android.graphics.Bitmap;

public class Item {
    Bitmap image;
    String title;
    private Boolean checked;

    public Item(Bitmap image, String title) {
        super();
        this.image = image;
        this.title = title;
        checked = false;
    }
    public Bitmap getImage() {
        return image;
    }
    public void setImage(Bitmap image) {
        this.image = image;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }


    public Boolean getChecked() {
        return checked;
    }

    public void toggleCheck() {
        System.out.println("Checked = " + checked);
        if(checked == false){
            checked = true;
            System.out.println("Setting True");
        }
        else if(checked == true){
            checked = false;
            System.out.println("Setting False");

        }
    }
}