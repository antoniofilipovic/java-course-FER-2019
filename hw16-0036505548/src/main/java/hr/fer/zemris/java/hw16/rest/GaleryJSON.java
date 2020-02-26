package hr.fer.zemris.java.hw16.rest;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import com.google.gson.Gson;

import hr.fer.zemris.java.hw16.servlets.GaleryDB;
import hr.fer.zemris.java.hw16.servlets.Picture;

/**
 * This class uses GSON to work with JSON format
 * 
 * @author af
 */
@Path("/slika")
public class GaleryJSON {
	/**
	 * This method returns all tags as list
	 * 
	 * @return
	 */

	@Context ServletContext sc;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTagsList() {
		Gson gson = new Gson();
		String text = gson.toJson(GaleryDB.getTags());

		return Response.status(Status.OK).entity(text).build();
	}

	@Path("{tag}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPicturesWithTag(@PathParam("tag") String filter) throws IOException {

		if (filter != null) {
			filter = filter.trim();
			if (filter.isEmpty()) {
				filter = null;
			}
		}
		java.nio.file.Path thumbnails = Paths.get(sc.getRealPath("/WEB-INF/slike/thumbnails"));
		if (!Files.exists(thumbnails)) {
			Files.createDirectory(thumbnails);
		}

		List<Picture> pictures = GaleryDB.getPicturesPath(filter);
		for (Picture picture : pictures) {
			if (picture.getThumbnailPath() == null) {

				java.nio.file.Path fullPicturePath = Paths
						.get(sc.getRealPath("/WEB-INF/slike/" + picture.getPath())).normalize();
				picture.setFullPicturePath(String.valueOf(fullPicturePath));

				picture.setThumbnailPath(
						sc.getRealPath("/WEB-INF/slike/thumbnails/" + picture.getPath()));

				System.out.println(picture.getThumbnailPath());

				createThumbnail(picture);

			}
		}

		

		Gson gson = new Gson();
		String jsonText = gson.toJson(pictures);

		return Response.status(Status.OK).entity(jsonText).build();

	}

	/**
	 * This method returns big picture
	 * 
	 * @param path path
	 * @return big picture as json format
	 */

	@Path("/bigPicture/{path}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBigPicture(@PathParam("path") String path) {

		if (path != null) {
			path = path.trim();
			if (path.isEmpty()) {
				path = null;
			}
		}
		Picture picture = GaleryDB.getBigPicture(path);

		Gson gson = new Gson();
		String jsonText = gson.toJson(picture);
		return Response.status(Status.OK).entity(jsonText).build();

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
