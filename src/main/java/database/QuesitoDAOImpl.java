package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Sessione;
import model.Cae;
import model.Partito;
import model.Quesito;
import model.SchedaElettorale;

public class QuesitoDAOImpl implements QuesitoDAO{

	public QuesitoDAOImpl() {}
	
	private Connection getConnection()  {
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
	public void elimina(Quesito q) {
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		try {
			stmt=conn.prepareStatement("DELETE FROM quesito WHERE id=?;");
			stmt.setInt(1, q.getId());
			stmt.execute();
		}catch(SQLException ex){
        	System.out.print("eliminaQuesito - SQLException: "+ ex.getMessage());
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
	public int crea(Quesito q) {
		int id = -1;
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs =null;
		try {
			stmt=conn.prepareStatement("INSERT INTO quesito(quesito) VALUES (?);", Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, q.getQuesito());
			stmt.execute();
			rs = stmt.getGeneratedKeys();
			while(rs.next()) {
				id=rs.getInt(1);
			}
		}catch(SQLException ex){
        	System.out.print("creaQuesito - SQLException: "+ ex.getMessage());
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
	public int inserisciInSchedaElettorale(SchedaElettorale s, Quesito q) {
		int id = -1;
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt=conn.prepareStatement("INSERT INTO informazionescheda(schedaelettorale, quesito) VALUES (?,?);", Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, s.getId());
			stmt.setInt(2, q.getId());
			stmt.execute();
			rs = stmt.getGeneratedKeys();
			while(rs.next()) {
				id=rs.getInt(1);
			}
		}catch(SQLException ex){
        	System.out.print("creaInformazioneSchedaQuesito - SQLException: "+ ex.getMessage());
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
	public Quesito getQuesito(int id) {
		Quesito quesito = null;
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs =null;
		try {
			stmt=conn.prepareStatement("SELECT quesito FROM quesito WHERE quesito.id = ?;");
			stmt.setInt(1, id);
			rs=stmt.executeQuery();			
			if(rs.next()) {
				quesito = new Quesito(id, rs.getString("quesito"));
			}
		}catch(SQLException ex){
        	System.out.print("getQuesito - SQLException: "+ ex.getMessage());
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
		return quesito;
	}
	
	@Override
	public boolean esiste(Quesito q) {
		boolean b=false;
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			
			stmt=conn.prepareStatement("SELECT EXISTS(SELECT id FROM quesito WHERE id=?);");
			stmt.setInt(1, q.getId());
			rs=stmt.executeQuery();		
			if(rs.next()) {
				b=rs.getBoolean(1);
			}
			
		}catch(SQLException ex){
        	System.out.print("Quesito esiste - SQLException: "+ ex.getMessage());
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
