package gestionDonnees;

import java.util.concurrent.atomic.AtomicInteger;

public class Joueur {
	private static final AtomicInteger count = new AtomicInteger(-1);
	private final int joueurID;
	private final String nom;
	private final String equipe;
	private final String position;
	private final int buts;
	private final int assistances;

	public Joueur(String _nom,
					String _equipe,
					String _position,
					int _buts,
					int _assistances) {
		nom = _nom;
		equipe = _equipe;
		position = _position;
		buts = _buts;
		assistances = _assistances;
		joueurID = count.incrementAndGet();
	}
	public int getButs() {
		return buts;
	}
	public int getAssistances() {
		return assistances;
	}
	public String getNom() {
		return nom;
	}
	public String getEquipe() {
		return equipe;
	}
	public String getPosition() {
		return position;
	}
	public int getJoueurID() { return joueurID; }
}
