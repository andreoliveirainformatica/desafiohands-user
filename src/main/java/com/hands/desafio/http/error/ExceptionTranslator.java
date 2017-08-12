package com.hands.desafio.http.error;

import com.hands.desafio.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class ExceptionTranslator {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorVM processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        return processFieldErrors(fieldErrors);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorVM> processNotFoundException(Exception ex) {
        BodyBuilder builder;
        ErrorVM errorVM;
        builder = ResponseEntity.status(HttpStatus.NOT_FOUND);
        errorVM = new ErrorVM("NotFound", ex.getMessage());
        return builder.body(errorVM);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorVM> processRuntimeException(Exception ex) {
        BodyBuilder builder;
        ErrorVM errorVM;
        builder = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        errorVM = new ErrorVM("Internal Server Error", ex.getMessage());
        log.error("Erro interno não tratado", ex);
        return builder.body(errorVM);
    }

    private ErrorVM processFieldErrors(List<FieldError> fieldErrors) {
        ErrorVM dto = new ErrorVM("Validation Error");

        for (FieldError fieldError : fieldErrors) {
            dto.add(fieldError.getObjectName(), fieldError.getField(), fieldError.getCode());
        }

        return dto;
    }
}
