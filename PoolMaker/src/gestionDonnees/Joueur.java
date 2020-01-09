package gestionDonnees;

public class Joueur {
	String nom;
	String equipe;
	String position;
	int buts;
	int assistances;

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
}
