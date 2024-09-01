package fornari.nucleo.helper;

public class Generator {
    public static String  generatePassword() {
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
        String password = "";
        while (password.length() < 6) {
            password += chars[(int) (Math.random() * chars.length)];
        }

        return password;
    }
}
