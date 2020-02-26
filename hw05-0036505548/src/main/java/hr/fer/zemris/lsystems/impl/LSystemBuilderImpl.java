package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import java.util.Arrays;
import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.*;

/**
 * This class represents implementation of LSystemBuilder that creates LSystem
 * to create Lindermayers fractales. It can be created by giving array of
 * strings or by calling method for method. Everything works with radians.
 * 
 * 
 * @author Antonio Filipovic
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {
	/**
	 * commands that must contain number
	 */
	public static final String[] values = { "draw", "skip", "scale", "rotate" };

	/**
	 * This dictionary represents all commands
	 */
	private Dictionary<Character, Command> commands;
	/**
	 * These are all productions used to create
	 */
	private Dictionary<Character, String> productions;
	/**
	 * Starting postions or origin
	 */
	private Vector2D origin = new Vector2D(0, 0);
	/**
	 * Angle
	 */
	private double angle = 0;
	/**
	 * Length in beginning
	 */
	private double unitLength = 0.1;
	/**
	 * Scaller unit
	 */
	private double unitLengthDegreeScaler = 1;
	/**
	 * Beginning axiom
	 */
	private String axiom = "";

	/**
	 * This is public constructor
	 */
	public LSystemBuilderImpl() {
		commands = new Dictionary<>();
		productions = new Dictionary<>();
	}

	/**
	 * This private class implements interface LSystem. 
	 * 
	 * @author Antonio Filipovic
	 *
	 */
	private class LSystemImpl implements LSystem {
		/**
		 * Generates string using productions and axiom. Depending on {@link arg0} that
		 * represents depth it uses productions multiple times
		 * 
		 * @param arg0 depth of string to be generated
		 * @return generated string
		 */
		@Override
		public String generate(int level) {
			String generatedString = axiom;

			for (int i = 0; i < level; i++) {
				StringBuilder sb = new StringBuilder();
				for (int j = 0; j < generatedString.length(); j++) {
					String production = (String) LSystemBuilderImpl.this.productions.get(generatedString.charAt(j));
					if (production == null) {
						sb.append(generatedString.charAt(j));
					} else {
						sb.append(production);
					}
				}
				generatedString = sb.toString();
			}
			return generatedString;
		}

		/**
		 * Draws lines depening on string generated and commands created
		 * 
		 * @param arg0 depth
		 * @param arg1 painter
		 */
		@Override
		public void draw(int level, Painter painter) {
			Context ctx = new Context();
			Vector2D dir = new Vector2D(1, 0);
			dir.rotate(angle);
			ctx.pushState(
					new TurtleState(origin, dir, Color.BLACK, unitLength * Math.pow(unitLengthDegreeScaler, level)));
			char[] commandsArray = generate(level).toCharArray();
			for (char c : commandsArray) {
				Command command = LSystemBuilderImpl.this.commands.get(c);
				if (command != null) {
					command.execute(ctx, painter);
				}
			}
		}
	};

	/**
	 * Returns LystemImplementation from generated LSystemBuilder
	 */
	@Override
	public LSystem build() {
		return new LSystemImpl();
	}

	/**
	 * This method is used to create LSystem from array of strings. For every string
	 * in array it calls method that processes it furhter. First parameter of string
	 * can be
	 * origin,angle,unitLength,unitLengthDegreeScaler,command,axiom,production.
	 * String can be empty.
	 * 
	 * @throws IllegalArgumentException if first parameter of string is not good.
	 */
	@Override
	public LSystemBuilder configureFromText(String[] arg0) {
		for (String s : arg0) {
			if (s.isBlank())
				continue;
			String[] parts = s.split("\\s+");

			if (parts[0].equals("origin")) {

				processOrigin(parts);

			} else if (parts[0].equals("angle")) {

				processAngle(parts);

			} else if (parts[0].equals("unitLength")) {

				processUnitLength(parts);

			} else if (parts[0].equals("unitLengthDegreeScaler")) {

				processUnitLengthDegreeScaler(parts);

			} else if (parts[0].equals("command")) {
				processComand(parts);

			} else if (parts[0].equals("axiom")) {
				processAxiom(parts);
			} else if (parts[0].equals("production")) {
				processProduction(parts);
			} else {
				throw new IllegalArgumentException("This argument was not expected in text.");
			}

		}
		return this;
	}

	/**
	 * This method registers command in Dictionary Method can be some of the
	 * following: color,draw, rotate, skip,scale, push, pop. First five of them need
	 * one more parameter.
	 * 
	 * @throws IllegalArgumentException if parameters or commands are not good
	 */
	@Override
	public LSystemBuilder registerCommand(char arg0, String arg1) {
		String[] command = arg1.split("\\s+");
		double number = 0;
		if (commands.get(arg0) != null) {
			throw new IllegalArgumentException("Symbol has already its command.");
		}
		if (Arrays.asList(values).contains(command[0])) {
			if (command.length != 2) {
				throw new IllegalArgumentException("Command was not correctly configured.");
			}
			try {
				number = Double.parseDouble(command[1]);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Command was not correctly configured.");
			}
		}
		if (command[0].equals("color")) {
			try {
				Color color = Color.decode("#" + command[1]);
				commands.put(arg0, new ColorCommand(color));
			} catch (Exception e) {
				throw new IllegalArgumentException("Command was not correctly configured.");
			}

		} else if (command[0].equals("draw")) {
			commands.put(arg0, new DrawCommand(number));

		} else if (command[0].equals("rotate")) {
			commands.put(arg0, new RotateCommand(number * Math.PI / 180));

		} else if (command[0].equals("skip")) {
			commands.put(arg0, new SkipCommand(number));

		} else if (command[0].equals("scale")) {
			commands.put(arg0, new ScaleCommand(number));
		} else if (command[0].equals("push")) {
			commands.put(arg0, new PushCommand());
		} else if (command[0].equals("pop")) {
			commands.put(arg0, new PopCommand());
		} else {
			throw new IllegalArgumentException("Command was not correctly configured.");
		}
		return this;

	}

	/**
	 * This method registers production
	 * 
	 * @return LSystemBuilder, in this case it returns this implementation
	 */
	@Override
	public LSystemBuilder registerProduction(char arg0, String arg1) {
		if (productions.get(arg0) != null) {
			throw new IllegalArgumentException("There cant be same symbols for productions.");
		}
		productions.put(arg0, arg1);
		return this;
	}

	/**
	 * Sets angle for state in beginning
	 * 
	 * @return LSystemBuilder, in this case it returns this implementation
	 */
	@Override
	public LSystemBuilder setAngle(double arg0) {
		this.angle = arg0*Math.PI/180 ;
		return this;
	}

	/**
	 * Sets first String in production. With that string production starts.
	 * 
	 * @return This implementation
	 */
	@Override
	public LSystemBuilder setAxiom(String arg0) {
		if (arg0 == null) {
			throw new IllegalArgumentException("Axiom was not correctly configured.");
		}
		this.axiom = arg0;
		return this;
	}

	/**
	 * Sets origin
	 * 
	 * @return this implementation
	 */
	@Override
	public LSystemBuilder setOrigin(double arg0, double arg1) {
		if (arg0 < 0 || arg0 > 1 || arg1 < 0 || arg1 > 1) {
			throw new IllegalArgumentException("Origin was not correctly configured.");
		}
		origin = new Vector2D(arg0, arg1);
		return this;
	}

	/**
	 * Sets unit length
	 * 
	 * @return this implementation
	 */
	@Override
	public LSystemBuilder setUnitLength(double arg0) {// treba li ograda tu
		this.unitLength = arg0;
		return this;
	}

	/**
	 * Sets Unit Length Degree Scaler
	 * 
	 * @return this implementation
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double arg0) {// treba li ograda tu
		this.unitLengthDegreeScaler = arg0;
		return this;
	}

	/**
	 * This method processes origin from text
	 * 
	 * @param parts splited parts from origin string
	 */
	private void processOrigin(String[] parts) {
		// what if origin was already created
		if (parts.length == 3) {
			try {
				double xCoordinate = Double.parseDouble(parts[1]);
				double yCoordinate = Double.parseDouble(parts[2]);
				setOrigin(xCoordinate, yCoordinate);
				return;

			} catch (NumberFormatException e) {
				// pass
			}
		}

		throw new IllegalArgumentException("Origin was not correctly configured.");

	}

	/**
	 * Processes angle
	 * 
	 * @param parts angle parts
	 */
	private void processAngle(String[] parts) {
		if (parts.length == 2) {
			try {
				this.angle = Double.parseDouble(parts[1])*Math.PI/180;
				return;
			} catch (NumberFormatException e) {
				// pass
			}
		}
		throw new IllegalArgumentException("Angle was not correctly configured.");
	}

	/**
	 * processes unit lenght
	 * 
	 * @param parts
	 */
	private void processUnitLength(String[] parts) {
		if (parts.length == 2) {
			try {
				setUnitLength(Double.parseDouble(parts[1]));
				return;
			} catch (NumberFormatException e) {
				// pass
			}

		}

		throw new IllegalArgumentException("Unit length was not correctly configured.");
	}

	/**
	 * Processes unit length scaler. It can have zero, one or multiple blanks so it
	 * must be carefully parsed
	 * 
	 * @param parts from string that was splitted
	 */
	private void processUnitLengthDegreeScaler(String[] parts) {
		if (!(parts.length == 2 || parts.length == 4 || parts.length == 3)) {
			throw new IllegalArgumentException("Unit Length Degree Scaler was not correctly configured.");
		}
		String merged = "";
		for (int i = 0; i < parts.length; i++) {
			if (i == 0)
				continue;
			merged = merged + parts[i];
		}
		if (parts.length == 2) {
			merged = merged + "/" + 1.0;
		}
		String[] newParts = merged.split("/");
		if (newParts.length == 2) {
			try {
				double firstNumber = Double.parseDouble(newParts[0]);
				double secondNumber = Double.parseDouble(newParts[1]);
				setUnitLengthDegreeScaler(firstNumber / secondNumber);
				return;
			} catch (NumberFormatException | ArithmeticException e) {
				// pass
			}
		}
		throw new IllegalArgumentException("Unit Length Degree Scaler was not correctly configured.");
	}

	/**
	 * Processes command by calling register command
	 * 
	 * @param parts splited parts from string
	 */
	private void processComand(String[] parts) {
		if (parts.length == 4) {
			registerCommand(parts[1].charAt(0), parts[2] + " " + parts[3]);
			return;
		} else if (parts.length == 3) {
			registerCommand(parts[1].charAt(0), parts[2]);
			return;
		}
		throw new IllegalArgumentException("Unit Length Degree Scaler was not correctly configured.");
	}

	/**
	 * Processes axiom and calls setAxiom
	 * 
	 * @param parts
	 */
	private void processAxiom(String[] parts) {
		if (parts.length == 2) {
			setAxiom(parts[1]);
			return;
		}
		throw new IllegalArgumentException("Axiom was not correctly configured.");
	}

	/**
	 * Processes production from origin string. Calls registerProduction.
	 * 
	 * @param parts
	 */
	private void processProduction(String[] parts) {
		if (parts.length == 3) {
			registerProduction(parts[1].charAt(0), parts[2]);
			return;
		}
		throw new IllegalArgumentException("Unit Length Degree Scaler was not correctly configured.");
	}

}
