package org.common;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;

public class PostObject implements Serializable {
    private String postText;
    private File imageFile;
    private File videoFile;
    private String userEmail;
    private String imageDestination;
    private String videoDestination;


    public String getImageDestination() {
        return imageDestination;
    }

    public void setImageDestination(String imageDestination) {
        this.imageDestination = imageDestination;
    }

    public String getVideoDestination() {
        return videoDestination;
    }

    public void setVideoDestination(String videoDestination) {
        this.videoDestination = videoDestination;
    }

    public String getPostText() {
        return postText;
    }
    public void setPostText(String postText) {
        this.postText = postText;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    public File getVideoFile() {
        return videoFile;
    }

    public void setVideoFile(File videoFile) {
        this.videoFile = videoFile;
    }
}
