package com.appwbd.examen.citas.controller;

import com.appwbd.examen.citas.model.Cita;
import com.appwbd.examen.citas.model.Evento;
import com.google.gson.Gson;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.*;
import java.util.List;

@Controller
@RequestMapping("/")
public class CitaController {

    private List<Cita>  citas = new ArrayList<>();

    @GetMapping("/registro")
    public String inicializarCita(Model model){

        List<String> doctores = new ArrayList<>();
        doctores.add("DR. GUILLERMO VILLALOBOS GUISADO");
        doctores.add("DRA. MONICA RIBAS GRANELL");
        doctores.add("DR. FELIX MALAGON NAVARRO");
        doctores.add("DRA. ANA ESCOLAR ROBLEDO");
        doctores.add("DR. JULIAN VILLANUEVA LOPEZ");
        doctores.add("DRA. JOSEFINA MAESTRE GRANERO");

        model.addAttribute("doctores", doctores);
        model.addAttribute("cita", new Cita());

        return "Altas";
    }

    @PostMapping("/registro")
    public String recibirCita(@ModelAttribute("cita") Cita nuevaCita){
        //Se le añaden segundos y milisegundos. Es necesario para añadir horas en fullcalendar
        nuevaCita.setHora(nuevaCita.getHora().concat(":00.000"));

        citas.add(nuevaCita);

        //Sort con un comparator creado sobre la marcha. Compara por fechas. Regresa 0
        //si alguno de los valores es nulo
        Collections.sort(citas, new Comparator<Cita>(){
            @Override
            public int compare(Cita o1, Cita o2) {
                if (o1.getFecha() == null || o2.getFecha() == null)
                    return 0;
                return o1.getFecha().compareTo(o2.getFecha());
            }
        });

        for (Cita test: citas) {
            System.out.println(test.getPaciente() + "\t" + test.getDoctor() + "\t" + test.getAsunto()
            + "\t" + test.getFecha() + "\t" + test.getHora() + "\n");
        }

        return "redirect:/registro";
    }

    //La etiqueta @ResponseBody hace que trabaja como un RestController y regrese algo
    // (en este caso codigo json) en vez de una direccion a un template
    @RequestMapping(value = "/eventos", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Evento> getEvents(){
        //String json = null;
        List<Evento> eventos = new ArrayList<Evento>();
        Evento evento = new Evento();

        for (Cita cita: citas) {
            evento.setTitle(cita.getDoctor());
            evento.setStart(cita.getFecha()+"T"+cita.getHora());
            evento.setEnd(evento.getStart());
            evento.setDescription("Paciente: "+ cita.getPaciente() +". " + cita.getAsunto());
            eventos.add(evento);
            evento = new Evento();
        }

        /*Gson gson = new Gson();
        json = gson.toJson(eventos);*/

        return eventos;
    }
}
