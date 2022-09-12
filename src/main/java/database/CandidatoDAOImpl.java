package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Cae;
import model.Candidato;
import model.Quesito;
import model.SchedaElettorale;

public class CandidatoDAOImpl implements CandidatoDAO{

	public CandidatoDAOImpl() {}
	
	private Connection getConnection(){
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
	public void elimina(Candidato c) {
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		try {
			stmt=conn.prepareStatement("DELETE FROM candidato WHERE codicefiscale=?;");
			stmt.setString(1, c.getCodiceFiscale());
			stmt.execute();
		}catch(SQLException ex){
        	System.out.print("eliminaCandidato - SQLException: "+ ex.getMessage());
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
	public void crea(Candidato c) {
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		try {
			stmt=conn.prepareStatement("INSERT INTO candidato(codicefiscale, nome, cognome) VALUES (?,?,?);");
			stmt.setString(1, c.getCodiceFiscale());
			stmt.setString(2, c.getNome());
			stmt.setString(3, c.getCognome());
			stmt.execute();
		}catch(SQLException ex){
        	System.out.print("creaCandidato - SQLException: "+ ex.getMessage());
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
	public int inserisciInSchedaElettorale(SchedaElettorale s, Candidato c) {
		int id = -1;
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt=conn.prepareStatement("INSERT INTO informazionescheda(schedaelettorale, candidato) VALUES (?,?);", Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, s.getId());
			stmt.setString(2, c.getCodiceFiscale());
			stmt.execute();
			rs = stmt.getGeneratedKeys();
			while(rs.next()) {
				id=rs.getInt(1);
			}
		}catch(SQLException ex){
        	System.out.print("inserisciInSchedaElettorale Candidato - SQLException: "+ ex.getMessage());
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
	public Candidato getCandidato(String codiceFiscale) {
		Candidato candidato = null;
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt=conn.prepareStatement("SELECT * FROM candidato WHERE candidato.codicefiscale = ?;");
			stmt.setString(1, codiceFiscale);
			rs=stmt.executeQuery();			
			if(rs.next()) {
				candidato = new Candidato(codiceFiscale, rs.getString("nome"), rs.getString("cognome"));
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
		return candidato;
	}

	@Override
	public boolean esiste(Candidato c) {
		boolean b=false;
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			
			stmt=conn.prepareStatement("SELECT EXISTS(SELECT codicefiscale FROM candidato WHERE codicefiscale=?);");
			stmt.setString(1, c.getCodiceFiscale());
			rs=stmt.executeQuery();			
			if(rs.next()) {
				b=rs.getBoolean(1);
			}
			
		}catch(SQLException ex){
        	System.out.print("Candidato esiste - SQLException: "+ ex.getMessage());
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
