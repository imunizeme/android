package me.imunize.imunizeme.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.commons.codec.binary.Base64;

import me.imunize.imunizeme.Web.WebClient;
import me.imunize.imunizeme.converter.UsuarioConverter;
import me.imunize.imunizeme.models.Usuario;

/**
 * Created by Sr. Décio Montanhani on 14/08/2017.
 */

public class EnviaLoginTask extends AsyncTask<Usuario, Void, String> {

    private final Context context;
    private ProgressDialog dialog;

    public EnviaLoginTask(Context context) {
        this.context = context;
    }


    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, "Aguarde", "Verificando Login...", true, true);
    }


    @Override
    protected String doInBackground(Usuario... usuarios) {

        Usuario usuario = usuarios[0];
        UsuarioConverter converter = new UsuarioConverter();
        String json = converter.converteParaJSONLogin(usuario);
        WebClient client = new WebClient();
        String resposta = client.post(json, "192.168.0.12:4000/auth");
        return resposta;

    }

    @Override
    protected void onPostExecute(String s) {
        dialog.dismiss();
        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
    }

}
