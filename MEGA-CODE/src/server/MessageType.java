package server;

public enum MessageType {
	SERVER_MESSAGE (0),
	CONNECT (1);
	
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
