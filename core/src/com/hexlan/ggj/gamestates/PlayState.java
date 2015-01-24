package com.hexlan.ggj.gamestates;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
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
import com.hexlan.ggj.entities.Player;
import com.hexlan.ggj.entities.Torch;
import com.hexlan.ggj.systems.GSM;

public class PlayState extends GameState
{

	private TiledMap tiledMap;
	private TiledMapRenderer tiledMapRenderer;
	private OrthographicCamera camera;

	private Player player;
	private Music music;
	
	private Torch torch;
	
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
        player.setPosition(308, 6800);
        player.setWidth(48*5/4);
        player.setHeight(64*5/4);
        player.setCollisionLayer((TiledMapTileLayer) tiledMap.getLayers().get(1));
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
		
		music = Gdx.audio.newMusic(Gdx.files.internal("sound/MainLoop.wav"));
		music.setLooping(true);
		music.play();
		
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
		
	}

	@Override
	public void handleInput() {
		player.left = Gdx.input.isKeyPressed(Keys.LEFT);
		player.right = Gdx.input.isKeyPressed(Keys.RIGHT);
		player.jumping = Gdx.input.isKeyPressed(Keys.UP);
	}

	@Override
	public void update(float dt) 
	{
		player.update(dt);
		torch.update(dt);
		
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
		
        TiledMapTileLayer objects = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        
        sb.begin();
        
        for(int x = 0; x < objects.getWidth(); x++)
        {
        	for(int y = 0; y < objects.getHeight(); y++)
            {
        		Cell cell = objects.getCell(x, y);
        		if(cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("torch"))
        		{
        			sb.draw(torch.getImage(), x*objects.getTileWidth() + 16 - (camera.position.x - camera.viewportWidth/2), y*objects.getTileHeight() + 8 - (camera.position.y - camera.viewportHeight/2));
        		}
            }
        }
        
		
		if(player.facingRight) { sb.draw(player.animation.getImage(), player.getX() + player.animation.getImage().getRegionWidth()/2 - (camera.position.x - camera.viewportWidth/2), player.getY() - player.animation.getImage().getRegionHeight()/2 - (camera.position.y - camera.viewportHeight/2), -player.animation.getImage().getRegionWidth()*5/4, player.animation.getImage().getRegionHeight()*5/4); }
		else {sb.draw(player.animation.getImage(), player.getX() - player.animation.getImage().getRegionWidth()/2 - (camera.position.x - camera.viewportWidth/2), player.getY() - player.animation.getImage().getRegionHeight()/2 - (camera.position.y - camera.viewportHeight/2), player.animation.getImage().getRegionWidth()*5/4, player.animation.getImage().getRegionHeight()*5/4); }
		sb.end();
	}

}
