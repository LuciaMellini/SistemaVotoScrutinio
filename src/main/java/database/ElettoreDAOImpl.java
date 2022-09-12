package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import model.Sessione;
import model.Cae;
import model.Candidato;
import model.Elettore;
import model.SchedaElettorale;
import model.Voce;

public class ElettoreDAOImpl implements ElettoreDAO{

	public ElettoreDAOImpl() {}

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
	public void elimina(Elettore e) {
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		try {
			stmt=conn.prepareStatement("DELETE FROM elettore WHERE email=?;");
			stmt.setString(1, e.getEmail());
			stmt.execute();
		}catch(SQLException ex){
        	System.out.print("eliminaElettore - SQLException: "+ ex.getMessage());
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
	public void crea(Elettore e) {
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		try {
			stmt=conn.prepareStatement("INSERT INTO elettore(email, codicefiscale, luogoresidenza) VALUES (?,?,?);");
			stmt.setString(1, e.getEmail());
			stmt.setString(2, e.getCodiceFiscale());
			stmt.setString(3, e.getLuogoResidenza());
			stmt.execute();
		}catch(SQLException ex){
        	System.out.print("creaElettore - SQLException: "+ ex.getMessage());
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
	public List<Elettore> getElettori() {
		List<Elettore> list=new LinkedList<>();
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt=conn.prepareStatement("SELECT email, codicefiscale, luogoresidenza FROM utente;");
			rs=stmt.executeQuery();			
			while(rs.next()) {
				list.add(new Elettore(rs.getString("email"), rs.getString("codicefiscale"), rs.getString("luogoresidenza")));
			}
		}catch(SQLException ex){
        	System.out.print("getUtenti - SQLException: "+ ex.getMessage());
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
		
		return list;
	}

	@Override
	public boolean esiste(Elettore e) {
		boolean b=false;
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt=conn.prepareStatement("SELECT EXISTS(SELECT email FROM elettore WHERE email=?);");
			stmt.setString(1, e.getEmail());
			rs=stmt.executeQuery();		
			if(rs.next()) {
				b=rs.getBoolean(1);
			}			
		}catch(SQLException ex){
        	System.out.print("esiste - SQLException: "+ ex.getMessage());
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


	@Override
	public String codiceFiscale(Elettore e) {
		String cf= new String();
		if(e.registrato()) {
			Connection conn=getConnection();
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try {
				stmt=conn.prepareStatement("SELECT codicefiscale FROM elettore WHERE email=?;");
				stmt.setString(1, e.getEmail());
				rs=stmt.executeQuery();			
				rs.next();
				cf=rs.getString("codicefiscale");
			}catch(SQLException ex){
	        	System.out.print("SQLException: "+ ex.getMessage());
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
		}
		return cf;
	}

	@Override
	public Set<Sessione> getSessioni(Elettore e) {
		Set<Sessione> set=new HashSet<>();
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			
			stmt=conn.prepareStatement("SELECT Sessione, descrizione FROM elettoriSessione JOIN Sessione ON Sessione.id = elettoriSessione.Sessione WHERE elettore = ?;");
			stmt.setString(1, e.getEmail());
			rs=stmt.executeQuery();	
			SessioneDAO cDAO = new SessioneDAOImpl(); 
			while(rs.next()) {
				Sessione  Sessione = cDAO.getSessione(rs.getInt("Sessione"));
				set.add(Sessione);
			}
		}catch(SQLException ex){
        	System.out.print("getSessioni - SQLException: "+ ex.getMessage());
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

	@Override
	public void segnaVoceComeNonEsprimibile(Elettore e, SchedaElettorale s) {
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		try {
			stmt=conn.prepareStatement("INSERT INTO elettorepreferenzeespresse(elettore, schedaelettorale) VALUES (?,?);");
			stmt.setString(1, e.getEmail());
			stmt.setInt(2, s.getId());
			stmt.execute();
		}catch(SQLException ex){
        	System.out.print("espressaPreferenza - SQLException: "+ ex.getMessage());
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
	
	public boolean preferenzaEspressa(Elettore e, SchedaElettorale s){
		boolean b = false;
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			
			stmt=conn.prepareStatement("SELECT EXISTS(SELECT * FROM elettorepreferenzeespresse WHERE schedaelettorale = ? AND elettore = ?);");
			stmt.setInt(1, s.getId());
			stmt.setString(2, e.getEmail());
			rs=stmt.executeQuery();	
			while(rs.next()) {
				b = rs.getBoolean(1);
			}
		}catch(SQLException ex){
        	System.out.print("preferenzaEspressa - SQLException: "+ ex.getMessage());
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

	@Override
	public Elettore getElettore(String email) {
		Elettore elettore = null;
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt=conn.prepareStatement("SELECT codicefiscale, luogoresidenza FROM elettore WHERE email=?;");
			stmt.setString(1, email);
			rs=stmt.executeQuery();			
			if(rs.next())	elettore = new Elettore(email, rs.getString("codicefiscale"), rs.getString("luogoresidenza"));
		}catch(SQLException ex){
        	System.out.print("SQLException: "+ ex.getMessage());
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
		return elettore;
	}
}
