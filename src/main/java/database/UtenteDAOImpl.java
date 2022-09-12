package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import model.Cae;
import model.Utente;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UtenteDAOImpl implements UtenteDAO{
	
	public UtenteDAOImpl() {}
	
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
		
	//previsto nel database un trigger tipo before insert che inserisce l'hash della stringa password nel campo 'password'
	@Override
	public void crea(Utente u, String password) {
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		try {
			
			stmt=conn.prepareStatement("INSERT INTO utente(email, password) VALUES (?,?);");
			stmt.setString(1, u.getEmail());
			stmt.setString(2, password);
			stmt.execute();
		}catch(SQLException ex){
        	System.out.print("crea - SQLException: "+ ex.getMessage());
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
	public void aggiornaPassword(Utente u, String password) {
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		
		try {
			
			stmt=conn.prepareStatement("UPDATE utente SET password=? WHERE email=?;");
			stmt.setString(2, password);
			stmt.setString(1, u.getEmail());		
		}catch(SQLException ex){
        	System.out.print("aggiornaPassword - SQLException: "+ ex.getMessage());
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
	public boolean esiste(Utente u) {
		boolean b=false;
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt=conn.prepareStatement("SELECT EXISTS(SELECT email FROM utente WHERE email=?);");
			stmt.setString(1, u.getEmail());
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
	public List<Utente> getUtenti(){
		
		List<Utente> list=new LinkedList<>();
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			
			stmt=conn.prepareStatement("SELECT email FROM utente;");
			rs=stmt.executeQuery();			
			while(rs.next()) {
				list.add(new Utente(rs.getString("email")));
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
	public void elimina(Utente u) {
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		
		try {
			stmt=conn.prepareStatement("DELETE FROM utente WHERE email=?;");
			stmt.setString(1, u.getEmail());
			stmt.execute();
		}catch(SQLException ex){
        	System.out.print("elimina - SQLException: "+ ex.getMessage());
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
	

	private static String hash(String s) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-1");
		}catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
		
        BigInteger no = new BigInteger(1, md.digest(s.getBytes()));

        String hashtext = no.toString(16);
		return hashtext;
	}
	
	@Override
	public boolean passwordCorretta(Utente u, String password) {
		String digest=hash(password);
		boolean b=false;
		if(this.esiste(u)) {
			Connection conn=getConnection();
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try {
				stmt=conn.prepareStatement("SELECT password FROM utente WHERE email=?;");
				stmt.setString(1, u.getEmail());
				rs=stmt.executeQuery();			
				rs.next();
				b=(rs.getString("password").equals(digest));
				rs.close();
				conn.close();
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
		
		return b;
	}
	
	@Override
	public boolean isCAE(Utente u) {
		CaeDAO cDAO = new CaeDAOImpl();
		return cDAO.esiste(new Cae(u.getEmail(), false, false));
	}
}
