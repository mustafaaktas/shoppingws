package com.shoppingws.controller;

import com.shoppingws.model.AjaxResponseBody;
import com.shoppingws.model.photo.Photo;
import com.shoppingws.services.PhotoServices;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class PhotoController {
    private final MinioClient minioClient;
    private final PhotoServices photoServices;
    AjaxResponseBody<Integer> resultDel=null;
    AjaxResponseBody<Photo> addPhotoResult=null;

    @Autowired
    public PhotoController(MinioClient minioClient,PhotoServices photoServices) {
        this.minioClient = minioClient;
        this.photoServices=photoServices;
    }

    @RequestMapping("/photo")
    public String menuAdminList(Map<String, Object> model, HttpServletRequest request)
    {
        model.put("critea",new Photo());
        return "admin/photo-index";
    }
    @RequestMapping("/photo/list")
    public String userList(Map<String, Object> model, HttpServletRequest request) {
        List<Photo> photos=new ArrayList<>();
        photos=photoServices.getPhotoList();
        model.put("photos",photos);
        return "admin/ajax/photo/ajax-photo-list";
    }
    @RequestMapping(value = "/photo/insert", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addUser(@ModelAttribute Photo critea, @RequestParam("photo") MultipartFile referenceFile, Map<String, Object> model, HttpServletRequest request) {
        addPhotoResult=new AjaxResponseBody<>();
        try{
            if (!minioClient.bucketExists("mehmetemindemir")){
                minioClient.makeBucket("mehmetemindemir");
            }
            minioClient.putObject("mehmetemindemir", referenceFile.getOriginalFilename(), referenceFile.getInputStream(), referenceFile.getSize(), referenceFile.getContentType());
            critea.setPhotoUrl(referenceFile.getOriginalFilename());
            if(photoServices.addPhoto(critea)>0){
                addPhotoResult.setStatus("OK");
                addPhotoResult.setResult(critea);
            }else {
                addPhotoResult.setStatus("NOK");
                addPhotoResult.setResult(critea);
            }
        }catch (Exception e){
            e.printStackTrace();
            addPhotoResult.setResult(null);
            addPhotoResult.setStatus("NOK");
            addPhotoResult.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(addPhotoResult);
    }

    @RequestMapping("/photo/del")
    public ResponseEntity<?> companyDel(@RequestParam("photoId") int photoId, Map<String, Object> model, HttpServletRequest request) {
        resultDel=new AjaxResponseBody<>();
        try {

            if(photoServices.removePhoto(photoId)>0){
                resultDel.setStatus("OK");
                resultDel.setResult(photoId);
            }else {
                resultDel.setStatus("NOK");
                resultDel.setResult(photoId);
            }

        }catch (Exception e){
            resultDel.setStatus("NOK");
            resultDel.setMsg(e.getMessage());

        }
        return ResponseEntity.ok(resultDel);
    }
}
