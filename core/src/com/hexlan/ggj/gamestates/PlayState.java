package com.hexlan.ggj.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.hexlan.ggj.entities.Player;
import com.hexlan.ggj.systems.GSM;

public class PlayState extends GameState
{

	private TiledMap tiledMap;
	private TiledMapRenderer tiledMapRenderer;
	private OrthographicCamera camera;

	private Player player;
	
	ShapeRenderer sr;
	
	public PlayState(GSM gsm) 
	{
		super(gsm);
		
		float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();
        tiledMap = new TmxMapLoader().load("maps/Map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        
        player = new Player();
        player.setPosition(308, 400);
        player.setWidth(48);
        player.setHeight(64);
        player.setTexture(new Texture("images/hero.png"));
        player.setCollisionLayer((TiledMapTileLayer) tiledMap.getLayers().get(0));
        
        sr = new ShapeRenderer();
		sr.setColor(Color.CYAN);
		Gdx.gl.glLineWidth(3);
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		player.left = Gdx.input.isKeyPressed(Keys.LEFT);
		player.right = Gdx.input.isKeyPressed(Keys.RIGHT);
		player.jumping = Gdx.input.isKeyPressed(Keys.UP);

		System.out.println("" + player.falling);
	}

	@Override
	public void update(float dt) 
	{
		player.update(dt);
		
		camera.position.set(player.getX(), player.getY(), 0);
		
		MapProperties prop = tiledMap.getProperties();
		int mapWidth = prop.get("width", Integer.class) * prop.get("tilewidth", Integer.class);
		int mapHeight = prop.get("height", Integer.class) * prop.get("tileheight", Integer.class);
		
		if(camera.position.x < camera.viewportWidth/2) { camera.position.x =  camera.viewportWidth/2; }
		if(camera.position.x + camera.viewportWidth/2 > mapWidth) { camera.position.x =  mapWidth - camera.viewportWidth/2; }
		
		if(camera.position.y < camera.viewportHeight/2) { camera.position.y =  camera.viewportHeight/2; }
		if(camera.position.y + camera.viewportHeight/2 > mapHeight) { camera.position.y =  mapHeight - camera.viewportHeight/2; }
	}

	@Override
	public void render(SpriteBatch sb) 
	{
		//camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
		camera.update();
		tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
		
		sb.begin();
		if(player.facingRight) { sb.draw(player.getTexture(), player.getX() + player.getWidth()/2 - (camera.position.x - camera.viewportWidth/2), player.getY() - player.getHeight()/2 - (camera.position.y - camera.viewportHeight/2), -player.getWidth(), player.getHeight()); }
		else {sb.draw(player.getTexture(), player.getX() - player.getWidth()/2 - (camera.position.x - camera.viewportWidth/2), player.getY() - player.getHeight()/2 - (camera.position.y - camera.viewportHeight/2)); }
		sb.end();
	}

}
