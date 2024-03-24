package photos.models;

import java.util.Calendar;

public class Album {

    String albumName;
    String user;
    private final Calendar creationDate;
    int numberOfPhotos;

    public Album(String albumName, String user, int numberOfPhotos) {
        this.creationDate = Calendar.getInstance();
        creationDate.set(Calendar.MILLISECOND,0);
        this.albumName = albumName;
        this.user = user;
        this.numberOfPhotos = numberOfPhotos;
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String setAlbumName(String newName) {
        albumName = newName;
        return albumName;
    }

    public String getUser() {
        return user;
    }

    public int getNumberOfPhotos() {
        return numberOfPhotos;
    }

    public int setNumberOfPhotos(int newNumber) {
        numberOfPhotos = newNumber;
        return newNumber;
    }
}
