package me.imunize.imunizeme.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import me.imunize.imunizeme.models.Usuario;

/**
 * Created by Sr. Décio Montanhani on 09/08/2017.
 */

public class UsuarioDAO extends SQLiteOpenHelper {

    public UsuarioDAO(Context context) {
        super(context, "Imunizeme", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE Users (id INTEGER PRIMARY KEY, " +
                "nome TEXT NOT NULL, " +
                "email TEXT, " +
                "cpf TEXT, " +
                "senha TEXT, logado INTEGER);";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        String sql = "DROP TABLE IF EXISTS Users";
        db.execSQL(sql);

    }


    public void insere(Usuario usuario) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDoUsuario(usuario);

        db.insert("Users", null, dados);
    }

    @NonNull
    private ContentValues pegaDadosDoUsuario(Usuario usuario) {
        ContentValues dados = new ContentValues();
        dados.put("nome", usuario.getName());
        dados.put("email", usuario.getEmail());
        dados.put("cpf", usuario.getCpfCnpj());
        dados.put("senha", usuario.getPassword());
        return dados;
    }

    public List<Usuario> buscaUsuario() {
        String sql = "SELECT * FROM Users;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Usuario> usuarios = new ArrayList<Usuario>();
        while (c.moveToNext()) {
            Usuario usuario = new Usuario();
            usuario.setId(c.getLong(c.getColumnIndex("id")));
            usuario.setName(c.getString(c.getColumnIndex("nome")));
            usuario.setEmail(c.getString(c.getColumnIndex("email")));
            usuario.setCpfCnpj(c.getString(c.getColumnIndex("cpf")));
            usuario.setPassword(c.getString(c.getColumnIndex("senha")));

            usuarios.add(usuario);
        }
        c.close();

        return usuarios;
    }

    public void deleta(Usuario usuario) {
        SQLiteDatabase db = getWritableDatabase();

        String[] params = {usuario.getId().toString()};
        db.delete("Users", "id = ?", params);
    }

    public void altera(Usuario usuario) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDoUsuario(usuario);

        String[] params = {usuario.getId().toString()};
        db.update("Users", dados, "id = ?", params);
    }

    public boolean existeCPF(String cpf){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Users WHERE cpf = ?", new String[]{cpf});

        if (c.moveToNext()){
            c.close();
            return true;
        }else{
            c.close();
            return false;
        }

    }

    public Usuario fazLogin(Usuario usuario){

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Users WHERE cpf = ? AND senha = ?", new String[]{usuario.getCpfCnpj(), usuario.getPassword()});

        if (c.moveToNext()) {
            usuario.setId(c.getLong(c.getColumnIndex("id")));
            usuario.setName(c.getString(c.getColumnIndex("nome")));
            usuario.setEmail(c.getString(c.getColumnIndex("email")));
            usuario.setCpfCnpj(c.getString(c.getColumnIndex("cpf")));
            usuario.setPassword(c.getString(c.getColumnIndex("senha")));

            return usuario;
        }
        c.close();

        return null;
    }


}
