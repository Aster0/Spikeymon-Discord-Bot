package management;

public class Validator {


    public static boolean isValidEmail(String email)
    {


        if(email.split("@")[0].length() == 8 && email.contains("student.tp.edu.sg"))
        {

            return true;
        }

        return false;
    }
}
