package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
/**
 * This class represents jpa implementation of dao
 * @author af
 *
 */
public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

	@Override
	public void createUser(BlogUser user) throws DAOException {
		JPAEMProvider.getEntityManager().persist(user);

	}

	@Override
	public BlogUser getUser(String nick) throws DAOException {
		@SuppressWarnings("unchecked")
		List<BlogUser> blogUsers = JPAEMProvider.getEntityManager()
				.createQuery("SELECT b from BlogUser as b where b.nick=:n1").setParameter("n1", nick).getResultList();

		if (blogUsers.isEmpty()) {
			return null;
		} else {
			return blogUsers.get(0);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BlogUser> getUsers() throws DAOException {

		return JPAEMProvider.getEntityManager().createQuery("SELECT b from BlogUser as b ").getResultList();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BlogEntry> getBlogEntries(BlogUser user) throws DAOException {

		return JPAEMProvider.getEntityManager().createQuery("SELECT b from BlogEntry as b where b.creator=:n1 ")
				.setParameter("n1", user).getResultList();

	}

	@Override
	public void createBlogEntry(BlogEntry blogEntry) throws DAOException {
		EntityManager em=JPAEMProvider.getEntityManager();
		
		if (blogEntry.getId() != null) {
            em.merge(blogEntry);
        } else {
        	em.persist(blogEntry);
        }
		

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BlogComment> getBlogComments(BlogEntry entry) throws DAOException {
		return JPAEMProvider.getEntityManager().createQuery("SELECT b from BlogComment as b where b.blogEntry=:n1 ")
				.setParameter("n1", entry).getResultList();
	}

	@Override
	public void createBlogComment(BlogComment comment) throws DAOException {
		JPAEMProvider.getEntityManager().persist(comment);
		
	}

}