package com.sigau.common.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static ResponseEntity<Map<String, String>> respuesta(
            HttpStatus estado, String error, String mensaje) {

        return ResponseEntity
                .status(estado)
                .body(Map.of("error", error, "mensaje", mensaje));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentials(
            BadCredentialsException ex) {

        return respuesta(HttpStatus.UNAUTHORIZED, "No autorizado",
                "Email o contraseña incorrectos.");
    }

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(
            RecursoNoEncontradoException ex) {

        return respuesta(HttpStatus.NOT_FOUND, "Recurso no encontrado",
                ex.getMessage());
    }

    @ExceptionHandler(RecursoDuplicadoException.class)
    public ResponseEntity<Map<String, String>> handleConflict(
            RecursoDuplicadoException ex) {

        return respuesta(HttpStatus.CONFLICT, "Conflicto", ex.getMessage());
    }

    @ExceptionHandler(ReglaNegocioException.class)
    public ResponseEntity<Map<String, String>> handleReglaNegocio(
            ReglaNegocioException ex) {

        return respuesta(HttpStatus.BAD_REQUEST, "Operación no permitida",
                ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleIntegridad(
            DataIntegrityViolationException ex) {

        return respuesta(HttpStatus.CONFLICT, "Conflicto",
                "La operación viola una restricción de integridad de los datos.");
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<Map<String, String>> handleReferenciaFaltante(
            InvalidDataAccessApiUsageException ex) {

        return respuesta(HttpStatus.BAD_REQUEST, "Datos inválidos",
                "Falta el identificador de un recurso relacionado.");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleCuerpoInvalido(
            HttpMessageNotReadableException ex) {

        return respuesta(HttpStatus.BAD_REQUEST, "Datos inválidos",
                "El cuerpo de la petición está vacío o tiene un formato incorrecto.");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleTipoParametro(
            MethodArgumentTypeMismatchException ex) {

        return respuesta(HttpStatus.BAD_REQUEST, "Datos inválidos",
                "El valor del parámetro '" + ex.getName() + "' no es válido.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(
            MethodArgumentNotValidException ex) {

        List<String> errores = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": "
                        + error.getDefaultMessage())
                .toList();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "error", "Datos inválidos",
                        "mensaje", "Los datos enviados no cumplen las validaciones.",
                        "detalles", errores
                ));
    }
}
