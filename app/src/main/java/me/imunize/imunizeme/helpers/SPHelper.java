package me.imunize.imunizeme.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import me.imunize.imunizeme.SignUpActivity;

/**
 * Created by Sr. Décio Montanhani on 05/09/2017.
 */

public class SPHelper {

    private Context context;
    private SharedPreferences preferences;

    public SPHelper(Context context) {

        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

    }

    public boolean isFirstTime(){

        return !preferences.contains("primeiroAcesso");
    }

    public void registrarAcesso(){

        SharedPreferences.Editor ed = preferences.edit();
        ed.putBoolean("primeiroAcesso", true);
        ed.commit();

    }

    public void salvaLogin(){

        SharedPreferences.Editor ed = preferences.edit();
        ed.putBoolean("primeiroAcesso", true);
        ed.commit();

    }

    public void gravaAuth(String auth){


        SharedPreferences.Editor ed = preferences.edit();
        ed.putString("auth", auth);
        ed.commit();

    }

    public String estaLogado(){

        return preferences.getString("auth", null);
    }


    public void gravaToken(String token){
        token = "Bearer " + token;
        SharedPreferences.Editor ed = preferences.edit();
        ed.putString("token", token);
        ed.commit();

    }

    public void gravaIdUsuario(int id){

        SharedPreferences.Editor ed = preferences.edit();
        ed.putInt("userId", id);
        ed.commit();

    }

    public int pegaIdUsuario(){
        return preferences.getInt("userId", 0);
    }
    

    public String pegaToken(){

        return preferences.getString("token", null);
    }

    public void fazLogoff(){

        SharedPreferences.Editor ed = preferences.edit();
        ed.putString("token", "");
        ed.putString("auth", "");
        ed.commit();
    }
}
