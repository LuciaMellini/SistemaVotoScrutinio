package database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import controllers.SceltaTipoUtenteController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Sessione;
import model.Cae;
import model.InformazioneScheda;
import model.ModCalcoloVincitore;
import model.ModVoto;
import model.Partito;
import model.Preferenza;
import model.SchedaElettorale;
import model.Voce;

public class SchedaElettoraleDAOImpl implements SchedaElettoraleDAO{

	public SchedaElettoraleDAOImpl() {}
	
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
	public void elimina(SchedaElettorale s) {
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		try {
			stmt=conn.prepareStatement("DELETE FROM schedaelettorale WHERE id=?;");
			stmt.setInt(1, s.getId());
			stmt.execute();
		}catch(SQLException ex){
        	System.out.print("eliminaSchedaElettorale - SQLException: "+ ex.getMessage());
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
	public int crea(SchedaElettorale s) {
		int id = -1;
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt=conn.prepareStatement("INSERT INTO schedaelettorale(limiteEtà, modVoto, modCalcoloVincitore, descrizione, quorum) VALUES (?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, s.getLimiteEta());
			stmt.setString(2, s.getModVoto().literalString());
			stmt.setString(3, s.getModCalcoloVincitore().literalString());
			stmt.setString(4,  s.getDescrizione());
			stmt.setInt(5,  s.getQuorum());
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			if(rs.next()) {
				id=rs.getInt(1);
			}
		}catch(SQLException ex){
        	System.out.print("creaSchedaElettorale - SQLException: "+ ex.getMessage());
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
	public void esprimiPreferenza(int idVoceInSchedaElettorale, int preferenza) {
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		try {
			
			stmt=conn.prepareStatement("INSERT INTO preferenza(voce, preferenza) VALUES (?,?);");
			stmt.setInt(1, idVoceInSchedaElettorale);
			stmt.setInt(2, preferenza);
			stmt.execute();
		}catch(SQLException ex){
        	System.out.print("esprimiPreferenza - SQLException: "+ ex.getMessage());
        	System.out.print("SQLState: "+ ex.getSQLState());
        	System.out.print("VendorError: "+ ex.getErrorCode());
		}	finally {
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
	public SchedaElettorale getSchedaElettorale(int id) {
		SchedaElettorale schedaElettorale = null;
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			
			stmt=conn.prepareStatement("SELECT * FROM schedaelettorale WHERE id = ?;");
			stmt.setInt(1, id);
			rs=stmt.executeQuery();			
			if(rs.next()) {
				schedaElettorale = new SchedaElettorale(id, rs.getString("descrizione"), this.getInformazione(rs.getInt("id")), rs.getInt("limiteEtà"), ModVoto.valueOf(rs.getString("modVoto")), ModCalcoloVincitore.valueOf(rs.getString("modCalcoloVincitore")), rs.getInt("quorum"));
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
		return schedaElettorale;
	}

	private InformazioneScheda getInformazione(int idScheda) {
		InformazioneScheda informazioneScheda = new InformazioneScheda();
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement("SELECT id, quesito, partito, candidato FROM informazionescheda WHERE schedaelettorale = ?");
			stmt.setInt(1, idScheda);
			rs = stmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");	
				if(rs.getInt("quesito")!=0) {
					QuesitoDAO qDAO = new QuesitoDAOImpl();
					informazioneScheda.add(qDAO.getQuesito(rs.getInt("quesito")), id);
				}
				
				if(!Objects.isNull(rs.getString("partito"))) {						
					PartitoDAO pDAO = new PartitoDAOImpl();
					informazioneScheda.add(pDAO.getPartito(rs.getString("partito")), id);
				}
				if(!Objects.isNull(rs.getString("candidato"))) {
					CandidatoDAO cDAO = new CandidatoDAOImpl();
					informazioneScheda.add(cDAO.getCandidato(rs.getString("candidato")), id);
				}
			}
			
		}catch (SQLException ex){
			System.out.print("getInformazione - SQLException: "+ ex.getMessage());
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
		return informazioneScheda;
	}

	@Override
	public Set<Sessione> getSessioni(SchedaElettorale s) {
		Set<Sessione> set=new HashSet<>();
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt=conn.prepareStatement("SELECT Sessione FROM schedeelettoraliSessione WHERE schedaelettorale = ?;");
			stmt.setInt(1, s.getId());
			rs=stmt.executeQuery();
			SessioneDAO cDAO = new SessioneDAOImpl();
			while(rs.next()) {
				Sessione Sessione = cDAO.getSessione(rs.getInt("Sessione"));
				set.add(Sessione);
			}
		}catch(SQLException ex){
        	System.out.print("getSessioni- SQLException: "+ ex.getMessage());
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
	public int getNumeroElettoriTotali(SchedaElettorale s) {
		int numeroElettori = -1;
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt=conn.prepareStatement("SELECT COUNT(elettore) FROM elettoriSessione JOIN schedeelettoraliSessione ON elettoriSessione.Sessione = schedeelettoraliSessione.Sessione WHERE schedaelettorale = ?;");
			stmt.setInt(1, s.getId());
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

	
	public int getNumeroElettoriEffettivi(SchedaElettorale s) {
		int numeroElettori = -1;
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt=conn.prepareStatement("SELECT COUNT(*) FROM elettorepreferenzeespresse WHERE schedaelettorale = ?;");
			stmt.setInt(1, s.getId());
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

	@Override
	public List<Entry<Voce, Integer>> getPreferenze(SchedaElettorale s) {
		List<Entry<Voce, Integer>> preferenze = new ArrayList<>();
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			InformazioneScheda informazioneScheda = s.getInformazione();
			for(Voce v : informazioneScheda.getVoci()) {
				stmt=conn.prepareStatement("SELECT preferenza FROM preferenza WHERE voce = ?;");
				stmt.setInt(1, informazioneScheda.getId(v));
				rs=stmt.executeQuery();			
				while(rs.next()) {
					preferenze.add(new SimpleEntry(v, rs.getInt("preferenza")));
				}
				rs.close();
				stmt.close();
			}
		}catch(SQLException ex){
        	System.out.print("getNumeroElettori - SQLException: "+ ex.getMessage());
        	System.out.print("SQLState: "+ ex.getSQLState());
        	System.out.print("VendorError: "+ ex.getErrorCode());
		}finally {
			try {
				
				conn.close();
			} catch (SQLException ex) {
				System.out.print("closeConnection - SQLException: "+ ex.getMessage());
	        	System.out.print("SQLState: "+ ex.getSQLState());
	        	System.out.print("VendorError: "+ ex.getErrorCode());
	        	ex.printStackTrace();
			}
		}
		
		return preferenze;
	}
	
	@Override
	public boolean esiste(SchedaElettorale s) {
		boolean b=false;
		Connection conn=getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			
			stmt=conn.prepareStatement("SELECT EXISTS(SELECT id FROM schedaelettorale WHERE id=?);");
			stmt.setInt(1, s.getId());
			rs=stmt.executeQuery();		
			if(rs.next()) {
				b=rs.getBoolean(1);
			}
			
		}catch(SQLException ex){
        	System.out.print("SchedaElettorale esiste - SQLException: "+ ex.getMessage());
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
