package carteira.kohatsu.com.carteiradeclientes;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import carteira.kohatsu.com.carteiradeclientes.database.BancoDados;
import carteira.kohatsu.com.carteiradeclientes.entities.Cliente;
import carteira.kohatsu.com.carteiradeclientes.repositories.ClienteRepository;

public class ActCadCliente extends AppCompatActivity {

    private EditText editNome, editEndereco, editEmail, editTelefone;
    private ConstraintLayout layoutContentCadCliente;
    private BancoDados db;
    private SQLiteDatabase connection;
    private ClienteRepository repo;
    private Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cad_cliente);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editNome = (EditText) findViewById(R.id.editNome);
        editEndereco = (EditText) findViewById(R.id.editEndereco);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editTelefone = (EditText) findViewById(R.id.editTelefone);

        layoutContentCadCliente = (ConstraintLayout) findViewById(R.id.layoutContentCadCliente);

        createConnectionWithDB();
        verifyParam();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater  = getMenuInflater();
        inflater.inflate(R.menu.menu_act_cad_cliente, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_ok:
                confirm();
                Toast.makeText(this, "Cadastrado com sucesso!", Toast.LENGTH_LONG).show();
                returnActMain();
                break;

            case R.id.action_remove:
                remove();
                returnActMain();
                break;

            case android.R.id.home:
                returnActMain();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void confirm(){

        if(validarCampos() == false){
            try{
                if(cliente.getId() == 0){
                    repo.insert(cliente);
                } else {
                    repo.update(cliente);
                }

//                Toast.makeText(this, "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
            }catch(SQLException e){
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle(R.string.dlg_title_aviso);
                dialog.setMessage(e.getMessage());
                dialog.setNeutralButton(R.string.dlg_button_ok, null);
                dialog.show();
            }
        }

    }

    private boolean validarCampos(){
        boolean result = false;

        String nome = editNome.getText().toString();
        String endereco = editEndereco.getText().toString();
        String email = editEmail.getText().toString();
        String telefone = editTelefone.getText().toString();

        cliente.setNome(nome);
        cliente.setEndereco(endereco);
        cliente.setEmail(email);
        cliente.setTelefone(telefone);


        if(result = isCamposVazio(editNome.getText().toString())){
            editNome.requestFocus();
            isTrue(result);
        } else if(result = isCamposVazio(editEndereco.getText().toString())){
            editEndereco.requestFocus();
            isTrue(result);
        } else if(result = !isEmailValido(editEmail.getText().toString())){
            editEmail.requestFocus();
            isTrue(result);
        } else if(result = isCamposVazio(editTelefone.getText().toString())){
            editTelefone.requestFocus();
            isTrue(result);
        }
        return result;
    }

    private boolean isCamposVazio(String valor){
        boolean res = (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
        return res;
    }

    private boolean isEmailValido(String email){
        boolean res = (!isCamposVazio(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        return res;
    }

    private void isTrue(boolean result){
        if(result == true){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(R.string.dlg_title_aviso);
            dialog.setMessage(R.string.dlg_message_campos_invalidos);
            dialog.setNeutralButton(R.string.dlg_button_ok, null);
            dialog.show();
        }
    }

    private void createConnectionWithDB(){

        try{
            db = new BancoDados(this);
            connection = db.getWritableDatabase();

            Snackbar.make(layoutContentCadCliente, R.string.snackbar_connection, Snackbar.LENGTH_LONG)
                    .setAction(R.string.dlg_button_ok, null).show();

            repo = new ClienteRepository(connection);
        }catch(SQLException e){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(R.string.dlg_title_aviso);
            dialog.setMessage(e.getMessage());
            dialog.setNeutralButton(R.string.dlg_button_ok, null);
            dialog.show();
        }
    }

    private void returnActMain(){
        Intent intent = new Intent(ActCadCliente.this, ActMain.class);
        startActivity(intent);
    }

    private void verifyParam(){
        Bundle bundle = getIntent().getExtras();

        cliente = new Cliente();

        if(bundle != null && (bundle.containsKey("cliente"))){
            cliente = (Cliente) bundle.getSerializable("cliente");

            editNome.setText(cliente.getNome());
            editEndereco.setText(cliente.getEndereco());
            editEmail.setText(cliente.getEmail());
            editTelefone.setText(cliente.getTelefone());
        }
    }

    private void remove(){
        repo.delete(cliente.getId());
    }

}
