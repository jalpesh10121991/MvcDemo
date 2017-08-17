package in.mvcdemo.Utills;


public class Validator {


    public static boolean validateString(String object) {
        boolean flag = false;
        if (object != null && object.equalsIgnoreCase("false") != true &&
                object.equalsIgnoreCase("null") != true &&
                object.trim().length() > 0 &&
                object.equalsIgnoreCase("NV") != true) {
            flag = true;
        }
        return flag;
    }


}
