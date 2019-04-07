import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import java.sql.*;
import java.util.ArrayList;

public class EventHandler_GUI extends Application {
	
	private ImageView bot = new ImageView();
	Image bot_image = new Image(EventHandler_GUI.class.getClassLoader().getResource("handler_bot.jpg").toString());
	static String userName;
	String passWord;

	private Stage stage;
	private BorderPane root;

	private Button ok;
	private Button signUp;
	private Button clear;
	private Button done;
	private TextField username;
	private PasswordField password;

	private TextField setUser;
	private PasswordField setPass;

	private TextField ev, loc;
	private Button combine;
	private ArrayList<String> events = new ArrayList<String>();
	private String eventName, location, info;
	private String eventList = "";

	private MenuBar afterLoginMenu = new MenuBar();
	private Menu eventMenu = new Menu("Events");
	private Menu helpMenu = new Menu("Help");
	private Menu settings = new Menu("Settings");

	private MenuItem currEvents = new MenuItem("My Events");
	private MenuItem newEvents = new MenuItem("Events Available");
	private MenuItem myLoc = new MenuItem("Change My Location");
	private MenuItem logOut = new MenuItem("Log Out");
	private MenuItem account = new MenuItem("My Account");
	private MenuItem about = new MenuItem("About Us");
	private MenuItem pwChange = new MenuItem("Change Password");
	SelectHandler bob = new SelectHandler();

	HBox signUpBox;
	HBox beforeLogin;
	HBox afterLogin;
	VBox login;
	VBox makeAcct;
	TextField us;
	TextField pa;
	Button changeInfo;

	public static void main(String[] args) {
		createNewTable();
		launch();
	}

	@Override
	public void start(Stage s) throws Exception {
		stage = s;
		stage.setTitle("Event Handler");
		stage.setResizable(false);
		stage.sizeToScene();

		root = new BorderPane();
		Scene scene = new Scene(root, 300, 550);
		stage.setScene(scene);

		Text name = new Text("EventHandler");
		name.setFill(Color.web("#ffffff"));
		name.setFont(Font.font("Monospaced", FontWeight.EXTRA_BOLD, 30));
		name.setTextAlignment(TextAlignment.CENTER);
		name.setStyle("-fx-background-color: #e5c430;");

		eventMenu.setStyle("-fx-background-color: #e5c430;");
		settings.setStyle("-fx-background-color: #e5c430;");
		helpMenu.setStyle("-fx-background-color: #e5c430;");

		currEvents.setOnAction(bob);
		newEvents.setOnAction(bob);
		myLoc.setOnAction(bob);
		logOut.setOnAction(bob);
		account.setOnAction(bob);
		about.setOnAction(bob);
		pwChange.setOnAction(bob);

		HBox tbox = new HBox();
		tbox.setPadding(new Insets(15, 20, 15, 40));
		tbox.setStyle("-fx-background-color: #540784;");
		root.setTop(tbox);
		tbox.getChildren().add(name);

		beforeLogin = new HBox(8);
		beforeLogin.setPadding(new Insets(15, 20, 15, 65));
		beforeLogin.setStyle("-fx-background-color: #540784;");

		beforeLogin.setAlignment(Pos.BOTTOM_RIGHT);
		ok = new Button("Sign In");
		ok.setStyle("-fx-text-fill: #540784");
		ok.setOnAction(new ButtonHandler());

		clear = new Button("Clear");
		clear.setOnAction(new ButtonHandler());
		clear.setStyle("-fx-text-fill: #540784");

		signUp = new Button("Sign Up");
		signUp.setOnAction(new ButtonHandler());
		signUp.setStyle("-fx-text-fill: #540784");

		beforeLogin.getChildren().addAll(ok, clear, signUp);
		root.setBottom(beforeLogin);

		afterLogin = new HBox();
		afterLogin.setPadding(new Insets(15, 30, 15, 30));
		afterLogin.setAlignment(Pos.CENTER);
		afterLogin.setStyle("-fx-background-color: #540784;");
		eventMenu.getItems().addAll(currEvents, newEvents);
		helpMenu.getItems().addAll(myLoc, about);
		settings.getItems().addAll(logOut, account, pwChange);
		afterLoginMenu.getMenus().addAll(helpMenu, settings, eventMenu);
		afterLoginMenu.setStyle("-fx-background-color: #e5c430;");
		afterLogin.getChildren().add(afterLoginMenu);

		signUpBox = new HBox();
		signUpBox.setAlignment(Pos.BOTTOM_CENTER);
		signUpBox.setPadding(new Insets(15, 0, 15, 0));
		signUpBox.setStyle("-fx-background-color: #540784;");
		done = new Button("Done");

		done.setOnAction(new ButtonHandler());
		signUpBox.getChildren().add(done);

		setSignInScreen();

		stage.show();
	}

	private void setSignInScreen() {

		login = new VBox(8);
		login.setPadding(new Insets(15, 6, 15, 6));
		login.setStyle("-fx-background-color: #fcdb3a;");
		login.setAlignment(Pos.CENTER);

		Text signIn = new Text("Sign in:");
		signIn.setFill(Color.web("#540784"));
		signIn.setFont(Font.font("Monospaced", FontWeight.EXTRA_BOLD, 20));
		login.getChildren().add(signIn);
		
		bot.setImage(bot_image);
		bot.setFitWidth(100);
		bot.setFitHeight(100);

		HBox userBox = new HBox(8);
		userBox.setPadding(new Insets(10, 12, 10, 12));
		userBox.setSpacing(10);
		userBox.setAlignment(Pos.CENTER);
		Label userPrompt = new Label("Username:");
		userPrompt.setTextFill(Paint.valueOf("#540784"));
		userPrompt.setFont(Font.font("Monospaced", FontWeight.EXTRA_BOLD, 15));
		username = new TextField();
		userBox.getChildren().addAll(userPrompt, username);

		HBox passBox = new HBox(8);
		passBox.setPadding(new Insets(10, 12, 10, 12));
		passBox.setSpacing(10);
		passBox.setAlignment(Pos.CENTER);
		Label passPrompt = new Label("Password:");
		passPrompt.setFont(Font.font("Monospaced", FontWeight.EXTRA_BOLD, 15));
		passPrompt.setTextFill(Paint.valueOf("#540784"));
		password = new PasswordField();
		passBox.getChildren().addAll(passPrompt, password);

		login.getChildren().addAll(bot, userBox, passBox);
		root.setCenter(login);

		Text hint = new Text("New User? Sign up for an account below!");
		hint.setFont(Font.font("Monospaced", FontWeight.EXTRA_BOLD, 10));
		hint.setFill(Color.web("#540784"));
		login.getChildren().add(hint);

		root.setBottom(beforeLogin);
	}

	private void unzip(String eventList) {
		String prev = "";
		events.clear();

 		for(int i = 0; i < eventList.length(); i++) {
 			
 			if(eventList.charAt(i) == '/') {
 				prev += " at ";
 			}else if(eventList.charAt(i) == ',' || i == eventList.length()-1) {
 				events.add(prev);
 				prev = "";
 			}else{
 				prev += eventList.charAt(i);
 			}
 		}	
 	}

	private void setSignUpScreen() {
		makeAcct = new VBox(8);
		makeAcct.setPadding(new Insets(15, 6, 15, 6));
		makeAcct.setStyle("-fx-background-color: #fcdb3a;");
		makeAcct.setAlignment(Pos.CENTER);

		Text signIn = new Text("Sign up:");
		signIn.setFill(Color.web("#540784"));
		signIn.setFont(Font.font("Monospaced", FontWeight.EXTRA_BOLD, 20));

		HBox userBox = new HBox(8);
		userBox.setPadding(new Insets(10, 12, 10, 12));
		userBox.setSpacing(10);
		userBox.setAlignment(Pos.CENTER);
		Label userPrompt = new Label("Username:");
		userPrompt.setTextFill(Paint.valueOf("#540784"));
		userPrompt.setFont(Font.font("Monospaced", FontWeight.EXTRA_BOLD, 15));
		setUser = new TextField();
		userBox.getChildren().addAll(userPrompt, setUser);

		HBox passBox = new HBox(8);
		passBox.setPadding(new Insets(10, 12, 10, 12));
		passBox.setSpacing(10);
		passBox.setAlignment(Pos.CENTER);
		Label passPrompt = new Label("Password:");
		passPrompt.setFont(Font.font("Monospaced", FontWeight.EXTRA_BOLD, 15));
		passPrompt.setTextFill(Paint.valueOf("#540784"));
		setPass = new PasswordField();
		passBox.getChildren().addAll(passPrompt, setPass);

		makeAcct.getChildren().addAll(signIn, userBox, passBox);

		root.setCenter(makeAcct);
		root.setBottom(signUpBox);

	}

	private void addMap() {
		WebView webview = new WebView();
		WebEngine engine = webview.getEngine();
		String url = "https://www.google.com/maps";
		engine.load(url);

		root.setCenter(webview);
	}

	private void setScreenAfterLogin() {
		addMap();
		root.setBottom(afterLogin);
	}

	private class ButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {
			Alert wrong = new Alert(AlertType.ERROR);
			if (e.getSource().equals(ok)) {
				if (root.getCenter().equals(login)) {
					if (isUser(username.getText(), password.getText())) {
						userName = username.getText();
						passWord = password.getText();
						setScreenAfterLogin();
					} else {
						wrong.setContentText("Invalid username!");
						wrong.showAndWait();
					}
				} else if (root.getCenter().equals(makeAcct)) {
					setSignInScreen();
				}

			} else if (e.getSource().equals(clear)) {
				if (root.getCenter().equals(login)) {
					username.setText("");
					password.setText("");
				} else if (root.getCenter().equals(makeAcct)) {
					setUser.setText("");
					setPass.setText("");
				}

			} else if (e.getSource().equals(signUp)) {
				if (root.getCenter().equals(login)) {
					setSignUpScreen();
				}
			} else if (e.getSource() == done) {
				if (root.getCenter().equals(makeAcct)) {
					if (!setUser.getText().equals(null) && !setPass.getText().equals(null)) {
						String dbUser = setUser.getText();
						String dbPass = setPass.getText();
						add(dbUser, dbPass, "");
						setSignInScreen();
					} else {
						Alert emptyField = new Alert(AlertType.ERROR);
						emptyField.setContentText("One of the fields is empty!");
						emptyField.show();
					}
				}
			} else if (e.getSource() == combine) {
				eventName = ev.getText();
				location = loc.getText();
				info = eventName + "/" + location + ",";
				eventList += info;
				ev.clear();
				loc.clear();
				addEventsToBase(eventList);
				System.out.println(eventList);
				unzip(eventList);
				System.out.println(events);
				
			} else if(e.getSource().equals(changeInfo)){
				//us pa
				String pas = pa.getText();
				if(pas != null) {
					changePass(pas);
//					Alert finish= new Alert(AlertType.NONE);
//					finish.setContentText("Done! please log out to save changes");
//					finish.showAndWait();
				} else {
					Alert a = new Alert(AlertType.ERROR);
					a.setContentText("Cannot change password to nothing!");
					a.showAndWait();
				}
			}
		}
	}

	private class SelectHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			if (event.getSource() == currEvents) {
				VBox vbox = new VBox();
				vbox.setStyle("-fx-background-color:  #fcdb3a;");
				Label t = new Label("Your current \n events: \n");
				t.setFont(Font.font("Monospaced", FontWeight.EXTRA_BOLD, 25));
				String evs = "";
				HBox box = new HBox();
				box.setStyle("-fx-background-color:  #fcdb3a;");
				if(events.size() > 0) {
					for(int i = 0; i < events.size(); i++) {
						evs = evs + "Event #" + (i+1) + ": " +events.get(i) + "\n";
					}
				}else {
					evs = "You don't currently \n have any events";
				}
				Text thing = new Text(evs);
				thing.setFont(Font.font("Monospaced", FontWeight.EXTRA_BOLD, 15));
				box.setPadding(new Insets(15, 0, 15, 0));
				vbox.setPadding(new Insets(15, 10, 15, 10));
				vbox.setSpacing(15); 
				box.setAlignment(Pos.CENTER);
				box.getChildren().add(thing);
				vbox.getChildren().addAll(t,box);
				root.setCenter(vbox);
			} else if (event.getSource() == newEvents) {				
				HBox box = new HBox();
				VBox vbox = new VBox();
				box.setPadding(new Insets(15, 0, 15, 0));
				vbox.setPadding(new Insets(15, 0, 15, 0));
				vbox.setSpacing(15);
				box.setStyle("-fx-background-color:  #fcdb3a;");
				Label addEvent = new Label("Add a new Event: ");
				addEvent.setFont(Font.font("Monospaced", FontWeight.EXTRA_BOLD, 25));
				ev = new TextField();
				ev.setPromptText("Enter event name");
				loc = new TextField();
				loc.setPromptText("Enter event location");
				combine = new Button("Add Event");
				combine.setAlignment(Pos.BOTTOM_CENTER);
				combine.setOnAction(new ButtonHandler());
				vbox.getChildren().addAll(addEvent, ev, loc, combine);
				box.getChildren().add(vbox);
				box.setAlignment(Pos.BOTTOM_CENTER);
				root.setCenter(box);
			} else if (event.getSource() == myLoc) {
				addMap();
			} else if (event.getSource() == logOut) {
				HBox box = new HBox();
				box.setStyle("-fx-background-color: #224490;");
				root.setCenter(box);
				setSignInScreen();
			} else if (event.getSource() == account) {
				VBox box = new VBox();
				box.setStyle("-fx-background-color:  #fcdb3a;");
				box.setPadding(new Insets(15, 10, 15, 6));
				HBox userBox = new HBox();
				Label uN = new Label("Welcome " + userName + "!");
				uN.setFont(Font.font("Monospaced", FontWeight.EXTRA_BOLD, 25));
				userBox.getChildren().add(uN);

				Label pW = new Label("Password: " + passWord);
				pW.setFont(Font.font("Monospaced", FontWeight.EXTRA_BOLD, 15));
				HBox passBox = new HBox();
				passBox.getChildren().add(pW);

				box.getChildren().addAll(userBox, passBox);
				root.setCenter(box);
			} else if (event.getSource() == about) {
				VBox box = new VBox();
				box.setPadding(new Insets(15, 15, 15, 15));
				box.setSpacing(15);
				Text title = new Text("About EventHandler:");
				title.setFill(Color.web("#540784"));
				title.setFont(Font.font("Monospaced", FontWeight.EXTRA_BOLD, 20));
				Text about = new Text("Our mission is to \nestablish a connection between \nvolunteer and event host \nin an effort to simplify\nvolunteerism."
						+ "\nWe believe that finding \nvolunteering opportunities \nshould be a streamlined \nprocess, easy and efficient \nfor anyone and everyone.");
				about.setFill(Color.web("#540784"));
				about.setFont(Font.font("Monospaced", FontWeight.EXTRA_BOLD, 15));
				bot.setImage(bot_image);
				bot.setFitWidth(100);
				bot.setFitHeight(100);
				box.getChildren().addAll(bot, title, about);
				box.setStyle("-fx-background-color:  #fcdb3a;");
				root.setCenter(box);
			} else if (event.getSource() == pwChange) {
				VBox box = new VBox();
				box.setPadding(new Insets(15, 10, 15, 6));
				box.setSpacing(15);
				box.setAlignment(Pos.CENTER);
				Label p = new Label("Enter new \n password");
				p.setFont(Font.font("Monospaced", FontWeight.EXTRA_BOLD, 25));
				HBox changePass = new HBox();
				changePass.setAlignment(Pos.CENTER);
				pa = new TextField();
				changePass.getChildren().addAll(pa);
				box.setStyle("-fx-background-color:  #fcdb3a;");
				changeInfo = new Button("Change info"); 
				changeInfo.setOnAction(new ButtonHandler());
				box.setPadding(new Insets(10, 10, 10, 10));
				box.getChildren().addAll(p, changePass, changeInfo);
				root.setCenter(box);
				
			}
		}
	}

	private static Connection connect() {
		String url = "jdbc:sqlite:users.db";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	private static void add(String user, String pass, String evts) {
		String insert = "INSERT INTO userBase(username, password, event) VALUES(?, ?, ?)";
		try (Connection conn = connect(); 
				PreparedStatement pstmt = conn.prepareStatement(insert)) {
			pstmt.setString(1, user);
			pstmt.setString(2, pass);
			pstmt.setString(3, evts);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}
	
	private static void addEventsToBase(String evts) {
		String insert = "UPDATE userBase SET event = ? WHERE username = ?";
		try (Connection conn = connect(); 
				PreparedStatement pstmt = conn.prepareStatement(insert)) {
			pstmt.setString(1, evts);
			pstmt.setString(2, userName);
			pstmt.executeUpdate();
			System.out.println("hjvw");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private static void changePass(String pass) {
		String insert = "UPDATE userBase SET password = ? WHERE username = ?";
		try (Connection conn = connect(); 
				PreparedStatement pstmt = conn.prepareStatement(insert)) {
			pstmt.setString(1, pass);
			pstmt.setString(2, userName);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static boolean isUser(String user, String pass) {
		String sql = "SELECT * FROM userBase";

		try (Connection conn = connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				if (rs.getString("username").equals(user) && rs.getString("password").equals(pass)) {
					return true;
				}

			}
			return false;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public static void createNewTable() {
		String url = "jdbc:sqlite:users.db";

		String sql = "CREATE TABLE IF NOT EXISTS userBase (\n"
				+ "username text PRIMARY KEY,\n"
				+ "	password text NOT NULL,\n" 
				+ " event text NOT NULL\n" 
				+ ");";

		try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}






