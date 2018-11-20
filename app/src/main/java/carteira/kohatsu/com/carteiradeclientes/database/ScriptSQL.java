package carteira.kohatsu.com.carteiradeclientes.database;

public class ScriptSQL {

    public static String getCreateTableCliente(){
        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE IF NOT EXISTS cliente ( ");
        sql.append("id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ");
        sql.append("nome VARCHAR(255) NOT NULL DEFAULT (''), ");
        sql.append("endereco VARCHAR(255) NOT NULL DEFAULT (''), ");
        sql.append("email VARCHAR(255) NOT NULL DEFAULT (''), ");
        sql.append("telefone VARCHAR(255) NOT NULL DEFAULT ('') )");
        return sql.toString();
    }

    public static String getUpdateTableCliente(){
        StringBuilder sql = new StringBuilder();

        sql.append("DROP TABLE IF EXISTS cliente");
        return sql.toString();
    }

}
