package hr.fer.zemris.java.hw16.servlets;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * This servlet prepares thumbnails if they dont exist and sends filtered
 * pictures
 * 
 * @author af
 *
 */
@WebServlet(urlPatterns = { "/servlets/thumbnails" })
public class PrepareThumbNails extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		GaleryDB.initDB(req.getServletContext());
		String filter = req.getParameter("tag");

		if (filter != null) {
			filter = filter.trim();
			if (filter.isEmpty()) {
				filter = null;
			}
		}
		Path thumbnails = Paths.get(req.getServletContext().getRealPath("/WEB-INF/slike/thumbnails"));
		if (!Files.exists(thumbnails)) {
			Files.createDirectory(thumbnails);
		}

		List<Picture> pictures = GaleryDB.getPicturesPath(filter);
		for (Picture picture : pictures) {
			if (picture.getThumbnailPath() == null) {

				Path fullPicturePath = Paths
						.get(req.getServletContext().getRealPath("/WEB-INF/slike/" + picture.getPath())).normalize();
				picture.setFullPicturePath(String.valueOf(fullPicturePath));

				picture.setThumbnailPath(
						req.getServletContext().getRealPath("/WEB-INF/slike/thumbnails/" + picture.getPath()));

				System.out.println(picture.getThumbnailPath());

				createThumbnail(picture);

			}
		}

		resp.setContentType("application/json;charset=UTF-8");

		Gson gson = new Gson();
		String jsonText = gson.toJson(pictures);

		resp.getWriter().write(jsonText);

		resp.getWriter().flush();

	}

	/**
	 * This method creates thumbnail
	 * 
	 * @param picture picture
	 * @throws IOException exception
	 */
	private void createThumbnail(Picture picture) throws IOException {
		BufferedImage bigImage = ImageIO.read(new File(picture.getFullPicturePath()));

		BufferedImage smallImage = new BufferedImage(150, 150, BufferedImage.TYPE_INT_RGB);

		Graphics2D g2d = smallImage.createGraphics();
		g2d.setComposite(AlphaComposite.Src);
		g2d.drawImage(bigImage, 0, 0, 150, 150, null);
		g2d.dispose();

		ImageIO.write(smallImage, "jpg", new File(picture.getThumbnailPath()));

	}

}
