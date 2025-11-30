package app;

import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder
                .addWelcomeView()
                .addWelcomeUseCase()
                .addSignupView()
                .addSignupUseCase()
                .addLoginView()
                .addLoginUseCase()
                .addProfileView()
                .addProfileUseCase()
                .addEnterInfoView()
                .addEnterInfoUseCase()
                .addDashboardView()
                .addDashboardUseCase()
                .build();
        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
