package xatu.school.bean;


//评教
public class EvaluateBean {
    private int id;
    private String content;

    public EvaluateBean(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
