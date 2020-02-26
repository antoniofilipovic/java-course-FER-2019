package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;
/**
 * This class represents circle worker
 * @author af
 *
 */
public class CircleWorker implements IWebWorker  {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2d = image.createGraphics();
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
		g2d.setColor(Color.RED);
		g2d.fillOval(0, 0, image.getWidth(), image.getHeight());
		
		g2d.dispose();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		ImageIO.write(image, "png", bos);
		
		context.setMimeType("image/png");

		byte[] podatci = bos.toByteArray();


		context.write(podatci);
	
		
	}

}
