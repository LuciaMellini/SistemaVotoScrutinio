package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import model.Candidato;
import model.Sessione;
import model.Elettore;
import model.Partito;
import model.Quesito;
import model.SchedaElettorale;

public class SistemaVotoScrutinioDAOImpl implements SistemaVotoScrutinioDAO{

	public SistemaVotoScrutinioDAOImpl() {}
	
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
	public Set<Elettore> getElettori() {
		Set<Elettore> set=new HashSet<>();
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt=conn.prepareStatement("SELECT email FROM elettore;");
			rs=stmt.executeQuery();			
			while(rs.next()) {
				ElettoreDAO eDAO = new ElettoreDAOImpl();
				set.add(eDAO.getElettore(rs.getString("email")));
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
	public Set<Sessione> getSessioni() {
		Set<Sessione> set=new HashSet<>();
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt=conn.prepareStatement("SELECT * FROM Sessione;");
			rs=stmt.executeQuery();			
			while(rs.next()) {
				SessioneDAO cDAO = new SessioneDAOImpl();
				set.add(cDAO.getSessione(rs.getInt("id")));
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
	public Set<SchedaElettorale> getSchedeElettorali(){
		Set<SchedaElettorale> set=new HashSet<>();
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt=conn.prepareStatement("SELECT * FROM schedaelettorale;");
			rs=stmt.executeQuery();			
			while(rs.next()) {
				SchedaElettoraleDAO sDAO = new SchedaElettoraleDAOImpl();
				set.add(sDAO.getSchedaElettorale(rs.getInt("id")));
			}
		}catch(SQLException ex){
        	System.out.print("getSchedeElettorali - SQLException: "+ ex.getMessage());
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
	public Set<Partito> getPartiti() {
		Set<Partito> set=new HashSet<>();
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt=conn.prepareStatement("SELECT nome, capopartito FROM partito;");
			rs=stmt.executeQuery();	
			PartitoDAO pDAO = new PartitoDAOImpl();
			while(rs.next()) {
				Partito p = pDAO.getPartito(rs.getString("nome"));
				set.add(p);
			}
		}catch(SQLException ex){
        	System.out.print("getPartiti - SQLException: "+ ex.getMessage());
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
	public Set<Candidato> getCandidati() {
		Set<Candidato> set=new HashSet<>();
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt=conn.prepareStatement("SELECT codicefiscale FROM candidato;");
			rs=stmt.executeQuery();	
			CandidatoDAO cDAO = new CandidatoDAOImpl();
			while(rs.next()) {
				set.add(cDAO.getCandidato(rs.getString("codicefiscale")));
			}
		}catch(SQLException ex){
	    	System.out.print("getCandidati - SQLException: "+ ex.getMessage());
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
	public Set<Candidato> getCandidatiSenzaPartito() {
		Set<Candidato> set=new HashSet<>();
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt=conn.prepareStatement("SELECT codicefiscale FROM candidato WHERE !EXISTS(SELECT candidato FROM candidati WHERE candidati.candidato = candidato.codicefiscale);");
			rs=stmt.executeQuery();			
			CandidatoDAO cDAO = new CandidatoDAOImpl();
			while(rs.next()) {
				set.add(cDAO.getCandidato(rs.getString("codicefiscale")));
			}
		}catch(SQLException ex){
	    	System.out.print("getCandidatiSenzaPartito - SQLException: "+ ex.getMessage());
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
	public Set<Quesito> getQuesiti() {
		Set<Quesito> set=new HashSet<>();
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt=conn.prepareStatement("SELECT * FROM quesito;");
			rs=stmt.executeQuery();			
			while(rs.next()) {
				set.add(new Quesito(rs.getString("quesito")));
			}
		}catch(SQLException ex){
        	System.out.print("getQuesiti - SQLException: "+ ex.getMessage());
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
	public int getNumeroElettori() {
		int numeroElettori = -1;
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt=conn.prepareStatement("SELECT COUNT(*) FROM elettore;");
			rs=stmt.executeQuery();			
			if(rs.next()) {
				numeroElettori = rs.getInt(1);
			}
		}catch(SQLException ex){
        	System.out.print("getNumeroElettori - SQLException: "+ ex.getMessage());
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
		
		return numeroElettori;
	}


}
