package controllers;

import java.net.URL;

import org.controlsfx.control.Notifications;

import classes.Usuarios;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import utils.DAOHibernate;

public class loginController {

	@FXML
	private TextField userLogin;

	@FXML
	private PasswordField passwordLogin;

	@FXML
	private Button btnLogin;

	@FXML
	private Button btnCadastrar;

	private DAOHibernate<Usuarios> createCon() {

		DAOHibernate<Usuarios> dao = new DAOHibernate<>();
		return dao;
	}

	@FXML
	private void login() throws Exception {

		DAOHibernate<Usuarios> dao = createCon();

		String usuario = userLogin.getText();

		String senha = passwordLogin.getText();

		Usuarios query = dao.getFirst("loginCheck", "usuario", usuario, "senha", senha);

		if (query == null) {
			Notifications.create().title("Alerta de Login").text("Usuario ou senha incorreto").showWarning();

			
		} else {
			Notifications.create().title("Alerta de Login").text("Login bem sucedido").showConfirm();

			URL fxmlMainMenu = getClass().getResource("/fxml/MenuPrincipal.fxml");
			
			System.out.println("logged in");

			FXMLLoader loader = new FXMLLoader(fxmlMainMenu);

			Parent mainMenuP = loader.load();

			Stage mainMenu = new Stage();
			
			Scene mainMenuScene = new Scene(mainMenuP);
			
			mainMenu.setScene(mainMenuScene);
			
			
			Stage window = (Stage) btnLogin.getScene().getWindow();
			
			window.close();
			mainMenu.show();
			
			mainMenuController mainMenuController = loader.getController();
			
			mainMenuController.setUserLogin(query);
			
			
		}
	}

	@FXML
	private void cadastrar() throws Exception {

		URL fxmlCadastro = getClass().getResource("/fxml/CadastroDeEmpresa.fxml");

		GridPane cadastroEmpresa = FXMLLoader.load(fxmlCadastro);

		Stage window = (Stage) btnCadastrar.getScene().getWindow();

		window.setScene(new Scene(cadastroEmpresa));
	}

}
