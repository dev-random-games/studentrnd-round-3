package game;

import org.lwjgl.opengl.GL11;

import mvc.TextureSprite;

public class GatlingTower extends Tower{
	
	TextureSprite base, guns, tower, hoverRing, upgradeArrow;
	
	float upgradeArrowTranslate = 0;

	public GatlingTower(Map map, float x, float y) {
		super(map, x, y);
		
		range = 150;
		damage = 20;
		cooldown = 2;
		
		evolution = 0;
		evolutionScalar = 1.25;
		
		base = new TextureSprite(this.x, this.y, w, h, 20, "src/data/RabbitBase128.png");
		guns = new TextureSprite(this.x, this.y, w, h, 30, "src/data/RabbitArms128.png");
		tower = new TextureSprite(this.x, this.y, w, h, 40, "src/data/RabbitBody128.png");
		hoverRing = new TextureSprite(this.x, this.y, w, h, 45, "src/data/hoverRing.png");
		upgradeArrow = new TextureSprite(this.x + w / 4, this.y, w / 2, h / 2, 45, "src/data/upgrade.png");
	}

	public void draw(){
		base.setRot((float) r);
		guns.setRot((float) r);
		tower.setRot((float) r);
		
		base.draw();
		guns.draw();
		tower.draw();
		
		r += 1;
		
		if (mouseHovering){
			GL11.glColor4f(1.0f, 1.0f, 1.0f, .5f);
			hoverRing.w = hoverRing.h = (float) (range * 2);
			hoverRing.x = (float) (x + w / 2 - range);
			hoverRing.y = (float) (y + h / 2 - range);
			hoverRing.r += 10;
			hoverRing.draw();
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			
			upgradeArrowTranslate ++;
			upgradeArrow.y = y + upgradeArrowTranslate;
			upgradeArrow.draw();
			if (upgradeArrowTranslate > h){
				upgradeArrowTranslate = 0;
			}
		}
	}
}
