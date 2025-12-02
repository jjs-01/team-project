package use_case.login;

import com.studyarc.data_access.InMemoryDataUserDataAccessObject;
import com.studyarc.interface_adapter.login.LoginPresenter;
import com.studyarc.use_case.login.*;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginInteractorTest {
    @Test
    public void testRegisterSuccess() {
        InMemoryDataUserDataAccessObject dao = new InMemoryDataUserDataAccessObject();

        RegisterInputData inputData = new RegisterInputData("toadytop", "changeme", false);
        LoginOutputBoundary presenter = new LoginOutputBoundary() {
            @Override
            public void prepareView(LoginOutputData loginOutputData) {
                fail(); // this should not run.
            }

            @Override
            public void prepareView(RegisterOutputData registerOutputData) {
                assertTrue(registerOutputData.isSuccess());
                assertFalse(registerOutputData.isGoToLogin());
                assertEquals(registerOutputData.getUsername(), "toadytop");
                assertEquals(registerOutputData.getErrorMessage(), "");
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(dao, presenter);
        interactor.register(inputData);
    }
    @Test
    public void testRegisterFailEmptyUsername() {
        InMemoryDataUserDataAccessObject dao = new InMemoryDataUserDataAccessObject();

        RegisterInputData inputData = new RegisterInputData("", "changeme", false);
        LoginOutputBoundary presenter = new LoginOutputBoundary() {
            @Override
            public void prepareView(LoginOutputData loginOutputData) {
                fail(); // this should not run.
            }

            @Override
            public void prepareView(RegisterOutputData registerOutputData) {
                assertFalse(registerOutputData.isSuccess());
                assertFalse(registerOutputData.isGoToLogin());
                assertEquals(registerOutputData.getUsername(), "");
                assertEquals(registerOutputData.getErrorMessage(), "Empty username!");
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(dao, presenter);
        interactor.register(inputData);
    }
    @Test
    public void testRegisterFailExistsUsername() {
        InMemoryDataUserDataAccessObject dao = new InMemoryDataUserDataAccessObject();
        dao.registerUser("toadytop", "changeme");
        RegisterInputData inputData = new RegisterInputData("toadytop", "changeme", false);
        LoginOutputBoundary presenter = new LoginOutputBoundary() {
            @Override
            public void prepareView(LoginOutputData loginOutputData) {
                fail(); // this should not run.
            }

            @Override
            public void prepareView(RegisterOutputData registerOutputData) {
                assertFalse(registerOutputData.isSuccess());
                assertFalse(registerOutputData.isGoToLogin());
                assertEquals(registerOutputData.getUsername(), "toadytop");
                assertEquals(registerOutputData.getErrorMessage(), "User already exists!");
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(dao, presenter);
        interactor.register(inputData);
    }
    @Test
    public void testRegisterGoToLogin() {
        InMemoryDataUserDataAccessObject dao = new InMemoryDataUserDataAccessObject();
        RegisterInputData inputData = new RegisterInputData("toadytop", "changeme", true);
        LoginOutputBoundary presenter = new LoginOutputBoundary() {
            @Override
            public void prepareView(LoginOutputData loginOutputData) {
                fail(); // this should not run.
            }

            @Override
            public void prepareView(RegisterOutputData registerOutputData) {
                assertFalse(registerOutputData.isSuccess());
                assertTrue(registerOutputData.isGoToLogin());
                assertEquals(registerOutputData.getUsername(), "toadytop");
                assertEquals(registerOutputData.getErrorMessage(), "");
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(dao, presenter);
        interactor.register(inputData);
    }
    @Test
    public void testLoginSuccess() {
        InMemoryDataUserDataAccessObject dao = new InMemoryDataUserDataAccessObject();
        dao.registerUser("toadytop", "changeme");
        LoginInputData inputData = new LoginInputData("toadytop", "changeme", false);
        LoginOutputBoundary presenter = new LoginOutputBoundary() {
            @Override
            public void prepareView(LoginOutputData loginOutputData) {
                assertTrue(loginOutputData.isSuccess());
                assertFalse(loginOutputData.isGoToRegister());
                assertEquals(loginOutputData.getUsername(), "toadytop");
            }

            @Override
            public void prepareView(RegisterOutputData registerOutputData) {
                fail(); // this should not run.
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(dao, presenter);
        interactor.login(inputData);
    }
    @Test
    public void testLoginFailWrongPassword() {
        InMemoryDataUserDataAccessObject dao = new InMemoryDataUserDataAccessObject();
        dao.registerUser("toadytop", "changeme2");
        LoginInputData inputData = new LoginInputData("toadytop", "changeme", false);
        LoginOutputBoundary presenter = new LoginOutputBoundary() {
            @Override
            public void prepareView(LoginOutputData loginOutputData) {
                assertFalse(loginOutputData.isSuccess());
                assertFalse(loginOutputData.isGoToRegister());
                assertEquals(loginOutputData.getUsername(), "toadytop");
            }

            @Override
            public void prepareView(RegisterOutputData registerOutputData) {
                fail(); // this should not run.
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(dao, presenter);
        interactor.login(inputData);
    }

    @Test
    public void testLoginFailUserNotExists() {
        InMemoryDataUserDataAccessObject dao = new InMemoryDataUserDataAccessObject();
        dao.registerUser("toadytop", "changeme2");
        LoginInputData inputData = new LoginInputData("toadytop2", "changeme", false);
        LoginOutputBoundary presenter = new LoginOutputBoundary() {
            @Override
            public void prepareView(LoginOutputData loginOutputData) {
                assertFalse(loginOutputData.isSuccess());
                assertFalse(loginOutputData.isGoToRegister());
                assertEquals(loginOutputData.getUsername(), "toadytop2");
            }

            @Override
            public void prepareView(RegisterOutputData registerOutputData) {
                fail(); // this should not run.
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(dao, presenter);
        interactor.login(inputData);
    }
    @Test
    public void testLoginGoToRegister() {
        InMemoryDataUserDataAccessObject dao = new InMemoryDataUserDataAccessObject();
        dao.registerUser("toadytop", "changeme");
        LoginInputData inputData = new LoginInputData("toadytop", "changeme", true);
        LoginOutputBoundary presenter = new LoginOutputBoundary() {
            @Override
            public void prepareView(LoginOutputData loginOutputData) {
                assertFalse(loginOutputData.isSuccess());
                assertTrue(loginOutputData.isGoToRegister());
                assertEquals(loginOutputData.getUsername(), "toadytop");
            }

            @Override
            public void prepareView(RegisterOutputData registerOutputData) {
                fail(); // this should not run.
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(dao, presenter);
        interactor.login(inputData);
    }
}
