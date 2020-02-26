package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * This class represents request for context
 * 
 * @author af
 *
 */
public class RequestContext {
	/**
	 * output
	 */
	private OutputStream outputStream;
	/**
	 * charset
	 */
	private Charset charset;
	/**
	 * params
	 */
	private Map<String, String> parameters;
	/**
	 * persistent params
	 */
	private Map<String, String> persistentParameters;
	/**
	 * cookies
	 */
	private List<RCCookie> outputCookies;
	/**
	 * temp params
	 */
	private Map<String, String> temporaryParameters;
	/**
	 * header
	 */
	private boolean headerGenerated = false;
	/**
	 * encoding
	 */
	private String encoding = "UTF-8";
	/**
	 * status code
	 */
	private int statusCode = 200;
	/**
	 * text
	 */
	private String statusText = "OK";
	/**
	 * mime type
	 */
	private String mimeType = "text/html";
	/**
	 * length
	 */
	private Long contentLength = null;
	/**
	 * sid
	 */

	private String SID;
	/**
	 * dispatcher
	 */

	private IDispatcher iDispatcher;

	/**
	 * Public constructor
	 * 
	 * @param outputStream         output
	 * @param parameters           params
	 * @param persistentParameters persistnetn params
	 * @param outputCookies        cookies
	 * @param temporaryParameters  tempparams
	 * @param dispatcher           dispatcher
	 * @param SID                  sid
	 */

	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies,
			Map<String, String> temporaryParameters, IDispatcher dispatcher, String SID) {

		Objects.requireNonNull(outputStream, "Output stream cannot be null");
		this.outputStream = outputStream;
		this.parameters = parameters == null ? new LinkedHashMap<String, String>() : parameters;
		this.persistentParameters = persistentParameters == null ? new LinkedHashMap<String, String>()
				: persistentParameters;
		this.outputCookies = outputCookies == null ? new ArrayList<>() : outputCookies;
		this.temporaryParameters = temporaryParameters == null ? new LinkedHashMap<String, String>()
				: temporaryParameters;

		this.iDispatcher = dispatcher;
		this.SID = SID;
	}

	/**
	 * Constructor
	 * 
	 * @param outputStream         output
	 * @param parameters           params
	 * @param persistentParameters persistent params
	 * @param outputCookies        cookies
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
		this(outputStream, parameters, persistentParameters, outputCookies, null, null, null);

	}

	/**
	 * This class represents cookie
	 * 
	 * @author af
	 *
	 */
	public static class RCCookie {
		/**
		 * Name
		 */
		private String name;
		/**
		 * Value
		 */
		private String value;
		/**
		 * Domain
		 */
		private String domain;
		/**
		 * PAth
		 */
		private String path;
		/**
		 * Max age
		 */
		private Integer maxAge;

		/**
		 * Cookie constructor
		 * 
		 * @param name   name
		 * @param value  value
		 * @param maxAge age
		 * @param domain domain
		 * @param path   path
		 */

		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			super();
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}

		/**
		 * Getter for name
		 * 
		 * @return
		 */
		public String getName() {
			return name;
		}

		/**
		 * Getter for value
		 * 
		 * @return
		 */
		public String getValue() {
			return value;
		}

		/**
		 * GEtter for domain
		 * 
		 * @return
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * GEtter for path
		 * 
		 * @return
		 */
		public String getPath() {
			return path;
		}

		/**
		 * Getter for max age
		 * 
		 * @return
		 */
		public Integer getMaxAge() {
			return maxAge;
		}

	}

	/**
	 * method that retrieves value from parameters map (or null if no association
	 * 
	 * @param name
	 * @return
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}

	/**
	 * REturns dispathcer
	 * 
	 * @return
	 */
	public IDispatcher getIDispatcher() {
		return iDispatcher;
	}

	/**
	 * method that retrieves names of all parameters in parameters map
	 * 
	 * @return set
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(parameters.keySet());
	}

	/**
	 * method that retrieves value from persistentParameters map
	 * 
	 * @param name name
	 * @return
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}

	/**
	 * method that retrieves names of all parameters in persistent parameters map
	 * 
	 * @return
	 */
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}

	/**
	 * method that stores a value to persistentParameters map:
	 * 
	 * @param name  name
	 * @param value value
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}

	/**
	 * method that removes a value from persistentParameters map:
	 * 
	 * @param name
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}

	/**
	 * method that retrieves value from temporaryParameters map
	 * 
	 * @param name
	 * @return string
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}

	/**
	 * method that retrieves names of all parameters in temporary parameters map
	 * 
	 * @return set
	 */
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}

	/**
	 * method that retrieves an identifier which is unique for current user session
	 * 
	 * @return
	 */
	public String getSessionID() {
		return SID;
	}

	/**
	 * method that stores a value to temporaryParameters map:
	 * 
	 * @param name
	 * @param value
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}

	/**
	 * method that removes a value from temporaryParameters map:
	 * 
	 * @param name
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}

	/**
	 * Sets enc
	 * 
	 * @param encoding enc
	 */
	public void setEncoding(String encoding) {
		if (headerGenerated) {
			throw new RuntimeException();
		}
		this.encoding = encoding;
	}

	/**
	 * Sets status code
	 * 
	 * @param statusCode
	 */
	public void setStatusCode(int statusCode) {
		if (headerGenerated) {
			throw new RuntimeException();
		}
		this.statusCode = statusCode;
	}

	/**
	 * Sets status text
	 * 
	 * @param statusText
	 */
	public void setStatusText(String statusText) {
		if (headerGenerated) {
			throw new RuntimeException();
		}
		this.statusText = statusText;
	}

	/**
	 * Sets mime type
	 * 
	 * @param mimeType
	 */
	public void setMimeType(String mimeType) {
		if (headerGenerated) {
			throw new RuntimeException();
		}
		this.mimeType = mimeType;
	}

	/**
	 * Sets length
	 * 
	 * @param contentLength
	 */
	public void setContentLength(Long contentLength) {
		if (headerGenerated) {
			throw new RuntimeException();
		}
		this.contentLength = contentLength;
	}

	/**
	 * Adds cookie
	 * 
	 * @param rcCookie
	 */
	public void addRCCookie(RCCookie rcCookie) {
		if (headerGenerated) {
			throw new RuntimeException();
		}
		outputCookies.add(rcCookie);

	}

	/**
	 * REturns charser
	 * 
	 * @return
	 */
	public Charset getCharset() {
		return charset;
	}

	/**
	 * This method writes bytes
	 * 
	 * @param data data
	 * @return context
	 * @throws IOException exception
	 */
	public RequestContext write(byte[] data) throws IOException {
		if (headerGenerated == false) {
			generateHeader();

		}
		outputStream.write(data);
		outputStream.flush();
		return this;

	}

	/**
	 * This method writes data to offset
	 * 
	 * @param data   data
	 * @param offset offset
	 * @param len    len
	 * @return context
	 * @throws IOException
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException {
		if (headerGenerated == false) {
			generateHeader();

		}
		outputStream.write(data, offset, len);
		outputStream.flush();
		return this;
	}

	/**
	 * This method writes text
	 * 
	 * @param text text
	 * @return
	 * @throws IOException
	 */
	public RequestContext write(String text) throws IOException {
		if (headerGenerated == false) {
			generateHeader();

		}
		outputStream.write(text.getBytes(charset));
		outputStream.flush();
		return this;
	}

	/**
	 * This method generates header
	 */
	private void generateHeader() {
		charset = Charset.forName(encoding);
		StringBuilder sb = new StringBuilder();
		sb.append("HTTP/1.1 " + statusCode + " " + statusText + "\r\n");
		sb.append("Content-Type: " + mimeType);
		if (mimeType.startsWith("text/")) {
			sb.append("; charset=" + encoding);
		}
		sb.append("\r\n");
		if (contentLength != null) {
			sb.append("Content-Length:" + contentLength);
		}
		if (outputCookies.size() != 0) {
			StringBuilder sbForCookie = new StringBuilder();
			for (RCCookie cookie : outputCookies) {
				if (cookie.getDomain() != null) {
					sbForCookie.append("; Domain=" + cookie.getDomain());
				}
				if (cookie.getPath() != null) {
					sbForCookie.append("; Path=" + cookie.getPath());
				}
				if (cookie.getMaxAge() != null) {
					sbForCookie.append("; Max-Age=" + cookie.getMaxAge());
				}
				sb.append("Set-Cookie: " + cookie.getName() + "=\"" + cookie.getValue() + "\"" + sbForCookie.toString()
						+ " ; HttpOnly\r\n");//

				sbForCookie.setLength(0);
			}

		}
		sb.append("\r\n");

		try {
			outputStream.write(sb.toString().getBytes(StandardCharsets.ISO_8859_1));
		} catch (IOException e) {
			System.out.println("Header could not be generated.");
			return;
		}
		headerGenerated = true;
	}

}
