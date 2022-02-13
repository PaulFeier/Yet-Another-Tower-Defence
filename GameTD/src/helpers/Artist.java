package helpers;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.io.IOException;
import java.io.InputStream;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Artist {

	public static final int WIDTH = 1472, HEIGHT = 960, TILE_SIZE = 64;
	/**
	 * Creates the main frame of the game.
	 */
	public static void BeginSession() {
		Display.setTitle("Yet Another Tower Defence 1.0");
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1); // seteaza frameu ca primu pixel din coltu stanga sus sa fie de (0, 0)
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);		// pentru a face transparencyu de la png sa blending cu peisajul
	}

	public static boolean CheckCollision(float x1, float y1, float width1, float height1,
											float x2, float y2, float width2 ,float height2) {
		if (x1 + width1 > x2 && x1 < x2 + width2 && y1 + height1 > y2 && y1 < y2 + height2) 
			return true;
		return false;
	}
	/**
	 * used to help me in the past with drawing on frame, not used anymore.
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public static void DrawQuad(float x, float y, float width, float height) {
		glBegin(GL_QUADS);
		glVertex2f(x, y); 					// Top left corner
		glVertex2f(x + width, y); 			// Top right corner
		glVertex2f(x + width, y + height);  // Bottom right corner
		glVertex2f(x, y + height); 			// Bottom left corner
		glEnd();
	}
	/**
	 * The current drawing method for the frame. It uses openGL.
	 * @param tex
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public static void DrawQuadTex(Texture tex, float x, float y, float width, float height) {
		tex.bind();
		glTranslatef(x, y, 0);	
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(0, 0);
		glTexCoord2f(1, 0);
		glVertex2f(width, 0);
		glTexCoord2f(1, 1);
		glVertex2f(width, height);
		glTexCoord2f(0, 1);
		glVertex2f(0, height);
		
		glEnd();
		glLoadIdentity();
	}
	/**
	 * Loads a custom picture (preferably of 64 x 64 pix) into the game and can display it on the main frame.
	 * @param path -> example: C:\Users\Paul\Desktop\image.png
	 * @param fileType -> PNG, JPEG, etc. It only accepts PNG though.
	 * @return the said texture 
	 */
	public static Texture LoadTexture(String path, String fileType) {		
		Texture tex = null;
		InputStream in = ResourceLoader.getResourceAsStream(path);
		try {
			tex = TextureLoader.getTexture(fileType, in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tex;
	}
	/**
	 * A quick solution to load a texture in the game. Instead of writing LoadTexture("Resources/Image.png", "PNG"), you just
	 * type QuickLoad("Image.png") and it makes your life much easier.
	 * @param name -> name of the said image that you want to incorporate in the game
	 * @return the texture of the image
	 */
	public static Texture QuickLoad(String name) {
		Texture tex = null;	
		tex = LoadTexture("Resources/" + name + ".png", "PNG");	
		return tex;
	}
	/**
	 * This method is used to animate the rotation of the towers which are used to shoot the enemies;
	 * @param tex -> tower's texture
	 * @param x 
	 * @param y 
	 * @param width 
	 * @param height
	 * @param angle
	 */
	public static void DrawQuadTexRotate(Texture tex, float x, float y, float width, float height, float angle) {
		tex.bind();
		glTranslatef(x + width / 2, y + height / 2, 0);	
		glRotatef(angle, 0, 0, 1);
		glTranslatef(- width / 2, - height / 2, 0);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(0, 0);
		glTexCoord2f(1, 0);
		glVertex2f(width, 0);
		glTexCoord2f(1, 1);
		glVertex2f(width, height);
		glTexCoord2f(0, 1);
		glVertex2f(0, height);
		glEnd();
		glLoadIdentity();
	}
}
