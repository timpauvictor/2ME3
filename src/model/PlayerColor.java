package model;


public enum PlayerColor {//Two colours of the player
    Blue, Red, Black;
	
	public static PlayerColor convert(String str) {
		if (str.equals("Red")) {return PlayerColor.Red;}
		else if (str.equals("Blue")) {return PlayerColor.Blue;}
		else {return PlayerColor.Black;}
	}

	public String toString() {
        if (this == this.Blue)
            return "Blue";
        else if (this == this.Red)
            return "Red";
        else
            return "Black";
    }
}
