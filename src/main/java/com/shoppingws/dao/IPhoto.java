package com.shoppingws.dao;

import com.shoppingws.model.photo.Photo;

import java.util.List;

public interface IPhoto {
    int addPhoto(Photo critea);
    List<Photo> getPhotoList();
    int removePhoto(int photoId);
}
