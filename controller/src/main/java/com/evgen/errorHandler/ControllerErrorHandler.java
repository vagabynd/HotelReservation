package com.evgen.errorHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ServerWebInputException;

import com.evgen.wrapper.ErrorWrapper;

@CrossOrigin
@ControllerAdvice
public class ControllerErrorHandler {

  private static final Logger LOGGER = LogManager.getLogger(ControllerErrorHandler.class);

  @ExceptionHandler(ServerWebInputException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorWrapper handleServerWebInputException(ServerWebInputException ex) {
    LOGGER.debug("Handling ServerWebInputException: " + ex);
    return ErrorWrapper.wrap(ex);
  }

  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorWrapper handleRuntimeException(RuntimeException ex) {
    LOGGER.debug("Handling RuntimeException: " + ex);
    return ErrorWrapper.wrap(ex);
  }

  @ExceptionHandler(DuplicateKeyException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorWrapper handleDuplicateKeyException(DuplicateKeyException ex) {
    LOGGER.debug("Handling DuplicateKeyException: " + ex);
    return ErrorWrapper.wrap(ex);
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorWrapper handleAllExceptions(Exception ex) {
    LOGGER.debug("Handling Exception: " + ex);
    return ErrorWrapper.wrap(ex);
  }
}
