package me.imunize.imunizeme.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sr. Décio Montanhani on 23/09/2017.
 */

public class SexoBO {

    public List<Sexo> list(){
        List<Sexo> lista = new ArrayList<>();
        lista.add(new Sexo("F", "Feminino"));
        lista.add(new Sexo("M", "Masculino"));

        return lista;
    }
}
