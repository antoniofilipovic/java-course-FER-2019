package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * This class represents blog entry. Every entry has its creator
 * 
 * @author af
 *
 */
@Entity
@Table(name = "blog_entries")
public class BlogEntry {
	/**
	 * Id
	 */
	private Long id;
	/**
	 * Comments
	 */
	private List<BlogComment> comments = new ArrayList<>();
	/**
	 * Created at
	 */
	private Date createdAt;
	/**
	 * Last modified at
	 */
	private Date lastModifiedAt;
	/**
	 * Title
	 */
	private String title;
	/**
	 * Text
	 */
	private String text;
	/**
	 * Creator
	 */
	private BlogUser creator;

	/**
	 * Getter for id
	 * 
	 * @return id
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	/**
	 * Setter for id
	 * @param id id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * Getter for comments
	 * @return comments
	 */
	@OneToMany(mappedBy = "blogEntry", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderBy("postedOn")
	public List<BlogComment> getComments() {
		return comments;
	}
	/**
	 * Setter for comments
	 * @param comments comments
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}
	
	public void addComment(BlogComment comment) {
		this.comments.add(comment);
	}
	/**
	 * Getter for created at date
	 * @return created at date
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getCreatedAt() {
		return createdAt;
	}
	/**
	 * Setter for created at date
	 * @param createdAt created at
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	/**
	 * Getter for last modified at
	 * @return
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}
	/**
	 * Setter for last modifed at
	 * @param lastModifiedAt
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}
	/**
	 * Getter for title
	 * @return title
	 */
	@Column(length = 200, nullable = false)
	public String getTitle() {
		return title;
	}
	/**
	 * Setter for title
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * Getter for text
	 * @return text
	 */
	@Column(length = 4096, nullable = false)
	public String getText() {
		return text;
	}
	/**
	 * Setter for text
	 * @param text text
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * Getter for creator
	 * @return creator
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	public BlogUser getCreator() {
		return creator;
	}
	/**
	 * Setter for creator
	 * @param creator
	 */
	public void setCreator(BlogUser creator) {
		this.creator = creator;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}