package Use_Case.signup;

// this class will have 3 methods :
// 1 ) A constructor method that will be used at the very end by the App builder
// 2 ) An execute method that will be called by the Controller
// 3 ) A view switching method ( from signup window to login window )
//     -that will be called by the Controller ( Why do we need it here then ?! )

import Entity.Client;
import Entity.ClientFactory;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class SignupInteractor implements SignupInputBoundary {
    private SignupDataAcessObject DAO;
    private SignupOutputBoundary presenter;
    private ClientFactory clientFactory;

    public SignupInteractor(SignupDataAcessObject DAO, SignupOutputBoundary presenter, ClientFactory clientFactory) {
        this.DAO = DAO;
        this.presenter = presenter;
        this.clientFactory = clientFactory;
    }


    @Override
    public void execute(SignupInputData input) throws IOException, ParseException {
        String name= input.getUserName();
        String pass = input.getPassWord();
        if(DAO.alreadyExists(name)){
            presenter.prepareFailView("This User name is taken !");}
        else if(pass.length() < 4){
             presenter.prepareFailView("The password is not strong enough!");}
        else{
            Client theClient = clientFactory.create(name,pass);
            DAO.save(theClient);
            ArrayList<String> things = DAO.getClasses(name);
            presenter.prepareSuccessView();
        }
        }





    @Override
    public void switchToLoginView() {

    }
}