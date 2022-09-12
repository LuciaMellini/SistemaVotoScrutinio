package database;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.Calendar;
import java.util.Date;

import com.mysql.cj.xdevapi.Result;

import model.Sessione;
import model.Cae;
import model.Elettore;
import model.InformazioneScheda;
import model.ModCalcoloVincitore;
import model.ModVoto;
import model.Partito;
import model.SchedaElettorale;

public class SessioneDAOImpl implements SessioneDAO{

	public SessioneDAOImpl() {}
	
	private Connection getConnection() {
		Connection conn=null;
		try {
        conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/SistVotoScrutinio?user=root&password=root");
        }catch(SQLException ex){
        	System.out.print("getConnection - SQLException: "+ ex.getMessage());
        	System.out.print("SQLState: "+ ex.getSQLState());
        	System.out.print("VendorError: "+ ex.getErrorCode());
		}	
		return conn;
	}
	
	@Override
	public void elimina(Sessione s) {
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		try {
			stmt=conn.prepareStatement("DELETE FROM sessione WHERE id=?;");
			stmt.setInt(1, s.getId());
			stmt.execute();
		}catch(SQLException ex){
        	System.out.print("eliminaSessione - SQLException: "+ ex.getMessage());
        	System.out.print("SQLState: "+ ex.getSQLState());
        	System.out.print("VendorError: "+ ex.getErrorCode());
		}finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException ex) {
				System.out.print("closeConnection - SQLException: "+ ex.getMessage());
	        	System.out.print("SQLState: "+ ex.getSQLState());
	        	System.out.print("VendorError: "+ ex.getErrorCode());
	        	ex.printStackTrace();
			}
		}
		
	}
	
	@Override
	public int crea(Sessione c) {	
		int id = -1;
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			
			stmt=conn.prepareStatement("INSERT INTO sessione(descrizione, inizio, fine, luogo) VALUES (?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, c.getDescrizione());
			stmt.setDate(2, new java.sql.Date(c.getDataInizio().getTime()));
			stmt.setDate(3, new java.sql.Date(c.getDataFine().getTime()));
			stmt.setString(4, c.getLuogo());
			stmt.execute();
			rs = stmt.getGeneratedKeys();
			while(rs.next()) {
				id = rs.getInt(1);
			}
			conn.close();
		}catch(SQLException ex){
        	System.out.print("creaSessione - SQLException: "+ ex.getMessage());
        	System.out.print("SQLState: "+ ex.getSQLState());
        	System.out.print("VendorError: "+ ex.getErrorCode());
		}finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException ex) {
				System.out.print("closeConnection - SQLException: "+ ex.getMessage());
	        	System.out.print("SQLState: "+ ex.getSQLState());
	        	System.out.print("VendorError: "+ ex.getErrorCode());
	        	ex.printStackTrace();
			}
		}	
		return id;
	}
	
	@Override
	public void inserisciElettore(Sessione s, Elettore e) {
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		try {
			
			stmt=conn.prepareStatement("INSERT INTO elettorisessione(sessione, elettore) VALUES (?,?);");
			stmt.setInt(1, s.getId());
			stmt.setString(2, e.getEmail());
			stmt.execute();
			conn.close();	
		}catch(SQLException ex){
        	System.out.print("inserisciElettore - SQLException: "+ ex.getMessage());
        	System.out.print("SQLState: "+ ex.getSQLState());
        	System.out.print("VendorError: "+ ex.getErrorCode());
		}finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException ex) {
				System.out.print("closeConnection - SQLException: "+ ex.getMessage());
	        	System.out.print("SQLState: "+ ex.getSQLState());
	        	System.out.print("VendorError: "+ ex.getErrorCode());
	        	ex.printStackTrace();
			}
		}	
	}

	@Override
	public void inserisciSchedaElettorale(Sessione c, SchedaElettorale s) {
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		try {
			
			stmt=conn.prepareStatement("INSERT INTO schedeelettoralisessione(schedaelettorale, sessione) VALUES (?,?);");
			stmt.setInt(1, s.getId());
			stmt.setInt(2, c.getId());
			stmt.execute();
		}catch(SQLException ex){
        	System.out.print("inserisciSchedaElettorale - SQLException: "+ ex.getMessage());
        	System.out.print("SQLState: "+ ex.getSQLState());
        	System.out.print("VendorError: "+ ex.getErrorCode());
		}finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException ex) {
				System.out.print("closeConnection - SQLException: "+ ex.getMessage());
	        	System.out.print("SQLState: "+ ex.getSQLState());
	        	System.out.print("VendorError: "+ ex.getErrorCode());
	        	ex.printStackTrace();
			}
		}	
	}

	private Set<SchedaElettorale> getSchedeElettorali(int id) {
		Set<SchedaElettorale> set=new HashSet<>();
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt=conn.prepareStatement("SELECT schedaelettorale FROM schedeelettoraliSessione WHERE Sessione = ?;");
			stmt.setInt(1, id);
			rs=stmt.executeQuery();
			SchedaElettoraleDAO sDAO = new SchedaElettoraleDAOImpl();
			while(rs.next()) {
				SchedaElettorale scheda = sDAO.getSchedaElettorale(rs.getInt("schedaelettorale"));
				set.add(scheda);
			}
		}catch(SQLException ex){
        	System.out.print("getSchedeElettorali- SQLException: "+ ex.getMessage());
        	System.out.print("SQLState: "+ ex.getSQLState());
        	System.out.print("VendorError: "+ ex.getErrorCode());
		}finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException ex) {
				System.out.print("closeConnection - SQLException: "+ ex.getMessage());
	        	System.out.print("SQLState: "+ ex.getSQLState());
	        	System.out.print("VendorError: "+ ex.getErrorCode());
	        	ex.printStackTrace();
			}
		}
		return set;
	}

	
	@SuppressWarnings("deprecation")
	@Override
	public Sessione getSessione(int id) {
		Sessione Sessione = null;
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			
			stmt=conn.prepareStatement("SELECT * FROM Sessione WHERE id = ?;");
			stmt.setInt(1, id);
			rs=stmt.executeQuery();			
			if(rs.next()) {
				Date inizio = new Date(rs.getTimestamp("inizio").getTime());
				Date fine = new Date(rs.getTimestamp("fine").getTime());
				
				Calendar c = Calendar.getInstance();
				c.setTime(fine);
				c.add(Calendar.DATE, 1);
				c.add(Calendar.MILLISECOND, -1);
				fine = c.getTime();
				Sessione = new Sessione(rs.getInt("id"), rs.getString("descrizione"), inizio, fine, this.getSchedeElettorali(rs.getInt("id")), rs.getString("luogo"));
			}
		}catch(SQLException ex){
	    	System.out.print("getSessioneFromId - SQLException: "+ ex.getMessage());
	    	System.out.print("SQLState: "+ ex.getSQLState());
	    	System.out.print("VendorError: "+ ex.getErrorCode());
		}finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException ex) {
				System.out.print("closeConnection - SQLException: "+ ex.getMessage());
	        	System.out.print("SQLState: "+ ex.getSQLState());
	        	System.out.print("VendorError: "+ ex.getErrorCode());
	        	ex.printStackTrace();
			}
		}
		return Sessione;
	}
	
	@Override
	public boolean esiste(Sessione s) {
		boolean b=false;
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			
			stmt=conn.prepareStatement("SELECT EXISTS(SELECT id FROM sessione WHERE id=?);");
			stmt.setInt(1, s.getId());
			rs=stmt.executeQuery();		
			if(rs.next()) {
				b=rs.getBoolean(1);
			}
			
		}catch(SQLException ex){
        	System.out.print("Sessione esiste - SQLException: "+ ex.getMessage());
        	System.out.print("SQLState: "+ ex.getSQLState());
        	System.out.print("VendorError: "+ ex.getErrorCode());
		}finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException ex) {
				System.out.print("closeConnection - SQLException: "+ ex.getMessage());
	        	System.out.print("SQLState: "+ ex.getSQLState());
	        	System.out.print("VendorError: "+ ex.getErrorCode());
	        	ex.printStackTrace();
			}
		}
		return b;
	}
}
