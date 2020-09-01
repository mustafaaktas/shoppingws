package com.shoppingws.model.photo;

import lombok.Data;

public @Data
class Photo {
  private int  photoId;
  private String photoAlt;
  private String photoUrl;
  private int photoStatus;
  private String photoCreateDate;
}
