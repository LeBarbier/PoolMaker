package joueur;

public interface Joueur {
	public int getButs();
	public int getAssistances();
	public String getNom();
	public String getEquipe();
	public String getPosition();
	public int getJoueurID();
	public boolean getChoisit();
	public void setChoisit(boolean _choisit);
}
