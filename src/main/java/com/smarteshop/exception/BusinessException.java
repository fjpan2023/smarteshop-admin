package com.smarteshop.exception;

import java.text.MessageFormat;

public class BusinessException extends RuntimeException{

  /**
   *
   */
  private static final long serialVersionUID = -8250196009713067070L;
  private String errorMessage="";
  private Object[] args = new Object[]{};
  public BusinessException(Throwable cause){
      super(cause);
  }

  public BusinessException(String errorMessage){
      this.errorMessage = errorMessage;
  }
  public BusinessException(String errorCode, Object ... args){
      this.errorMessage = errorCode;
      this.args = args;
  }

  @Override
  public String getMessage(){
      return MessageFormat.format(this.errorMessage, args);
  }

}
