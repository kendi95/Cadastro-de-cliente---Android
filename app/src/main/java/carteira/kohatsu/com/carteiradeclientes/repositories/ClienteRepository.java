package carteira.kohatsu.com.carteiradeclientes.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import carteira.kohatsu.com.carteiradeclientes.entities.Cliente;

public class ClienteRepository {

    private SQLiteDatabase connection;

    public ClienteRepository(SQLiteDatabase connection){
        this.connection = connection;
    }

    public void insert(Cliente cliente){
        ContentValues values = new ContentValues();
        values.put("nome", cliente.getNome());
        values.put("endereco", cliente.getEndereco());
        values.put("email", cliente.getEmail());
        values.put("telefone", cliente.getTelefone());

        connection.insertOrThrow("cliente", null, values);
    }

    public void update(Cliente cliente){
        ContentValues values = new ContentValues();
        values.put("nome", cliente.getNome());
        values.put("endereco", cliente.getEndereco());
        values.put("email", cliente.getEmail());
        values.put("telefone", cliente.getTelefone());

        String[] param = new String[1];
        param[0] = String.valueOf(cliente.getId());

        connection.update("cliente", values, "id = ?", param);
    }

    public void delete(Integer id){
        String[] param = new String[1];
        param[0] = String.valueOf(id);

        connection.delete("cliente", "id = ?", param);
    }

    public List<Cliente> findAll(){
        List<Cliente> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM cliente");

        Cursor cursor = connection.rawQuery(sql.toString(), null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do{
                list.add(new Cliente(cursor));
            }while(cursor.moveToNext());
        }

        return list;
    }

    public Cliente findById(Integer id){
        String[] param = new String[1];
        param[0] = String.valueOf(id);

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM cliente WHERE id = ?");

        Cursor cursor = connection.rawQuery(sql.toString(), param);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            return new Cliente(cursor);
        } else {
            return null;
        }
    }

}
