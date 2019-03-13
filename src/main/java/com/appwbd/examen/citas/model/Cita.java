package com.appwbd.examen.citas.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class Cita {
    //Tengo entendido que yyyy-MM-dd es el formato default de fecha, pero
    //si no pongo esta etiqueta me marca error de validacion al pasar del
    //form a la instancia. Lo mismo pasaba con el tiempo, pero como no funciono
    // la etiquet lo tuve que cambiar a String.
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;
    //private LocalTime hora;
    private String hora;
    private String paciente;
    private String doctor;
    private String asunto;
}
