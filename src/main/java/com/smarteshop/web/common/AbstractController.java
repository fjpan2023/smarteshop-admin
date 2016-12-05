package com.smarteshop.web.common;

import com.redis.E;

public abstract class AbstractController {

  protected void onLoad(Long id){};
  protected void onCreate(E entity){};
  protected void onUpdate(E entity){};
  protected void onDelete(Long id) {   }
  protected void postCreate(E entity){};
  protected void postDelete(Long id){};
  protected void postUpdate(E entity){};
  protected void postLoad(Long id, E entity){};


}
