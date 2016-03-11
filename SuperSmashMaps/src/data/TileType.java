package data;

public enum TileType {

	Primary("res/primaryTile.png", true), Secondary("res/secondaryTile.png", false), Background("res/background.png", true);
	
	String textureName;
	boolean passable;
	
	TileType(String textureName, boolean passable){
		this.textureName = textureName;
		this.passable = passable;
	}
}
