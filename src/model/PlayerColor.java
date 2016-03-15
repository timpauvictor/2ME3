package model;


public enum PlayerColor {//Two colours of the player
    Blue, Red, Black;
	
	public static PlayerColor converter(String str) {
		if (str.equals("Red")) {return PlayerColor.Red;}
		else if (str.equals("Blue")) {return PlayerColor.Blue;}
		else {return PlayerColor.Black;}
	}
}
