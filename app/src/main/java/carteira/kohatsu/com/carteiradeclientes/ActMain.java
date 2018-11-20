package carteira.kohatsu.com.carteiradeclientes;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import carteira.kohatsu.com.carteiradeclientes.adapters.ClienteAdapter;
import carteira.kohatsu.com.carteiradeclientes.database.BancoDados;
import carteira.kohatsu.com.carteiradeclientes.entities.Cliente;
import carteira.kohatsu.com.carteiradeclientes.repositories.ClienteRepository;

public class ActMain extends AppCompatActivity {

    private FloatingActionButton fab;
    private RecyclerView lstDados;
    private ConstraintLayout layoutContentMain;
    private BancoDados db;
    private SQLiteDatabase connection;
    private ClienteRepository repo;
    private ClienteAdapter adapter;
    private ClienteAdapter clienteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        lstDados = (RecyclerView) findViewById(R.id.lstDados);

        layoutContentMain = (ConstraintLayout) findViewById(R.id.layoutContentMain);

        createConnectionWithDB();

        lstDados.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lstDados.setLayoutManager(linearLayoutManager);

        repo = new ClienteRepository(connection);

        clienteAdapter = new ClienteAdapter(repo.findAll());

        lstDados.setAdapter(clienteAdapter);

    }

    public void exibirCadastro(View view){
        Intent intent = new Intent(ActMain.this, ActCadCliente.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        List<Cliente> dados = repo.findAll();
        adapter = new ClienteAdapter(dados);
        lstDados.setAdapter(adapter);
    }

    private void createConnectionWithDB(){

        try{
            db = new BancoDados(this);
            connection = db.getWritableDatabase();

            Snackbar.make(layoutContentMain, R.string.snackbar_connection, Snackbar.LENGTH_LONG)
                    .setAction(R.string.dlg_button_ok, null).show();

        }catch(SQLException e){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(R.string.dlg_title_aviso);
            dialog.setMessage(e.getMessage());
            dialog.setNeutralButton(R.string.dlg_button_ok, null);
            dialog.show();
        }
    }

}
