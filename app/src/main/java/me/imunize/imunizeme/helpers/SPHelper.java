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

    public String pegaFirebaseToken(){
        return preferences.getString("firebase", null);
    }

    public void registrarAcesso(){

        SharedPreferences.Editor ed = preferences.edit();
        ed.putBoolean("primeiroAcesso", true);
        ed.commit();

    }



    public void gravaFirebaseToken(String token){

        SharedPreferences.Editor ed = preferences.edit();
        ed.putString("firebase", token);
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
        ed.putString("nome", "");
        ed.putString("email", "");

        ed.commit();
    }

    public void gravaPerfil(String name, String email, String aniversario) {

        SharedPreferences.Editor ed = preferences.edit();
        ed.putString("nome", name);
        ed.putString("email", email);
        ed.putString("aniversario", aniversario);
        ed.commit();


    }

    public String pegaAniversario(){
        return preferences.getString("aniversario", null);
    }

    public String pegaNome(){
        return preferences.getString("nome", "Imunize.me");
    }

    public String pegaEmail(){
        return preferences.getString("email", "Imunize.me");
    }

}
