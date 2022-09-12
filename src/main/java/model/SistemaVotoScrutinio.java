package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import database.CandidatoDAO;
import database.CandidatoDAOImpl;
import database.SessioneDAO;
import database.SessioneDAOImpl;
import database.PartitoDAO;
import database.PartitoDAOImpl;
import database.SchedaElettoraleDAO;
import database.SchedaElettoraleDAOImpl;
import database.SistemaVotoScrutinioDAO;
import database.SistemaVotoScrutinioDAOImpl;

public class SistemaVotoScrutinio{

	private static SistemaVotoScrutinio istanza = null;
	private BufferedWriter logger;
	
	private SistemaVotoScrutinio() {
		try {
			FileWriter fw = new FileWriter("./logging/SistemaVotoScrutinio.log");

			logger = new BufferedWriter(fw);
		}catch (IOException e) {
			System.out.println("FileWriter");
			System.out.println(e.getStackTrace().toString());	
		}
	}
	
	public static SistemaVotoScrutinio getIstanza() {
		if(Objects.isNull(istanza)) {
			istanza = new SistemaVotoScrutinio();
		}
		return istanza;
	}
	
	public void log(String s) {
		Date now = new Date();
		try {
			logger.write(now.toString() + "\t");
			logger.write(s);
			logger.newLine();
			logger.flush();
		}catch (IOException e){
			System.out.println("Writing in log");
			e.printStackTrace();;
		}
	}
	
	public int getNumeroElettori() {
		SistemaVotoScrutinioDAO svsDAO = new SistemaVotoScrutinioDAOImpl();		
		return svsDAO.getNumeroElettori();
	}
	
	public Set<Elettore> getElettori(){
		SistemaVotoScrutinioDAO svsDAO = new SistemaVotoScrutinioDAOImpl();		
		return svsDAO.getElettori();
	}
	
	public Set<Sessione> getSessioni() {
		SistemaVotoScrutinioDAO svsDAO = new SistemaVotoScrutinioDAOImpl();		
		return svsDAO.getSessioni();
	}
	
	public Set<SchedaElettorale> getSchedeElettorali(){
		SistemaVotoScrutinioDAO svsDAO = new SistemaVotoScrutinioDAOImpl();		
		return svsDAO.getSchedeElettorali();
	}
	
	public Set<Partito> getPartiti(){
		SistemaVotoScrutinioDAO svsDAO = new SistemaVotoScrutinioDAOImpl();			
		return svsDAO.getPartiti();
	}
	
	public Set<Candidato> getCandidati(){
		SistemaVotoScrutinioDAO svsDAO = new SistemaVotoScrutinioDAOImpl();		
		return svsDAO.getCandidati();
	}
	
	public Set<Candidato> getCandidatiSenzaPartito(){
		SistemaVotoScrutinioDAO svsDAO = new SistemaVotoScrutinioDAOImpl();		
		return svsDAO.getCandidatiSenzaPartito();
	}
	
	public Set<Quesito> getQuesiti(){
		SistemaVotoScrutinioDAO svsDAO = new SistemaVotoScrutinioDAOImpl(); 		
		return svsDAO.getQuesiti();
	}
		
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Sistema di voto e scrutinio");
		for(Sessione c: getSessioni()) {
			sb.append(c.toString() + "\n");
		}
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof SistemaVotoScrutinio)) return false;
		//questa classe implementa il design pattern Singleton, dunque esisterà una sola istanza di SistemaVotoScrutinio, che è sempre uguale a se stessa
		return true;
	}
	
	@Override
	public int hashCode() {
		return 42;
	}
}
