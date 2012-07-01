package server;

public enum MessageType {
	SERVER_MESSAGE (0),
	CONNECT (1),
	DISCONNECT (2),
	ADD_TOWER (3),
	ADD_MONSTER (4),
	REQUEST_STATE (5),
	PROVIDE_STATE (6),
	MOVE_MONSTER (7),
	UPGRADE (8);
	
	
	private final int index;
	
	MessageType(int index){
		this.index = index;
	}
	
	public char value(){
		return (char) index;
	}
	
	public static MessageType translate(char value){
		return MessageType.values()[(int) value];
	}
}
