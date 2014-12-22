package Modele;


public interface Observer {
  public void update(String str);
  public void updateCoords(int iD, int suivant);
  public int updateDebutMission(int dep);
  public void updatePassagers(int attdepart, int attvoiture);
}