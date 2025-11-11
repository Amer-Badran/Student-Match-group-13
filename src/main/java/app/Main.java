package app;

import Data_Access.JSONDataobject;

import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder
                .addWelcomeView()
                .addWelcomeUseCase()
                .addSignupView()
                .addSignupUseCase()
                .addLoginView()
                .addLoginUseCase()
                .build();
        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
