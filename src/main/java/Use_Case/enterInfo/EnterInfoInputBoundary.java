package Use_Case.enterInfo;

import java.io.IOException;
import java.text.ParseException;

public interface EnterInfoInputBoundary {
    void execute(EnterInfoInputData inputData) throws IOException, ParseException, org.json.simple.parser.ParseException;
}
