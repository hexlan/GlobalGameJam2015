package com.hexlan.ggj.gamestates;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.hexlan.ggj.entities.Animation;
import com.hexlan.ggj.entities.Arrow;
import com.hexlan.ggj.entities.Player;
import com.hexlan.ggj.entities.Torch;
import com.hexlan.ggj.systems.GSM;

public class PlayState extends GameState
{

	private TiledMap tiledMap;
	private TiledMapRenderer tiledMapRenderer;
	private OrthographicCamera camera;

	private Player player;
	
	private Torch torch;
	private Texture spikes;
	private Texture treasure;
	private Texture block;
	private Texture banner;
	
	ShapeRenderer sr;
	public static boolean pause = false;
	public float pauseCounter = 0.0f;
	
	ArrayList<Arrow> arrows;
	int arrowCounter = 0;
	
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
        player.setPosition(256, 4648);
        player.setWidth(36*5/4);
        player.setHeight(64*5/4);
        player.setCollisionLayer((TiledMapTileLayer) tiledMap.getLayers().get(1));
        player.hazardLayer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        player.sprites = new ArrayList<TextureRegion[]>();
        player.sprites.add(new TextureRegion[4]);
        player.sprites.add(new TextureRegion[6]);
        player.sprites.add(new TextureRegion[1]);
        player.sprites.add(new TextureRegion[1]);
        Texture temp = new Texture("images/Idle.png");
        for(int i = 0; i < 4; i++)
		{
			player.sprites.get(player.IDLE)[i] = new TextureRegion(temp, i*64, 0, 64, 64);
		}
        
        temp = new Texture("images/run.png");
        for(int i = 0; i < 6; i++)
		{
			player.sprites.get(player.WALKING)[i] = new TextureRegion(temp, i*72, 0, 72, 72);
		}
        
        temp = new Texture("images/jump.png");
		player.sprites.get(player.JUMPING)[0] = new TextureRegion(temp, 0, 0, 72, 72);
		
		temp = new Texture("images/fall.png");
		player.sprites.get(player.FALLING)[0] = new TextureRegion(temp, 0, 0, 72, 72);
        
        player.animation = new Animation();
        player.animation.setFrames(player.sprites.get(player.IDLE));
        player.animation.setDelay(player.SPRITEDELAYS[player.IDLE]);
        player.animation.setNumFrames(player.NUMFRAMES[player.IDLE]);
        
        sr = new ShapeRenderer();
		sr.setColor(Color.CYAN);
		Gdx.gl.glLineWidth(3);
		
		torch = new Torch();
		torch.setPosition(64, 80);
		torch.animation = new Animation();
		TextureRegion[] textures = new TextureRegion[4];
		temp = new Texture("images/Torch.png");
		for(int i = 0; i < 4; i++)
		{
			textures[i] = new TextureRegion(temp, i*16, 0, 16, 16);
		}
		torch.animation.setFrames(textures);
		torch.animation.setDelay(8);
		torch.animation.setNumFrames(4);
		
		spikes = new Texture("images/Spikes.png");
		treasure = new Texture("maps/Treasure.png");
		block = new Texture("images/Block.png");
		banner = new Texture("maps/banner.png");
		
		arrows = new ArrayList<Arrow>();
	}

	@Override
	public void handleInput() {
		if(!pause)
		{
			player.left = Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A);
			player.right = Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D);
			player.jumping = Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W);

		}
		
		
		//System.out.println("X: "+player.getX() + "\nY: "+player.getY());
	}

	@Override
	public void update(float dt) 
	{
		if(pause)
		{
			pauseCounter += 0.005f;
		}
		
		arrowCounter++;
		if(arrowCounter >= 180)
		{
			arrows.add(new Arrow(78, 1340, (TiledMapTileLayer) tiledMap.getLayers().get(1), true));
			arrows.add(new Arrow(2270, 910, (TiledMapTileLayer) tiledMap.getLayers().get(1), false));
			arrows.add(new Arrow(78, 480, (TiledMapTileLayer) tiledMap.getLayers().get(1), true));
			
			arrows.add(new Arrow(6832, 5135, (TiledMapTileLayer) tiledMap.getLayers().get(1), false));
			arrows.add(new Arrow(6832, 5900, (TiledMapTileLayer) tiledMap.getLayers().get(1), false));
			
			arrowCounter = 0;
		}
		
		for(int i = 0; i < arrows.size(); i++)
		{
			arrows.get(i).update(dt);
			arrows.get(i).hitPlayer(player);
			if(arrows.get(i).Collision())
			{
				arrows.remove(i);
				i--;
			}
		}
		player.update(dt);
		torch.update(dt);
		
		camera.position.set(player.getX(), player.getY(), 0);
		if(GSM.shaking) 
		{
			camera.position.x += Math.random() * 10 - 10 / 2;
			camera.position.y += Math.random() * 10 - 10 / 2;
		}
		
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
		
        TiledMapTileLayer objects = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        
        sb.begin();
        
        for(int i = 0; i < arrows.size(); i++)
        {
        	if(arrows.get(i).goingRight) sb.draw(arrows.get(i).getTexture(), arrows.get(i).getX() - arrows.get(i).getWidth()/2 - (camera.position.x - camera.viewportWidth/2) , arrows.get(i).getY() - arrows.get(i).getHeight()/2 - (camera.position.y - camera.viewportHeight/2));
        	else sb.draw(arrows.get(i).getTexture(), arrows.get(i).getX() - arrows.get(i).getWidth()/2 - (camera.position.x - camera.viewportWidth/2) , arrows.get(i).getY() - arrows.get(i).getHeight()/2 - (camera.position.y - camera.viewportHeight/2), -arrows.get(i).getTexture().getWidth(), arrows.get(i).getTexture().getHeight());
        }
        
        for(int x = 0; x < objects.getWidth(); x++)
        {
        	for(int y = 0; y < objects.getHeight(); y++)
            {
        		Cell cell = objects.getCell(x, y);
        		if(cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("torch"))
        		{
        			sb.draw(torch.getImage(), x*objects.getTileWidth() + 16 - (camera.position.x - camera.viewportWidth/2), y*objects.getTileHeight() + 8 - (camera.position.y - camera.viewportHeight/2));
        		}
        		if(cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("spike"))
        		{
        			sb.draw(spikes, x*objects.getTileWidth() - (camera.position.x - camera.viewportWidth/2), y*objects.getTileHeight() - (camera.position.y - camera.viewportHeight/2));
        		}
        		if(cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("treasure1"))
        		{
        			sb.draw(treasure, x*objects.getTileWidth() - (camera.position.x - camera.viewportWidth/2), y*objects.getTileHeight() - (camera.position.y - camera.viewportHeight/2));
        		}
        		if(cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocks1"))
        		{
        			sb.draw(block, x*objects.getTileWidth() - (camera.position.x - camera.viewportWidth/2), y*objects.getTileHeight() - (camera.position.y - camera.viewportHeight/2));
        		}
        		if(cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("treasure2"))
        		{
        			sb.draw(treasure, x*objects.getTileWidth() - (camera.position.x - camera.viewportWidth/2), y*objects.getTileHeight() - (camera.position.y - camera.viewportHeight/2));
        		}
        		if(cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocks2"))
        		{
        			sb.draw(block, x*objects.getTileWidth() - (camera.position.x - camera.viewportWidth/2), y*objects.getTileHeight() - (camera.position.y - camera.viewportHeight/2));
        		}
        		if(cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("treasure3"))
        		{
        			sb.draw(treasure, x*objects.getTileWidth() - (camera.position.x - camera.viewportWidth/2), y*objects.getTileHeight() - (camera.position.y - camera.viewportHeight/2));
        		}
        		if(cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocks3"))
        		{
        			sb.draw(block, x*objects.getTileWidth() - (camera.position.x - camera.viewportWidth/2), y*objects.getTileHeight() - (camera.position.y - camera.viewportHeight/2));
        		}
        		if(cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("treasure4"))
        		{
        			sb.draw(treasure, x*objects.getTileWidth() - (camera.position.x - camera.viewportWidth/2), y*objects.getTileHeight() - (camera.position.y - camera.viewportHeight/2));
        		}
        		if(cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("banner"))
        		{
        			sb.draw(banner, x*objects.getTileWidth() - (camera.position.x - camera.viewportWidth/2), y*objects.getTileHeight() - (camera.position.y - camera.viewportHeight/2)-48);
        		}
            }
        }
        
		
		if(player.facingRight) { sb.draw(player.animation.getImage(), player.getX() + player.animation.getImage().getRegionWidth()/2 - (camera.position.x - camera.viewportWidth/2), player.getY() - player.animation.getImage().getRegionHeight()/2 - (camera.position.y - camera.viewportHeight/2), -player.animation.getImage().getRegionWidth()*5/4, player.animation.getImage().getRegionHeight()*5/4); }
		else {sb.draw(player.animation.getImage(), player.getX() - player.animation.getImage().getRegionWidth()/2 - (camera.position.x - camera.viewportWidth/2), player.getY() - player.animation.getImage().getRegionHeight()/2 - (camera.position.y - camera.viewportHeight/2), player.animation.getImage().getRegionWidth()*5/4, player.animation.getImage().getRegionHeight()*5/4); }
		sb.end();
		
		if(pause)
		{
			Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, pauseCounter);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			if(pauseCounter >= 1.0)
			{
				pause = false;
				GSM.shaking = false;
				gsm.set(new CreditsState(gsm));
			}
		}
	}

}
