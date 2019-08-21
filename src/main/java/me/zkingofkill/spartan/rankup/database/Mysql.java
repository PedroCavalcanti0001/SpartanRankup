package me.zkingofkill.spartan.rankup.database;

import me.zkingofkill.spartan.estrelas.SpartanEstrelas;
import me.zkingofkill.spartan.estrelas.objects.Estrelas;
import me.zkingofkill.spartan.rankup.SpartanRankup;
import me.zkingofkill.spartan.rankup.objects.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.sql.*;

public class Mysql {
    private String tabela;
    private Connection con;
    public Mysql(){
        this.tabela = SpartanRankup.getInstance().getConfig().getString("mysql.table");
    }
    private Connection openCon() {
        try {
            String password = SpartanRankup.getInstance().getConfig().getString("mysql.password");
            String user = SpartanRankup.getInstance().getConfig().getString("mysql.user");
            String host = SpartanRankup.getInstance().getConfig().getString("mysql.host");
            int port = SpartanRankup.getInstance().getConfig().getInt("mysql.port");
            String database = SpartanRankup.getInstance().getConfig().getString("mysql.database");
            String type = "jdbc:mysql://";
            String url = type + host + ":" + port + "/" + database;
            return DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            System.out.println("[SpartanRankup] Ocorreu um erro ao tentar abrir conexão com o banco de dados: ");
            e.printStackTrace();
        }
        return null;
    }

    public void setup() throws SQLException {
            con = openCon();
            PreparedStatement st = con.prepareStatement("CREATE TABLE IF NOT EXISTS " + tabela+"(`Player` VARCHAR(25) NOT NULL,`Rank` INTEGER NOT NULL,`Prestigy` INTEGER NOT NULL);");
            st.executeUpdate();
            con.close();
    }
    public void insertUser(User user) {
        try {
            con = openCon();
            PreparedStatement insert = con.prepareStatement("INSERT INTO " + tabela + " VALUES (?,?,?);");
            insert.setString(1, user.getOfflinePlayer().getName());
            insert.setInt(2, user.getRank().getPosition());
            insert.setInt(3, user.getPrestigy());
            insert.execute();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateUser(User user) {
        try {
            con = openCon();
            PreparedStatement insert = con.prepareStatement("UPDATE " + tabela + " SET Rank = ?, Prestigy = ? WHERE Player = ?;");
            insert.setInt(1, user.getRank().getPosition());
            insert.setInt(2, user.getPrestigy());
            insert.setString(3, user.getOfflinePlayer().getName());
            insert.executeUpdate();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean hasUser(OfflinePlayer player){
        try {
            con = openCon();
            PreparedStatement st = con.prepareStatement("SELECT * FROM " + tabela+ " WHERE Player = ?;");
            st.setString(1, player.getName());
            ResultSet rs = st.executeQuery();
           if(rs.next()){
               return true;
            }
            con.close();

        } catch (Exception e) {
            System.out.println("[SpartanRankup] Ocorreu um erro ao tentar verificar o jogador: ");
            e.printStackTrace();
        }
        return false;
    }

    public User loadUser(OfflinePlayer p){
        User u = null;
        try {
            con = openCon();
            PreparedStatement st = con.prepareStatement("SELECT * FROM " + tabela+" WHERE Player = ?;");
            st.setString(1, p.getName());
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                int rank = rs.getInt("Rank");
                int prestigy = rs.getInt("Prestigy");
                u = new User(p, SpartanRankup.getInstance().getCache().getByPosition(rank), prestigy);
            }
            con.close();

        } catch (Exception e) {
            System.out.println("[SpartanRankup] Ocorreu um erro ao tentar verificar o cargo do jogador: ");
            e.printStackTrace();
        }
        return u;
    }
}