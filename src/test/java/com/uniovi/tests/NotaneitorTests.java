package com.uniovi.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_NavView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.util.SeleniumUtils;

//Ordenamos las pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING)


public class NotaneitorTests {
	//En Windows (Debe ser la versión 65.0.1 y desactivar las actualizacioens automáticas)):
	static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	static String Geckdriver022 = "G:\\UNI\\3. TERCERO\\SDI\\prac5\\PL-SDI-Sesión5-material\\geckodriver024win64.exe";
	//En MACOSX (Debe ser la versión 65.0.1 y desactivar las actualizacioens automáticas):
	//static String PathFirefox65 = "/Applications/Firefox.app/Contents/MacOS/firefox-bin";
	//static String Geckdriver024 = "/Users/delacal/selenium/geckodriver024mac";
	//Común a Windows y a MACOSX
	static WebDriver driver = getDriver(PathFirefox65, Geckdriver022);
	static String URL = "http://localhost:8090";
	
	
	public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckdriver);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}


	//Antes de cada prueba se navega al URL home de la aplicaciónn
	@Before
	public void setUp(){
		driver.navigate().to(URL);
	}
	//Después de cada prueba se borran las cookies del navegador
	@After
	public void tearDown(){
		driver.manage().deleteAllCookies();
	}
	//Antes de la primera prueba
	@BeforeClass
	static public void begin() {
	}
	//Al finalizar la última prueba
	@AfterClass
	static public void end() {
		//Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}

//	@Test
//	public void test() {
//		//Cambiamos el idioma a Inglés
//		PO_HomeView.changeIdiom(driver, "btnEnglish");
//		//Esperamos porque aparezca que aparezca el texto de bienvenida en inglés
//		SeleniumUtils.EsperaCargaPagina(driver, "text", p.getString("welcome.message",
//				PO_Properties.ENGLISH), getTimeout());
//	}
	
	
	//PR01. Acceder a la página principal /
	@Test
	public void PR01() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
	}

	//PR02. OPción de navegación. Pinchar en el enlace Registro en la página home
	@Test
	public void PR02() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
	}

	//PR03. OPción de navegación. Pinchar en el enlace Identificate en la página home
	@Test
	public void PR03() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
	}

	//PR04. OPción de navegación. Cambio de idioma de Español a Ingles y vuelta a Español
	@Test
	public void PR04() {
		PO_HomeView.checkChangeIdiom(driver, "btnSpanish", "btnEnglish",
				PO_Properties.getSPANISH(), PO_Properties.getENGLISH());
		//SeleniumUtils.esperarSegundos(driver, 2);
	}

	//PR05. Prueba del formulario de registro. registro con datos correctos
	@Test
	public void PR05() {
		//Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		//Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "77777778L", "Josefo", "Perez", "77777",
				"77777");
		//Comprobamos que entramos en la sección privada
		PO_View.checkElement(driver, "text", "Notas del usuario");
	}

	
	//PR06. Prueba del formulario de registro. DNI repetido en la BD, Nombre corto, .... pagination
	//		pagination-centered, Error.signup.dni.length
	@Test
	public void PR06() {
		//Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		//Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "99999990A", "Josefo", "Perez", "77777",
				"77777");
		PO_View.getP();
		//COmprobamos el error de DNI repetido.
		PO_RegisterView.checkKey(driver, "Error.signup.dni.duplicate",
				PO_Properties.getSPANISH() );
		//Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "99999990B", "Jose", "Perez", "77777",
				"77777");
		//COmprobamos el error de Nombre corto .
		PO_RegisterView.checkKey(driver, "Error.signup.name.length",
				PO_Properties.getSPANISH() );
		//Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "99999990B", "Josefo", "Per", "77777",
				"77777");
	}

	//PR07. Loguearse con exito desde el ROl de Usuario, 99999990D, 123456
	@Test
	public void PR07() {
		//Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		//Rellenamos el formulario
		PO_LoginView.fillForm(driver, "99999990A" , "123456" );
		//COmprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Notas del usuario");
	}
	
	
	//PR08. Loguearse con exito desde el  ROL profesor ( 99999993D/123456)
	@Test
	public void PR08() {
		//Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		//Rellenamos el formulario
		PO_LoginView.fillForm(driver, " 99999993D" , "123456" );
		//Probamos a acceder a la vista Agregar nota
		//PO_View.checkElement(driver, "text", "Notas del usuario");
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'marks-menu')]/a");
		elementos.get(0).click();
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'mark/add')]");
		elementos.get(0).click();
		SeleniumUtils.esperarSegundos(driver, 2);
	}
	
	//PR09. Loguearse con exito desde el  ROL Administrador (99999988F/123456)
	@Test
	public void PR09() {
		//Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		//Rellenamos el formulario
		PO_LoginView.fillForm(driver, " 99999988F" , "123456" );
		//Probamos a acceder a la vista Ver usuarios
		//PO_View.checkElement(driver, "text", "Notas del usuario");
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'users-menu')]/a");
		elementos.get(0).click();
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/list')]");
		elementos.get(0).click();
		SeleniumUtils.esperarSegundos(driver, 2);
	}
	
	//PR10. Loguearse sin exito desde el ROl alumno (99999990A/123456).
	@Test
	public void PR10() {
		//Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		//Rellenamos el formulario
		PO_LoginView.fillForm(driver, "99999990" , "123456" );
		//COmprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Identifícate");
		//SeleniumUtils.esperarSegundos(driver, 2);
	}
	
	//PR11. Loguearse con exito desde el ROl de Usuario (99999990A/123456) y desconectarse
	@Test
	public void PR11() {
		//Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		//Rellenamos el formulario
		PO_LoginView.fillForm(driver, "99999990A" , "123456" );
		//COmprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Notas del usuario");
		// Nos desconectamos.
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
		SeleniumUtils.esperarSegundos(driver, 2);
	}

	
	
	
	
	
}