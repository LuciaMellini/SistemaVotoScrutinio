package database;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import model.Cae;
import model.Candidato;
import model.SchedaElettorale;


public class CaeDAOImpl implements CaeDAO{

	public CaeDAOImpl() {}
	
	private Connection getConnection() {
		Connection conn=null;
		try {
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/SistVotoScrutinio?user=root&password=root");
        }catch(SQLException ex){
        	System.out.print("getConnection - SQLException: "+ ex.getMessage());
        	System.out.print("SQLState: "+ ex.getSQLState());
        	System.out.print("VendorError: "+ ex.getErrorCode());
        	ex.printStackTrace();
		}
		return conn;
	}
	
	@Override
	public Cae getCae(String email) {
		Cae cae = null;
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt=conn.prepareStatement("SELECT * FROM cae WHERE email = ?;");
			stmt.setString(1, email);
			rs=stmt.executeQuery();			
			if(rs.next()) {
				cae = new Cae(email, rs.getBoolean("scrutinatore"), rs.getBoolean("configuratore"));
			}
		}catch(SQLException ex){
        	System.out.print("getCae - SQLException: "+ ex.getMessage());
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
		return cae;
	}


	@Override
	public void crea(Cae c, String password) {
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		try {
			stmt=conn.prepareStatement("INSERT INTO cae(email, password) VALUES (?,?);");
			stmt.setString(1, c.getEmail());
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
	public List<Cae> getCae() {
		List<Cae> list=new LinkedList<>();
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		try {
			stmt=conn.prepareStatement("SELECT email, scrutinatore, configuratore FROM cae;");
			ResultSet rs=stmt.executeQuery();			
			while(rs.next()) {
				list.add(new Cae(rs.getString("email"), rs.getBoolean("scrutinatore"), rs.getBoolean("configuratore")));
			}
		}catch(SQLException ex){
        	System.out.print("getUtenti - SQLException: "+ ex.getMessage());
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
		
		return list;
	}

	@Override
	public void aggiornaPassword(Cae c, String password) {
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		try {
			stmt=conn.prepareStatement("UPDATE cae SET password=? WHERE email=?;");
			stmt.setString(2, password);
			stmt.setString(1, c.getEmail());	
			stmt.executeQuery();
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
	public boolean esiste(Cae c) {
		boolean b=false;
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {			
			stmt=conn.prepareStatement("SELECT EXISTS(SELECT password FROM cae WHERE email=?);");
			stmt.setString(1, c.getEmail());
			rs=stmt.executeQuery();			
			rs.next();
			b=rs.getBoolean(1);
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
	public void elimina(Cae c) {
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		try {
			stmt=conn.prepareStatement("DELETE FROM cae WHERE email=?;");
			stmt.setString(1, c.getEmail());
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
	public boolean passwordCorretta(Cae c, String password) {
		String digest=hash(password);
		boolean b=false;
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt=conn.prepareStatement("SELECT password FROM cae WHERE email=?;");
			stmt.setString(1, c.getEmail());
			rs=stmt.executeQuery();			
			rs.next();
			b=(rs.getString("password").equals(digest));
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
		
		return b;
	}

	@Override
	public boolean isScrutinatore(Cae c) {
		boolean scrutinatore = false;
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt=conn.prepareStatement("SELECT scrutinatore FROM cae WHERE email=?;");
			stmt.setString(1, c.getEmail());
			rs=stmt.executeQuery();			
			rs.next();
			scrutinatore = rs.getBoolean(1);
		}catch(SQLException ex){
        	System.out.print("isScutinatore - SQLException: "+ ex.getMessage());
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
		return scrutinatore;
	}

	@Override
	public boolean isConfiguratore(Cae c) {
		boolean configuratore = false;
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {			
			stmt=conn.prepareStatement("SELECT configuratore FROM cae WHERE email=?;");
			stmt.setString(1, c.getEmail());
			rs=stmt.executeQuery();			
			rs.next();
			configuratore = rs.getBoolean(1);
		}catch(SQLException ex){
	    	System.out.print("isConfiguratore - SQLException: "+ ex.getMessage());
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
	
		return configuratore;
	}

	@Override
	public Set<SchedaElettorale> getSchedeElettorali(Cae c) {
		Set set = new HashSet<>();
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt=conn.prepareStatement("SELECT schedaelettorale FROM caeschedeelettorali WHERE cae=?;");
			stmt.setString(1, c.getEmail());
			rs=stmt.executeQuery();		
			SchedaElettoraleDAO sDAO = new SchedaElettoraleDAOImpl();
			while(rs.next()) {
				set.add(sDAO.getSchedaElettorale(rs.getInt("schedaelettorale")));
			}
			
		}catch(SQLException ex){
	    	System.out.print("isConfiguratore - SQLException: "+ ex.getMessage());
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
	public void inserisciSchedaElettorale(Cae c, SchedaElettorale s) {
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		try {
			stmt=conn.prepareStatement("INSERT INTO caeschedeelettorali(cae, schedaelettorale) VALUES (?,?);");
			stmt.setString(1, c.getEmail());
			stmt.setInt(2, s.getId());
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

}
