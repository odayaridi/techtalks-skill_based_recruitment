package org.example.techtalksskillbasedrecruitment.ai.dto.request;
import java.util.List;
public class Groqrequest {
    private String model;
    private List<Message> messages;

    public Groqrequest() {
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
