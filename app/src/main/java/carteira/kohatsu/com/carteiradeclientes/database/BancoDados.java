package carteira.kohatsu.com.carteiradeclientes.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoDados extends SQLiteOpenHelper {

    private static int version = 3;

    public BancoDados(Context context){
        super(context, "DADOS", null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ScriptSQL.getCreateTableCliente());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL(ScriptSQL.getUpdateTableCliente());
//        onCreate(db);
    }

}
