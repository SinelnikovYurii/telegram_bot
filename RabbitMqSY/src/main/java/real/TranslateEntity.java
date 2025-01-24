package real;


public class TranslateEntity {

    private String id;

    private MethodOfProcessing methodOfProcessing;

    public TranslateEntity(String id, MethodOfProcessing methodOfProcessing) {
        this.id = id;
        this.methodOfProcessing = methodOfProcessing;
    }

    public TranslateEntity() {
        this.id = "0";
        this.methodOfProcessing = MethodOfProcessing.HOMEWORKANALIZE;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MethodOfProcessing getMethodOfProcessing() {
        return methodOfProcessing;
    }

    public void setMethodOfProcessing(MethodOfProcessing methodOfProcessing) {
        this.methodOfProcessing = methodOfProcessing;
    }


    @Override
    public String toString() {
        return "TranslateEntity [id=" + id + ", methodOfProcessing=" + methodOfProcessing + "]";
    }
}
