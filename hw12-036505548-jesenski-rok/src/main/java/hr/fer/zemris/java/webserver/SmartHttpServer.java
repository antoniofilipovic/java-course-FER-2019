package hr.fer.zemris.java.webserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * This class represents smart htpp server for serving clients
 * 
 * @author af
 *
 */
public class SmartHttpServer {
	/**
	 * Adress
	 */
	private String address;
	/**
	 * Domain name
	 */
	private String domainName;
	/**
	 * Port
	 */
	private int port;
	/**
	 * Worker threads
	 */
	private int workerThreads;
	/**
	 * Session timeout
	 */
	private int sessionTimeout;
	/**
	 * Mime types
	 */
	private Map<String, String> mimeTypes = new HashMap<String, String>();
	/**
	 * Server thread
	 */
	private ServerThread serverThread;
	/**
	 * Thread pool
	 */
	private ExecutorService threadPool;
	/**
	 * Document root
	 */
	private Path documentRoot;
	/***
	 * Workers map
	 */
	private Map<String, IWebWorker> workersMap = new HashMap<>();
	/**
	 * Work
	 */
	private volatile boolean work = true;
	/**
	 * Sessions
	 */

	private Map<String, SessionMapEntry> sessions = new ConcurrentHashMap<String, SmartHttpServer.SessionMapEntry>();
	/**
	 * random generator
	 */
	private Random sessionRandom = new Random();

	@SuppressWarnings("deprecation")
	/**
	 * Public constructor for server
	 * 
	 * @param configFileName file name
	 */
	public SmartHttpServer(String configFileName) {
		InputStream is = null;
		try {
			is = Files.newInputStream(Paths.get(configFileName));
		} catch (IOException | InvalidPathException e1) {
			System.out.println("Wrong server path.");
			System.exit(-1);
		}
		Properties props = new Properties();
		try {
			props.load(is);
		} catch (IOException e) {
			System.out.println("Error while reading from server properties");
			System.exit(-1);
		}

		address = props.getProperty("server.address");
		domainName = props.getProperty("server.domainName");
		port = Integer.parseInt(props.getProperty("server.port"));
		workerThreads = Integer.parseInt(props.getProperty("server.workerThreads"));
		sessionTimeout = Integer.parseInt(props.getProperty("session.timeout"));
		documentRoot = Paths.get(props.getProperty("server.documentRoot"));

		InputStream isMime = null;

		try {
			isMime = Files.newInputStream(Paths.get(props.getProperty("server.mimeConfig")));
		} catch (IOException e) {
			System.out.println("Wrong path for mime properties.");
			System.exit(-1);
		}
		Properties propsMime = new Properties();

		try {
			propsMime.load(isMime);
		} catch (IOException e) {
			System.out.println("Error while reading from mime properties");
			System.exit(-1);
		}

		mimeTypes.put("html", propsMime.getProperty("html"));
		mimeTypes.put("htm", propsMime.getProperty("htm"));
		mimeTypes.put("txt", propsMime.getProperty("txt"));
		mimeTypes.put("gif", propsMime.getProperty("gif"));
		mimeTypes.put("png", propsMime.getProperty("png"));
		mimeTypes.put("jpg", propsMime.getProperty("jpg"));

		Path workers = Paths.get(props.getProperty("server.workers"));
		List<String> lines = null;
		try {
			lines = Files.readAllLines(workers);
		} catch (IOException e1) {
			System.out.println("Can't read from workers properites.");
		}
		for (String line : lines) {
			if (line.startsWith("#")) {
				continue;
			}
			String[] parts = line.split("=");
			if (parts.length != 2) {
				continue;
			}
			if (workersMap.containsKey(parts[0])) {
				throw new RuntimeException("There are multiple lines with same path.");
			}
			Class<?> referenceToClass = null;
			try {
				referenceToClass = this.getClass().getClassLoader().loadClass(parts[1].trim());
			} catch (ClassNotFoundException e) {
				System.out.println("Class not found.");
				System.exit(-1);
			}
			Object newObject = null;
			try {
				newObject = referenceToClass.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				System.out.println("Can't allocate new instance of this class.");
			}
			IWebWorker iww = (IWebWorker) newObject;
			workersMap.put(parts[0].trim(), iww);

		}

		start();

	}

	/**
	 * This method starts server
	 */
	protected synchronized void start() {
		if (threadPool == null) {
			threadPool = Executors.newFixedThreadPool(workerThreads);
		}
		if (serverThread == null) {
			serverThread = new ServerThread();
			serverThread.start();

		}

	}

	/**
	 * This method stops server
	 */
	protected synchronized void stop() {
		serverThread.kill();
		threadPool.shutdown();
	}

	/**
	 * This class represents server thread
	 * 
	 * @author af
	 *
	 */
	protected class ServerThread extends Thread {
		/**
		 * This method kills server
		 */
		private void kill() {
			work = false;
		}

		@SuppressWarnings("resource")
		@Override
		public void run() {
			ServerSocket serverSocket = null;
			try {
				serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress((InetAddress) null, port));
			} catch (IOException e2) {
				System.out.println("Can't start server socket"+e2.getMessage());
				System.exit(-1);
			}

			while (work) {
				Socket client;
				try {
					client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);
				} catch (IOException e1) {
					System.out.println("Exception in server socket.");
				}

			}
			try {
				serverSocket.close();
			} catch (IOException e) {
				System.out.println("Error while trying to close the socket");
			}

		}
	}

	/**
	 * This class represents client worker
	 * 
	 * @author af
	 *
	 */
	private class ClientWorker implements Runnable, IDispatcher {
		/**
		 * Socket
		 */
		private Socket csocket;
		/**
		 * Input stream
		 */
		private PushbackInputStream istream;
		/**
		 * Output stream
		 */
		private OutputStream ostream;
		/**
		 * Version
		 */
		private String version;
		/**
		 * Method
		 */
		private String method;
		/**
		 * Host
		 */
		private String host;
		/**
		 * Parameters
		 */
		private Map<String, String> params = new LinkedHashMap<String, String>();
		/**
		 * Temp parameters
		 */
		private Map<String, String> tempParams = new HashMap<String, String>();
		/**
		 * Perm parameters
		 */
		private Map<String, String> permPrams = new HashMap<String, String>();
		/**
		 * Cookies
		 */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		/**
		 * sid
		 */
		private String SID;
		/**
		 * Context
		 */
		private RequestContext rc = null;

		/**
		 * Public constructor
		 * 
		 * @param csocket socket
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = new BufferedOutputStream(csocket.getOutputStream());

				byte[] request = readRequest(istream);
				if (request == null) {
					sendError(ostream, 400, "Bad request");

					ostream.flush();
					csocket.close();
					return;
				}
				String requestStr = new String(request, StandardCharsets.US_ASCII);

				List<String> headers = extractHeaders(requestStr);
				String[] firstLine = headers.isEmpty() ? null : headers.get(0).split(" ");
				if (firstLine == null || firstLine.length != 3) {
					
					sendError(ostream, 400, "Bad request");

					ostream.flush();
					csocket.close();
					return;
				}

				method = firstLine[0].toUpperCase();
				if (!method.equals("GET")) {
					sendError(ostream, 405, "Method Not Allowed");
					ostream.flush();
					csocket.close();
					return;
				}

				version = firstLine[2].toUpperCase();

				if (!version.equals("HTTP/1.1")) {
					sendError(ostream, 505, "HTTP Version Not Supported");
					ostream.flush();
					csocket.close();
					return;
				}

				for (String s : headers) {
					if (s.contains("Host:")) {
						host = s.substring(s.indexOf(':') + 1);
					}
				}
				if (host != null) {
					host = host.substring(0, host.indexOf(":"));
				} else {
					host = domainName;
				}

				String[] pathAndParams = firstLine[1].split("\\?");

				checkSession(headers);

				if (pathAndParams.length == 2) {
					parseParameters(pathAndParams[1]);
				}

				String path = pathAndParams[0];
				internalDispatchRequest(path, true);

			} catch (Exception ex) {
				//
			} finally {
				try {
					csocket.close();
				} catch (IOException e) {
					System.out.println("Can't close a socket.");
				}
			}

		}

		/**
		 * This method creates session
		 */
		private void createSession() {
			SID = getRandomCharacters();
			SessionMapEntry entry = new SessionMapEntry();
			entry.sid = SID;
			entry.host = host;
			entry.validUntil = System.currentTimeMillis() + sessionTimeout * 1000;
			sessions.put(entry.sid, entry);
			outputCookies.add(new RCCookie("sid", SID, null, host, "/"));
			permPrams = entry.map;

		}

		/**
		 * This method goes through headers and checks for sid and creates sessions if
		 * necessary
		 * 
		 * @param headers headers
		 * @return sid
		 */
		private synchronized void checkSession(List<String> headers) {
			String temporarySid = null;
			for (String s : headers) {
				if (s.startsWith("Cookie:")) {
					String[] parts = s.substring(s.indexOf(":") + 1).trim().split(";");
					for (String s1 : parts) {
						if (s1.startsWith("sid")) {
							temporarySid = s1.substring(s1.indexOf("=") + 1);
							temporarySid = temporarySid.substring(1, temporarySid.length() - 1);
							break;
						}
					}
				}
			}

			if (temporarySid == null) {
				createSession();
			} else {
				SessionMapEntry entry = sessions.get(temporarySid);
				if (entry == null) {
					createSession();
				} else {
					if (entry.validUntil < System.currentTimeMillis()) {
						createSession();
					} else {
						permPrams = entry.map;
						entry.validUntil = System.currentTimeMillis() + sessionTimeout * 1000;
					}
				}
			}

		}

		/**
		 * REturns random chars for sid
		 * 
		 * @return sid
		 */
		private String getRandomCharacters() {
			char[] chars = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
			StringBuilder sb = new StringBuilder(20);

			for (int i = 0; i < 20; i++) {
				char c = chars[sessionRandom.nextInt(chars.length)];
				sb.append(c);
			}
			return sb.toString();
		}

		/**
		 * Parses parameters
		 * 
		 * @param string
		 */
		private void parseParameters(String string) {
			String[] parts = string.split("&");

			for (String s : parts) {
				String[] keyValue = s.split("=");
				params.put(keyValue[0], keyValue[1]);
			}

		}

		/**
		 * REturns mime type
		 * 
		 * @param name name
		 * @return type
		 */
		private String determineMimeType(String name) {
			String ext = name.substring(name.indexOf('.') + 1, name.length());

			return mimeTypes.get(ext);

		}

		/**
		 * Generates documetent node
		 * 
		 * @param path path
		 * @return node
		 */
		private DocumentNode generateDocumentNode(Path path) {
			String docBody = "";
			try {
				docBody = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
			} catch (IOException e1) {
				System.out.println("Excpetion while reading from script.");
			}
			SmartScriptParser parser = null;
			try {
				parser = new SmartScriptParser(docBody);
			} catch (SmartScriptParserException e) {
				System.out.println("Unable to parse document!");
				System.exit(-1);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.exit(-1);
			}
			return parser.getDocumentNode();

		}

		/**
		 * Serves file
		 * 
		 * @param rc            context
		 * @param requestedFile file
		 * @param mime          type
		 * @throws IOException exception
		 */
		private void serveFile(RequestContext rc, Path requestedFile, String mime) throws IOException {

			try (InputStream is = new BufferedInputStream(Files.newInputStream(requestedFile))) {

				byte[] buf = new byte[1024];
				while (true) {
					int r = is.read(buf);
					if (r < 1)
						break;
					rc.write(buf, 0, r);
				}

			}

		}

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);

		}

		/**
		 * This method represents internal dispatch request
		 * 
		 * @param urlPath    contains / on first index
		 * @param directCall
		 * @throws Exception
		 */
		@SuppressWarnings("deprecation")
		public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {
			if(rc==null) {
				rc = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this, SID);
			}
			

			if (urlPath.startsWith("/private") && directCall) {
				sendError(ostream, 404, "Bad request");

				ostream.flush();
				csocket.close();
				return;
			}

			if (urlPath.startsWith("/ext/")) {
				Class<?> referenceToClass = null;
				StringBuilder sb = new StringBuilder();
				sb.append((this.getClass().getPackage().getName())).append(".workers.")
						.append(urlPath.replaceAll("/ext/", ""));
				try {
					referenceToClass = this.getClass().getClassLoader().loadClass(sb.toString());
				} catch (ClassNotFoundException e) {
					System.out.println("Class not found.");
					System.exit(-1);
				}
				Object newObject = null;
				try {
					newObject = referenceToClass.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					System.out.println("Can't allocate new instance of this class.");
				}
				IWebWorker iww = (IWebWorker) newObject;
				iww.processRequest(rc);
				return;
			}

			if (workersMap.containsKey(urlPath)) {
				IWebWorker webWorker = workersMap.get(urlPath);
				webWorker.processRequest(rc);
				return;
			}

			Path requestedFile = null;
			requestedFile = documentRoot.resolve(urlPath.substring(1)).toAbsolutePath();

			if (!requestedFile.startsWith(documentRoot)) {
				sendError(ostream, 403, "Forbiden path");
				ostream.flush();
				csocket.close();
				return;
			}

			if (!Files.exists(requestedFile) || !Files.isReadable(requestedFile)
					|| !Files.isRegularFile(requestedFile)) {
				sendError(ostream, 404, "Bad request");
				ostream.flush();
				csocket.close();
			
			}

			if (urlPath.endsWith(".smscr")) {
				System.out.println(requestedFile);
				DocumentNode node = generateDocumentNode(requestedFile);
				SmartScriptEngine engine = new SmartScriptEngine(node, rc);
				engine.execute();
			} else {
				String mimeType = determineMimeType(urlPath);
				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}
				rc.setMimeType(mimeType);
				rc.setStatusCode(200);
				serveFile(rc, requestedFile, mimeType);
			}

		}

	}

	/**
	 * This method stars when main program starts
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("Expected server path.");
			return;
		}
		new SmartHttpServer(args[0]);

	}

	/**
	 * This method reads request
	 * 
	 * @param is input
	 * @return bytes
	 * @throws IOException exception
	 */
	private byte[] readRequest(InputStream is) throws IOException {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int state = 0;
		l: while (true) {
			int b = is.read();
			if (b == -1)
				return null;
			if (b != 13) {
				bos.write(b);
			}
			switch (state) {
			case 0:
				if (b == 13) {
					state = 1;
				} else if (b == 10)
					state = 4;
				break;
			case 1:
				if (b == 10) {
					state = 2;
				} else
					state = 0;
				break;
			case 2:
				if (b == 13) {
					state = 3;
				} else
					state = 0;
				break;
			case 3:
				if (b == 10) {
					break l;
				} else
					state = 0;
				break;
			case 4:
				if (b == 10) {
					break l;
				} else
					state = 0;
				break;
			}
		}
		return bos.toByteArray();
	}

	/**
	 * This method extracts headers
	 * 
	 * @param requestHeader header
	 * @return
	 */
	private List<String> extractHeaders(String requestHeader) {
		List<String> headers = new ArrayList<String>();
		String currentLine = null;
		for (String s : requestHeader.split("\n")) {
			if (s.isEmpty())
				break;
			char c = s.charAt(0);
			if (c == 9 || c == 32) {
				currentLine += s;
			} else {
				if (currentLine != null) {
					headers.add(currentLine);
				}
				currentLine = s;
			}
		}
		if (!currentLine.isEmpty()) {
			headers.add(currentLine);
		}
		return headers;
	}

	/**
	 * This method sends error
	 * 
	 * @param cos
	 * @param statusCode
	 * @param statusText
	 * @throws IOException
	 */
	private void sendError(OutputStream cos, int statusCode, String statusText) throws IOException {

		cos.write(("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" + "Server: simple java server\r\n"
				+ "Content-Type: text/plain;charset=UTF-8\r\n" + "Content-Length: 0\r\n" + "Connection: close\r\n"
				+ "\r\n").getBytes(StandardCharsets.US_ASCII));
		//cos.flush();

	}

	/**
	 * This class represents session entry
	 * 
	 * @author af
	 *
	 */
	private static class SessionMapEntry {
		/**
		 * sid
		 */
		private String sid;
		/**
		 * host
		 */
		private String host;
		/**
		 * valid until
		 */
		private long validUntil;
		/**
		 * map
		 */
		private Map<String, String> map = new ConcurrentHashMap<>();

	}

}
