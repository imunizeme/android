package me.imunize.imunizeme.converter;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONStringer;

import me.imunize.imunizeme.models.Usuario;

/**
 * Created by Sr. Décio Montanhani on 14/08/2017.
 */

public class UsuarioConverter {
    public String converteParaJSONLogin (Usuario usuario){
        JSONStringer js = new JSONStringer();

        String authValue = encriptationValue(usuario.getCpf_cnpj(), usuario.getPassword());

        try{
            js.object();
            js.key("authValue").value(authValue);
            js.endObject();

            return js.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String encriptationValue(String cpf, String senha){

        //senha = DigestUtils.sha1(senha).toString();

        String info = cpf + ":" + senha;

        return Base64.encodeBase64String(info.getBytes());
    }

}
