package com.smarteshop.config.elasticsearch;

import org.junit.Test;

public class ContentTest {
   private String content = "<jx:forEach ... ></jx:forEach>";



   @Test
   public void parseContent() {
     System.out.println(this.content.startsWith("<jx:forEach"));

   }

}
