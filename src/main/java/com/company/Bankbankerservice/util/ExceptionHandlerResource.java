package com.company.Bankbankerservice.util;

import com.company.Bankbankerservice.dto.ErrorDto;
import com.company.Bankbankerservice.dto.ResponseDto;
import jakarta.validation.UnexpectedTypeException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandlerResource {
    @ExceptionHandler
    public ResponseEntity<ResponseDto<Void>> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<ErrorDto> errors = fieldErrors.stream().map(f ->
                {
                    String field = f.getField();
                    String message = f.getDefaultMessage();
                    String rejectionValue = String.valueOf(f.getRejectedValue());
                    return new ErrorDto(field, message + "Rejection value" + rejectionValue);
                }
        ).toList();
        return ResponseEntity.badRequest().body(
                ResponseDto.<Void>builder()
                        .message("Validation error!")
                        .errors(errors)
                        .build()
        );
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto<Void>> unexpectedTypeException(UnexpectedTypeException e) {
        List<ErrorDto> errors = new ArrayList();
        errors.add(new ErrorDto("Error", e.getMessage()));
        return ResponseEntity.badRequest().body(
                ResponseDto.<Void>builder()
                        .message("Validation error")
                        .errors(errors)
                        .build()
        );
    }
   /* @ExceptionHandler
    public  ResponseEntity<ResponseDto<Void>> usernameNotFoundException(UsernameNotFoundException e){
        List<ErrorDto> errors=new ArrayList<>();
        errors.add(new ErrorDto("UsernameNotFoundException",e.getMessage()));
        return ResponseEntity.badRequest().body(ResponseDto.<Void>builder()
                .message("Validation error")
                .code(-2)
                .build());
    }*/
@ExceptionHandler
    public ResponseEntity<?> expiredJwtException(Exception ex) {
        return new ResponseEntity<>(
                ResponseDto.builder()
                        .message(String.format("Error %s", ex.getMessage()))
                        .success(false)
                        .build(),
                HttpStatusCode.valueOf(403)
        );
    }

    @ExceptionHandler
    public ResponseEntity<?>httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e){
    return new ResponseEntity<>(
            ResponseDto.builder()
                    .message(String.format("Error %s",e.getMessage()))
                    .success(false)
                    .build(),
            HttpStatusCode.valueOf(456)
    );
    }

}