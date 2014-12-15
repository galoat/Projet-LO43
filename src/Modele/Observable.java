package Modele;



public interface Observable {
  public void addObserver(Observer obs);
  public void removeObserver();
  public void notifyObserver(String str);
  public void notifyCoords(int iD, int dep, int ar);
}