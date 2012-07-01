package game;

import mvc.TextureSprite;

public class GatlingTower extends Tower{
	
	TextureSprite base, guns, tower;

	public GatlingTower(Map map, float x, float y) {
		super(map, x, y);
		
		range = 100;
		damage = 20;
		cooldown = 2;
		
		evolution = 0;
		evolutionScalar = 1;
		
		base = new TextureSprite(this.x, this.y, w, h, 20, "src/data/RabbitBase128.png");
		guns = new TextureSprite(this.x, this.y, w, h, 30, "src/data/RabbitArms128.png");
		tower = new TextureSprite(this.x, this.y, w, h, 40, "src/data/RabbitBody128.png");
	}

	public void draw(){
		base.setRot((float) r);
		guns.setRot((float) r);
		tower.setRot((float) r);
		
		base.draw();
		guns.draw();
		tower.draw();
		
		r += 1;
	}
}
