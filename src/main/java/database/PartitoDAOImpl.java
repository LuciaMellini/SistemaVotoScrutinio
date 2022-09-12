package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.mysql.cj.protocol.Resultset;

import model.Cae;
import model.Candidato;
import model.Elettore;
import model.Partito;
import model.Quesito;
import model.SchedaElettorale;

public class PartitoDAOImpl implements PartitoDAO{

	public PartitoDAOImpl() {}
	
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
	public void elimina(Partito p) {
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		try {
			stmt=conn.prepareStatement("DELETE FROM partito WHERE nome=?;");
			stmt.setString(1, p.getNome());
			stmt.execute();
		}catch(SQLException ex){
        	System.out.print("eliminaPartito - SQLException: "+ ex.getMessage());
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
	public Set<Partito> getPartiti() {
		Set<Partito> set=new HashSet<>();
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			
			stmt=conn.prepareStatement("SELECT nome, capopartito FROM partito;");
			rs=stmt.executeQuery();
			
			while(rs.next()) {
				CandidatoDAO cDAO = new CandidatoDAOImpl();
				Partito partito = this.getPartito(rs.getString("nome"));
				set.add(partito);
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
		return set;
	}

	@Override
	public void crea(Partito p) {
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		try {
			stmt=conn.prepareStatement("INSERT INTO partito(nome, capopartito) VALUES (?,?);");
			stmt.setString(1, p.getNome());
			stmt.setString(2, p.getCapoPartito().getCodiceFiscale());
			stmt.execute();
			
			for(Candidato c: p) {
				stmt = conn.prepareStatement("INSERT INTO candidati(partito, candidato) VALUES (?,?);");
				stmt.setString(1, p.getNome());
				stmt.setString(2, c.getCodiceFiscale());
				stmt.execute();
			}
		}catch(SQLException ex){
        	System.out.print("creaPartito - SQLException: "+ ex.getMessage());
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
	public int inserisciInSchedaElettorale(SchedaElettorale s, Partito p) {
		int id = -1;
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs =null;
		try {
			
			stmt=conn.prepareStatement("INSERT INTO informazionescheda(schedaelettorale, partito) VALUES (?,?);", Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, s.getId());
			stmt.setString(2, p.getNome());
			stmt.execute();
			rs = stmt.getGeneratedKeys();
			while(rs.next()) {
				id=rs.getInt(1);
			}
		}catch(SQLException ex){
        	System.out.print("inserisciInSchedaElettorale Partito - SQLException: "+ ex.getMessage());
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
	public Partito getPartito(String nome) {
		Partito partito = null;
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		PreparedStatement stmt1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		try{
			stmt=conn.prepareStatement("SELECT * FROM partito WHERE partito.nome = ?;");
			stmt.setString(1, nome);
			rs=stmt.executeQuery();			
			if(rs.next()) {
				CandidatoDAO cDAO = new CandidatoDAOImpl();
				partito = new Partito(rs.getString("nome"), cDAO.getCandidato(rs.getString("capopartito")), this.getCandidati(rs.getString("nome")));
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
		return partito;
	}

	@Override
	public boolean esiste(Partito p) {
		boolean b=false;
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			
			stmt=conn.prepareStatement("SELECT EXISTS(SELECT nome FROM partito WHERE nome=?);");
			stmt.setString(1, p.getNome());
			rs=stmt.executeQuery();		
			if(rs.next()) {
				b=rs.getBoolean(1);
			}
			
		}catch(SQLException ex){
        	System.out.print("Partito esiste - SQLException: "+ ex.getMessage());
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

	
	private Set<Candidato> getCandidati(String nome) {
		Set<Candidato> set=new HashSet<>();
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt=conn.prepareStatement("SELECT candidato FROM candidati WHERE partito = ?;");
			stmt.setString(1, nome);
			rs=stmt.executeQuery();		
			CandidatoDAO cDAO = new CandidatoDAOImpl();
			while(rs.next()) {
				set.add(cDAO.getCandidato(rs.getString("candidato")));
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
		return set;
	}

	@Override
	public Candidato getCapoPartito(Partito p) {
		Candidato candidato = null;
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			
			stmt=conn.prepareStatement("SELECT capopartito FROM partito WHERE partito.nome = ?;");
			stmt.setString(1, p.getNome());
			rs=stmt.executeQuery();			
			if(rs.next()) {
				CandidatoDAO cDAO = new CandidatoDAOImpl();
				candidato = cDAO.getCandidato(rs.getString("capopartito"));
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
}
