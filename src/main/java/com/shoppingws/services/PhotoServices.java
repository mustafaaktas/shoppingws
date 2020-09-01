package com.shoppingws.services;

import com.shoppingws.dao.IPhoto;
import com.shoppingws.model.photo.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoServices {

    @Autowired
    private IPhoto iArticle;

   public int addPhoto(Photo critea){
        return iArticle.addPhoto(critea);
    }
    public   List<Photo> getPhotoList(){  return iArticle.getPhotoList();  }
    public int removePhoto(int photoId){
        return iArticle.removePhoto(photoId);
    }
}
