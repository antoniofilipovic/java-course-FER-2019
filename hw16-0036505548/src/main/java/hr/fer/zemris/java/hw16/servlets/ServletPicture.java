package hr.fer.zemris.java.hw16.servlets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * This class loads picture, depending on parametar it loads thumbnail that should have been prepared or big picture
 * @author af
 *
 */
@WebServlet(urlPatterns = { "/servlets/picture" })
public class ServletPicture extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String path=req.getParameter("url");
		BufferedImage bi = ImageIO.read(new File(path));
		
		OutputStream out = resp.getOutputStream();
		ImageIO.write(bi, "jpg", out);
		out.close();
	}
}
