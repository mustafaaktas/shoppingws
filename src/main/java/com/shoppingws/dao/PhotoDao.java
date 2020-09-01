package com.shoppingws.dao;

import com.shoppingws.model.photo.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PhotoDao implements IPhoto {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public int addPhoto(Photo critea) {
        return jdbcTemplate.update(
                " INSERT INTO schema_medblog.photo(photoAlt, photoUrl, photoStatus) VALUES (?,?,?) ",
                new Object[]{critea.getPhotoAlt(), critea.getPhotoUrl(),1}

        );
    }

    @Override
    public List<Photo> getPhotoList() {
        List<Photo>  photoList=new ArrayList<>();
        StringBuilder   sql=null;
        sql = new StringBuilder();
        sql.append(" select * from schema_medblog.photo a where a.photoStatus=1");

        photoList= jdbcTemplate.query(sql.toString(),new BeanPropertyRowMapper<>(Photo.class));
        return photoList;
    }

    @Override
    public int removePhoto(int photoId) {
        return jdbcTemplate.update(" update schema_medblog.photo set photoStatus=0 where  photoId=?",new Object[]{photoId});
    }
}
