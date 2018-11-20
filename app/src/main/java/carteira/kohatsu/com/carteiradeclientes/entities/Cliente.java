package carteira.kohatsu.com.carteiradeclientes.entities;

import android.database.Cursor;

import java.io.Serializable;

public class Cliente implements Serializable {

    private Integer id;
    private String nome;
    private String endereco;
    private String email;
    private String telefone;

    public Cliente(){
        this.id = 0;
    }

    public Cliente(Integer id, String nome, String endereco, String email, String telefone) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.email = email;
        this.telefone = telefone;
    }

    public Cliente(Cursor cursor){
        this.id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        this.nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
        this.endereco = cursor.getString(cursor.getColumnIndexOrThrow("endereco"));
        this.email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
        this.telefone = cursor.getString(cursor.getColumnIndexOrThrow("telefone"));
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
