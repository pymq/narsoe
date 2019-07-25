package win.grishanya.narsoe.network;

public class PhoneNumberHandler {

    public String validatePhoneNumber(String phoneNumber){
        return handlePhoneNumber(phoneNumber,new char[]{'8'},"7");
    }

    public String prettifyPhoneNumber(String phoneNumber){
        return handlePhoneNumber(phoneNumber,new char[]{'8','7'},"+7");
    }

    private String handlePhoneNumber(String phoneNumber,char [] firstSymbolToRemove,String firstSymbolToPaste){
        if (!phoneNumber.substring(0,firstSymbolToPaste.length()).equals(firstSymbolToPaste)) {
            if (!Character.isDigit(phoneNumber.charAt(0))) {
                phoneNumber = phoneNumber.substring(1);
                phoneNumber = validatePhoneNumber(phoneNumber);
            }
            for (char symbol:firstSymbolToRemove) {
                if (phoneNumber.charAt(0) == symbol) {
                    phoneNumber = firstSymbolToPaste + phoneNumber.substring(1);
                    break;
                }
            }

        }
        return phoneNumber;
    }

    private static String removeCharAt(String s, int pos) {
        return s.substring(0, pos) + s.substring(pos + 1); // Возвращаем подстроку s, которая начиная с нулевой позиции переданной строки (0) и заканчивается позицией символа (pos), который мы хотим удалить, соединенную с другой подстрокой s, которая начинается со следующей позиции после позиции символа (pos + 1), который мы удаляем, и заканчивается последней позицией переданной строки.
    }
}
