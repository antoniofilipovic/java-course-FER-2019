package hr.fer.zemris.java.hw16.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;

/**
 * This class represetns gallery db
 * 
 * @author af
 *
 */
public class GaleryDB {
	/**
	 * List of all pictures
	 */
	public static List<Picture> pictures =null;
	/**
	 * List of tags
	 */
	private static Set<String> tags = new LinkedHashSet<>();
	/**
	 * Initialization of db
	 * @param descriptionPath
	 */
	private static void initialization(String descriptionPath) {
		pictures=new ArrayList<>();
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get(descriptionPath));
		} catch (IOException e) {
			System.err.println("Cant read file");
		}
		for (int i = 0; i < lines.size(); i += 3) {
			String path = lines.get(i);
			String description = lines.get(i + 1);
			String allTags = lines.get(i + 2);
			String[] parts = allTags.split(",");
			for (String t : parts) {
				t = t.trim();
				if (!t.isEmpty()) {
					tags.add(t);
				}

			}
			Picture p = new Picture(path, description, parts);
			pictures.add(p);

		}

	}

	/**
	 * Initialization of db
	 * @param context
	 */
	public static void initDB(ServletContext context) {
		if(pictures==null) {
			initialization(context.getRealPath("/WEB-INF/opisnik.txt" ));
		}
		
	}
	
	/**
	 * This method returns tags
	 * @return
	 */
	public static String[] getTags() {
		return tags.toArray(new String[tags.size()]);
	}

	public static List<Picture> getPicturesPath(String keyTag) {
		Set<String> pathSet = new LinkedHashSet<>();
		List<Picture> listOfPictures = new ArrayList<>();
		for (Picture p : pictures) {
			String[] pictureTags = p.getTags();
			for (String tag : pictureTags) {
				if (tag.trim().equals(keyTag)) {
					pathSet.add(p.getPath());
					listOfPictures.add(p);
					break;
				}
			}

		}
		System.out.println(listOfPictures.size());
		return listOfPictures;
		// return pathSet.toArray(new String[pathSet.size()]);

	}
	/**
	 * Gets big picture
	 * @param path
	 * @return
	 */
	public static Picture getBigPicture(String path) {
		for (Picture p : pictures) {
			if (p.getPath().equals(path)) {
				return p;
			}

		}
		return null;
		

	}

}
