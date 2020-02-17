package zeldaGame.graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

//Upar imagens 2d para o game
public class SpriteSheet {

	private BufferedImage spriteSheet;
	
	public SpriteSheet(String path) {
		try {
			//Upa a imagem para o atributo spriteSheet
			spriteSheet = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getSprite(int x, int y, int width, int height) {
		return spriteSheet.getSubimage(x, y, width, height);
	}
	
}
