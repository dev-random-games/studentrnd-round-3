package mvc;

import org.lwjgl.opengl.GL11;

public class Effect {
	
	public float x, y, endX, endY;
	public int duration;
	public float r, g, b;
	
	public Effect(float x, float y, float endX, float endY, int duration, int r, int g, int b) {
		this.x = x;
		this.y = y;
		this.endX = endX;
		this.endY = endY;
		this.duration = duration;
		
		this.r = ((float) r)/255f;
		this.g = ((float) g)/255f;
		this.b = ((float) b)/255f;
	}
	
	public void draw() {
		System.out.println(duration);
		this.duration--;
		GL11.glColor3f(r, g, b);
		float w = 10;
		float depth = 50;
		float h = 10;
//		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3f(x, y, depth);
		GL11.glVertex3f(endX, endY, depth);
//		GL11.glVertex3f(x, y, depth);
//		GL11.glVertex3f(x + w, y, depth);
//		GL11.glVertex3f(x + w, y + h, depth);
//		GL11.glVertex3f(x, y + h, depth);
		GL11.glEnd();
	}
	
	public boolean shouldDie() {
		return this.duration <= 0;
	}
}
