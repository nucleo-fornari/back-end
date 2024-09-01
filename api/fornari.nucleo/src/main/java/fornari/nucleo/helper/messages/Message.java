package fornari.nucleo.helper.messages;

public class Message {
    public String text;

    public Message(String text) {
        this.text = text;
    }

    public String geMessage() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
